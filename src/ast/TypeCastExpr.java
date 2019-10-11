package ast;

public class TypeCastExpr extends Expr{

	//TypecastExpr ::= Type Expr
	
	public final Type type;
	public final Expr expr;
	
	public TypeCastExpr(Type type, Expr expr) {
		this.type=type;
		this.expr=expr;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visitTypeCastExpr(this);
	}
	
	
}
