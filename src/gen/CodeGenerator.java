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
    private int orCounter=1;
    private int andCounter=1;
    private int myifCounter=1;
    private int mywhileCounter=1;
	private String currentFunctionName;
	private Register resultReg;//this will be t9
	private int frameOffset =0;
    private int stackoffset = 0;
    private int gpoffset = 0;



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
	    			//System.out.println("visiting var decl in block");
	            vd.accept(this);
	        }
	        for (Stmt stmt : b.stmts) {
//	        		if(stmt instanceof Return) {
//	        			stmt.accept(this);
//	        			break;
//	        		}
	            stmt.accept(this);
	        }
        return null;
    }

    @Override
    public Register visitFunDecl(FunDecl fd) {
    	writer.println("	"+fd.name+":");

		frameOffset=0;
		writer.println("move $fp $sp");
    	if (!(fd.name.equals("main"))){
    	}
    	        fd.type.accept(this);
    	        if (!fd.vardecls.isEmpty()){
    		        	for (VarDecl vd : fd.vardecls) {
    		                vd.accept(this);
    		            }
    	        }
    	        fd.block.accept(this);
    	        writer.println(fd.name+"End:");
    	        fd.finalframeoffset=frameOffset;
    	        writer.println("move $sp, $fp");//pop all variables off the sp
    	        if (!(fd.name.equals("main"))){
    	        	//Register raRetrieving = getRegister(); 
            		//writer.println("lw "+raRetrieving+", "+0+"($fp)");
            		//writer.println("move $ra, "+raRetrieving);
            		writer.println("jr $ra");
            		//freeRegister(raRetrieving);

            }
            writer.print("");
        return null;
    }

    @Override
    public Register visitProgram(Program p) {
    		resultReg = getRegister();//this will be $t9
    		writer.println(".data");//this is where we put struct type decls and var decls
    		new DataVisitor(writer,p);//this is where I put all the strings in the data section first
    		writer.println();
	    	for (StructTypeDecl std : p.structTypeDecls) {
	    		std.accept(this);
        }
        for (VarDecl vd : p.varDecls) {//defining global variables
        		vd.localOrGlobal="global";
        		if(vd.type instanceof BaseType){
        			BaseType mybt = (BaseType) vd.type;
        			if(mybt.equals(BaseType.INT)) {
            			writer.println(vd.varName+": .word 0");
        			}
        			if(mybt.equals(BaseType.CHAR)) {
            			writer.println(vd.varName+": .word 0");//this used to be .byte 1
        			}
        		}
        		if(vd.type instanceof PointerType){
        			PointerType myPointer = (PointerType) vd.type;
        			if (myPointer.type==BaseType.CHAR) {
            			writer.println(vd.varName+": .word 4");
        			}
        			
        		}
        		if(vd.type instanceof StructType){
        			StructType mystructype = (StructType) vd.type;
        			for (StructTypeDecl std : p.structTypeDecls) {
        				if (std.structType.string.equals(mystructype.string)) {
        					writer.println(vd.varName+": .space "+(-std.structSize));
        				}
                }
        		}
        }
        writer.println("");
        writer.println(".text");//.text then functions in mips
        writer.println();
        writer.println("j main");
        for (FunDecl fd : p.funDecls) {
    			currentFunctionName= fd.name;
            fd.accept(this);
        }
	    writer.println("li $v0, 10");//this is how you end in a program in MIPS
	    	writer.println("syscall");
    	
    	
        //don't think I need to free up registers at the the end of the program
        return null;
    }

	@Override
    public Register visitVarDecl(VarDecl vd) {
		vd.localOrGlobal="local";
		if (vd.type instanceof ArrayType) {
			ArrayType myarray = (ArrayType) vd.type;
			vd.frameoffset=frameOffset;
			vd.wordsize = (myarray.i*4);
			frameOffset=frameOffset+(myarray.i*4);
			stackoffset=stackoffset-(myarray.i*4);
			writer.println("addi $sp,$sp,-"+(myarray.i*4));
			return null;
		}
		if (vd.type instanceof StructType) {
			StructType mystruct = (StructType) vd.type;
			vd.frameoffset=frameOffset;
			vd.wordsize = mystruct.std.structSize;
			frameOffset=frameOffset+(mystruct.std.structSize);
			stackoffset=stackoffset-(mystruct.std.structSize);

			writer.println("addi $sp,$sp,-"+mystruct.std.structSize);
			return null;
		}
    		vd.frameoffset=frameOffset;//characters, ints are all 4
    		vd.wordsize = 4;
    		frameOffset=frameOffset+4;
    		stackoffset=stackoffset-4;
    		writer.println("addi $sp,$sp,-4");
        return null;
    }

    

    @Override
    public Register visitVarExpr(VarExpr v) {//this should return a register with the variable's location in memory
    		if (v.type instanceof StructType) {
    			StructType mystruct = (StructType) v.type;
    			Register result = getRegister ();
    			writer.println("la "+result+", "+mystruct.string);
		    	return result;
    		}
    		if (v.vd.localOrGlobal.equals("local")) {
		    	Register result = getRegister ();
		    	writer.println("la "+result+", "+-v.vd.frameoffset+"($fp)");
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
		return null;
	}

	@Override
	public Register visitStructType(StructType structType) {
		return null;
	}

	@Override
	public Register visitArrayType(ArrayType arrayType) {
		return null;
	}

	@Override
	public Register visitIntLiteral(IntLiteral intLiteral) {
		Register result = getRegister (); 
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
		Register result = getRegister (); 
		if (chrLiteral.escC!='z') {
			writer.println("la "+ result+" '\\"+chrLiteral.escC+"'"); 
			return result;
		}
		writer.println("la "+ result+" '"+chrLiteral.c+"'"); 
		return result;
	}

	@Override
	public Register visitFunCallExpr(FunCallExpr funCallExpr) {
		if(funCallExpr.string.equals("print_i")) {
			Register numberRegister = funCallExpr.expressions.get(0).accept(this);
			if (funCallExpr.expressions.get(0) instanceof FunCallExpr) {
				writer.println("li $v0, 1");
				writer.println("move $a0, "+resultReg);
				writer.println("syscall");
				freeRegister(numberRegister);
				return resultReg;
			}
			if(funCallExpr.expressions.get(0) instanceof VarExpr || funCallExpr.expressions.get(0) instanceof ArrayAccessExpr || funCallExpr.expressions.get(0) instanceof FieldAccessExpr){
				writer.println("lw "+numberRegister+", ("+numberRegister+")");//if it's a variable expression it will be an address
			}
			writer.println("li $v0, 1");
			writer.println("move $a0, "+numberRegister);
			writer.println("syscall");
			freeRegister(numberRegister);
			return null;
		}
		if(funCallExpr.string.equals("print_s")) {
			Register stringRegister = funCallExpr.expressions.get(0).accept(this);
			if (funCallExpr.expressions.get(0) instanceof FunCallExpr) {
				writer.println("li $v0, 4");
				writer.println("move $a0, "+resultReg);
				writer.println("syscall");
				freeRegister(stringRegister);
				return resultReg;
			}
			if(funCallExpr.expressions.get(0) instanceof VarExpr || funCallExpr.expressions.get(0) instanceof FieldAccessExpr){
				writer.println("lw "+stringRegister+", ("+stringRegister+")");//if it's a variable expression it will be an address
			}
			if(funCallExpr.expressions.get(0) instanceof TypeCastExpr){
				TypeCastExpr mytypecast = (TypeCastExpr) funCallExpr.expressions.get(0);
				if (mytypecast.expr instanceof VarExpr) {
					VarExpr myvar = (VarExpr) mytypecast.expr;
					if(myvar.type instanceof ArrayType) {
						ArrayType myarray = (ArrayType) myvar.type;
						int arraySize = myarray.i;
						writer.println("lw "+stringRegister+", ("+stringRegister+")");//used to be lb
						writer.println("li $v0, 11");
						writer.println("move $a0, "+stringRegister);
						writer.println("syscall");
						for (int i=1;i<arraySize;i++) {
						writer.println("la "+stringRegister+", "+-(i*4)+"($fp)");
						writer.println("lw "+stringRegister+", ("+stringRegister+")");//used to be lb
						writer.println("li $v0, 11");
						writer.println("move $a0, "+stringRegister);
						writer.println("syscall");
						}
						freeRegister(stringRegister);
						return null;
					}
				}
				if (mytypecast.expr instanceof StrLiteral) {
					writer.println("li $v0, 4");
					writer.println("move $a0, "+stringRegister);
					freeRegister(stringRegister);
					writer.println("syscall");
					return null;
				}
				if(mytypecast.expr.type instanceof ArrayType) {
					ArrayType myarray = (ArrayType) mytypecast.expr.type;
					int arraySize = myarray.i;
					writer.println("lw "+stringRegister+", ("+stringRegister+")");
					writer.println("li $v0, 11");
					writer.println("move $a0, "+stringRegister);
					writer.println("syscall");
					for (int i=1;i<arraySize;i++) {
					writer.println("la "+stringRegister+", "+(i*4)+"($sp)");
					writer.println("lw "+stringRegister+", ("+stringRegister+")");
					writer.println("li $v0, 11");
					writer.println("move $a0, "+stringRegister);
					writer.println("syscall");
					}
					freeRegister(stringRegister);
					return null;
				}
			}
			writer.println("li $v0, 4");
			writer.println("move $a0, "+stringRegister);
			writer.println("syscall");
			freeRegister(stringRegister);
			return null;
		}
		if(funCallExpr.string.equals("print_c")) {
			Register charRegister = funCallExpr.expressions.get(0).accept(this);
			if (funCallExpr.expressions.get(0) instanceof FunCallExpr) {
				writer.println("li $v0, 11");
				writer.println("move $a0, "+resultReg);
				writer.println("syscall");
				freeRegister(charRegister);
				return resultReg;
			}
			if(funCallExpr.expressions.get(0) instanceof VarExpr || funCallExpr.expressions.get(0) instanceof ArrayAccessExpr  || funCallExpr.expressions.get(0) instanceof FieldAccessExpr){
				writer.println("lw "+charRegister+", ("+charRegister+")");//if it's a variable expression it will be an address
			}
			writer.println("li $v0, 11");
			writer.println("move $a0, "+charRegister);
			writer.println("syscall");
			freeRegister(charRegister);
			return null;
		}
		if(funCallExpr.string.equals("read_i")) {
			writer.println("li $v0, 5");
			writer.println("syscall");
			writer.println( "move "+ resultReg+", $v0");
			return resultReg;
		}
		if(funCallExpr.string.equals("read_c")) {
			writer.println("li $v0, 12");
			writer.println("syscall");
			writer.println( "move "+ resultReg+", $v0");
			return resultReg;
		}
		if (funCallExpr.string.equals("mcmalloc")){
			Register numberRegister = funCallExpr.expressions.get(0).accept(this);
			if(funCallExpr.expressions.get(0) instanceof VarExpr || funCallExpr.expressions.get(0) instanceof ArrayAccessExpr || funCallExpr.expressions.get(0) instanceof FieldAccessExpr){
				writer.println("lw "+numberRegister+", ("+numberRegister+")");//if it's a variable expression it will be an address
			}
			writer.println("move $a0, "+numberRegister);
			freeRegister(numberRegister);
			writer.println("li $v0, 9");
			writer.println("syscall");
			writer.println( "move "+ resultReg+", $v0");
			return resultReg;
		}
		else {
writer.println("#pushing regs");
			pushToStack(Register.fp,4);
			pushToStack(Register.ra,4);
			Stack<Register> regsUsedInThisFunction = new Stack<Register>();
						for (Register r : Register.tmpRegs) {
							if (!(freeRegs.contains(r))) {
								if (!(r.equals(resultReg))){
									//System.out.println(r);
									pushToStack(r,4);
									freeRegister(r);
									regsUsedInThisFunction.push(r);
								}
							}
						}

						//System.out.println("end of regs");
			int argsoffset = 0;
			if (!funCallExpr.expressions.isEmpty()) {//Before we call a function, put the arguments in the right place in the stack for that function.
				for (Expr e : funCallExpr.expressions) {//getting the arguments
					Register exp = e.accept(this);
					if(e instanceof VarExpr || e instanceof ArrayAccessExpr || e instanceof FieldAccessExpr){
						writer.println("lw "+exp+", ("+exp+")");//if it's a variable expression it will be an address
					}
					writer.println("#"+e.toString());
					if (e.type instanceof BaseType || e.type instanceof PointerType || e instanceof BinOp) {
						pushToStack(exp,4);
						freeRegister(exp);
						argsoffset=argsoffset+4;
					}
					if (e.type instanceof ArrayType) {
						ArrayType myarray = (ArrayType) e.type;
						pushToStack(exp,myarray.i*4);
						freeRegister(exp);
						argsoffset=argsoffset+(myarray.i*4);
					}
				}
			}
			writer.println("add $sp, $sp, "+(argsoffset-4));//this allows funcallexpr to work perfectly
			writer.println("jal "+funCallExpr.string);
			writer.println("add $sp, $sp, "+(argsoffset-(argsoffset-4)));
			while (!(regsUsedInThisFunction.isEmpty())) {
						Register currentReg = regsUsedInThisFunction.pop();
						popFromStack(currentReg);
					}
			popFromStack(Register.ra);
			popFromStack(Register.fp);
		}
		return resultReg;
	}

	@Override
	public Register visitBinOp(BinOp binOp) {
		Register result = getRegister();
		Register lhsReg = binOp.lhs.accept(this);
		if(binOp.lhs instanceof VarExpr || binOp.lhs instanceof ArrayAccessExpr || binOp.lhs instanceof FieldAccessExpr){
			writer.println("lw "+lhsReg+", ("+lhsReg+")");//if it's a variable expression it will be an address
		}
		if(binOp.op.equals(Op.OR)) {
			writer.println("bne "+lhsReg+", 0 TrueOR"+orCounter);
			Register rhsReg = binOp.rhs.accept(this);//this means these lines for whatever rhs is will be skipped over if lhs was true
			if(binOp.rhs instanceof VarExpr || binOp.rhs instanceof ArrayAccessExpr || binOp.rhs instanceof FieldAccessExpr){
				writer.println("lw "+rhsReg+", ("+rhsReg+")");//if it's a variable expression it will be an address
			}
			writer.println("bne "+rhsReg+",0 TrueOR"+orCounter);
			writer.println("li "+result+", 0");
			writer.println("j AfterOR"+orCounter);
			writer.println("TrueOR"+orCounter+": ");
			writer.println("li "+result+", 1");
			writer.println("AfterOR"+orCounter+": ");
			freeRegister (lhsReg );
			freeRegister (rhsReg ); 
			orCounter++;
			return result ;
		}
		if(binOp.op.equals(Op.AND)) {
			writer.println("bne " +lhsReg+", "+0+", LHStrue"+andCounter);
			writer.println("li "+result+", 0");
			writer.println("j AfterAND"+andCounter);
			writer.println("LHStrue"+andCounter+": ");
			Register rhsReg = binOp.rhs.accept(this);
			if(binOp.rhs instanceof VarExpr || binOp.rhs instanceof ArrayAccessExpr || binOp.rhs instanceof FieldAccessExpr){
				writer.println("lw "+rhsReg+", ("+rhsReg+")");//if it's a variable expression it will be an address
			}
			writer.println("bne " +rhsReg+", "+0+", RHStrue"+andCounter);
			writer.println("li "+result+", 0");
			writer.println("j AfterAND"+andCounter);
			writer.println("RHStrue"+andCounter+": ");
			writer.println("li "+result+", 1");
			writer.println("AfterAND"+andCounter+": ");
			freeRegister (lhsReg );
			freeRegister (rhsReg ); 
			andCounter++;
			return result ;
		}
		Register rhsReg = binOp.rhs.accept(this); 
		if(binOp.rhs instanceof VarExpr || binOp.rhs instanceof ArrayAccessExpr || binOp.rhs instanceof FieldAccessExpr){
			writer.println("lw "+rhsReg+", ("+rhsReg+")");//if it's a variable expression it will be an address
		}
		switch(binOp.op) {
			case ADD:
				writer.println("add "+result+", "+lhsReg+", "+rhsReg);
				break ;
			case MUL:
				writer.println("mul "+result+", "+lhsReg+", "+rhsReg); 
				break ;
			case DIV:
				writer.println("div "+lhsReg+", "+rhsReg); 
				writer.println("mflo "+result);//gets the integer quotient
				break;
			case EQ:
				writer.println("seq "+result+", " +lhsReg+", "+rhsReg);
//				writer.println("li "+result+", 0");
//				writer.println("j AfterEqualTo"+binOpLabelCounter);
//				writer.println("EqualTo"+binOpLabelCounter+": ");
//				writer.println("li "+result+", 1");
//				writer.println("AfterEqualTo"+binOpLabelCounter+":");
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
		Register arrayPosition = arrayAccessExpr.expr2.accept(this);
		Register myvarxpr = arrayAccessExpr.expr1.accept(this);
		writer.println("mul "+arrayPosition+", "+arrayPosition+", 4");
		writer.println("add "+myvarxpr+", "+myvarxpr+", "+arrayPosition);
		freeRegister(arrayPosition);
		Register result = getRegister ();
		writer.println("la "+result+", ("+myvarxpr+")");
		freeRegister(myvarxpr);
		return result;
	}

	@Override
	public Register visitFieldAccessExpr(FieldAccessExpr fieldAccessExpr) {
		Register structAddress = getRegister ();
		structAddress = fieldAccessExpr.expr.accept(this);
		//Here result will contain address of the struct
		if (fieldAccessExpr.type instanceof StructType) {
			StructType mystruct = (StructType) fieldAccessExpr.type;
			for (VarDecl vd : mystruct.std.varDecls) {
				if (vd.varName.equals(fieldAccessExpr.string)){//we find the variable inside the struct
					Register varInStruct = getRegister();
			    	writer.println("la "+varInStruct+", "+-vd.structOffset+"("+structAddress+")");
			    	freeRegister(structAddress);
			    	return varInStruct;
				}
			}
		}
		return null;
	}

	@Override
	public Register visitValueAtExpr(ValueAtExpr valueAtExpr) {
		return null;
	}

	@Override
	public Register visitSizeOfExpr(SizeOfExpr sizeOfExpr) {
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
		int tmp = mywhileCounter;
		mywhileCounter++;
		Register expression = myWhile.expr.accept(this);
		writer.println("MyWhile"+tmp+": beq "+expression+", "+0+", AfterWhile"+tmp);
		myWhile.stmt.accept(this);
		Register check = myWhile.expr.accept(this);
		writer.println("move "+expression+", "+check);
		writer.println("j MyWhile"+tmp);
		writer.println("AfterWhile"+tmp+": ");
		freeRegister(expression);
		freeRegister(check);
		return null;
	}

	@Override
	public Register visitIf(If myIf) {
		int tmp = myifCounter;
		myifCounter++;
		Register expression = myIf.expr.accept(this);
		writer.println("beq "+expression+", 0, "+"AfterIf"+tmp);
		freeRegister(expression);
		//System.out.println(myIf.stmt);
		myIf.stmt.accept(this);
		writer.println("j AfterIfElse"+tmp);
		writer.println("AfterIf"+tmp+":");
		if (myIf.optStmt!=null){
			//System.out.println(myIf.optStmt);
			myIf.optStmt.accept(this);
		}
		writer.println("AfterIfElse"+tmp+":");
		return null;
	}

	@Override
	public Register visitAssign(Assign assign) {
		Register lhs = assign.expr1.accept(this);
		Register rhs = assign.expr2.accept(this);
//need to accommodate type cast expression
		if (assign.expr2 instanceof FunCallExpr) {
			writer.println("sw "+resultReg+", ("+lhs+")");
			freeRegister(rhs);
			freeRegister(lhs);
			return null;
		}
		if (assign.expr2 instanceof VarExpr) {
			writer.println("lw "+rhs+", ("+rhs+")");
			writer.println("sw "+rhs+", ("+lhs+")");
			freeRegister(rhs);
			freeRegister(lhs);
			return null;
		}
		if (assign.expr2 instanceof ArrayAccessExpr) {
			writer.println("lw "+rhs+", ("+rhs+")");
			writer.println("sw "+rhs+", ("+lhs+")");
			freeRegister(rhs);
			freeRegister(lhs);
			return null;
		}
		writer.println("sw "+rhs+", ("+lhs+")");
		freeRegister(rhs);
		freeRegister(lhs);
		return null;
	}

	@Override
	public Register visitExprStmt(ExprStmt exprStmt) {
		exprStmt.expr.accept(this);
		return null;
	}

	@Override
	public Register visitReturn(Return myReturn) {
		if (myReturn.optExpr!=null) {
			if (myReturn.optExpr instanceof ArrayAccessExpr || myReturn.optExpr instanceof VarExpr) {
				Register optexpr = myReturn.optExpr.accept(this);
				writer.println("lw "+optexpr+", ("+optexpr+")");
				writer.println("move $t9, "+optexpr);
				writer.println("j "+currentFunctionName+"End");
				freeRegister(optexpr);
				return null;
			}
			Register optexpr =  myReturn.optExpr.accept(this);
			writer.println("move $t9, "+optexpr);
			writer.println("j "+currentFunctionName+"End");
			freeRegister(optexpr);
			return null;
		}
		writer.println("j "+currentFunctionName+"End");
		return null;
	}
	
	public void pushToStack(Register r, int i) {
		writer.println("addi $sp,$sp,-"+i);
		writer.println("sw "+r+", ($sp)");
	}
	public void popFromStack(Register r) {
		writer.println("lw "+r+" ,($sp)");
		writer.println("addiu $sp,$sp,4");
	}
	
	
}

