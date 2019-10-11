package ast;

public class If extends Stmt{
//If         ::= Expr Stmt [Stmt]
	
	public final Expr expr;
	public final Stmt stmt;
	public final Stmt optStmt;
	
	public If(Expr expr, Stmt stmt, Stmt optStmt) {
		this.expr=expr;
		this.stmt=stmt;
		this.optStmt=optStmt;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visitIf(this);
	}
}
