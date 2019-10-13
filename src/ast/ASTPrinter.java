package ast;

import java.io.PrintWriter;

public class ASTPrinter implements ASTVisitor<Void> {

    private PrintWriter writer;

    public ASTPrinter(PrintWriter writer) {
            this.writer = writer;
    }

    @Override
    public Void visitBlock(Block b) {
        writer.print("Block("); //copying way program is done
        // to complete
        String delimiter = "";
        for (VarDecl vd : b.varDecls) {
            writer.print(delimiter);
            delimiter = ",";
            vd.accept(this);
        }
        for (Stmt stmt : b.stmts) {
            writer.print(delimiter);
            delimiter = ",";
            stmt.accept(this);
        }
        writer.print(")");
        return null;
    }

    @Override
    public Void visitFunDecl(FunDecl fd) {
        writer.print("FunDecl(");
        fd.type.accept(this);
        writer.print(","+fd.name+",");
        String delimiter = "";
        if (!fd.vardecls.isEmpty()){
	        	for (VarDecl vd : fd.vardecls) {
	                writer.print(delimiter);
	                delimiter = ",";
	                vd.accept(this);
	            }
	        		writer.print(",");
	            fd.block.accept(this);
	            writer.print(")");
	            return null;
        }
        fd.block.accept(this);
        writer.print(")");
        return null;
    }

    @Override
    public Void visitProgram(Program p) {
        writer.print("Program(");
        String delimiter = "";
        for (StructTypeDecl std : p.structTypeDecls) {
            writer.print(delimiter);
            delimiter = ",";
            std.accept(this);
        }
        for (VarDecl vd : p.varDecls) {
            writer.print(delimiter);
            delimiter = ",";
            vd.accept(this);
        }
        for (FunDecl fd : p.funDecls) {
            writer.print(delimiter);
            delimiter = ",";
            fd.accept(this);
        }
        writer.print(")");
	    writer.flush();
        return null;
    }

    @Override
    public Void visitVarDecl(VarDecl vd){
        writer.print("VarDecl(");
        vd.type.accept(this);
        writer.print(","+vd.varName);
        writer.print(")");
        return null;
    }

    @Override
    public Void visitVarExpr(VarExpr v) {
        writer.print("VarExpr(");
        writer.print(v.name);
        writer.print(")");
        return null;
    }

    @Override
    public Void visitBaseType(BaseType bt) {
        // to complete ...
    		//writer.print("BaseType(");
    		writer.print(bt.toString());//gives name of the enum constant. not sure if i use .name or .toString
    		//writer.print(")");
        return null;
    }

    @Override //StructTypeDecl ::= StructType VarDecl*
    public Void visitStructTypeDecl(StructTypeDecl st) {
        // to complete ...
    		writer.print("StructTypeDecl(");
    		String delimiter = "";
    		writer.print("StructType(");
    		writer.print(st.structType); //not sure if I'm meant to accept this
    		writer.print(")");
    		writer.print(",");
    		for (VarDecl vd : st.varDecls) {//copying varDecls thing from program printer given
                writer.print(delimiter);
                delimiter = ",";
                vd.accept(this);
            }
    		writer.print(")");//last bracket for the entire struct type decl
    		writer.flush(); //flushing the stream because i think youre meant to do that after dealing with a list of types
        return null;
    }

	@Override
	public Void visitPointerType(PointerType pt) {
		writer.print("PointerType(");
		writer.print(pt.type);
		writer.print(")");
		return null;
	}

	@Override
	public Void visitStructType(StructType structType) {
		writer.print("StuctType(");
		writer.print("String(");
		writer.print(structType.string);
		writer.print("))");
		return null;
	}

	@Override
	public Void visitArrayType(ArrayType arrayType) {
		writer.print("ArrayType(");
		writer.print("Type(");
		writer.print(arrayType.type+"),");
		writer.print("int(");
		writer.print(arrayType.i+")");
		writer.print(")");
		return null;
	}

	@Override
	public Void visitIntLiteral(IntLiteral intLiteral) {
		writer.print("IntLiteral(");
		writer.print(intLiteral.i);
		writer.print(")");
		return null;
	}

	@Override
	public Void visitStrLiteral(StrLiteral strLiteral) {
		writer.print("StrLiteral(");
		writer.print(strLiteral.string+")");
		return null;
	}

	@Override
	public Void visitChrLiteral(ChrLiteral chrLiteral) {
		writer.print("ChrLiteral(");
		writer.print(chrLiteral.c+")");
		return null;
	}

	@Override //FunCallExpr ::= String Expr*
	public Void visitFunCallExpr(FunCallExpr funCallExpr) {
		String delimiter = "";
		writer.print("FunCallExpr(");
		//writer.print("String(");
		writer.print(funCallExpr.string);
		//writer.print(",");//might need to say EXPR( here
		for (Expr exp : funCallExpr.expressions){//this is probably not how you print the expressions list
			writer.print(",");
			writer.print(delimiter);
            delimiter = ",";
            exp.accept(this);
		}
		writer.print(")");
		return null;
	}

	@Override //BinOp      ::= Expr Op Expr
	public Void visitBinOp(BinOp binOp) {
		writer.print("BinOp(");
		binOp.lhs.accept(this);
		writer.print(",");
		writer.print(binOp.op+",");
		binOp.rhs.accept(this);
		writer.print(")");
		return null;
	}

	@Override //ArrayAccessExpr ::= Expr Expr
	public Void visitArrayAccessExpr(ArrayAccessExpr arrayAccessExpr) {
		writer.print("ArrayAccessExpr(");
		writer.print(arrayAccessExpr.expr1+",");
		writer.print(arrayAccessExpr.expr2+")");
		return null;
	}
	
	//FieldAccessExpr ::= Expr String
	@Override
	public Void visitFieldAccessExpr(FieldAccessExpr fieldAccessExpr) {
		writer.print("FieldAccessExpr");
		writer.print("Expression(");
		writer.print(fieldAccessExpr.expr+"),String(");
		//fieldAccessExpr.type.accept(this); see a line of code like this I need to figure it out. I think it's what would automatically gives the types before the bracket
		writer.print(fieldAccessExpr.string+")");
		writer.print(")");
		return null;
	}

	@Override   //ValueAtExpr ::= Expr
	public Void visitValueAtExpr(ValueAtExpr valueAtExpr) {//This Expression() stuff I've been doing is definitely wrong but it's temporary
		writer.print("ValueAtExpr(");
		writer.print(valueAtExpr.expr+")");
		return null;
	}

	@Override
	public Void visitSizeOfExpr(SizeOfExpr sizeOfExpr) {
		writer.print("SizeOfExpr(");
		sizeOfExpr.type.accept(this);//testing what this does
		writer.print(")");
		return null;
	}
	//TypecastExpr ::= Type Expr

	@Override
	public Void visitTypeCastExpr(TypeCastExpr typeCastExpr) {//doing this one a different way so i can see how it comes out for testing reasons
		writer.print("TypeCastExpr(");
		typeCastExpr.type.accept(this);
		writer.print(",");
		typeCastExpr.expr.accept(this);
		writer.print(")");
		return null;
	}

	@Override ////While      ::= Expr Stmt
	public Void visitWhile(While myWhile) {
		writer.print("While(");
		myWhile.expr.accept(this);
		writer.print(",");
		myWhile.stmt.accept(this);
		writer.print(")");
		return null;
	}

	@Override ////If         ::= Expr Stmt [Stmt]
	public Void visitIf(If myIf) {
		writer.print("If(");
		myIf.expr.accept(this);
		writer.print(",");
		myIf.stmt.accept(this);
		if (myIf.optStmt!=null){
			writer.print(",");
			myIf.optStmt.accept(this);
		}
		writer.print(")");
		return null;
	}

	@Override //Expr Expr
	public Void visitAssign(Assign assign) {
		writer.print("Assign(");
		assign.expr1.accept(this);
		writer.print(",");
		assign.expr2.accept(this);
		writer.print(")");;
		return null;
	}

	@Override
	public Void visitExprStmt(ExprStmt exprStmt) {
		writer.print("ExprStmt(");
		exprStmt.expr.accept(this);
		writer.print(")");
		return null;
	}

	@Override
	public Void visitReturn(Return myReturn) {
		writer.print("Return(");
		if (myReturn.optExpr!=null) {
			myReturn.optExpr.accept(this);
		}
		writer.print(")");
		return null;
	}
}
