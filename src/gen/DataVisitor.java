package gen;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ast.ASTVisitor;
import ast.ArrayAccessExpr;
import ast.ArrayType;
import ast.Assign;
import ast.BaseType;
import ast.BinOp;
import ast.Block;
import ast.ChrLiteral;
import ast.Expr;
import ast.ExprStmt;
import ast.FieldAccessExpr;
import ast.FunCallExpr;
import ast.FunDecl;
import ast.If;
import ast.IntLiteral;
import ast.PointerType;
import ast.Program;
import ast.Return;
import ast.SizeOfExpr;
import ast.Stmt;
import ast.StrLiteral;
import ast.StructType;
import ast.StructTypeDecl;
import ast.TypeCastExpr;
import ast.ValueAtExpr;
import ast.VarDecl;
import ast.VarExpr;
import ast.While;


public class DataVisitor implements ASTVisitor<Register>{

	public HashMap<String,StructTypeDecl> mystds = new HashMap<String,StructTypeDecl>();
	public HashMap<String,Integer> mystructsizes = new HashMap<String,Integer>();
	PrintWriter writer;
	public int noOfStrings=1;

    public DataVisitor(PrintWriter writer, Program program) {
    	 this.writer=writer;
    	visitProgram(program);
    	//writer.close();
	}

	@Override
    public Register visitBlock(Block b) {
        // to complete
        for (VarDecl vd : b.varDecls) {
            vd.accept(this);
        }
        for (Stmt stmt : b.stmts) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public Register visitFunDecl(FunDecl fd) {
        fd.type.accept(this);
        if (!fd.vardecls.isEmpty()){
	        	for (VarDecl vd : fd.vardecls) {
	                vd.accept(this);
	            }
	            fd.block.accept(this);
	            return null;
        }
        fd.block.accept(this);
        return null;
    }

    @Override
    public Register visitProgram(Program p) {
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
    public Register visitVarDecl(VarDecl vd){
//    	if (vd.type instanceof StructType) {
//    		StructType myStructType = (StructType) vd.type;
//    		if(mystds.containsKey(myStructType.string)) {
//    			//System.out.println(mystds.get(myStructType.string).structSize);
//    			myStructType.std=mystds.get(myStructType.string);
//    		}
//    	}
        vd.type.accept(this);
        return null;
    }

    @Override
    public Register visitVarExpr(VarExpr v) {
        return null;
    }

    @Override
    public Register visitBaseType(BaseType bt) {
        return null;
    }

    @Override //StructTypeDecl ::= StructType VarDecl*
    public Register visitStructTypeDecl(StructTypeDecl st) {

//
//		VarDecl structName = new VarDecl(st.structType, st.structType.string);//the name of the struct is now a variable
//		structName.localOrGlobal="struct";
//		VarExpr structVar = new VarExpr(st.structType.string);
    		int structSpace = 0;
    		st.structType.accept(this);
    		for (VarDecl vd : st.varDecls) {//copying varDecls thing from program printer given
        		if(vd.type==BaseType.INT) {
        			vd.structOffset=structSpace;
        			structSpace=structSpace-4;//4 bytes for an int word
        			vd.structOffSetWordSize = 4;
        		}
        		if(vd.type==BaseType.CHAR) {
        			vd.structOffset=structSpace;
        			structSpace=structSpace-4;//4 bytes for an char word
        			vd.structOffSetWordSize = 4;
        		}
        		if (vd.type instanceof PointerType) {
        			vd.structOffset=structSpace;
        			structSpace=structSpace-4;//4 bytes for a pointer word
        			vd.structOffSetWordSize = 4;
        		}
        		if (vd.type instanceof ArrayType) {
        			ArrayType myarray = (ArrayType) vd.type;
        			vd.structOffset=structSpace;
        			structSpace=structSpace-(myarray.i*4);//4 bytes for each item in array
        			vd.structOffSetWordSize = (myarray.i*4);
        		}
        }
    		st.structSize=-structSpace;
    		writer.println(st.structType.string+": .space "+(-structSpace));
    		mystds.put(st.structType.string, st);
    		mystructsizes.put(st.structType.string, -structSpace);
        return null;
    }

	@Override
	public Register visitPointerType(PointerType pt) {
		pt.type.accept(this);
		return null;
	}

	@Override
	public Register visitStructType(StructType structType) {
		return null;
	}

	@Override
	public Register visitArrayType(ArrayType arrayType) {
		arrayType.type.accept(this);
		return null;
	}

	@Override
	public Register visitIntLiteral(IntLiteral intLiteral) {
		return null;
	}

	@Override
	public Register visitStrLiteral(StrLiteral strLiteral) {
		writer.println("stringLabel"+noOfStrings+": .asciiz \""+strLiteral.string+"\"");
		writer.println(".align 8");
		strLiteral.label=("stringLabel"+noOfStrings);
		noOfStrings++;
		return null;
	}

	@Override
	public Register visitChrLiteral(ChrLiteral chrLiteral) {
		return null;
	}

	@Override //FunCallExpr ::= String Expr*
	public Register visitFunCallExpr(FunCallExpr funCallExpr) {
		for (Expr exp : funCallExpr.expressions){//this is probably not how you print the expressions list
            exp.accept(this);
		}
		return null;
	}

	@Override //BinOp      ::= Expr Op Expr
	public Register visitBinOp(BinOp binOp) {
		binOp.lhs.accept(this);
		binOp.rhs.accept(this);
		return null;
	}

	@Override //ArrayAccessExpr ::= Expr Expr
	public Register visitArrayAccessExpr(ArrayAccessExpr arrayAccessExpr) {
		arrayAccessExpr.expr1.accept(this);
		arrayAccessExpr.expr2.accept(this);
		return null;
	}
	
	//FieldAccessExpr ::= Expr String
	@Override
	public Register visitFieldAccessExpr(FieldAccessExpr fieldAccessExpr) {
	    fieldAccessExpr.expr.accept(this);
		//fieldAccessExpr.type.accept(this); see a line of code like this I need to figure it out. I think it's what would automatically gives the types before the bracket
		return null;
	}

	@Override   //ValueAtExpr ::= Expr
	public Register visitValueAtExpr(ValueAtExpr valueAtExpr) {//This Expression() stuff I've been doing is definitely wrong but it's temporary
		valueAtExpr.expr.accept(this);
		return null;
	}

	@Override
	public Register visitSizeOfExpr(SizeOfExpr sizeOfExpr) {
		sizeOfExpr.type.accept(this);//testing what this does
		return null;
	}
	//TypecastExpr ::= Type Expr

	@Override
	public Register visitTypeCastExpr(TypeCastExpr typeCastExpr) {//doing this one a different way so i can see how it comes out for testing reasons
		typeCastExpr.type.accept(this);
		typeCastExpr.expr.accept(this);
		return null;
	}

	@Override ////While      ::= Expr Stmt
	public Register visitWhile(While myWhile) {
		myWhile.expr.accept(this);
		myWhile.stmt.accept(this);
		return null;
	}

	@Override ////If         ::= Expr Stmt [Stmt]
	public Register visitIf(If myIf) {
		myIf.expr.accept(this);
		myIf.stmt.accept(this);
		if (myIf.optStmt!=null){
			myIf.optStmt.accept(this);
		}
		return null;
	}

	@Override //Expr Expr
	public Register visitAssign(Assign assign) {
		assign.expr1.accept(this);
		assign.expr2.accept(this);
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
			myReturn.optExpr.accept(this);
		}
		return null;
	}
}
