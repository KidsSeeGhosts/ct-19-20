package ast;

public class Assign extends Stmt {

	//Assign     ::= Expr Expr
	
	public final Expr expr1;
	public final Expr expr2;
	
	public Assign(Expr expr1, Expr expr2) {
		this.expr1=expr1;
		this.expr2=expr2;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visitAssign(this);
	}
	
}
