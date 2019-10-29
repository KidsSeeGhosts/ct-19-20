package gen;

import ast.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.EmptyStackException;
import java.util.Stack;

public class CodeGenerator implements ASTVisitor<Register> {

    /*
     * Simple register allocator.
     */

    // contains all the free temporary registers
    private Stack<Register> freeRegs = new Stack<Register>();
    //private Stack<Register> usedRegs = new Stack<Register>();
    private int stackOffset = 0;

    public CodeGenerator() {
        freeRegs.addAll(Register.tmpRegs);
    }

    private class RegisterAllocationError extends Error {}

    private Register getRegister() {
        try {
            return freeRegs.pop();
        } catch (EmptyStackException ese) {
            throw new RegisterAllocationError(); // no more free registers, bad luck!
        }
    }

    private void freeRegister(Register reg) {
        freeRegs.push(reg);
    }

    private int binOpLabelCounter=1;
    private int myifCounter=1;
    private int mywhileCounter=1;
	private String currentFunctionName;



    private PrintWriter writer; // use this writer to output the assembly instructions


    public void emitProgram(Program program, File outputFile) throws FileNotFoundException {
        writer = new PrintWriter(outputFile);
        //Before doing visit program, here I should do DataVisitor
        visitProgram(program);
        //System.out.println("reached writer.close");
        writer.close();
    }

    @Override
    public Register visitBaseType(BaseType bt) {
        return null;
    }

    @Override
    public Register visitStructTypeDecl(StructTypeDecl st) {
        return null;
    }

    @Override
    public Register visitBlock(Block b) {
	    	for (VarDecl vd : b.varDecls) {
	            vd.accept(this);
	        }
	    	//System.out.println("finished var decls in block");
	        for (Stmt stmt : b.stmts) {
	        		if(stmt instanceof Return) {
	        			stmt.accept(this);
	        			break;
	        		}
	            stmt.accept(this);
	        }
        return null;
    }

    @Override
    public Register visitFunDecl(FunDecl fd) {
        // TODO: to complete
    		//System.out.println("inside fun decl");
    		writer.println("	"+fd.name+":");//function name is a label
        fd.type.accept(this);//will return a register'
        if (!fd.vardecls.isEmpty()){
	        	for (VarDecl vd : fd.vardecls) {
	                vd.accept(this);
	            }
	            fd.block.accept(this);
	            return null;
        }
        //System.out.println("about to do block");
        fd.block.accept(this);
        
        return null;
    }

    @Override
    public Register visitProgram(Program p) {
    		Register result = getRegister();
    		writer.println(".data");//this is where we put struct type decls and var decls
    		new DataVisitor(writer,p);//this is where I put all the strings in the data section first
    		writer.println();
	    	for (StructTypeDecl std : p.structTypeDecls) {
	    		Register stdReg = std.accept(this);
        }
        for (VarDecl vd : p.varDecls) {//defining global variables
        		vd.localOrGlobal="global";
        		if(vd.type instanceof BaseType){
        			BaseType mybt = (BaseType) vd.type;
        			if(mybt.equals(BaseType.INT)) {
            			writer.println(vd.varName+": .word");
        			}
        			if(mybt.equals(BaseType.CHAR)) {
            			writer.println(vd.varName+": .byte 1");
        			}
        		}
        }
        writer.println("");
        writer.println(".text");//.text then functions in mips
        writer.println();
        for (FunDecl fd : p.funDecls) {
    			currentFunctionName= fd.name;
            Register fdReg = fd.accept(this);
            writer.println(fd.name+"End:");
            writer.print("");
        }
	    writer.println("li $v0, 10");//this is how you end in a program in MIPS
	    	writer.println("syscall");
    	
    	
        //don't think I need to free up registers at the the end of the program
        return result;
    }

	@Override
    public Register visitVarDecl(VarDecl vd) {
		vd.localOrGlobal="local";
    		if(vd.type==BaseType.INT) {
    			vd.stackOffset=stackOffset;
    			stackOffset=stackOffset-4;//4 bytes for an int word
    			vd.stackOffSetWordSize = 4;
    		}
    		if (vd.type instanceof PointerType) {
    			vd.stackOffset=stackOffset;
    			stackOffset=stackOffset-4;//4 bytes for an int word
    			vd.stackOffSetWordSize = 4;
    		}
    		//Register varReg = getRegister();
    		//usedRegs.push(varReg);//pushing it onto my stack
        return null;
    }

    

//loadI @x → r1  load the address of x into r1
//loadA r1 → r2  now value of x is in r2
    @Override
    public Register visitVarExpr(VarExpr v) {//this should return a register with the variable's location in memory
    		if (v.vd.localOrGlobal.equals("local")) {
		    	Register result = getRegister ();
		    	writer.println("la "+result+", "+((-stackOffset)-v.vd.stackOffset-v.vd.stackOffSetWordSize)+"($sp)");
		    	return result;
    		}
    		if (v.vd.localOrGlobal.equals("global")) {
    			Register result = getRegister ();
    			writer.println("la "+result+", "+v.name);
		    	return result ;
    		}
	    	return null ;
    }

	@Override
	public Register visitPointerType(PointerType pt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register visitStructType(StructType structType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register visitArrayType(ArrayType arrayType) {
		// TODO Auto-generated method stub
		return null;
	}

	//loadI 3 →r1 is what should be printed
	@Override
	public Register visitIntLiteral(IntLiteral intLiteral) {
		//System.out.println("in visit int literal");
		Register result = getRegister (); 
		//System.out.println(result);
		writer.println("li "+ result.toString()+" "+intLiteral.i); 
		return result ;
	}

	@Override
	public Register visitStrLiteral(StrLiteral strLiteral) {
		Register stringReg = getRegister();
		writer.println("la "+stringReg+", "+strLiteral.label);
		return stringReg;
	}

	@Override
	public Register visitChrLiteral(ChrLiteral chrLiteral) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register visitFunCallExpr(FunCallExpr funCallExpr) {
		//System.out.println("inside fun call expr");
		if(funCallExpr.string.equals("print_i")) {
			Register numberRegister = funCallExpr.expressions.get(0).accept(this);
			if(funCallExpr.expressions.get(0) instanceof VarExpr){
				writer.println("lw "+numberRegister+", ("+numberRegister+")");//if it's a variable expression it will be an address
			}
			writer.println("li $v0, 1");
			writer.println("move $a0, "+numberRegister);
			writer.println("syscall");
		}
		if(funCallExpr.string.equals("print_s")) {
			Register stringRegister = funCallExpr.expressions.get(0).accept(this);
			if(funCallExpr.expressions.get(0) instanceof VarExpr){
				writer.println("lw "+stringRegister+", ("+stringRegister+")");//if it's a variable expression it will be an address
			}
			writer.println("li $v0, 4");
			writer.println("move $a0, "+stringRegister);
			writer.println("syscall");

		}
		return null;
	}

	@Override
	public Register visitBinOp(BinOp binOp) {
		//System.out.println("in bin op");
		Register lhsReg = binOp.lhs.accept(this);
		if(binOp.lhs instanceof VarExpr){
			writer.println("lw "+lhsReg+", ("+lhsReg+")");//if it's a variable expression it will be an address
		}
		Register rhsReg = binOp.rhs.accept(this); 
		if(binOp.rhs instanceof VarExpr){
			writer.println("lw "+rhsReg+", ("+rhsReg+")");//if it's a variable expression it will be an address
		}
		Register result = getRegister();
		switch(binOp.op) {
		//add $s0, $s1, $s2    s0 = g + h   where f = g+h where f is s0, g is s1 and h is s2
			case ADD:
				writer.println("add "+result+", "+lhsReg+", "+rhsReg);
				break ;
			case MUL:
				writer.println("mul "+result+", "+lhsReg+", "+rhsReg); 
				break ;
			case AND:
				writer.println("beq " +lhsReg+", "+1+", LHStrue"+binOpLabelCounter);
				writer.println("li "+result+", 0");
				writer.println("j AfterAND"+binOpLabelCounter);
				writer.println("LHStrue"+binOpLabelCounter+": ");
				writer.println("beq " +rhsReg+", "+1+", RHStrue"+binOpLabelCounter);
				writer.println("li "+result+", 0");
				writer.println("j AfterAND"+binOpLabelCounter);
				writer.println("RHStrue"+binOpLabelCounter+": ");
				writer.println("li "+result+", 1");
				writer.println("AfterAND"+binOpLabelCounter+": ");
				break;
			case DIV:
				writer.println("div "+lhsReg+", "+rhsReg); 
				writer.println("mflo "+result);//gets the integer quotient
				break;
			case EQ:
				writer.println("beq " +lhsReg+", "+rhsReg+", EqualTo"+binOpLabelCounter);
				writer.println("li "+result+", 0");
				writer.println("j AfterEqualTo"+binOpLabelCounter);
				writer.println("EqualTo"+binOpLabelCounter+": ");
				writer.println("li "+result+", 1");
				writer.println("AfterEqualTo"+binOpLabelCounter+":");
				break;
			case GE:
				writer.println("slt "+result+", "+rhsReg+", "+lhsReg); //returns 1 if left is greater than right 15>3
				writer.println("beq "+ result+", 1, GreaterThanOrEqualTo"+binOpLabelCounter);   //if $s0 > $s1, goes to label1
				writer.println("beq " +lhsReg+", "+rhsReg+", GreaterThanOrEqualTo"+binOpLabelCounter); //  if equal goes to label 1
				writer.println("j AfterGreaterThanOrEqualTo"+binOpLabelCounter);
				writer.println("GreaterThanOrEqualTo"+binOpLabelCounter+": ");
				writer.println("li "+result+", 1");
				writer.println("AfterGreaterThanOrEqualTo"+binOpLabelCounter+": ");
				break;
			case GT:
				writer.println("slt "+result+", "+rhsReg+", "+lhsReg); //returns 1 if left is greater than right 15>3
				break;
			case LE:
				writer.println("slt "+result+", "+lhsReg+", "+rhsReg); //returns 1 if left is greater than right 15>3
				writer.println("beq "+ result+", 1, LessThanOrEqualTo"+binOpLabelCounter);   //if $s0 > $s1, goes to label1
				writer.println("beq " +lhsReg+", "+rhsReg+", LessThanOrEqualTo"+binOpLabelCounter); //  if equal goes to label 1
				writer.println("j AfterLessThanOrEqualTo"+binOpLabelCounter);
				writer.println("LessThanOrEqualTo"+binOpLabelCounter+": ");
				writer.println("li "+result+", 1");
				writer.println("AfterLessThanOrEqualTo"+binOpLabelCounter+": ");
				break;
			case LT:
				writer.println("slt "+result+", "+lhsReg+", "+rhsReg);
				break;
			case MOD:
				writer.println("div "+lhsReg+", "+rhsReg); 
				writer.println("mfhi "+result);//gets the reaminder
				break;
			case NE:
				writer.println("bne " +lhsReg+", "+rhsReg+", NotEqualTo"+binOpLabelCounter);
				writer.println("li "+result+", 0");
				writer.println("j AfterNotEqualTo"+binOpLabelCounter);
				writer.println("NotEqualTo"+binOpLabelCounter+": ");
				writer.println("li "+result+", 1");
				writer.println("AfterNotEqualTo"+binOpLabelCounter+":");
				break;
			case OR:
				writer.println("beq "+lhsReg+", 1 TrueOR"+binOpLabelCounter);
				writer.println("beq "+rhsReg+",1 TrueOR"+binOpLabelCounter);
				writer.println("li "+result+", 0");
				writer.println("j AfterOR"+binOpLabelCounter);
				writer.println("TrueOR"+binOpLabelCounter+": ");
				writer.println("li "+result+", 1");
				writer.println("AfterOR"+binOpLabelCounter+": ");
				break;
			case SUB:
				writer.println("sub "+result+", "+lhsReg+", "+rhsReg);
				break;
			default:
				break;
		} 
		freeRegister (lhsReg );
		freeRegister (rhsReg ); 
		binOpLabelCounter++;
		return result ;
	}

	@Override
	public Register visitArrayAccessExpr(ArrayAccessExpr arrayAccessExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register visitFieldAccessExpr(FieldAccessExpr fieldAccessExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register visitValueAtExpr(ValueAtExpr valueAtExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register visitSizeOfExpr(SizeOfExpr sizeOfExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register visitTypeCastExpr(TypeCastExpr typeCastExpr) {
		typeCastExpr.type.accept(this);
		Register exprReg = typeCastExpr.expr.accept(this);
		return exprReg;
	}

	@Override
	public Register visitWhile(While myWhile) {
		Register expression = myWhile.expr.accept(this);
		Register oneReg = getRegister();
		writer.println("li "+oneReg+", 1");
		writer.println("MyWhile"+mywhileCounter+": bne "+expression+", "+oneReg+", AfterWhile"+mywhileCounter);
		mywhileCounter++;
		myWhile.stmt.accept(this);
		Register check = myWhile.expr.accept(this);
		writer.println("move "+expression+", "+check);
		writer.println("j MyWhile"+(mywhileCounter-1));
		writer.println("AfterWhile"+(mywhileCounter-1)+": ");
		freeRegister(oneReg);
		freeRegister(expression);
		return null;
	}

	@Override
	public Register visitIf(If myIf) {
		Register expression = myIf.expr.accept(this);
		Register oneReg = getRegister();
		writer.println("li "+oneReg+", 1");
		writer.println("bne "+expression+", "+oneReg+", AfterIf"+myifCounter);
		myIf.stmt.accept(this);
		writer.println("AfterIf"+myifCounter+": ");
		myifCounter++;
		if (myIf.optStmt!=null){
			myIf.optStmt.accept(this);
		}
		freeRegister(oneReg);
		freeRegister(expression);
		return null;
	}

	@Override
	public Register visitAssign(Assign assign) {
		Register lhs = assign.expr1.accept(this);
		Register rhs = assign.expr2.accept(this);
		writer.println("sw "+rhs+", ("+lhs+")"); //put 6 into y
		return lhs;
	}

	@Override
	public Register visitExprStmt(ExprStmt exprStmt) {
		exprStmt.expr.accept(this);
		return null;
	}

	@Override
	public Register visitReturn(Return myReturn) {
		if (myReturn.optExpr!=null) {
			Register optexpr = myReturn.optExpr.accept(this);
			writer.println("j "+currentFunctionName+"End");
			return optexpr;
		}
		writer.println("j "+currentFunctionName+"End");
		return null;
	}
}
