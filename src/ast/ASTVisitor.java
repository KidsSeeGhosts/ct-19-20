package ast;

public interface ASTVisitor<T> {
    public T visitBaseType(BaseType bt);
    public T visitStructTypeDecl(StructTypeDecl st);
    public T visitBlock(Block b);
    public T visitFunDecl(FunDecl p);
    public T visitProgram(Program p);
    public T visitVarDecl(VarDecl vd);
    public T visitVarExpr(VarExpr v);
    public T visitPointerType(PointerType pt);
	public T visitStructType(StructType structType);
	public T visitArrayType(ArrayType arrayType);
	public T visitIntLiteral(IntLiteral intLiteral);
	public T visitStrLiteral(StrLiteral strLiteral);
	public T visitChrLiteral(ChrLiteral chrLiteral);
	public T visitFunCallExpr(FunCallExpr funCallExpr);
	public T visitBinOp(BinOp binOp);
	public T visitArrayAccessExpr(ArrayAccessExpr arrayAccessExpr);
	public T visitFieldAccessExpr(FieldAccessExpr fieldAccessExpr);
	public T visitValueAtExpr(ValueAtExpr valueAtExpr);
	public T visitSizeOfExpr(SizeOfExpr sizeOfExpr);
	public T visitTypeCastExpr(TypeCastExpr typeCastExpr);
	public T visitWhile(While myWhile);
	public T visitIf(If myIf);
	public T visitAssign(Assign assign);
	public T visitExprStmt(ExprStmt exprStmt);
	public T visitReturn(Return myReturn);

    // to complete ... (should have one visit method for each concrete AST node class)
}
