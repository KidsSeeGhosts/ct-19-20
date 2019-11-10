package ast;

import java.util.List;

public class StructTypeDecl implements ASTNode {


    // to be completed done
	
	//StructTypeDecl ::= StructType VarDecl*
	
	public final StructType structType;
	public final List<VarDecl> varDecls;
	public int structSize;
	public String localOrGlobal;
	
	public StructTypeDecl(StructType structType, List<VarDecl> varDecls) {
		this.structType=structType;
		this.varDecls=varDecls;
	}
	
	
    public <T> T accept(ASTVisitor<T> v) {
        return v.visitStructTypeDecl(this);
    }

}
