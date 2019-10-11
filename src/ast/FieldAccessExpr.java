package ast;

public class FieldAccessExpr extends Expr{

	//FieldAccessExpr ::= Expr String // the Expr represents the structure, the String represents the name of the field
	
	public final Expr expr;
	public final String string;
	
	public FieldAccessExpr(Expr expr, String string) {
		this.expr=expr;
		this.string=string;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visitFieldAccessExpr(this);
	}
	
	
}
