package sem;

import ast.FunDecl;

public class ProcSymbol extends Symbol{
	
	//public String name;

	public FunDecl fd;
	
	public ProcSymbol(FunDecl fd){ 
		//super(fd);
		this.fd = fd; 
		this.name = fd.name;
		this.isProc=true;
		this.isVar=false;
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
