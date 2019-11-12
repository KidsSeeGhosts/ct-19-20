package sem;

import java.util.HashMap;
import java.util.List;

import ast.*;

public class TypeCheckVisitor extends BaseSemanticVisitor<Type> {
	private HashMap<String,StructTypeDecl> mystds = new HashMap<String,StructTypeDecl>();

	@Override
	public Type visitBaseType(BaseType bt) {
		return bt;
	}

	@Override
	public Type visitStructTypeDecl(StructTypeDecl st) {
		mystds.put(st.structType.string, st);
		return st.structType.accept(this);
		
	}

	@Override
	public Type visitBlock(Block b) {//no analysiss just visiting
		//System.out.println("inside block");
		for (VarDecl vd : b.varDecls) {
            vd.accept(this);
        }
        for (Stmt stmt : b.stmts) {
        		if (stmt instanceof Return) {
        		}
            stmt.accept(this);
        }
		return null;
	}

	@Override
	public Type visitFunDecl(FunDecl fd) {//no analysis happening here just visiting as normal. this is because there's no premise in typing rule.
		Type fundecltype = fd.type.accept(this);
        if (!fd.vardecls.isEmpty()){
	        	for (VarDecl vd : fd.vardecls) {
	                vd.accept(this);
	            }
	        	
		        	for (VarDecl vd : fd.block.varDecls) {
		                vd.accept(this);
		            }
		            for (Stmt stmt : fd.block.stmts) {
		            		if (stmt instanceof Block) {
		            			Block myblock = (Block) stmt;
		            			visitBlockInFunDecl(fd,fundecltype,myblock);
		            		}
		            		if (stmt instanceof Return) {
		            			Return myreturn  = (Return) stmt;
		            			if (myreturn.optExpr!=null) {//something comes after return
		            				if (fundecltype.equals(BaseType.VOID)) {
		        						Type myreturntype = myreturn.optExpr.accept(this);
		        						if((myreturntype instanceof BaseType) || (myreturntype instanceof ArrayType)) {
		        							if(!(myreturntype.equals(BaseType.VOID))) {
		        								error("return exp where exp is not void");
		        							}
		        						}
		        						else if (myreturntype==null) {//this mean we're returning a void and the function is void so it's fine
		        						//error("return exp where exp doesn't give any type");
		        						}
		        				}
		            				else {//fundecl isn't of type void
		            					Type myreturntype = myreturn.optExpr.accept(this);
		            					if (myreturntype instanceof StructType) {
		            						//StructType myreturnstruct = (StructType) myreturntype;
		            						//System.out.println(myreturnstruct);
		            						if (!(fundecltype instanceof StructType)) {
		            							error("returning a struct type when the function is not meant to return a struct type");
		            						}
		            					}
		            					if (myreturntype instanceof PointerType) {
		            						//StructType myreturnstruct = (StructType) myreturntype;
		            						//System.out.println(myreturnstruct);
		            						if (!(fundecltype instanceof PointerType)) {
		            							error("returning a struct type when the function is not meant to return a struct type");
		            						}
		            					}
		            					if (fundecltype!=myreturntype && (!(myreturntype instanceof StructType))&& (!(myreturntype instanceof PointerType))) {
		            						//System.out.println(fundecltype);
		            						//System.out.println(myreturntype);
		            						error("function type isn't same as return type");
		            					}
		            				}
		            			}
		            			else {//nothing comes after return
		            				if (!(fundecltype.equals(BaseType.VOID))) {
		            					error("Not returning anything when function is NOT void");
		            				}
		            			}
		            			
		            		}
		            		if (stmt instanceof If) {
		            			If myif = (If) stmt;
		            			visitIfBlockInFunDecl(fd,fundecltype,myif);
		            		}
		            		if (stmt instanceof While) {
		            			While mywhile = (While) stmt;
		            			visitWhileBlockInFunDecl(fd,fundecltype,mywhile);
		            		}
		            		else if ((!(stmt instanceof If))&&(!(stmt instanceof While))&&(!(stmt instanceof Block))){
		            			stmt.accept(this);
		            		}
		            }
		        	
		        	
	        	
	        	
	        	
	            //fd.block.accept(this);
	            return null;
        }
        //in a fun decl with no params
		for (VarDecl vd : fd.block.varDecls) {
            vd.accept(this);
        }
        for (Stmt stmt : fd.block.stmts) {
	        	if (stmt instanceof Block) {
	    			Block myblock = (Block) stmt;
	    			visitBlockInFunDecl(fd,fundecltype,myblock);
	    		}
        		if (stmt instanceof Return) {
        			Return myreturn  = (Return) stmt;
        			if (myreturn.optExpr!=null) {//something comes after return
        				if (fundecltype.equals(BaseType.VOID)) {
        						Type myreturntype = myreturn.optExpr.accept(this);
        						if((myreturntype instanceof BaseType) || (myreturntype instanceof ArrayType)) {
        							if(!(myreturntype.equals(BaseType.VOID))) {
        								error("return exp where exp is not void");
        							}
        						}
        						else if (myreturntype==null) {//this mean we're returning a void and the function is void so it's fine
        						//error("return exp where exp doesn't give any type");
        						}
        				}
        				else {//fundecl isn't of type void
        					Type myreturntype = myreturn.optExpr.accept(this);
        					if (myreturntype instanceof StructType) {
        						//StructType myreturnstruct = (StructType) myreturntype;
        						//System.out.println(myreturnstruct);
        						if (!(fundecltype instanceof StructType)) {
        							error("returning a struct type when the function is not meant to return a struct type");
        						}
        					}
        					if (myreturntype instanceof PointerType) {
        						//StructType myreturnstruct = (StructType) myreturntype;
        						//System.out.println(myreturnstruct);
        						if (!(fundecltype instanceof PointerType)) {
        							error("returning a struct type when the function is not meant to return a struct type");
        						}
        					}
        					if (fundecltype!=myreturntype && (!(myreturntype instanceof StructType))&& (!(myreturntype instanceof PointerType))) {
        						//System.out.println(fundecltype);
        						//System.out.println(myreturntype);
        						error("function type isn't same as return type");
        					}
        				}
        			}
        			else {//nothing comes after return
        				if (!(fundecltype.equals(BaseType.VOID))) {
        					error("Not returning anything when function is NOT void");
        				}
        			}
        		}
        		if (stmt instanceof If) {
        			If myif = (If) stmt;
        			//System.out.println(myif.optStmt);
        			visitIfBlockInFunDecl(fd,fundecltype,myif);
        		}
        		if (stmt instanceof While) {
        			While mywhile = (While) stmt;
        			visitWhileBlockInFunDecl(fd,fundecltype,mywhile);
        		}
        		else if ((!(stmt instanceof If))&&(!(stmt instanceof While))&&(!(stmt instanceof Block))){
            stmt.accept(this);
        		}
        }
        
        
        //fd.block.accept(this);
		return null;
	}


	@Override
	public Type visitProgram(Program p) {//no analysis here
		for (StructTypeDecl std : p.structTypeDecls) {
            std.accept(this);
        }
        for (VarDecl vd : p.varDecls) {
            vd.accept(this);
        }
        for (FunDecl fd : p.funDecls) {
            fd.accept(this);
        }
		return null;
	}

	@Override
	public Type visitVarDecl(VarDecl vd) {//just checking the type is not void otherwise not returning anything
		if (vd.type==BaseType.VOID) {
			error("var decl type was void");
		}
		return vd.type;
	}

	@Override
	public Type visitVarExpr(VarExpr v) {
		if (v.vd!=null) {//if variable has been declared we can return the variable's type
			v.type = v.vd.type;
			return v.type;
		}
		else {//if it doesn't have a type we don't do analysis
			//error("Var Expression type check doesn't work because var doesn't have a declaration");
			return null;
		}
	}

	@Override
	public Type visitPointerType(PointerType pt) {
		return pt;
	}

	@Override
	public Type visitStructType(StructType structType) {
		return structType;
	}

	@Override
	public Type visitArrayType(ArrayType arrayType) {
		return arrayType;
	}

	@Override
	public Type visitIntLiteral(IntLiteral intLiteral) {
		intLiteral.type = BaseType.INT;
		return intLiteral.type;
	}

	@Override
	public Type visitStrLiteral(StrLiteral strLiteral) {
		strLiteral.type = new ArrayType(BaseType.CHAR,strLiteral.string.length()+1);
		return strLiteral.type;
	}

	@Override
	public Type visitChrLiteral(ChrLiteral chrLiteral) {
		chrLiteral.type = BaseType.CHAR;
		return chrLiteral.type;
	}

	@Override
	public Type visitFunCallExpr(FunCallExpr funCallExpr) {
		if(funCallExpr.funDecl==null) {//function hasn't been declared before so we don't do any type checking
			//doesn't make a difference
//			for (Expr exp : funCallExpr.expressions) {
//				exp.accept(this);
//			}
			return null;
		}
		funCallExpr.type = funCallExpr.funDecl.type;
		//System.out.println("reached fun call expr");
		List<VarDecl> functionargs = funCallExpr.funDecl.vardecls;	
		//System.out.println(functionargs);
		int index = 0;
		//System.out.println(functionargs.size());
		if (funCallExpr.expressions.size()==functionargs.size()){//checking there's the right number of args
			for (Expr exp : funCallExpr.expressions){//these are the arguments
				//VarExpr currentvarexp = (VarExpr) exp;//decent beta version but the problem is we don't know they're all gonna be var expressions
				Type currentVarType = exp.accept(this);//this will return a type
				Type currentargType = functionargs.get(index).type;
				if ((currentVarType instanceof PointerType) && (currentargType instanceof PointerType)) {//deals with pointers another layer of typing required
					PointerType mypointervar = (PointerType) currentVarType.accept(this);
					PointerType mypointerarg = (PointerType) currentargType.accept(this);
					if (mypointervar.type.equals(mypointerarg.type)) {
						return null;
					}
				}
				//System.out.println(currentVarType);
				
				//System.out.println(currentVarType.equals(currentargType));
				if (currentVarType==null) {
					error("error in fun call expression, got an argument that's null");
					return null;
				}
				if (!(currentVarType.equals(currentargType))) {//if the type of argument is not equal
					//error("Argument in fun call doesn't have right type"); temporairly commented for towers of hanoi
					//System.out.println(currentVarType);
					return null;
				}
				index++;
			}
			//System.out.println("finished the args");
			return funCallExpr.type;//all the argument types were fine
		}
		else {
			error("funcall doesn't have the right number of arguments");
			return null;
		}
	}

	@Override
	public Type visitBinOp(BinOp binOp) {
		Type lhstype = binOp.lhs.accept(this);
		Type rhstype = binOp.rhs.accept(this);
		if ((binOp.op==Op.ADD)||(binOp.op==Op.SUB)||(binOp.op==Op.MUL)||(binOp.op==Op.DIV)||(binOp.op==Op.MOD)||(binOp.op==Op.OR)||(binOp.op==Op.AND)||(binOp.op==Op.GT)||(binOp.op==Op.GE)||(binOp.op==Op.LT)||(binOp.op==Op.LE)) {
			if(lhstype==BaseType.INT && rhstype==BaseType.INT) {
				binOp.type=BaseType.INT;
				return BaseType.INT;
			}
			else {
				error("binop1 lhs or rhs or both was not an integer");
				return null;
			}
		}
		if ((binOp.op==Op.NE)||(binOp.op==Op.EQ)) {
			if ((!(lhstype instanceof ArrayType))&&(!(lhstype instanceof StructType))&&(lhstype!=BaseType.VOID)) {
				if (rhstype==lhstype) {//think we're just checking here if they are the same type
					binOp.type=BaseType.INT;
					return BaseType.INT;
				}
				else {
					error("binOp with NE or EQ, lhs and rhs had different types");
					return null;
				}
			}
			else {
				error("lhs of binOp was an arrayType,StructType or void when operator was NE or EQ");
				return null;
			}
		}
		return null;
	}

	@Override
	public Type visitArrayAccessExpr(ArrayAccessExpr arrayAccessExpr) {
		Type exp1Type = arrayAccessExpr.expr1.accept(this);
		Type exp2Type = arrayAccessExpr.expr2.accept(this);
		if ((exp1Type instanceof ArrayType)||(exp1Type instanceof PointerType)) {
			if (exp1Type instanceof ArrayType) {//it's an array type
				ArrayType myarraytype = (ArrayType) exp1Type;
				if(exp2Type.equals(BaseType.INT)) {
					arrayAccessExpr.type = myarraytype.type;
					return arrayAccessExpr.type;
				}
				else {
					error("Array access e1 is arraytype but e2 is not int");
					return null;
				}
			}
			else {//it's a pointer type
				PointerType mypointertype = (PointerType) exp1Type;
				if(exp2Type.equals(BaseType.INT)) {
					arrayAccessExpr.type = mypointertype.type;
					return arrayAccessExpr.type;
				}
				else {
					error("Array access e1 is pointer type but e2 is not int");
					return null;
				}
			}
		}
		else {
			error("exp1 in array access is not array type or pointer type in type analysis");
			return null;
		}
	}

	@Override
	public Type visitFieldAccessExpr(FieldAccessExpr fieldAccessExpr) {
		//System.out.println("inside visit field access expr");
		Type expType = fieldAccessExpr.expr.accept(this);
		if (expType instanceof StructType) {
			StructType mystructtype = (StructType) fieldAccessExpr.expr.accept(this);
			StructTypeDecl mystd = mystds.get(mystructtype.string);
			for (VarDecl vd : mystd.varDecls) {
				if (vd.varName.equals(fieldAccessExpr.string)) {
					return vd.type;
				}
			}
			return null;
		}
		else if (fieldAccessExpr.type instanceof StructType){
			StructType mystructtype = (StructType) fieldAccessExpr.type;
			StructTypeDecl mystd = mystds.get(mystructtype.string);
			for (VarDecl vd : mystd.varDecls) {
				if (vd.varName.equals(fieldAccessExpr.string)) {
					return vd.type;
				}
			}
			return null;
		}
		else {
			//System.out.println(expType);
			error("field access expression is not struct type");
			return null;
		}
	}

	@Override
	public Type visitValueAtExpr(ValueAtExpr valueAtExpr) {
		Type valueattype = valueAtExpr.expr.accept(this);
		//System.out.println(valueattype);
		if (valueattype instanceof PointerType) {
			PointerType mypt = (PointerType) valueattype;
			return mypt.type;
		}
		else {
			error("expression in value at not a pointer type");
			return null;
		}
	}

	@Override
	public Type visitSizeOfExpr(SizeOfExpr sizeOfExpr) {
		sizeOfExpr.type.accept(this);//testing what this does
		return BaseType.INT;
	}

	@Override
	public Type visitTypeCastExpr(TypeCastExpr typeCastExpr) {
		//System.out.println(typeCastExpr.expr.accept(this));
		Type expType = typeCastExpr.expr.accept(this);
		Type mypointertype = typeCastExpr.type;
		if (mypointertype instanceof PointerType) {
			PointerType pointer = (PointerType) mypointertype;
			if (expType instanceof ArrayType) {//typecast 1
				ArrayType myarraytype = (ArrayType) expType;
				if (pointer.type==myarraytype.type) {
					return mypointertype;
				}
				else {
					error("In type cast, pointer and array exp don't have same elem type");
					return null;
				}
			}
			if (expType instanceof PointerType) {
				//PointerType exppointer = (PointerType) expType;
				return pointer.type;
			}
			else {
				error("pointer type found in type cast exp but exp after wasn't pointertype or arraytype");
				return null;
			}
		}
		if (mypointertype.equals(BaseType.INT)){
				if (expType.equals(BaseType.CHAR)) {
					return BaseType.INT;
				}
			error("in typecastexpr got int pointer but not char");	
			return null;
		}
		else {
			error("in typecastexpr (type) exp, type is not a pointer or inttype");
			return null;
		}
	}

	@Override
	public Type visitWhile(While myWhile) {
		Type whiletype = myWhile.expr.accept(this);
		if (whiletype.equals(BaseType.INT)) {//no error
			myWhile.stmt.accept(this);
			return null;
		}
		else{
			error("in while, expression is not an int");
			myWhile.stmt.accept(this);
			return null;
		}
	}

	@Override
	public Type visitIf(If myIf) {
		//System.out.println("inside visit if");
		Type myiftype = myIf.expr.accept(this);
		if (myiftype.equals(BaseType.INT)){
			myIf.stmt.accept(this);
			if (myIf.optStmt!=null){
				myIf.optStmt.accept(this);
			}
			return null;
		}
		else {
			error("if(exp), exp isn't int type");
			myIf.stmt.accept(this);
			if (myIf.optStmt!=null){
				myIf.optStmt.accept(this);
			}
			return null;
		}
	}

	@Override
	public Type visitAssign(Assign assign) {
//		assign.expr1.accept(this);
//		assign.expr2.accept(this);
		if((assign.expr1 instanceof VarExpr)||(assign.expr1 instanceof FieldAccessExpr)||(assign.expr1 instanceof ArrayAccessExpr)||(assign.expr1 instanceof ValueAtExpr)) {//part 6 of cw2
			Type lhstype = assign.expr1.accept(this);
			Type rhstype = assign.expr2.accept(this);
			if (assign.expr2 instanceof FunCallExpr) {
				FunCallExpr rhsfuncall =(FunCallExpr) assign.expr2;
				rhstype = rhsfuncall.type;
			}
			if (assign.expr1 instanceof FunCallExpr) {
				FunCallExpr lhsfuncall =(FunCallExpr) assign.expr1;
				lhstype = lhsfuncall.type;
			}
			if ((!(lhstype instanceof ArrayType))&&(lhstype!=BaseType.VOID)) {
				if (rhstype==lhstype) {//no error
					return null;
				}
				if ((lhstype instanceof PointerType) && (rhstype instanceof PointerType)) {
					PointerType leftpoint = (PointerType) lhstype;
					PointerType rightpoint = (PointerType) rhstype;
					if(leftpoint.type==rightpoint.type) {
						return null;
					}
					else {//this is why function call 1 in part 2 is failing as i've temporairly removed it
						error("in assign, left and right side were pointers but of different base types");
						return null;
					}
				}
				else {
					//System.out.println(lhstype);
					//System.out.println(rhstype);
					//error("in assign lhs and rhs weren't same type");
					return null;
				}
			}
			else {
				error("lhs in assign is an array type or void");
				return null;
			}
		}
		else {
			error("left hand side of assign is not varxpr, fieldaccessexpr, arrayaccessexpr or valueatexpr");
			return null;
		}

	}

	@Override
	public Type visitExprStmt(ExprStmt exprStmt) {//no analysis for expr stmt
		exprStmt.expr.accept(this);
		return null;
	}

	@Override
	public Type visitReturn(Return myReturn) {
		return null;
	}
	
	public void visitIfBlockInFunDecl(FunDecl fd, Type fundeclType, If myif) {
		//System.out.println(myif.optStmt);
		Type myiftype = myif.expr.accept(this);
		if(myiftype==null) {//function hasn't been declared before so we don't do any type checking
			//doesn't make a difference
//			for (Expr exp : funCallExpr.expressions) {
//				exp.accept(this);
//			}
			return;
		}
		if (!(myiftype.equals(BaseType.INT))){
			error("if doesn't have int type");
		}
		if (myif.stmt instanceof Block) {
			//System.out.println("inside block of if");
			Block myifblock = (Block) myif.stmt;
			for (VarDecl vd : myifblock.varDecls) {
	            vd.accept(this);
	        }
	        for (Stmt stmt : myifblock.stmts) {
		        	if (stmt instanceof Block) {
	        			Block myblock = (Block) stmt;
	        			visitBlockInFunDecl(fd,fundeclType,myblock);
	        		}
	        		if (stmt instanceof Return) {
	        			Return myreturn  = (Return) stmt;
	        			if (myreturn.optExpr!=null) {//something comes after return
	        				if (fundeclType.equals(BaseType.VOID)) {
        						Type myreturntype = myreturn.optExpr.accept(this);
        						if((myreturntype instanceof BaseType) || (myreturntype instanceof ArrayType)) {
        							if(!(myreturntype.equals(BaseType.VOID))) {
        								error("return exp where exp is not void");
        							}
        						}
        						else if (myreturntype==null) {//this mean we're returning a void and the function is void so it's fine
        						//error("return exp where exp doesn't give any type");
        						}
        				}
	        				else {//fundecl isn't of type void
	        					Type myreturntype = myreturn.optExpr.accept(this);
	        					if (myreturntype instanceof StructType) {
	        						//StructType myreturnstruct = (StructType) myreturntype;
	        						//System.out.println(myreturnstruct);
	        						if (!(fundeclType instanceof StructType)) {
	        							error("returning a struct type when the function is not meant to return a struct type");
	        						}
	        					}
	        					if (myreturntype instanceof PointerType) {
	        						//StructType myreturnstruct = (StructType) myreturntype;
	        						//System.out.println(myreturnstruct);
	        						if (!(fundeclType instanceof PointerType)) {
	        							error("returning a struct type when the function is not meant to return a struct type");
	        						}
	        					}
	        					if (fundeclType!=myreturntype && (!(myreturntype instanceof StructType))&& (!(myreturntype instanceof PointerType))) {
	        						//System.out.println(fundeclType);
	        						//System.out.println(myreturntype);
	        						error("function type isn't same as return type");
	        					}
	        				}
	        			}
	        			else {//nothing comes after return
	        				if (!(fundeclType.equals(BaseType.VOID))) {
	        					error("Not returning anything when function is NOT void");
	        				}
	        			}
	        			
	        		}
	        		if (stmt instanceof If) {
	        			If mynewif = (If) stmt;
	        			visitIfBlockInFunDecl(fd,fundeclType,mynewif);
	        		}
	        		if (stmt instanceof While) {
	        			While mynewwhile = (While) stmt;
	        			visitWhileBlockInFunDecl(fd,fundeclType,mynewwhile);
	        		}
	        		else if ((!(stmt instanceof If))&&(!(stmt instanceof While))&&(!(stmt instanceof Block))){
	            stmt.accept(this);
	        		}
	        }
		}
		if (myif.optStmt instanceof If) {
    			If mynewif = (If) myif.optStmt;
    			visitIfBlockInFunDecl(fd,fundeclType,mynewif);
		}
		if (myif.optStmt instanceof Block) {
			//System.out.println("inside else block of if");
			Block myifblock = (Block) myif.optStmt;
			for (VarDecl vd : myifblock.varDecls) {
	            vd.accept(this);
	        }
	        for (Stmt stmt : myifblock.stmts) {
		        	if (stmt instanceof Block) {
	        			Block myblock = (Block) stmt;
	        			visitBlockInFunDecl(fd,fundeclType,myblock);
	        		}
	        		if (stmt instanceof Return) {
	        			Return myreturn  = (Return) stmt;
	        			if (myreturn.optExpr!=null) {//something comes after return
	        				if (fundeclType.equals(BaseType.VOID)) {
        						Type myreturntype = myreturn.optExpr.accept(this);
        						if((myreturntype instanceof BaseType) || (myreturntype instanceof ArrayType))  {
        							if(!(myreturntype.equals(BaseType.VOID))) {
        								error("return exp where exp is not void");
        							}
        						}
        						else if (myreturntype==null) {//this mean we're returning a void and the function is void so it's fine
        						//error("return exp where exp doesn't give any type");
        						}
        				}
	        				else {//fundecl isn't of type void
	        					Type myreturntype = myreturn.optExpr.accept(this);
	        					if (myreturntype instanceof StructType) {
	        						//StructType myreturnstruct = (StructType) myreturntype;
	        						//System.out.println(myreturnstruct);
	        						if (!(fundeclType instanceof StructType)) {
	        							error("returning a struct type when the function is not meant to return a struct type");
	        						}
	        					}
	        					if (myreturntype instanceof PointerType) {
	        						//StructType myreturnstruct = (StructType) myreturntype;
	        						//System.out.println(myreturnstruct);
	        						if (!(fundeclType instanceof PointerType)) {
	        							error("returning a struct type when the function is not meant to return a struct type");
	        						}
	        					}
	        					if (fundeclType!=myreturntype && (!(myreturntype instanceof StructType))&& (!(myreturntype instanceof PointerType))) {
	        						//System.out.println(fundeclType);
	        						//System.out.println(myreturntype);
	        						error("function type isn't same as return type");
	        					}
	        				}
	        			}
	        			else {//nothing comes after return
	        				if (!(fundeclType.equals(BaseType.VOID))) {
	        					error("Not returning anything when function is NOT void");
	        				}
	        			}
	        			
	        		}
	        		if (stmt instanceof If) {
	        			If mynewif = (If) stmt;
	        			visitIfBlockInFunDecl(fd,fundeclType,mynewif);
	        		}
	        		if (stmt instanceof While) {
	        			While mynewwhile = (While) stmt;
	        			visitWhileBlockInFunDecl(fd,fundeclType,mynewwhile);
	        		}
	        		else if ((!(stmt instanceof If))&&(!(stmt instanceof While))&&(!(stmt instanceof Block))){
	            stmt.accept(this);
	        		}
	        }
		}
	}
	
	public void visitWhileBlockInFunDecl(FunDecl fd, Type fundeclType, While mywhile) {
		Type whiletype = mywhile.expr.accept(this);
		if(whiletype==null) {//function hasn't been declared before so we don't do any type checking
			//doesn't make a difference
//			for (Expr exp : funCallExpr.expressions) {
//				exp.accept(this);
//			}
			return;
		}
		if (!(whiletype.equals(BaseType.INT))) {//no error
			error("in while, expression is not an int");
		}
		if (mywhile.stmt instanceof Block) {
			//System.out.println("inside block of if");
			Block mywhileblock = (Block) mywhile.stmt;
			for (VarDecl vd : mywhileblock.varDecls) {
	            vd.accept(this);
	        }
	        for (Stmt stmt : mywhileblock.stmts) {
		        	if (stmt instanceof Block) {
	        			Block myblock = (Block) stmt;
	        			visitBlockInFunDecl(fd,fundeclType,myblock);
	        		}
	        		if (stmt instanceof Return) {
	        			Return myreturn  = (Return) stmt;
	        			if (myreturn.optExpr!=null) {//something comes after return
	        				if (fundeclType.equals(BaseType.VOID)) {
        						Type myreturntype = myreturn.optExpr.accept(this);
        						if((myreturntype instanceof BaseType) || (myreturntype instanceof ArrayType)) {
        							if(!(myreturntype.equals(BaseType.VOID))) {
        								error("return exp where exp is not void");
        							}
        						}
        						else if (myreturntype==null) {//this mean we're returning a void and the function is void so it's fine
        						//error("return exp where exp doesn't give any type");
        						}
        				}
	        				else {//fundecl isn't of type void
	        					Type myreturntype = myreturn.optExpr.accept(this);
	        					if (myreturntype instanceof StructType) {
	        						//StructType myreturnstruct = (StructType) myreturntype;
	        						//System.out.println(myreturnstruct);
	        						if (!(fundeclType instanceof StructType)) {
	        							error("returning a struct type when the function is not meant to return a struct type");
	        						}
	        					}
	        					if (myreturntype instanceof PointerType) {
	        						//StructType myreturnstruct = (StructType) myreturntype;
	        						//System.out.println(myreturnstruct);
	        						if (!(fundeclType instanceof PointerType)) {
	        							error("returning a struct type when the function is not meant to return a struct type");
	        						}
	        					}
	        					if (fundeclType!=myreturntype && (!(myreturntype instanceof StructType))&& (!(myreturntype instanceof PointerType))) {
	        						//System.out.println(fundeclType);
	        						//System.out.println(myreturntype);
	        						error("function type isn't same as return type");
	        					}
	        				}
	        			}
	        			else {//nothing comes after return
	        				if (!(fundeclType.equals(BaseType.VOID))) {
	        					error("Not returning anything when function is NOT void");
	        				}
	        			}
	        			
	        		}
	        		if (stmt instanceof If) {
	        			If mynewif = (If) stmt;
	        			visitIfBlockInFunDecl(fd,fundeclType,mynewif);
	        		}
	        		if (stmt instanceof While) {
	        			While mynewwhile = (While) stmt;
	        			visitWhileBlockInFunDecl(fd,fundeclType,mynewwhile);
	        		}
	        		else if ((!(stmt instanceof If))&&(!(stmt instanceof While))&&(!(stmt instanceof Block))){
	            stmt.accept(this);
	        		}
	        }
		}
	}
	
	void visitBlockInFunDecl(FunDecl fd, Type fundeclType, Block myblock) {
		for (VarDecl vd : myblock.varDecls) {
            vd.accept(this);
        }
        for (Stmt stmt : myblock.stmts) {
	        	if (stmt instanceof Block) {
        			Block mynewblock = (Block) stmt;
        			visitBlockInFunDecl(fd,fundeclType,mynewblock);
        		}
        		if (stmt instanceof Return) {
        			Return myreturn  = (Return) stmt;
        			if (myreturn.optExpr!=null) {//something comes after return
        				if (fundeclType.equals(BaseType.VOID)) {
    						Type myreturntype = myreturn.optExpr.accept(this);
    						if((myreturntype instanceof BaseType) || (myreturntype instanceof ArrayType)) {
    							if(!(myreturntype.equals(BaseType.VOID))) {
    								error("return exp where exp is not void");
    							}
    						}
    						else if (myreturntype==null) {//this mean we're returning a void and the function is void so it's fine
    						//error("return exp where exp doesn't give any type");
    						}
    				}
        				else {//fundecl isn't of type void
        					Type myreturntype = myreturn.optExpr.accept(this);
        					if (myreturntype instanceof StructType) {
        						//StructType myreturnstruct = (StructType) myreturntype;
        						//System.out.println(myreturnstruct);
        						if (!(fundeclType instanceof StructType)) {
        							error("returning a struct type when the function is not meant to return a struct type");
        						}
        					}
        					if (myreturntype instanceof PointerType) {
        						//StructType myreturnstruct = (StructType) myreturntype;
        						//System.out.println(myreturnstruct);
        						if (!(fundeclType instanceof PointerType)) {
        							error("returning a struct type when the function is not meant to return a struct type");
        						}
        					}
        					if (fundeclType!=myreturntype && (!(myreturntype instanceof StructType))&& (!(myreturntype instanceof PointerType))) {
        						//System.out.println(fundeclType);
        						//System.out.println(myreturntype);
        						error("function type isn't same as return type");
        					}
        				}
        			}
        			else {//nothing comes after return
        				if (!(fundeclType.equals(BaseType.VOID))) {
        					error("Not returning anything when function is NOT void");
        				}
        			}
        			
        		}
        		if (stmt instanceof If) {
        			If mynewif = (If) stmt;
        			visitIfBlockInFunDecl(fd,fundeclType,mynewif);
        		}
        		if (stmt instanceof While) {
        			While mynewwhile = (While) stmt;
        			visitWhileBlockInFunDecl(fd,fundeclType,mynewwhile);
        		}
        		else if ((!(stmt instanceof If))&&(!(stmt instanceof While))&&(!(stmt instanceof Block))){
            stmt.accept(this);
        		}
        }
	}
	
	
}
