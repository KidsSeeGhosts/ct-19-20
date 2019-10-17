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
		for (VarDecl vd : st.varDecls) {//copying varDecls thing from program printer given
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
	        	Scope oldScope = scope;
	    		Scope newScope = new Scope(oldScope);
	    		scope=newScope;//change our current scope to the new scope
	        	for (VarDecl vd : fd.vardecls) {
	                vd.accept(this);
	        }
	        fd.block.accept(this);
	        scope = oldScope;
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
		Type charstar = new PointerType(BaseType.CHAR);
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
		if (s != null) {//it was found
			//System.out.println(s.isVar);
			error("This variable was already declared inside the current scope");
		}
		else {//allows us to declare this variable inside the current scope
			scope.put(new VarSymbol(vd));
			//System.out.println("Var Added to the symbol table");
		}
		return null;
	}

	@Override
	public Void visitVarExpr(VarExpr v) {
		//System.out.println("visit var expr");
		Symbol vs = scope.lookup(v.name);//looks in current then outer scope.
		//System.out.println(vs);
		if (vs == null) {
			error("VarExpr not found");
		}
//		else if(vs.isVar==false){ Don't think I need to worry about this until type check analysis
//			 error("Got a symbol but it's not a variable");
//		}
		else {   // everything is fine , record var decl
			v.vd = ((VarSymbol) vs).vd;//the variable stores its declaration
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
		Symbol fcs = scope.lookup(funCallExpr.string);//looks in current then outer scope.
		if (fcs == null) {
			error("FunCall has not been declared before");
		}
//		else if(fcs.isProc==false){ Not worrying about this until type check bit
//			 error("Got a symbol but it's not a function");
//		}
		else {   // everything is fine , record var decl
			funCallExpr.funDecl = ((ProcSymbol) fcs).fd;//the variable stores its declaration
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
	    fieldAccessExpr.expr.accept(this);
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
