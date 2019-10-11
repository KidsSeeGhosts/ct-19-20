package ast;

public class BinOp extends Expr {
	public final Expr lhs;
	public final Expr rhs;
	public final Op op;
	
	
	public BinOp(Op op, Expr lhs, Expr rhs) {
		this.op=op;
		this.rhs=rhs;
		this.lhs=lhs;
	}


	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visitBinOp(this);
	}
}
