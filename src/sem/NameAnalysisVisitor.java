package sem;

import ast.*;

import java.util.ArrayList;
import java.util.List;

public class NameAnalysisVisitor extends BaseSemanticVisitor<Void> {
	
	Scope scope;
	public NameAnalysisVisitor(Scope scope) {//one we're told to have in the slides
		this.scope = scope ; 
	}

	public NameAnalysisVisitor() {//one that works with semantic analyzer
		//this(new Scope());
	}

	@Override
	public Void visitBaseType(BaseType bt) {
		return null;
	}

	@Override
	public Void visitStructTypeDecl(StructTypeDecl st) {
		//System.out.println("inside struct type decl");
		st.structType.accept(this);
		Symbol s = scope.lookupCurrent(st.structType.string);
		if (s != null) {//it was found
			error("This struct was already declared inside the current scope");
		}
		else {//allows us to declare this variable inside the current scope
			scope.put(new StructSymbol(st));
			//System.out.println("Struct Added to the symbol table");
		}
		Scope oldScope = scope;
		Scope newScope = new Scope(oldScope);
		scope=newScope;//change our current scope to the new scope
		//scope.put(new StructSymbol(st));//inside of this scope put the struct symbol in so none of the var decls can have same name as the struct
		for (VarDecl vd : st.varDecls) {//copying varDecls thing from program printer given
			if (vd.type instanceof StructType){
				StructType thestructtype = (StructType) vd.type;
				if(thestructtype.string.equals(st.structType.string)) {
					error("can't declare struct inside of struct with same name!");
				}
			}
            vd.accept(this);
        }
		scope=oldScope;
		return null;
	}
	
	@Override
	public Void visitBlock(Block b) {
		Scope oldScope = scope;
		Scope newScope = new Scope(oldScope);
		scope=newScope;//change our current scope to the new scope
        for (VarDecl vd : b.varDecls) {
            vd.accept(this);
        }
        for (Stmt stmt : b.stmts) {
            stmt.accept(this);
        }
		scope = oldScope; 
		return null;
	}

	@Override
	public Void visitFunDecl(FunDecl fd) {
		fd.type.accept(this);//accept the type
        Symbol s = scope.lookupCurrent(fd.name);
        if (scope.outer==null) {
			if (s != null) {//it was found and we are in global socpe
				if (s.isStruct) {
					scope.put(new ProcSymbol(fd));
					return null;
				}
				else {
					error("Fun decl in global scope already been declared");
					return null;
				}
			}
		}
        //System.out.println(fd.name);
		if (s != null) {//it was found
			error("This function was already declared inside the current scope");
		}
		else {//allows us to declare this variable inside the current scope
			scope.put(new ProcSymbol(fd));
			//System.out.println("Function Added to the symbol table");
		}
		//end of name analysis
		//open a new scope before doing the params
        if (!fd.vardecls.isEmpty()){//if vardecls isn't empty visit them
        		//System.out.println("vardecls not empty in fun decl");
	        	Scope oldScope = scope;
	    		Scope newScope = new Scope(oldScope);
	    		scope=newScope;//change our current scope to the new scope
	        	for (VarDecl vd : fd.vardecls) {
	                vd.accept(this);
	        }
	        	//end of params, starting the block
	            for (VarDecl vd : fd.block.varDecls) {
	                vd.accept(this);
	            }
	            for (Stmt stmt : fd.block.stmts) {
	                stmt.accept(this);
	            }
	            scope=oldScope;
	        return null;
        }
        fd.block.accept(this);
		//do everything in the fun  decl
		return null;
	}


	@Override
	public Void visitProgram(Program p) {
		Scope firstscope = new Scope(null);//visit program is the top scope
		scope = firstscope;
		
		List<VarDecl> emptyVarDecls = new ArrayList<VarDecl>();
		List<Stmt> emptyStmts = new ArrayList<Stmt>();
		Block emptyblock = new Block(emptyVarDecls,emptyStmts);
		scope.put(new ProcSymbol((new FunDecl(BaseType.INT,"read_i",emptyVarDecls, emptyblock))));
		scope.put(new ProcSymbol((new FunDecl(BaseType.CHAR,"read_c",emptyVarDecls, emptyblock))));
		
		List<VarDecl> mcmallocvardecls = new ArrayList<VarDecl>();
		mcmallocvardecls.add(new VarDecl(BaseType.INT,"size"));
		Type voidstar = new PointerType(BaseType.VOID);
		scope.put(new ProcSymbol((new FunDecl(voidstar,"mcmalloc",mcmallocvardecls, emptyblock))));
		
		List<VarDecl> printSvardecls = new ArrayList<VarDecl>();
		PointerType charstar = new PointerType(BaseType.CHAR);
		printSvardecls.add(new VarDecl(charstar,"s"));
		scope.put(new ProcSymbol((new FunDecl(BaseType.VOID,"print_s",printSvardecls, emptyblock))));
		
		List<VarDecl> printIvardecls = new ArrayList<VarDecl>();
		printIvardecls.add(new VarDecl(BaseType.INT,"i"));
		scope.put(new ProcSymbol((new FunDecl(BaseType.VOID,"print_i",printIvardecls, emptyblock))));
		
		List<VarDecl> printCvardecls = new ArrayList<VarDecl>();
		printCvardecls.add(new VarDecl(BaseType.CHAR,"c"));
		scope.put(new ProcSymbol((new FunDecl(BaseType.VOID,"print_c",printCvardecls, emptyblock))));
		
		//Finished defining the 6 functions defined as part of our standard language
//		p.funDecls.add(new FunDecl(BaseType.VOID,"print_s",printsvardecls, emptyblock));
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
	public Void visitVarDecl(VarDecl vd) {
		//System.out.println(vd.varName);
		Symbol s = scope.lookupCurrent(vd.varName);
		if (scope.outer==null) {
			if (s != null) {//it was found and we are in global socpe
				if (s.isStruct) {
					StructSymbol thestructsymbol = (StructSymbol) s;
					StructType mynewstructtype = new StructType(vd.varName);
					StructTypeDecl mystd = new StructTypeDecl(mynewstructtype,thestructsymbol.std.varDecls);
					mynewstructtype.std = mystd;
					scope.put(new StructSymbol(mystd));//struct x y, x was found to be a struct and y was not found already declared in that struct
					return null;
				}
				else {
					error("Var decl in global scope already been declared");
					return null;
				}
			}
		}
		if (s != null) {//it was found
			//System.out.println(s.isVar);
			error("This variable was already declared inside the current scope");
			return null;
		}
		else {//allows us to declare this variable inside the current scope
//			Symbol checksymbol = scope.lookup(vd.varName);//check the global scope because can't have a variable with same name as something from global scope
//			if (checksymbol==null || checksymbol.isStruct || checksymbol.isVar){//nothing with same name in global scope
			if(vd.type instanceof StructType) {//struct x y; x must have been defined as a struct
				StructType mystructtype = (StructType) vd.type;
				Symbol mystructsymbol = scope.lookup(mystructtype.string);
				if (mystructsymbol!=null) {// symbol was found
					if(mystructsymbol.isStruct) {//struct symbol was found
						StructSymbol thestructsymbol = (StructSymbol) mystructsymbol;
						StructType mynewstructtype = new StructType(vd.varName);
						StructTypeDecl mystd = new StructTypeDecl(mynewstructtype,thestructsymbol.std.varDecls);
						mynewstructtype.std = mystd;
						scope.put(new StructSymbol(mystd));//struct x y, x was found to be a struct and y was not found already declared in that struct
						return null;
					}
					else {
						error("In struct x y; x was found but it was not a struct");
						return null;
					}
				}
				else {// symbol not found
					error("StructType variable can't be declared because in struct x y; x not found");
					return null;
				}
			}
			scope.put(new VarSymbol(vd));
			return null;
//			}
//			else {
//				error("Variable has same name as something in global scope");
//				return null;
//			}
			//System.out.println("Var Added to the symbol table");
		}
	}

	@Override
	public Void visitVarExpr(VarExpr v) {
		//System.out.println();
		//System.out.println("inside visit var expr");
		Symbol vs = scope.lookup(v.name);//looks in current then outer scope.
		//System.out.println(vs);
		if (vs == null) {
			//System.out.println(v.name);
			error("Var has not been declared before");
		}
//		else if(vs.isVar==false){ Don't think I need to worry about this until type check analysis
//			 error("Got a symbol but it's not a variable");
//		}
		else {   // everything is fine , record var decl
			if (vs instanceof VarSymbol){
			v.vd = ((VarSymbol) vs).vd;//the variable stores its declaration
			//System.out.println(v.vd);
			}
			if (vs instanceof StructSymbol) {
				//System.out.println("found struct symbol");
				VarDecl myvd = new VarDecl(((StructSymbol) vs).std.structType,v.name);
				v.vd = myvd;
			}
			if (vs instanceof ProcSymbol) {
				VarDecl myvd = new VarDecl(((ProcSymbol) vs).fd.type,v.name);
				v.vd = myvd;
			}
			
		}
		return null;
	}

	@Override
	public Void visitPointerType(PointerType pt) {
		pt.type.accept(this);
		return null;
	}

	@Override
	public Void visitStructType(StructType structType) {
		return null;
	}

	@Override
	public Void visitArrayType(ArrayType arrayType) {
		arrayType.type.accept(this);
		return null;
	}

	@Override
	public Void visitIntLiteral(IntLiteral intLiteral) {
		return null;
	}

	@Override
	public Void visitStrLiteral(StrLiteral strLiteral) {
		return null;
	}

	@Override
	public Void visitChrLiteral(ChrLiteral chrLiteral) {
		return null;
	}

	@Override
	public Void visitFunCallExpr(FunCallExpr funCallExpr) {
		Symbol currentcheck = scope.lookupCurrent(funCallExpr.string);
		if (currentcheck!=null) {//found the variable in localscope
			if(currentcheck.isVar) {
				error("Can't do a funcall on a variable in the local scope");
			}
		}
		Symbol fcs = scope.lookup(funCallExpr.string);//looks in current then outer scope.
		if (fcs == null) {
			error("FunCall has not been declared before");
			for (Expr exp : funCallExpr.expressions){//this is probably not how you print the expressions list
	            exp.accept(this);
			}
			return null;
			
		}
//		else if(fcs.isProc==false){ Not worrying about this until type check bit
//			 error("Got a symbol but it's not a function");
//		}
		if (currentcheck!=null) {
		if (!(currentcheck.isVar)){   // everything is fine , record var decl
			funCallExpr.funDecl = ((ProcSymbol) fcs).fd;//the variable stores its declaration
		}
		}
		if (currentcheck==null) {
				funCallExpr.funDecl = ((ProcSymbol) fcs).fd;//the variable stores its declaration
		}
		for (Expr exp : funCallExpr.expressions){//this is probably not how you print the expressions list
            exp.accept(this);
		}
		return null;
	}

	@Override
	public Void visitBinOp(BinOp binOp) {
		binOp.lhs.accept(this);
		binOp.rhs.accept(this);
		return null;
	}

	@Override
	public Void visitArrayAccessExpr(ArrayAccessExpr arrayAccessExpr) {
		arrayAccessExpr.expr1.accept(this);
		arrayAccessExpr.expr2.accept(this);
		return null;
	}

	@Override
	public Void visitFieldAccessExpr(FieldAccessExpr fieldAccessExpr) {
		//System.out.println(fieldAccessExpr.expr instanceof VarExpr);
		if (fieldAccessExpr.expr instanceof VarExpr) {
			VarExpr myvarexpr = (VarExpr) fieldAccessExpr.expr;
			//System.out.println(myvarexpr.name);
			Symbol potentialstructsymbol = scope.lookup(myvarexpr.name);
			if (potentialstructsymbol==null){
				error("Field access expression x.y, x was not defined anywhere");
				fieldAccessExpr.expr.accept(this);
				return null;
			}
			if (potentialstructsymbol.isStruct) {
				//System.out.println("it was a struct");
				StructSymbol mystructsymbol = (StructSymbol) potentialstructsymbol;
				StructTypeDecl typetosave = new StructTypeDecl(mystructsymbol.std.structType,mystructsymbol.std.varDecls);
				StructType finalstructtype = new StructType(myvarexpr.name);
				finalstructtype.std=typetosave;
				fieldAccessExpr.type=finalstructtype;
				//System.out.println(mystructsymbol.std.varDecls.contains(VarDecl));
				for (VarDecl vd : mystructsymbol.std.varDecls) {
					if (vd.varName.equals(fieldAccessExpr.string)) {
						//System.out.println("found it!");
						fieldAccessExpr.expr.accept(this);
						return null;
					}
				}
				error("Field Access Expression x.y, y was not defined in the struct x.");
				fieldAccessExpr.expr.accept(this);
				return null;
			}
			error("Field access expression x.y, x was not defined as a struct");
			fieldAccessExpr.expr.accept(this);
			return null;
		}
		if (fieldAccessExpr.expr instanceof FunCallExpr) {
			FunCallExpr myfuncallexpr = (FunCallExpr) fieldAccessExpr.expr;
			//System.out.println(myvarexpr.name);
			Symbol potentialstructsymbol = scope.lookup(myfuncallexpr.string);
			if (potentialstructsymbol==null){
				error("Field access expression x.y, x was not defined anywhere");
				fieldAccessExpr.expr.accept(this);
				return null;
			}
			ProcSymbol myprocsymbol = (ProcSymbol) potentialstructsymbol;
			if (myprocsymbol.fd.type instanceof StructType) {
				//System.out.println("it was a struct");
				StructType mystructtype = (StructType) myprocsymbol.fd.type;
				Symbol mystructsymbol = scope.lookup(mystructtype.string);
				StructSymbol myproperstructsymbol = (StructSymbol) mystructsymbol;
				StructTypeDecl typetosave = new StructTypeDecl(myproperstructsymbol.std.structType,myproperstructsymbol.std.varDecls);
				StructType finalstructtype = new StructType(mystructtype.string);
				finalstructtype.std=typetosave;
				fieldAccessExpr.type=finalstructtype;
				for (VarDecl vd : myproperstructsymbol.std.varDecls) {
					if (vd.varName.equals(fieldAccessExpr.string)) {
						System.out.println("found it!");
						//fieldAccessExpr.expr.accept(this);
						return null;
					}
				}
				error("Field Access Expression x.y, y was not defined in the struct x.");
				fieldAccessExpr.expr.accept(this);
				return null;
			}
			error("Field access expression x.y, x was not defined as a struct");
			fieldAccessExpr.expr.accept(this);
			return null;
		}
		
		return null;
	}

	@Override
	public Void visitValueAtExpr(ValueAtExpr valueAtExpr) {
		valueAtExpr.expr.accept(this);
		return null;
	}

	@Override
	public Void visitSizeOfExpr(SizeOfExpr sizeOfExpr) {
		sizeOfExpr.type.accept(this);//testing what this does
		return null;
	}

	@Override
	public Void visitTypeCastExpr(TypeCastExpr typeCastExpr) {
		typeCastExpr.type.accept(this);
		typeCastExpr.expr.accept(this);
		return null;
	}

	@Override
	public Void visitWhile(While myWhile) {
		myWhile.expr.accept(this);
		myWhile.stmt.accept(this);
		return null;
	}

	@Override
	public Void visitIf(If myIf) {
		myIf.expr.accept(this);
		myIf.stmt.accept(this);
		if (myIf.optStmt!=null){
			myIf.optStmt.accept(this);
		}
		return null;
	}

	@Override
	public Void visitAssign(Assign assign) {
		assign.expr1.accept(this);
		assign.expr2.accept(this);
		return null;
	}

	@Override
	public Void visitExprStmt(ExprStmt exprStmt) {
		exprStmt.expr.accept(this);
		return null;
	}

	@Override
	public Void visitReturn(Return myReturn) {
		if (myReturn.optExpr!=null) {
			myReturn.optExpr.accept(this);
		}
		return null;
	}



}
