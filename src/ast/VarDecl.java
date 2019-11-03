package ast;

public class VarDecl implements ASTNode {
    public Type type;
    public final String varName;
    public int frameoffset;
    public int structOffset;
    public int wordsize;
    public int structOffSetWordSize;
    public String localOrGlobal;

    public VarDecl(Type type, String varName) {
	    this.type = type;
	    this.varName = varName;
    }

     public <T> T accept(ASTVisitor<T> v) {
    	 	return v.visitVarDecl(this);
    }
}
