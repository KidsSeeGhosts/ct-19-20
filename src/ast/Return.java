package ast;

public class Return extends Stmt {
//Return     ::= [Expr]
	
	public final Expr optExpr;
	
	public Return(Expr optExpr) {
		this.optExpr=optExpr;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visitReturn(this);
	}
	
	
	
}
