package ast;

public class ExprStmt extends Stmt{
	
	//ExprStmt ::= Expr
	
	public final Expr expr;
	
	public ExprStmt(Expr expr) {
		this.expr=expr;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visitExprStmt(this);
	}

}
