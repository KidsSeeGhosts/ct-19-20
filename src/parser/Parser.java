package parser;

import lexer.Token;
import lexer.Tokeniser;
import lexer.Token.TokenClass;

import java.util.LinkedList;
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

    public void parse() {
        // get the first token
        nextToken();

        parseProgram();
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


    private void parseProgram() {
        parseIncludesRep();
        parseStructDeclsRep();
        parseVarDeclsRep();
        parseFunDeclsRep();
        expect(TokenClass.EOF);
    }

    // includes are ignored, so does not need to return an AST node
    private void parseIncludesRep() {//zero or more
        if (accept(TokenClass.INCLUDE)) {//first if means we are doing zero or more
            parseIncludes();
            if (accept(TokenClass.INCLUDE)) {//this if allows us to do more
            		parseIncludesRep();
            }
        }
    }
    
    private void parseIncludes() {
    		expect(TokenClass.INCLUDE);
    		expect(TokenClass.STRING_LITERAL);
    }
    
    private void parseStructDeclsRep() {//zero or more
    	if (accept(TokenClass.STRUCT)) {
    		parseStructDecls();
    		if (accept(TokenClass.STRUCT)) {
    			parseStructDeclsRep();
    		}
    	}
}
    
    private void parseVarDeclsRep() {//zero or more
    	Token checktoken = lookAhead(2);
	    	if ((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID,TokenClass.STRUCT) && ((checktoken.tokenClass== TokenClass.SC) || (checktoken.tokenClass== TokenClass.LSBR)))) {
	    		parseVarDecls();
	    		if ((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID,TokenClass.STRUCT) && ((checktoken.tokenClass== TokenClass.SC) || (checktoken.tokenClass== TokenClass.LSBR)))) {
	    			parseVarDeclsRep();
	    		}
	    	}
    }
    
    private void parseFunDeclsRep() {
    		Token checktoken = lookAhead(2);
    		if ((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID,TokenClass.STRUCT)) && (checktoken.tokenClass==TokenClass.LPAR)) {
    			parseFunDecls();
        		if ((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID,TokenClass.STRUCT)) && (checktoken.tokenClass==TokenClass.LPAR)) {
	    			parseFunDeclsRep();
	    		}
    		}
    }
    
    

    //structdecl ::= structtype "{" vardeclRepPlus "}" ";"
    //vardeclRepPlus ::= vardecl vardeclRepPlus | vardecl
    private void parseStructDecls() {
    		parseStructType();
    		expect(TokenClass.LBRA);
    		parseVarDeclRepPlus();
    		expect(TokenClass.RBRA);
    		expect(TokenClass.SC);
    }
    //vardeclRepPlus ::= vardecl vardeclRepPlus | vardecl
    private void parseVarDeclRepPlus() {//one or more grammar rule
    		parseVarDecls();//next check for identifier then semicolon or lsbr to know to parse
    		if (accept(TokenClass.INT) || accept(TokenClass.CHAR) || accept(TokenClass.VOID) || accept(TokenClass.STRUCT)) {//if we have type identifier and semicolon or left bracket
    			parseVarDecls();
    		}
    }
    
    //vardecl    ::= type IDENT ";" | type IDENT "[" INT_LITERAL "]" ";" //No changes 
    private void parseVarDecls() {
    		//System.out.println("parsing var decl");
        parseType();
        expect(TokenClass.IDENTIFIER);
        if (accept(TokenClass.SC)) {//checks current token is SC
        		expect(TokenClass.SC);//Doing expect because it's the end of vardecl
        }
        if (accept(TokenClass.LSBR)) {
        		nextToken();
        		expect(TokenClass.INT_LITERAL);
        		expect(TokenClass.RSBR);
        		expect(TokenClass.SC);
        }
    	
    }
    
    //fundecl    ::= type IDENT "(" params ")" block
    private void parseFunDecls() {
        parseType();
        expect(TokenClass.IDENTIFIER);
        expect(TokenClass.LPAR);
        parseParams();
        expect(TokenClass.RPAR);
        parseBlock();
    }

    private void parseStructType() {
        expect(TokenClass.STRUCT);
        expect(TokenClass.IDENTIFIER);
    }
    
    
    //type       ::= ("int" | "char" | "void" | structtype) ["*"]
    //Type ::= ("int" | "char" | "void" | structtype) starOpt
    //	starOpt ::= "*" | ε
    private void parseType() {
        if (accept(TokenClass.INT)) {
        		nextToken();
        		parseStarOpt();
        }
        if (accept(TokenClass.CHAR)) {
        		nextToken();
    			parseStarOpt();
        }
        if (accept(TokenClass.VOID)) {
        		nextToken();
    			parseStarOpt();
        }
        if (accept(TokenClass.STRUCT)) {
    			nextToken();
    			expect(TokenClass.IDENTIFIER);
			parseStarOpt();
        }
    }
    private void parseStarOpt() {
    		if (accept(TokenClass.ASTERIX)) {
    	        expect(TokenClass.ASTERIX);
    		}
    }
    
    //params     ::= [ type IDENT ("," type IDENT)* ]

//Params ::= paramsListOpt
//paramsListOpt ::= type IDENT paramsRep | ε
//paramsRep ::= "," type IDENT paramsRep | ε

    
    private void parseParams() {
     	if (accept(TokenClass.INT) || accept(TokenClass.CHAR) || accept(TokenClass.VOID) || accept(TokenClass.STRUCT)) {//checking for type as params optional
	        parseType();
	        expect(TokenClass.IDENTIFIER);
	        parseParamsListOpt();
     	}
    }
    
    private void parseParamsListOpt() {//if because it's optional
	    	if (accept(TokenClass.INT) || accept(TokenClass.CHAR) || accept(TokenClass.VOID) || accept(TokenClass.STRUCT)) {
	        parseType();
	        expect(TokenClass.IDENTIFIER);
	        parseParamsRep();
	    	}
    }
    private void parseParamsRep() {
	    	if (accept(TokenClass.COMMA)) {
	    	       expect(TokenClass.COMMA);
	    	       parseType();
	    	       expect(TokenClass.IDENTIFIER);
	    	}
    }
    

    //stmt ::= block | "while" "(" exp ")" stmt | "if" "(" exp ")" stmt elseStmtOpt | "return" expOpt ";" | exp "=" exp ";" | exp ";"
    //elseStmtOpt ::= "else" stmt | ε
    	//expOpt ::= exp | ε
    private void parseStmt() {
	    	if (accept(TokenClass.LBRA)) {//if start of block
	    		parseBlock();
	    	}
    		if (accept(TokenClass.WHILE)) {
    			nextToken();
    			expect(TokenClass.LPAR);
    			parseExp();
    			expect(TokenClass.RPAR);
    			parseStmt();
    		}
    		if (accept(TokenClass.IF)) {
    			nextToken();
    			expect(TokenClass.LPAR);
    			parseExp();
    			expect(TokenClass.RPAR);
    			parseStmt();
    			parseElseStmtOpt();
    		}
    		if (accept(TokenClass.RETURN)) {
    			nextToken();
    			parseExpOpt();
    			expect(TokenClass.SC);
    		}
    		if (accept(TokenClass.LPAR) || //if start of exp
    			accept(TokenClass.IDENTIFIER) ||
    			accept(TokenClass.INT_LITERAL) ||
    			accept(TokenClass.MINUS) ||
    			accept(TokenClass.CHAR_LITERAL) ||
    			accept(TokenClass.STRING_LITERAL) ||
    			accept(TokenClass.ASTERIX) || accept(TokenClass.SIZEOF)){
    			//System.out.println("We've got an expression");
    			parseExp();
    			if (accept(TokenClass.ASSIGN)) {
    				nextToken();
    				parseExp();
    				expect(TokenClass.SC);
    			}
    			if (accept(TokenClass.SC)) {
    				expect(TokenClass.SC);
    			}
    			
    		}
    }
  //elseStmtOpt ::= "else" stmt | ε
    private void parseElseStmtOpt(){
    		if (accept(TokenClass.ELSE)) {
    			nextToken();
    			parseStmt();
    		}
    }
    private void parseExpOpt() {
    	if (accept(TokenClass.LPAR) || //if start of exp
    			accept(TokenClass.IDENTIFIER) ||
    			accept(TokenClass.INT_LITERAL) ||
    			accept(TokenClass.MINUS) ||
    			accept(TokenClass.CHAR_LITERAL) ||
    			accept(TokenClass.STRING_LITERAL) ||
    			accept(TokenClass.ASTERIX) || accept(TokenClass.SIZEOF)){
    			parseExp();
    		}
    }
    
    //block ::= "{" vardeclRep stmtRep "}"
    //vardeclRep ::= vardecl vardeclRep | ε //duplicate
    //stmtRep ::= stmt stmtRep | ε
    private void parseBlock() {
        expect(TokenClass.LBRA);
        parseVarDeclRep();
        //System.out.println("About to do SmtREP in Block");
        parseStmtRep();
        expect(TokenClass.RBRA);
    }
    
    private void parseVarDeclRep() {
    		if (accept(TokenClass.INT) || accept(TokenClass.CHAR) || accept(TokenClass.VOID) || accept(TokenClass.STRUCT)) {
    			parseVarDecls();
    			if (accept(TokenClass.INT) || accept(TokenClass.CHAR) || accept(TokenClass.VOID) || accept(TokenClass.STRUCT)) {
    				parseVarDeclRep();
    			}
    		}
    }
    
    private void parseStmtRep() {
    		if (accept(TokenClass.LPAR) || //if start of exp
		accept(TokenClass.IDENTIFIER) ||
		accept(TokenClass.INT_LITERAL) ||
		accept(TokenClass.MINUS) ||
		accept(TokenClass.CHAR_LITERAL) ||
		accept(TokenClass.STRING_LITERAL) ||
		accept(TokenClass.ASTERIX) || accept(TokenClass.SIZEOF)){
    			//System.out.println("Got into the first parse stmt");
    			parseStmt();
    			if (accept(TokenClass.LPAR) || //if start of exp
    	    			accept(TokenClass.IDENTIFIER) ||
    	    			accept(TokenClass.INT_LITERAL) ||
    	    			accept(TokenClass.MINUS) ||
    	    			accept(TokenClass.CHAR_LITERAL) ||
    	    			accept(TokenClass.STRING_LITERAL) ||
    	    			accept(TokenClass.ASTERIX) || accept(TokenClass.SIZEOF)){
    				parseStmt();
    			}
    		}
    }
    
    

    private void parseExp(){
    		if (accept(TokenClass.LPAR)){//"(" exp ")" expAlt check for type 
    			if (accept(TokenClass.INT) || accept(TokenClass.CHAR) || accept(TokenClass.VOID) || accept(TokenClass.STRUCT)) {//if typecast
    				parseTypeCast();
    				parseExpAlt();
    			}
    			nextToken();
    			parseExp();
    			expect(TokenClass.RPAR);
    			parseExpAlt();
    		}
    		if (accept(TokenClass.MINUS)){//"-" exp exp'
             	nextToken();
             	parseExp();
             	parseExpAlt();
           }
    		if (accept(TokenClass.INT_LITERAL)){
    			nextToken();
    			parseExpAlt();
        }
    		if (accept(TokenClass.CHAR_LITERAL)){
         	nextToken();
         	parseExpAlt();
       }
    		if (accept(TokenClass.STRING_LITERAL)){
    			nextToken();
             parseExpAlt();
       }
    		if (accept(TokenClass.ASTERIX)){//valueat
    			parseValueAt();
    			parseExpAlt();
         		 //if start of exp
       }
    		if (accept(TokenClass.SIZEOF)){//
        		 parseSizeOf();
        		 parseExpAlt();
      }
    		if (accept(TokenClass.IDENTIFIER)){//NEED TO CHECK FOR FUNCALL HERE
    			if (accept(lookAhead(1).tokenClass.LPAR)) {
    				parseFunCall();
    				parseExpAlt();
    			}
    			parseExpAlt();
       	}
    		
    }
    
    //exp' ::= arrayaccess exp' | fieldaccess exp' | operatorExp | ε
    private void parseExpAlt() {
    		if (accept(TokenClass.LSBR)) {//FOR ARRAY ACCESS
    			nextToken();
    			parseExp();
    			expect(TokenClass.RSBR);
    		}
    		if (accept(TokenClass.DOT)) {//FOR FIELD ACCESS
    			nextToken();
    			expect(TokenClass.IDENTIFIER);
    		}
    		if (accept(TokenClass.GT,TokenClass.LT,TokenClass.GE,TokenClass.LE,
    			TokenClass.NE,TokenClass.PLUS,TokenClass.MINUS,TokenClass.DIV,TokenClass.ASTERIX,TokenClass.REM,TokenClass.OR,TokenClass.AND)) {//for operators
    				nextToken();
    				parseExp();
    		}
    }
    
    //funcall ::= IDENT "(" funargsOpt   ")"
    	//	funArgsOpt ::= exp funArgs | ε
    	//	funArgs ::= "," exp funArgs | ε 

    
    private void parseFunCall() {
    		expect(TokenClass.IDENTIFIER);
    		expect(TokenClass.LPAR);
    		parseFunArgsOpt();
    		expect(TokenClass.RPAR);
    }
    
    private void parseFunArgsOpt() {
    		if (accept(TokenClass.LPAR) || //if start of exp
	    			accept(TokenClass.IDENTIFIER) ||
	    			accept(TokenClass.INT_LITERAL) ||
	    			accept(TokenClass.MINUS) ||
	    			accept(TokenClass.CHAR_LITERAL) ||
	    			accept(TokenClass.STRING_LITERAL) ||
	    			accept(TokenClass.ASTERIX) || accept(TokenClass.SIZEOF)) {
        		parseExp();
        		parseFunArgs();
    		}
    		
    }
    private void parseFunArgs() {
    		if (accept(TokenClass.COMMA)) {
	    		nextToken();
	    		parseExp();
	    		parseFunArgs();
    		}
    }
    //valueat      ::= "*" exp //No changes
    private void parseValueAt() {
    		expect(TokenClass.ASTERIX);
    		parseExp();
    }
    //
    //sizeof ::= "sizeof" "(" type ")" //No changes
    private void parseSizeOf() {
    		expect(TokenClass.SIZEOF);
    		expect(TokenClass.LPAR);
    		parseType();
    		expect(TokenClass.RPAR);
    }
    
    private void parseTypeCast() {
	    	expect(TokenClass.LPAR);
	    	parseType();
	    	expect(TokenClass.RPAR);
	    	parseExp();
    }
    
}
