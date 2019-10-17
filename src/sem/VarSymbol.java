package sem;

import ast.VarDecl;

public class VarSymbol extends Symbol{
	
	//public String name;

	public VarDecl vd;
	
	public VarSymbol(VarDecl vd){ 
		//super(vd);
		this.vd = vd; 
		this.name = vd.varName;
		this.isVar=true;
		this.isProc=false;
		this.isStruct=false;
	
	}
	
	private boolean isVar() {
		return isVar;
	}
	private boolean isProc() {
		return isProc;
	}
	private boolean isStruct() {
		return isStruct;
	}
	
	
	
}
