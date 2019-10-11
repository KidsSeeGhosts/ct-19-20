package ast;

public class ArrayAccessExpr extends Expr{
	
	//ArrayAccessExpr ::= Expr Expr
	
	public final Expr expr1; //this is the array
	public final Expr expr2; //this is the index
	
	public ArrayAccessExpr(Expr expr1, Expr expr2) {
		this.expr1=expr1;
		this.expr2=expr2;
		
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visitArrayAccessExpr(this);
	}

}
