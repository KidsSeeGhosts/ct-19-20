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
    private Stack<Register> usedRegs = new Stack<Register>();

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





    private PrintWriter writer; // use this writer to output the assembly instructions


    public void emitProgram(Program program, File outputFile) throws FileNotFoundException {
        writer = new PrintWriter(outputFile);

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
	            stmt.accept(this);
	        }
        return null;
    }

    @Override
    public Register visitFunDecl(FunDecl fd) {
        // TODO: to complete
    		//System.out.println("inside fun decl");
    		writer.println("	"+fd.name+":");//functino name is a label
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
    		writer.println();
	    	for (StructTypeDecl std : p.structTypeDecls) {
	    		Register stdReg = std.accept(this);
        }
        for (VarDecl vd : p.varDecls) {
            Register vdReg = vd.accept(this);
        }
        writer.println(".text");//.text then functions in mips
        writer.println();
        for (FunDecl fd : p.funDecls) {
            Register fdReg = fd.accept(this);
            //writer.println("visit fd");
        }
	    writer.println("li $v0, 10");//this is how you end in a program in MIPS
	    	writer.println("syscall");
    	
    	
        //don't think I need to free up registers at the the end of the program
        return result;
    }

    @Override
    public Register visitVarDecl(VarDecl vd) {
        // TODO: to complete
    		Register varReg = getRegister();
    		usedRegs.push(varReg);//pushing it onto my stack
        return null;
    }

    

//loadI @x → r1  load the address of x into r1
//loadA r1 → r2  now value of x is in r2
    @Override
    public Register visitVarExpr(VarExpr v) {
	    	Register addressReg = getRegister ();
	    	Register result = getRegister ();
	    	Register varAddress = v.vd.accept(this);
	    	writer.println("li, " +addressReg.toString() +", "+ varAddress);
	    	writer.println("la"+ ", "+result.toString()+", "+addressReg.toString());
	    	freeRegister ( addressReg ) ;
	    	return result ;
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
		// TODO Auto-generated method stub
		return null;
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
			writer.println("li $v0, 1");
			writer.println("move $a0, "+numberRegister);
			writer.println("syscall");
		}
		return null;
	}

	@Override
	public Register visitBinOp(BinOp binOp) {
		//System.out.println("in bin op");
		Register lhsReg = binOp.lhs.accept(this);
		Register rhsReg = binOp.rhs.accept(this); 
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
				writer.println("beq " +lhsReg+", "+1+", LHStrue");
				writer.println("li "+result+", 0");
				writer.println("j AfterAND");
				writer.println("LHStrue: ");
				writer.println("beq " +rhsReg+", "+1+", RHStrue");
				writer.println("li "+result+", 0");
				writer.println("j AfterAND");
				writer.println("RHStrue: ");
				writer.println("li "+result+", 1");
				writer.println("AfterAND: ");
				break;
			case DIV:
				writer.println("div "+result+", "+lhsReg+", "+rhsReg); 
				break;
			case EQ:
				writer.println("beq " +lhsReg+", "+rhsReg+", EqualTo");
				writer.println("li "+result+", 0");
				writer.println("j AfterEqualTo");
				writer.println("EqualTo: ");
				writer.println("li "+result+", 1");
				writer.println("AfterEqualTo:");
				break;
			case GE:
				writer.println("slt "+result+", "+rhsReg+", "+lhsReg); //returns 1 if left is greater than right 15>3
				writer.println("beq "+ result+", 1, GreaterThanOrEqualTo  ");   //if $s0 > $s1, goes to label1
				writer.println("beq " +lhsReg+", "+rhsReg+", GreaterThanOrEqualTo"); //  if equal goes to label 1
				writer.println("j AfterGreaterThanOrEqualTo");
				writer.println("GreaterThanOrEqualTo: ");
				writer.println("li "+result+", 1");
				writer.println("AfterGreaterThanOrEqualTo: ");
				break;
			case GT:
				writer.println("slt "+result+", "+rhsReg+", "+lhsReg); //returns 1 if left is greater than right 15>3
				break;
			case LE:
				writer.println("slt "+result+", "+lhsReg+", "+rhsReg); //returns 1 if left is greater than right 15>3
				writer.println("beq "+ result+", 1, LessThanOrEqualTo  ");   //if $s0 > $s1, goes to label1
				writer.println("beq " +lhsReg+", "+rhsReg+", LessThanOrEqualTo"); //  if equal goes to label 1
				writer.println("j AfterLessThanOrEqualTo");
				writer.println("LessThanOrEqualTo: ");
				writer.println("li "+result+", 1");
				writer.println("AfterLessThanOrEqualTo: ");
				break;
			case LT:
				writer.println("slt "+result+", "+lhsReg+", "+rhsReg);
				break;
			case MOD:
				writer.println("rem "+result+", "+lhsReg+", "+rhsReg);
				break;
			case NE:
				writer.println("bne " +lhsReg+", "+rhsReg+", NotEqualTo");
				writer.println("li "+result+", 0");
				writer.println("j AfterNotEqualTo");
				writer.println("NotEqualTo: ");
				writer.println("li "+result+", 1");
				writer.println("AfterNotEqualTo:");
				break;
			case OR:
				writer.println("beq "+lhsReg+", 1 TrueOR");
				writer.println("beq "+rhsReg+",1 TrueOR");
				writer.println("li "+result+", 0");
				writer.println("j AfterOR");
				writer.println("TrueOR: ");
				writer.println("li "+result+", 1");
				writer.println("AfterOR: ");
				break;
			case SUB:
				writer.println("sub "+result+", "+lhsReg+", "+rhsReg);
				break;
			default:
				break;
		} 
		freeRegister (lhsReg );
		freeRegister (rhsReg ); 
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register visitWhile(While myWhile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register visitIf(If myIf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register visitAssign(Assign assign) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Register visitExprStmt(ExprStmt exprStmt) {
		exprStmt.expr.accept(this);
		return null;
	}

	@Override
	public Register visitReturn(Return myReturn) {
		// TODO Auto-generated method stub
		return null;
	}
}
