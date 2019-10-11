package ast;

import java.util.List;

public class FunCallExpr extends Expr{
	
	public final String string;
	public final List<Expr> expressions;
	
	public FunCallExpr(String string, List<Expr> expressions) {
		this.string=string;
		this.expressions=expressions;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visitFunCallExpr(this);
	}

}
