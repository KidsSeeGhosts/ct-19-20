package ast;

public class StructType implements Type {

	public final String string;
	public StructTypeDecl std;//to be filed in by name analyser
	
	public StructType(String string) {
		this.string=string;
	}

	public <T> T accept(ASTVisitor<T> v) {
		return v.visitStructType(this);
	}
	
	
}
