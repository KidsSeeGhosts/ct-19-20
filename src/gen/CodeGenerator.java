package gen;

import ast.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.EmptyStackException;
import java.util.HashMap;
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
    //private int gpoffset = 0;


    private PrintWriter writer; // use this writer to output the assembly instructions

	private HashMap<String,StructTypeDecl> mystds = new HashMap<String,StructTypeDecl>();
	private HashMap<String,VarDecl> structVDs = new HashMap<String,VarDecl>();
	private DataVisitor mydv;


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
    		mydv = new DataVisitor(writer,p);//this is where I put all the strings in the data section first
    		writer.println();
	    	for (StructTypeDecl std : p.structTypeDecls) {
	    		mystds.put(std.structType.string, std);
	    		std.localOrGlobal="global";
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
        					writer.println(vd.varName+": .space "+(std.structSize));
        				}
                }
        		}
        		if (vd.type instanceof ArrayType) {//forgot about this, maybe this will solve binary search once and for all
        			ArrayType myarraytype = (ArrayType) vd.type;
        			int arraysize = (myarraytype.i*4);
        			writer.println(vd.varName+": .space "+arraysize);
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
		vd.localOrGlobal="local";//this will only ever see local as global is already dealt with in visitprogram
		vd.frameoffset=frameOffset;
		if (vd.type instanceof ArrayType) {
			ArrayType myarray = (ArrayType) vd.type;
			vd.wordsize = (myarray.i*4);
			frameOffset=frameOffset+(myarray.i*4);
			stackoffset=stackoffset-(myarray.i*4);
			writer.println("addi $sp,$sp,-"+(myarray.i*4));
			return null;
		}
		if (vd.type instanceof StructType) {
			StructType mystruct = (StructType) vd.type;
			int currentstructsize = (mydv.mystructsizes.get(mystruct.string));
			vd.wordsize = currentstructsize;
			frameOffset=frameOffset+(currentstructsize);
			stackoffset=stackoffset-(currentstructsize);
			writer.println("addi $sp,$sp,-"+currentstructsize);
			structVDs.put(vd.varName, vd);
			return null;
		}
    		vd.wordsize = 4;
    		frameOffset=frameOffset+4;
    		stackoffset=stackoffset-4;
    		writer.println("addi $sp,$sp,-4");
        return null;
    }

    

    @Override
    public Register visitVarExpr(VarExpr v) {//this should return a register with the variable's location in memory
    		//System.out.println(v.vd.frameoffset);
    		Register result = getRegister();
    		if (v.type instanceof StructType) {
    			StructType mystruct = (StructType) v.type;
    			if (mystds.containsKey(v.name)){//if we're directly accessing the struct
	    			writer.println("la "+result+", "+mystruct.string);
	    		    return result;
    			}
    		}
    		if (v.vd.localOrGlobal.equals("local")) {
		    	writer.println("la "+result+", "+-v.vd.frameoffset+"($fp)");
		    	return result;
    		}
    		if (v.vd.localOrGlobal.equals("global")) {
    			writer.println("la "+result+", "+v.name);
    			writer.println("#just got register for global var expr address");
		    	return result ;
    		}
	    	return result ;
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
		Register result = getRegister(); 
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
				if (!(funCallExpr.expressions.get(0).type instanceof PointerType)) {
				writer.println("lw "+numberRegister+", ("+numberRegister+")");//if it's a variable expression it will be an address
				}
			}
			writer.println("li $v0, 1");
			writer.println("move $a0, "+numberRegister);
			writer.println("syscall");
			freeRegister(numberRegister);
			return null;
		}
		if(funCallExpr.string.equals("print_s")) {
			if(funCallExpr.expressions.get(0).type instanceof PointerType) {
				PointerType mypt = (PointerType) funCallExpr.expressions.get(0).type;
				//System.out.println(mypt.type);
			}
			writer.println("# start of print_s");
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
			if (funCallExpr.expressions.get(0).type instanceof PointerType) {
				PointerType mypt = (PointerType) funCallExpr.expressions.get(0).type;
				//System.out.println(mypt.type);
			}
			if(funCallExpr.expressions.get(0) instanceof TypeCastExpr){
				TypeCastExpr mytypecast = (TypeCastExpr) funCallExpr.expressions.get(0);
				if (mytypecast.expr instanceof VarExpr) {
					VarExpr myvar = (VarExpr) mytypecast.expr;
					if(myvar.type instanceof ArrayType) {
						ArrayType myarray = (ArrayType) myvar.type;
						int arraySize = myarray.i;
						Register printreg = getRegister();
						for (int i=0;i<arraySize;i++) {
							if (i==0) {
								writer.println("sub "+stringRegister+", "+stringRegister+", "+0);
							}
							else {
								writer.println("sub "+stringRegister+", "+stringRegister+", "+4);
							}
							writer.println("lw "+printreg+", ("+stringRegister+")");//used to be lb
							writer.println("li $v0, 11");
							writer.println("move $a0, "+printreg);
							writer.println("syscall");
						}
						freeRegister(printreg);
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
				//if (!(funCallExpr.expressions.get(0).type instanceof PointerType)) {
				writer.println("lw "+charRegister+", ("+charRegister+")");//if it's a variable expression it will be an address
				//}
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
									writer.println("#push to stack");
									pushToStack(r,4);
									freeRegister(r);
									regsUsedInThisFunction.push(r);
								}
								
							}
						}

						//System.out.println("end of regs");
			int argsoffset = 0;
			if (!funCallExpr.expressions.isEmpty()) {//Before we call a function, put the arguments in the right place in the stack for that function.
				//System.out.println(funCallExpr.expressions.get(0));
				for (Expr e : funCallExpr.expressions) {//getting the arguments
					writer.println("#Argument "+e.toString());
					Register exp = e.accept(this);
					if (e instanceof FunCallExpr) {//This allows a function
						Register myresult = getRegister();
						writer.println("move "+myresult+", "+exp);
						freeRegister(exp);
						//writer.println("lw "+exp+", ("+exp+")");
						if (e.type instanceof BaseType || e.type instanceof PointerType) {//used to have binop here
							
							pushToStack(myresult,4);
							freeRegister(myresult);
							argsoffset=argsoffset+4;
						}
						else if (e.type instanceof ArrayType) {
							ArrayType myarray = (ArrayType) e.type;
							pushToStack(myresult,myarray.i*4);
							freeRegister(myresult);
							argsoffset=argsoffset+(myarray.i*4);
						}
//						if (e.type instanceof StructType) {
//							StructType mystruct = (StructType) e.type;
//							StructTypeDecl mystd = (mystds.get(mystruct.string));
//							pushToStack(myresult,mystd.structSize);
//							freeRegister(myresult);
//							argsoffset=argsoffset+(mystd.structSize);
//						}
						if(e.type==null) {//this is to compensate for mistakes with if else block return type stuff in part 2
							//System.out.println("yes in funcall");
							pushToStack(exp,4);
							freeRegister(exp);
							argsoffset=argsoffset+4;
						}
					}
					else {
					
						if(e instanceof VarExpr || e instanceof ArrayAccessExpr || e instanceof FieldAccessExpr){
							if (!(e.type instanceof PointerType)) {
								writer.println("lw "+exp+", ("+exp+")");//if it's a variable expression it will be an address
							}
						}
						if (e.type instanceof BaseType || e.type instanceof PointerType) {//used to have bin op here
							pushToStack(exp,4);
							freeRegister(exp);
							argsoffset=argsoffset+4;
						}
						else if (e.type instanceof ArrayType) {
							ArrayType myarray = (ArrayType) e.type;
							pushToStack(exp,myarray.i*4);
							freeRegister(exp);
							argsoffset=argsoffset+(myarray.i*4);
						}
//						if (e.type instanceof StructType) {
//						StructType mystruct = (StructType) e.type;
//						StructTypeDecl mystd = (mystds.get(mystruct.string));
//						pushToStack(myresult,mystd.structSize);
//						freeRegister(myresult);
//						argsoffset=argsoffset+(mystd.structSize);
//					}
						if(e.type==null) {//this is to compensate for mistakes with if else block return type stuff in part 2
							//System.out.println("yes");//I'm acutely aware this will break with structs and arrays
							pushToStack(exp,4);
							freeRegister(exp);
							argsoffset=argsoffset+4;
						}
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
			writer.println("#end of function call expression");
		}
		return resultReg;
	}

	@Override
	public Register visitBinOp(BinOp binOp) {
		Register result = getRegister();
		Register lhsReg = binOp.lhs.accept(this);
		writer.println("#just accepted lhs of bin op");
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
		writer.println("#just accepted rhs of bin op");
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
		if(arrayAccessExpr.expr2 instanceof VarExpr || arrayAccessExpr.expr2 instanceof ArrayAccessExpr || arrayAccessExpr.expr2 instanceof FieldAccessExpr){
			writer.println("lw "+arrayPosition+", ("+arrayPosition+")");//if it's a variable expression it will be an address
		}//this key line and subtracting instead of adding fixed binary search!!
		Register arrayFPaddressReg = arrayAccessExpr.expr1.accept(this);
		writer.println("mul "+arrayPosition+", "+arrayPosition+", 4");//do the calculation then the frame pointer bit
		VarExpr myvarexpr;
		if (arrayAccessExpr.expr1 instanceof FieldAccessExpr) {
			FieldAccessExpr myfieldaccess = (FieldAccessExpr) arrayAccessExpr.expr1;
			myvarexpr = (VarExpr) myfieldaccess.expr;
		}
		else {
			myvarexpr = (VarExpr) arrayAccessExpr.expr1;
		}
		//System.out.println(myvarexpr.vd.localOrGlobal);
		if (myvarexpr.vd.localOrGlobal.equals("global")) {//data goes up in mips!
			writer.println("add "+arrayFPaddressReg+", "+arrayFPaddressReg+", "+arrayPosition);//this is the number to be subtracted from fp
			
		}
		else {
			writer.println("sub "+arrayFPaddressReg+", "+arrayFPaddressReg+", "+arrayPosition);//this is the number to be subtracted from fp
			
		}
		freeRegister(arrayPosition);
		Register result = getRegister ();
		writer.println("la "+result+", ("+arrayFPaddressReg+")");
		freeRegister(arrayFPaddressReg);
		return result;
	}

	@Override
	public Register visitFieldAccessExpr(FieldAccessExpr fieldAccessExpr) {
		VarExpr myvarexpr = (VarExpr) fieldAccessExpr.expr;
		if (mystds.containsKey(myvarexpr.name)) {//WE'RE DIRECTLY ACCESSING THE STRUCT
			//System.out.println("yes");
			Register structAddress = fieldAccessExpr.expr.accept(this);
			writer.println("#doing expression in field access");
				if (fieldAccessExpr.type instanceof StructType) {
					StructType mystruct = (StructType) fieldAccessExpr.type;
					for (VarDecl vd : mystruct.std.varDecls) {
						if (vd.varName.equals(fieldAccessExpr.string)){//we find the variable inside the struct
							Register varInStruct = getRegister();
						    	writer.println("la "+varInStruct+", "+vd.structOffset+"("+structAddress+")");
						    	freeRegister(structAddress);
						    	return varInStruct;
						}
					}
				}
		}
		else {//if we're here it means that we are working with an instance of a struct
			Register varexpr = fieldAccessExpr.expr.accept(this);//if it's local it will be 0fp, if global it will be address of global thing
			if (myvarexpr.vd.type instanceof StructType) {
				StructType mystructtype = (StructType) myvarexpr.vd.type;
				StructTypeDecl mystd = (mystds.get(mystructtype.string));
				for (VarDecl vd : mystd.varDecls) {
					if (vd.varName.equals(fieldAccessExpr.string)){
						int varoffset = (vd.structOffset);
						Register result=getRegister();
						if (myvarexpr.vd.localOrGlobal.equals("global")) {//data goes up in mips!
							writer.println("add "+varexpr+", "+varexpr+", "+varoffset);
						}
						else {

							writer.println("sub "+varexpr+", "+varexpr+", "+varoffset);
						}
						writer.println("la "+result+", ("+varexpr+")");
						freeRegister(varexpr);
						return result;
					}
				}
			}
		}
		return null;
	}

	@Override
	public Register visitValueAtExpr(ValueAtExpr valueAtExpr) {
		Register visitvalueat = valueAtExpr.expr.accept(this);
		if(valueAtExpr.expr instanceof VarExpr || valueAtExpr.expr instanceof ArrayAccessExpr || valueAtExpr.expr instanceof FieldAccessExpr){
			writer.println("lw "+visitvalueat+", ("+visitvalueat+")");//if it's a variable expression it will be an address
		}
		return visitvalueat;
	}

	@Override
	public Register visitSizeOfExpr(SizeOfExpr sizeOfExpr) {
		Register result = getRegister();
		if (sizeOfExpr.type instanceof PointerType) {
			writer.println("li "+result+", 4");
			return result;
		}
		if (sizeOfExpr.type.equals(BaseType.INT)) {
			writer.println("li "+result+", "+4);
			return result;
		}
		if (sizeOfExpr.type.equals(BaseType.CHAR)) {
			writer.println("li "+result+", "+4);
			return result;
		}
		if (sizeOfExpr.type.equals(BaseType.VOID)) {
			writer.println("li "+result+", "+1);
			return result;
		}
		if (sizeOfExpr.type instanceof StructType) {
			StructType myst = (StructType) sizeOfExpr.type;
			StructTypeDecl mystd = mystds.get(myst.string);
			writer.println("li "+result+", "+mystd.structSize);
			return result;
		}
		return result;
	}

	@Override
	public Register visitTypeCastExpr(TypeCastExpr typeCastExpr) {
		//typeCastExpr.type.accept(this);
		Register exprReg = typeCastExpr.expr.accept(this);
		return exprReg;
	}

	@Override
	public Register visitWhile(While myWhile) {
		int tmp = mywhileCounter;
		mywhileCounter++;
		writer.println("MyWhile"+tmp+":");
		Register expression = myWhile.expr.accept(this);
		if(myWhile.expr instanceof VarExpr || myWhile.expr instanceof ArrayAccessExpr || myWhile.expr instanceof FieldAccessExpr){
			writer.println("lw "+expression+", ("+expression+")");//if it's a variable expression it will be an address
		}
		writer.println("beq "+expression+", "+0+", AfterWhile"+tmp);
		freeRegister(expression);//this line was the final thing that fixed tic tac toe
		writer.println("MyWhileStatement"+tmp+":");
		
		myWhile.stmt.accept(this);
		
		
		
		Register secondexpr = myWhile.expr.accept(this);
		if(myWhile.expr instanceof VarExpr || myWhile.expr instanceof ArrayAccessExpr || myWhile.expr instanceof FieldAccessExpr){
			writer.println("lw "+secondexpr+", ("+secondexpr+")");//if it's a variable expression it will be an address
		}
		writer.println("bne "+secondexpr+", "+0+", MyWhileStatement"+tmp);
	
//		writer.println("j MyWhile"+tmp);
		writer.println("AfterWhile"+tmp+": ");
		//freeRegister(expression);//Moving this line up fixed tic tac toe but made other stuff impossible
		freeRegister(secondexpr);
		return null;
	}

	@Override
	public Register visitIf(If myIf) {
		int tmp = myifCounter;
		myifCounter++;
		Register expression = myIf.expr.accept(this);
		if(myIf.expr instanceof VarExpr || myIf.expr instanceof ArrayAccessExpr || myIf.expr instanceof FieldAccessExpr){
			writer.println("lw "+expression+", ("+expression+")");//if it's a variable expression it will be an address
		}
		writer.println("beq "+expression+", 0, "+"AfterIf"+tmp);
		freeRegister(expression);
		myIf.stmt.accept(this);
		writer.println("j AfterIfElse"+tmp);
		writer.println("AfterIf"+tmp+":");
		if (myIf.optStmt!=null){
			myIf.optStmt.accept(this);
			//writer.println("j AfterIfElse"+tmp);//don't see something wrong with this
		}
		writer.println("AfterIfElse"+tmp+":");
		//freeRegister(expression);
		return null;
	}

	@Override
	public Register visitAssign(Assign assign) {
		if (assign.expr2 instanceof FunCallExpr) {
			Register rhs = assign.expr2.accept(this);
			Register lhs = assign.expr1.accept(this);
			writer.println("sw "+rhs+", ("+lhs+")");
			if(rhs.equals(resultReg)) {
				//freeRegister(rhs);
				freeRegister(lhs);
				return null;
			}
			freeRegister(rhs);
			freeRegister(lhs);
			return null;
		}
		writer.println("#about to do rhs in assign");
		Register rhs = assign.expr2.accept(this);
		writer.println("#about to do lhs in assign");
		Register lhs = assign.expr1.accept(this);
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
		if (assign.expr2 instanceof TypeCastExpr) {
			//TypeCastExpr mytypecastexpr = (TypeCastExpr) assign.expr2;
			//System.out.println(assign.expr1.type);
			writer.println("#assign of type cast expr");
			//writer.println("lw "+rhs+", ("+rhs+")");
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
				freeRegister(optexpr);
				writer.println("j "+currentFunctionName+"End");
				return null;
			}
			Register optexpr =  myReturn.optExpr.accept(this);
			writer.println("move $t9, "+optexpr);
			freeRegister(optexpr);
			writer.println("j "+currentFunctionName+"End");
			return null;
		}
		writer.println("j "+currentFunctionName+"End");
		return null;
	}
	
	public void pushToStack(Register r, int i) {
		writer.println("addi $sp,$sp,-"+i +" #hello");
		writer.println("sw "+r+", ($sp)");
	}
	public void popFromStack(Register r) {
		writer.println("lw "+r+" ,($sp)");
		writer.println("addiu $sp,$sp,4");
	}
	
	
}



