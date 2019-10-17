package sem;

import java.util.HashMap;
import java.util.Map;

public class Scope {
	private Scope outer;
	private Map<String, Symbol> symbolTable;
	
	public Scope(Scope outer) { //make a scope given an outer scope
		this.outer = outer; 
		this.symbolTable= new HashMap<String, Symbol>();
	}
	public Symbol lookup(String name) {//given a name it will return the symbol corresponding to its name
		Symbol s = this.symbolTable.get(name);
		if (s!=null) {
			//System.out.println("symbol found in lookup");
			return s;//gets the symbol in the symbol table with the same name
		}
		if (outer!=null){//symbol wans't found in current scope's symbol table, if the outer scope is not null (i.e we're not in the top scope)
			Symbol outersymbol = outer.lookup(name);//searches in the scope above
			return outersymbol;
		}
		else {
			return null;
		}
	}
	
	public Symbol lookupCurrent(String name) {//looks for symbol in current scope and nowhere else! Used for declarations because you can declare inside a scope
		//System.out.println(symbolTable);
		Symbol s = this.symbolTable.get(name);
		//System.out.println(s);
		if (s!=null) {
			return s;//gets the symbol in the symbol table with the same name
		}
		else {
			//System.out.println("couldn't find in symbol table");
			return null;
		}
	}
	
	public void put(Symbol sym) {//allows us to put a symbol in the current scope
		symbolTable.put(sym.name, sym);
	}
}
