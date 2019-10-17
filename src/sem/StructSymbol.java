package sem;

import ast.StructTypeDecl;

public class StructSymbol extends Symbol{
	
	//public String name;

	public StructTypeDecl std;
	
	public StructSymbol(StructTypeDecl std){ 
		this.std = std; 
		this.name = std.structType.string;
		this.isStruct=true;
		this.isVar=false;
		this.isProc=false;
	
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
