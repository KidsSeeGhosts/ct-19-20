package ast;

import java.util.List;

public class FunDecl implements ASTNode {
    public final Type type;
    public final String  name;
    public final List<VarDecl> vardecls;
    public final Block block;
	public int stackOffset;
	public int finalframeoffset;

    public FunDecl(Type type, String name, List<VarDecl> vardecls, Block block) {
	    this.type = type;
	    this.name = name;
	    this.vardecls = vardecls;
	    this.block = block;
    }

    public <T> T accept(ASTVisitor<T> v) {
	return v.visitFunDecl(this);
    }
}
