package parser;

import ast.*;

import lexer.Token;
import lexer.Tokeniser;
import lexer.Token.TokenClass;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @author cdubach
 */
public class Parser {

    private Token token;

    // use for backtracking (useful for distinguishing decls from procs when parsing a program for instance)
    private Queue<Token> buffer = new LinkedList<>();

    private final Tokeniser tokeniser;



    public Parser(Tokeniser tokeniser) {
        this.tokeniser = tokeniser;
    }

    public Program parse() {
        // get the first token
        nextToken();

        return parseProgram();
    }

    public int getErrorCount() {
        return error;
    }

    private int error = 0;
    private Token lastErrorToken;

    private void error(TokenClass... expected) {

        if (lastErrorToken == token) {
            // skip this error, same token causing trouble
            return;
        }

        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (TokenClass e : expected) {
            sb.append(sep);
            sb.append(e);
            sep = "|";
        }
        System.out.println("Parsing error: expected ("+sb+") found ("+token+") at "+token.position);

        error++;
        lastErrorToken = token;
    }

    /*
     * Look ahead the i^th element from the stream of token.
     * i should be >= 1
     */
    private Token lookAhead(int i) {
        // ensures the buffer has the element we want to look ahead
        while (buffer.size() < i)
            buffer.add(tokeniser.nextToken());
        assert buffer.size() >= i;

        int cnt=1;
        for (Token t : buffer) {
            if (cnt == i)
                return t;
            cnt++;
        }

        assert false; // should never reach this
        return null;
    }


    /*
     * Consumes the next token from the tokeniser or the buffer if not empty.
     */
    private void nextToken() {
        if (!buffer.isEmpty())
            token = buffer.remove();
        else
            token = tokeniser.nextToken();
    }

    /*
     * If the current token is equals to the expected one, then skip it, otherwise report an error.
     * Returns the expected token or null if an error occurred.
     */
    private Token expect(TokenClass... expected) {
        for (TokenClass e : expected) {
            if (e == token.tokenClass) {
                Token cur = token;
                nextToken();
                return cur;
            }
        }

        error(expected);
        return null;
    }

    /*
    * Returns true if the current token is equals to any of the expected ones.
    */
    private boolean accept(TokenClass... expected) {
        boolean result = false;
        for (TokenClass e : expected)
            result |= (e == token.tokenClass);
        return result;
    }


    private Program parseProgram() {
        parseIncludes();
        List<StructTypeDecl> stds = parseStructDeclsRep();
        List<VarDecl> vds = parseVarDeclsRep();
        List<FunDecl> fds = parseFunDeclsRep();
        expect(TokenClass.EOF);
        return new Program(stds, vds, fds);
    }

    // includes are ignored, so does not need to return an AST node
    private void parseIncludes() {
        if (accept(TokenClass.INCLUDE)) {
            nextToken();
            expect(TokenClass.STRING_LITERAL);
            parseIncludes();
        }
    }

    private List<StructTypeDecl> parseStructDeclsRep() {
    		List<StructTypeDecl> stds=new ArrayList<StructTypeDecl>();
    		Token checkToken = lookAhead(2); //lookahead 1 woukd be identifier of struct type
    		while (accept(TokenClass.STRUCT) && checkToken.tokenClass==TokenClass.LBRA) {
        		StructTypeDecl std = parseStructDecls();
        		stds.add(std);
        		checkToken = lookAhead(2);
        	}
        	return stds;
    }
    

    
    private StructTypeDecl parseStructDecls() {
    		StructType st = parseStructType();
    		expect(TokenClass.LBRA);
    		List<VarDecl> varDeclsRepPlus = parseVarDeclRepPlus();
	    	expect(TokenClass.RBRA);
	    	expect(TokenClass.SC);
	    	return new StructTypeDecl(st, varDeclsRepPlus);
    }
    
    private StructType parseStructType() {
        expect(TokenClass.STRUCT);
        String string = token.data;//gets the identifier name
        expect(TokenClass.IDENTIFIER);
        return new StructType(string);
    }
    

    
    private List<VarDecl> parseVarDeclRepPlus(){
    		List<VarDecl> varDeclsRepPlus=new ArrayList<VarDecl>();
    		VarDecl vd = parseVarDecls();
    		varDeclsRepPlus.add(vd);
    		Token checktoken1 = lookAhead(1);
        	Token checktoken2 = lookAhead(2);
        	Token checktoken3 = lookAhead(3);//for if we have a star
        	Token checktoken4 = lookAhead(4);
        	while  (((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass!=TokenClass.ASTERIX) && ((checktoken2.tokenClass== TokenClass.SC) || (checktoken2.tokenClass== TokenClass.LSBR)))
	    			||//int abc;
	    			((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass==TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.SC) || (checktoken3.tokenClass== TokenClass.LSBR)))
	    			||//int * abc;
	    			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass!=TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.SC) || (checktoken3.tokenClass== TokenClass.LSBR)))
	    			||//struct abc abc;
	    			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass==TokenClass.ASTERIX) && ((checktoken4.tokenClass== TokenClass.SC) || (checktoken4.tokenClass== TokenClass.LSBR)))
	    			//struct* abc abc;
	    			){
        		vd = parseVarDecls();
        		varDeclsRepPlus.add(vd);
        		checktoken1 = lookAhead(1);
            	checktoken2 = lookAhead(2);
            	checktoken3 = lookAhead(3);
            	checktoken4 = lookAhead(4);
        		
        	}
        	return varDeclsRepPlus;
    }
    

    //public VarDecl(Type type, String varName) {
    private VarDecl parseVarDecls() {
        Type type= parseType();
        String varName = token.data;
        expect(TokenClass.IDENTIFIER);
        if (accept(TokenClass.SC)) {
        		expect(TokenClass.SC);
        		return new VarDecl(type,varName);
        }
        if (accept(TokenClass.LSBR)) {
	    		nextToken();
	    		expect(TokenClass.INT_LITERAL);
	    		expect(TokenClass.RSBR);
	    		expect(TokenClass.SC);
	    		return new VarDecl(type,varName);
	    }
        else {
        		return null;
        }
    }
    
    
    //Type        ::= BaseType | PointerType | StructType | ArrayType in the abstract grammar
    private Type parseType() { // I put parse staropt inside my parseType
    	Token checkToken = lookAhead(1);
		if (accept(TokenClass.INT)) {
			if (checkToken.tokenClass==TokenClass.ASTERIX) {
				nextToken();
			    expect(TokenClass.ASTERIX);
			    return new PointerType(BaseType.INT);
			}
			else {
				return BaseType.INT;
			}
		}
		if (accept(TokenClass.CHAR)) {
				if (checkToken.tokenClass==TokenClass.ASTERIX) {
					nextToken();
			        expect(TokenClass.ASTERIX);
			        return new PointerType(BaseType.CHAR);
				}
				else {
					return BaseType.CHAR;
				}
		}
		if (accept(TokenClass.VOID)) {
				if (checkToken.tokenClass==TokenClass.ASTERIX) {
					nextToken();
			        expect(TokenClass.ASTERIX);
			        return new PointerType(BaseType.VOID);
				}
				else {
					return BaseType.VOID;
				}
		}
		if (accept(TokenClass.STRUCT)) {
			StructType st = parseStructType();
			if (accept(TokenClass.ASTERIX)) {
				nextToken();
		        expect(TokenClass.ASTERIX);
		        return new PointerType(st);
			}
			else {
				return st;
			}
		}
		else {
			return null;
		}
	}
    
    
    private List<VarDecl> parseVarDeclsRep() {
    	List<VarDecl> varDeclsRep=new ArrayList<VarDecl>();
    	Token checktoken1 = lookAhead(1);
    	Token checktoken2 = lookAhead(2);
    	Token checktoken3 = lookAhead(3);//for if we have a star
    	Token checktoken4 = lookAhead(4);
	    	while  (((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass!=TokenClass.ASTERIX) && ((checktoken2.tokenClass== TokenClass.SC) || (checktoken2.tokenClass== TokenClass.LSBR)))
	    			||//int abc;
	    			((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass==TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.SC) || (checktoken3.tokenClass== TokenClass.LSBR)))
	    			||//int * abc;
	    			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass!=TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.SC) || (checktoken3.tokenClass== TokenClass.LSBR)))
	    			||//struct abc abc;
	    			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass==TokenClass.ASTERIX) && ((checktoken4.tokenClass== TokenClass.SC) || (checktoken4.tokenClass== TokenClass.LSBR)))
	    			//struct* abc abc;
	    			){
	    		VarDecl vd = parseVarDecls();
	    		varDeclsRep.add(vd);
	    		checktoken1 = lookAhead(1);
	        	checktoken2 = lookAhead(2);
	        	checktoken3 = lookAhead(3);//for if we have a star
	        	checktoken4 = lookAhead(4);
	    	}
	    	return varDeclsRep;
    }
    


    private List<FunDecl> parseFunDeclsRep() {
    	List<FunDecl> funDeclsRep=new ArrayList<FunDecl>();
    	Token checktoken1 = lookAhead(1);
    	Token checktoken2 = lookAhead(2);
    	Token checktoken3 = lookAhead(3);//for if we have a star
    	Token checktoken4 = lookAhead(4);
	    	while  (((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass!=TokenClass.ASTERIX) && ((checktoken2.tokenClass== TokenClass.LPAR)))
	    			||//int abc;
	    			((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass==TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.LPAR)))
	    			||//int * abc;
	    			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass!=TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.LPAR)))
	    			||//struct abc abc;
	    			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass==TokenClass.ASTERIX) && ((checktoken4.tokenClass== TokenClass.LPAR)))
	    			//struct* abc abc;
	    			){
	    		FunDecl fd = parseFunDecls();
	    		funDeclsRep.add(fd);
	    		checktoken1 = lookAhead(1);
	        	checktoken2 = lookAhead(2);
	        	checktoken3 = lookAhead(3);//for if we have a star
	        	checktoken4 = lookAhead(4);
	    	}
	    	return funDeclsRep;
    }
    
    
//    
    //FunDecl(Type type, String name, List<VarDecl> params, Block block) { from abstract grammar
    private FunDecl parseFunDecls() {
    		Type type = parseType();
    		String name = token.data;
    		expect(TokenClass.IDENTIFIER);
    		expect(TokenClass.LPAR);
    		List<VarDecl> params = parseParams();
    		expect(TokenClass.RPAR);
    		Block block = parseBlock();
    		return new FunDecl(type,name,params,block);
    }
    

    private List<VarDecl> parseParams(){//this one is very different to the original
    		List<VarDecl> params=new ArrayList<VarDecl>();
	    	if (accept(TokenClass.INT) || accept(TokenClass.CHAR) || accept(TokenClass.VOID) || accept(TokenClass.STRUCT)) {
	    		VarDecl vd = parseVarDecls();
	    		params.add(vd);
	    		while (accept(TokenClass.COMMA)) {//Got ride of parse params list opt just implemented it here instead
	    			nextToken();
	    			vd = parseVarDecls();
		    		params.add(vd);
	    		}
	    		return params;
	    		
	    	}
	    	else {//empty params
	    		return params;
	    	}
    }
    
    
    private Block parseBlock() {//Block(List<VarDecl> varDecls, List<Stmt> stmts) in abstract grammar
    		List<VarDecl> varDecls=new ArrayList<VarDecl>();
    		List<Stmt> stmts=new ArrayList<Stmt>();
    		expect(TokenClass.LBRA);
    		varDecls = parseVarDeclsRep();
    		stmts = parseStmtRep();
    		expect(TokenClass.RBRA);
    		return new Block(varDecls,stmts);
    }
    
    
    private List<Stmt> parseStmtRep(){
    	List<Stmt> stmtRep = new ArrayList<Stmt>();
    	while (accept(TokenClass.LPAR) || //if start of exp
    			accept(TokenClass.IDENTIFIER) ||
    			accept(TokenClass.INT_LITERAL) ||
    			accept(TokenClass.MINUS) ||
    			accept(TokenClass.CHAR_LITERAL) ||
    			accept(TokenClass.STRING_LITERAL) ||
    			accept(TokenClass.ASTERIX) || accept(TokenClass.SIZEOF) ||//exp ones end here
    			accept(TokenClass.WHILE) || accept(TokenClass.IF) ||
    			accept(TokenClass.RETURN) || accept(TokenClass.LBRA)){
    		Stmt stmt = parseStmt();
    		stmtRep.add(stmt);
    }
    	return stmtRep;
    }

    private Stmt parseStmt() {
	    	if (accept(TokenClass.LBRA)) {//if start of block
	    		Block block = parseBlock();
	    		return block;
	    	}
	    	if (accept(TokenClass.WHILE)) {//public While(Expr expr, Stmt stmt) abstract grammar
				nextToken();
				expect(TokenClass.LPAR);//removed an if here i didn't feel was necessary
		    		Expr expr = parseExp();
		    		expect(TokenClass.RPAR);
		    		Stmt stmt = parseStmt();
		    		return new While(expr,stmt);
		}
	    	if (accept(TokenClass.IF)) { // If(Expr expr, Stmt stmt, Stmt optStmt) - abstract grammar
				nextToken();
				expect(TokenClass.LPAR);
				Expr expr = parseExp();
				expect(TokenClass.RPAR);
				Stmt stmt = 	parseStmt();//getting rid of parseElseStmtOpt here
				if (accept(TokenClass.ELSE)) {
					nextToken();
					Stmt optStmt = parseStmt();
					return new If(expr,stmt,optStmt);
				}
				else {
					Stmt optStmt=null;//I have no idea if this is valid or if this will have consequences
					return new If(expr,stmt,optStmt);
				}
				
		}
	    	if (accept(TokenClass.LPAR) || //if start of exp FOR ASSIGN OR EXPRSTMT
	    			accept(TokenClass.IDENTIFIER) ||
	    			accept(TokenClass.INT_LITERAL) ||
	    			accept(TokenClass.MINUS) ||
	    			accept(TokenClass.CHAR_LITERAL) ||
	    			accept(TokenClass.STRING_LITERAL) ||
	    			accept(TokenClass.ASTERIX) || accept(TokenClass.SIZEOF)){
	    			Expr expr1 = parseExp();
	    			if (accept(TokenClass.ASSIGN)) {//ASSIGN
					nextToken();
					Expr expr2 = parseExp();
					expect(TokenClass.SC);
					return new Assign(expr1,expr2);
				}
	    			if (accept(TokenClass.SC)) {//EXPRSTMT
	    				expect(TokenClass.SC);
	    				return new ExprStmt(expr1);
	    			}
	    			
	    	}
			if (accept(TokenClass.RETURN)) { //Return(Expr optExpr)
				nextToken();
				if (accept(TokenClass.LPAR) || //if start of exp
		    			accept(TokenClass.IDENTIFIER) ||
		    			accept(TokenClass.INT_LITERAL) ||
		    			accept(TokenClass.MINUS) ||
		    			accept(TokenClass.CHAR_LITERAL) ||
		    			accept(TokenClass.STRING_LITERAL) ||
		    			accept(TokenClass.ASTERIX) || accept(TokenClass.SIZEOF)){
		    			parseExp();
		    			Expr exprOpt = parseExp();
		    			expect(TokenClass.SC);
					return new Return(exprOpt);
				}
				else {
					Expr exprOpt=null;
					return new Return(exprOpt);//not sure if this will work either
				}
		}
			else {
				return null;
			}
    }
    
    private Expr parseExp() {
    	if (accept(TokenClass.IDENTIFIER)){//NEED TO CHECK FOR FUNCALL HERE. I think an identifier is a varExpr.
			Token checktoken=lookAhead(1);
			if (checktoken.tokenClass== TokenClass.LPAR) {
				FunCallExpr fc = parseFunCall();
				Expr expr = parseExpAlt(fc);
				return expr;
			}
			else {//identifier then not a left bracket means we have a variable expression
				VarExpr varexpr = new VarExpr(token.data);
				expect(TokenClass.IDENTIFIER);
				Expr expr = parseExpAlt(varexpr);
				return expr;
			}
	}
		if (accept(TokenClass.MINUS)){//"-" exp exp' UNARY MINUS going to be a BINOP in our abstract grammar
			IntLiteral zero = new IntLiteral(0);
     	nextToken();
     	Expr rhs = parseExp();
     	BinOp binOp = new BinOp(Op.SUB,zero,rhs);     //public BinOp(Op op, Expr lhs, Expr rhs)
     	Expr expr = parseExpAlt(binOp);
     	return expr;
   }
		if (accept(TokenClass.LPAR)){//"(" exp ")" expAlt check for type TYPE CAST
		Token checktoken=lookAhead(1);
		if ((checktoken.tokenClass== TokenClass.INT) || (checktoken.tokenClass== TokenClass.CHAR) 
			|| (checktoken.tokenClass== TokenClass.VOID) || (checktoken.tokenClass== TokenClass.STRUCT)){ //if typecast
			TypeCastExpr typecastexpr = parseTypeCast();
			Expr expr = parseExpAlt(typecastexpr);
			return expr;
		}
		else {
			nextToken();
			Expr notTypeCastExpr = parseExp();
			expect(TokenClass.RPAR);
			Expr expr = parseExpAlt(notTypeCastExpr);
			return expr;
		}
	}
		if (accept(TokenClass.ASTERIX)){//valueat POINTER INDIRECTION for VALUEATEXPR
		ValueAtExpr valueat = parseValueAt();
		Expr expr = parseExpAlt(valueat);
		return expr;
	}
		if (accept(TokenClass.SIZEOF)){//SIZE OF TYPE
  		 SizeOfExpr sizeofexpr = parseSizeOf();
  		 Expr expr = parseExpAlt(sizeofexpr);
  		 return expr;
	}
		if (accept(TokenClass.INT_LITERAL)){//pretty much all these are gonna check for array access, field access then operators in order
		IntLiteral myintliteral = new IntLiteral(Integer.parseInt(token.data));//no clue if this will work
		nextToken();
		Expr expr  =parseExpAlt(myintliteral);
		return expr;
	}
		if (accept(TokenClass.CHAR_LITERAL)){
			ChrLiteral mycharliteral =  new ChrLiteral(token.data.charAt(0));//ChrLiteral(char c) abstract gramamr THIS MIGHT NOT WORK FOR ESCAPE CHARACTERS BUT WE'LL FIND OUT
         	nextToken();
         	Expr expr = parseExpAlt(mycharliteral);
         	return expr;
    }
		if (accept(TokenClass.STRING_LITERAL)){
			StrLiteral mystringlit = new StrLiteral(token.data);
		nextToken();
     Expr expr = parseExpAlt(mystringlit);
     return expr;
	}
		
		else {
			return null;
		}
    }
    
    private FunCallExpr parseFunCall() {
	    	String string = token.data;
	    	expect(TokenClass.IDENTIFIER);
	    	expect(TokenClass.LPAR);
	    List<Expr> expressions=	parseFunArgsOpt();
	    	expect(TokenClass.RPAR);
	    	return new FunCallExpr(string,expressions);
    }
    
    private List<Expr> parseFunArgsOpt(){
    	List<Expr> funArgsOpt = new ArrayList<Expr>();
	    	while (accept(TokenClass.LPAR) || //if start of exp
	    			accept(TokenClass.IDENTIFIER) ||
	    			accept(TokenClass.INT_LITERAL) ||
	    			accept(TokenClass.MINUS) ||
	    			accept(TokenClass.CHAR_LITERAL) ||
	    			accept(TokenClass.STRING_LITERAL) ||
	    			accept(TokenClass.ASTERIX) || accept(TokenClass.SIZEOF)) {
	    		Expr expr = parseExp();
	    		funArgsOpt.add(expr);
	    	}
	    	return funArgsOpt;
    }
    
    private TypeCastExpr parseTypeCast() { //public TypeCastExpr(Type type, Expr expr) {
	    	expect(TokenClass.LPAR);
	    	Type type = parseType();
	    	expect(TokenClass.RPAR);
	    	Expr expr = parseExp();
	    	return new TypeCastExpr(type,expr);
    }
    
    //abstract grammar is ValueAtExpr(Expr expr)
    private ValueAtExpr parseValueAt() {
		expect(TokenClass.ASTERIX);
		Expr expr = parseExp();
		return new ValueAtExpr(expr);
    }
    
    
    private SizeOfExpr parseSizeOf() {
    	expect(TokenClass.SIZEOF);
    	expect(TokenClass.LPAR);
    Type type =	parseType();
    expect(TokenClass.RPAR);
    return new SizeOfExpr(type);
    }
    
    private Expr parseExpAlt(Expr e) {
		if (accept(TokenClass.LSBR)) {//FOR ARRAY ACCESS EXPR
		nextToken();
		Expr indexexpr = parseExp();
		expect(TokenClass.RSBR);
		ArrayAccessExpr arrayaccessexpr= new ArrayAccessExpr(e,indexexpr);
		Expr expr = parseExpAlt(arrayaccessexpr);
		return expr;
	}
		if (accept(TokenClass.DOT)) {//FOR FIELD ACCESS (STRUCT MEMBER ACCESS) abstract is FieldAccessExpr(Expr expr, String string) 
		nextToken();
		String string = token.data;
		expect(TokenClass.IDENTIFIER);
		FieldAccessExpr fieldaccessexpr = new FieldAccessExpr(e,string);
		Expr expr = parseExpAlt(fieldaccessexpr);
		return expr;
	}
		if (accept(TokenClass.DIV,TokenClass.ASTERIX,TokenClass.REM)) {//FACTOR -  TIER 3
			if (token.tokenClass==TokenClass.DIV) {
				Op myOp = Op.DIV;
				nextToken();
				Expr rhs = parseExp();
				BinOp binOp = new BinOp(myOp,e,rhs);
				Expr expr = parseExpAlt(binOp);
				return expr;
			}
			if (token.tokenClass==TokenClass.ASTERIX) {
				Op myOp = Op.MUL;
				nextToken();
				Expr rhs = parseExp();
				BinOp binOp = new BinOp(myOp,e,rhs);
				Expr expr = parseExpAlt(binOp);
				return expr;
			}
			if (token.tokenClass==TokenClass.REM) {
				Op myOp = Op.MOD;
				nextToken();
				Expr rhs = parseExp();
				BinOp binOp = new BinOp(myOp,e,rhs);
				Expr expr = parseExpAlt(binOp);
				return expr;
			}
	}
		if (accept(TokenClass.PLUS,TokenClass.MINUS)) { //Add subtract - TIER 4
			if (token.tokenClass==TokenClass.PLUS) {
				Op myOp = Op.ADD;
				nextToken();
				Expr rhs = parseExp();
				BinOp binOp = new BinOp(myOp,e,rhs);
				Expr expr = parseExpAlt(binOp);
				return expr;
			}
			if (token.tokenClass==TokenClass.MINUS) {
				Op myOp = Op.SUB;
				nextToken();
				Expr rhs = parseExp();
				BinOp binOp = new BinOp(myOp,e,rhs);
				Expr expr = parseExpAlt(binOp);
				return expr;
			}
			
	}
		if (accept(TokenClass.LT,TokenClass.LE,TokenClass.GT,TokenClass.GE)) {//RELATIONAL OPERATORS - TIER 5
			if (token.tokenClass==TokenClass.LT) {
				Op myOp = Op.LT;
				nextToken();
				Expr rhs = parseExp();
				BinOp binOp = new BinOp(myOp,e,rhs);
				Expr expr = parseExpAlt(binOp);
				return expr;
			}
			if (token.tokenClass==TokenClass.LE) {
				Op myOp = Op.LE;
				nextToken();
				Expr rhs = parseExp();
				BinOp binOp = new BinOp(myOp,e,rhs);
				Expr expr = parseExpAlt(binOp);
				return expr;
			}
			if (token.tokenClass==TokenClass.GT) {
				Op myOp = Op.GT;
				nextToken();
				Expr rhs = parseExp();
				BinOp binOp = new BinOp(myOp,e,rhs);
				Expr expr = parseExpAlt(binOp);
				return expr;
			}
			if (token.tokenClass==TokenClass.GE) {
				Op myOp = Op.GE;
				nextToken();
				Expr rhs = parseExp();
				BinOp binOp = new BinOp(myOp,e,rhs);
				Expr expr = parseExpAlt(binOp);
				return expr;
			}
	}
		if (accept(TokenClass.EQ,TokenClass.NE)) { //RELATIONAL OPERATORS - TIER 6
			if (token.tokenClass==TokenClass.EQ) {
				Op myOp = Op.EQ;
				nextToken();
				Expr rhs = parseExp();
				BinOp binOp = new BinOp(myOp,e,rhs);
				Expr expr = parseExpAlt(binOp);
				return expr;
			}	
			if (token.tokenClass==TokenClass.NE) {
				Op myOp = Op.NE;
				nextToken();
				Expr rhs = parseExp();
				BinOp binOp = new BinOp(myOp,e,rhs);
				Expr expr = parseExpAlt(binOp);
				return expr;
			}
	}
		if (accept(TokenClass.AND)) {//LOGICAL AND - TIER 7
			Op myOp = Op.AND;
			nextToken();
			Expr rhs = parseExp();
			BinOp binOp = new BinOp(myOp,e,rhs);
			Expr expr = parseExpAlt(binOp);
			return expr;
}
		if (accept(TokenClass.OR)) {//LOGICAL OR - TIER 8
			Op myOp = Op.OR;
			nextToken();
			Expr rhs = parseExp();
			BinOp binOp = new BinOp(myOp,e,rhs);
			Expr expr = parseExpAlt(binOp);
			return expr;
}
		else {
			return e;
		}
}
    
}
