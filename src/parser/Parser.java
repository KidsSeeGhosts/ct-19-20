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
        //System.out.println(token);
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
    		//System.out.println("Recognised struct, going to parse struct decl");
    		parseStructDecls();
    		if (accept(TokenClass.STRUCT)) {
    			parseStructDeclsRep();
    		}
    	}
}
    
    private void parseVarDeclsRep() {//zero or more
    	Token checktoken1 = lookAhead(1);
    	Token checktoken2 = lookAhead(2);
    	Token checktoken3 = lookAhead(3);//for if we have a star
    	Token checktoken4 = lookAhead(4);
	    	if  (((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass!=TokenClass.ASTERIX) && ((checktoken2.tokenClass== TokenClass.SC) || (checktoken2.tokenClass== TokenClass.LSBR)))
	    			||//int abc;
	    			((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass==TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.SC) || (checktoken3.tokenClass== TokenClass.LSBR)))
	    			||//int * abc;
	    			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass!=TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.SC) || (checktoken3.tokenClass== TokenClass.LSBR)))
	    			||//struct abc abc;
	    			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass==TokenClass.ASTERIX) && ((checktoken4.tokenClass== TokenClass.SC) || (checktoken4.tokenClass== TokenClass.LSBR)))
	    			//struct* abc abc;
	    			){
	    		//System.out.println("about to parse the var decl");
	    		parseVarDecls();
	    		if  (((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass!=TokenClass.ASTERIX) && ((checktoken2.tokenClass== TokenClass.SC) || (checktoken2.tokenClass== TokenClass.LSBR)))
	        			||//int abc;
	        			((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass==TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.SC) || (checktoken3.tokenClass== TokenClass.LSBR)))
	        			||//int * abc;
	        			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass!=TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.SC) || (checktoken3.tokenClass== TokenClass.LSBR)))
	        			||//struct abc abc;
	        			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass==TokenClass.ASTERIX) && ((checktoken4.tokenClass== TokenClass.SC) || (checktoken4.tokenClass== TokenClass.LSBR)))
	        			//struct* abc abc;
	        			){
	    			parseVarDeclsRep();
	    		}
	    	}
    }
    
    private void parseFunDeclsRep() {
    	Token checktoken1 = lookAhead(1);
    	Token checktoken2 = lookAhead(2);
    	Token checktoken3 = lookAhead(3);//for if we have a star
    	Token checktoken4 = lookAhead(4);
	    	if  (((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass!=TokenClass.ASTERIX) && ((checktoken2.tokenClass== TokenClass.LPAR)))
	    			||//int abc;
	    			((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass==TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.LPAR)))
	    			||//int * abc;
	    			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass!=TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.LPAR)))
	    			||//struct abc abc;
	    			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass==TokenClass.ASTERIX) && ((checktoken4.tokenClass== TokenClass.LPAR)))
	    			//struct* abc abc;
	    			){
	    		System.out.println("about to parse the var decl");
	    		parseFunDecls();
	    		if  (((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass!=TokenClass.ASTERIX) && ((checktoken2.tokenClass== TokenClass.LPAR)))
	        			||//int abc;
	        			((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID)) && (checktoken1.tokenClass==TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.LPAR)))
	        			||//int * abc;
	        			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass!=TokenClass.ASTERIX) && ((checktoken3.tokenClass== TokenClass.LPAR)))
	        			||//struct abc abc;
	        			((accept(TokenClass.STRUCT)) && (checktoken2.tokenClass==TokenClass.ASTERIX) && ((checktoken4.tokenClass== TokenClass.LPAR)))
	        			//struct* abc abc;
	        			){
	    			parseFunDeclsRep();
	    		}
	    	}
    }
    
    

    //structdecl ::= structtype "{" vardeclRepPlus "}" ";"
    //vardeclRepPlus ::= vardecl vardeclRepPlus | vardecl
    private void parseStructDecls() {
    		parseStructType();
    		expect(TokenClass.LBRA);
    		//System.out.println("Accepted left bracket");
    		parseVarDeclRepPlus();
    		expect(TokenClass.RBRA);
    		expect(TokenClass.SC);
    }
    //vardeclRepPlus ::= vardecl vardeclRepPlus | vardecl
    private void parseVarDeclRepPlus() {//one or more grammar rule
    		//System.out.println("About to parse var decl inside vardeclreplus");
    		parseVarDecls();//next check for identifier then semicolon or lsbr to know to parse
    		Token checktoken = lookAhead(2);
    		if ((accept(TokenClass.INT,TokenClass.CHAR,TokenClass.VOID,TokenClass.STRUCT) && ((checktoken.tokenClass== TokenClass.SC) || (checktoken.tokenClass== TokenClass.LSBR)))) {
	    		parseVarDeclRepPlus();
    		}
    }
    
    //vardecl    ::= type IDENT ";" | type IDENT "[" INT_LITERAL "]" ";" //No changes 
    private void parseVarDecls() {
    		//System.out.println("parsing var decl");
        parseType();
        expect(TokenClass.IDENTIFIER);
        if (accept(TokenClass.SC)) {//checks current token is SC
        	//System.out.println("Finished parsing var decl");
        	//System.out.println(token);
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
    		//System.out.println("Parsing fun decls");
        parseType();
        //System.out.println(token);
        expect(TokenClass.IDENTIFIER);
        expect(TokenClass.LPAR);
        //System.out.println(token);
        //System.out.println("About to parse params");
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
        		//System.out.println("Recognised that we have char");
        		nextToken();
    			parseStarOpt();
        }
        if (accept(TokenClass.VOID)) {
        		nextToken();
    			parseStarOpt();
        }
        if (accept(TokenClass.STRUCT)) {
    			parseStructType();
			parseStarOpt();
        }
    }
    private void parseStarOpt() {
    		if (accept(TokenClass.ASTERIX)) {
    			//System.out.println("Recognised asterix");
    	        expect(TokenClass.ASTERIX);
    		}
    }
    
    //params     ::= [ type IDENT ("," type IDENT)* ]

//Params ::= paramsListOpt
//paramsListOpt ::= type IDENT paramsRep | ε
//paramsRep ::= "," type IDENT paramsRep | ε

    
    private void parseParams() {
     	if (accept(TokenClass.INT) || accept(TokenClass.CHAR) || accept(TokenClass.VOID) || accept(TokenClass.STRUCT)) {//checking for type as params optional
            //System.out.println("Detected type in params");
	        parseType();
	        expect(TokenClass.IDENTIFIER);
	        //System.out.println("Detected identifier in params");
	        //System.out.println(token);
	        parseParamsListOpt();
     	}
    }
    
    private void parseParamsListOpt() {//if because it's optional
	    	if (accept(TokenClass.COMMA)) {
	    		//System.out.println("Detected comma");
	    		nextToken();
	        parseType();
	        expect(TokenClass.IDENTIFIER);
	        //System.out.println("Detected type and identifier");
	        parseParamsListOpt();
	    	}
    }
    

    //stmt ::= block | "while" "(" exp ")" stmt | "if" "(" exp ")" stmt elseStmtOpt | "return" expOpt ";" | exp "=" exp ";" | exp ";"
    //elseStmtOpt ::= "else" stmt | ε
    	//expOpt ::= exp | ε
    private void parseStmt() {
    		//System.out.println("Beginning to parse statement");
    		//System.out.println(token);
	    	if (accept(TokenClass.LBRA)) {//if start of block
	    		//System.out.println("recognised start of block");
	    		//System.out.println(token);
	    		parseBlock();
	    	}
    		if (accept(TokenClass.WHILE)) {
    			//System.out.println("recognised while");
    			nextToken();
    			expect(TokenClass.LPAR);
    			//System.out.println("recognised left bracket");
    			//System.out.println("about to parse exp in while stmt");
    			if (!accept(TokenClass.RPAR)){
	    			parseExp();
	    			expect(TokenClass.RPAR);
	    			parseStmt();
    			}
    		}
    		if (accept(TokenClass.IF)) {
    			//System.out.println("recognised if");
    			nextToken();
    			expect(TokenClass.LPAR);
    			//System.out.println("recognised left bracket");
    			if (!accept(TokenClass.RPAR)){
    				//System.out.println("Not empty brackets");
    				parseExp();
    				//System.out.println("Done parsing expression");
    				//System.out.println(token);
    				expect(TokenClass.RPAR);
    				parseStmt();
    				//System.out.println("Finished parsing the block in if");
    				//System.out.println(token);
    				parseElseStmtOpt();
    			}
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
    			if (accept(TokenClass.SC)) {
    				expect(TokenClass.SC);
    			}
    			if (accept(TokenClass.ASSIGN)) {
    				nextToken();
    				parseExp();
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
    		//System.out.println("About to parse block");
        expect(TokenClass.LBRA);
        //System.out.println("Got the left bracket");
        parseVarDeclsRep();
        //System.out.println(token);
        //System.out.println("About to do SmtREP in Block");
        parseStmtRep();
        //System.out.println("Finished parsing ");
        //System.out.println(token);
        expect(TokenClass.RBRA);
        //System.out.println("Finished Block");
        //System.out.println(token);
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
		accept(TokenClass.ASTERIX) || accept(TokenClass.SIZEOF) ||//exp ones end here
		accept(TokenClass.WHILE) || accept(TokenClass.IF) ||
		accept(TokenClass.RETURN) || accept(TokenClass.LBRA)){
    			//System.out.println("About to do parseStmt");
    			//System.out.println(token);
    			parseStmt();
    			//System.out.println("Finished parsing statement in rep");
    			//System.out.println(token);
    			if (accept(TokenClass.LPAR) || //if start of exp
    					accept(TokenClass.IDENTIFIER) ||
    					accept(TokenClass.INT_LITERAL) ||
    					accept(TokenClass.MINUS) ||
    					accept(TokenClass.CHAR_LITERAL) ||
    					accept(TokenClass.STRING_LITERAL) ||
    					accept(TokenClass.ASTERIX) || accept(TokenClass.SIZEOF) ||
    					accept(TokenClass.WHILE) || accept(TokenClass.IF) ||
    					accept(TokenClass.RETURN) || accept(TokenClass.LBRA)){
    				//System.out.println("HERE");
    				parseStmtRep();
    			}
    		}
    }
    
    

    private void parseExp(){
    		//System.out.println("About to parse exp");
		if (accept(TokenClass.IDENTIFIER)){//NEED TO CHECK FOR FUNCALL HERE
			//System.out.println("recognised identifier in parseExp");
			Token checktoken=lookAhead(1);
			if (checktoken.tokenClass== TokenClass.LPAR) {
				//System.out.println("About to do fun call");
				parseFunCall();
				//System.out.println("About to do parse exp-alt");
				parseExpAlt();
				//System.out.println("Finished parse expalt");
				//System.out.println(token);
			}
			else {
				nextToken();
				//System.out.println(token);
				//System.out.println("About to parseExpAlt");
				//System.out.println(token);
				//System.out.println("about to do parse exp alt on the operator LT");
				parseExpAlt();
			}
		}
    		if (accept(TokenClass.LPAR)){//"(" exp ")" expAlt check for type 
    			//System.out.println("Recognised the second left bracket in expression");
    			//System.out.println(token);
    			Token checktoken=lookAhead(1);
    			if ((checktoken.tokenClass== TokenClass.INT) || (checktoken.tokenClass== TokenClass.CHAR) 
    				|| (checktoken.tokenClass== TokenClass.VOID) || (checktoken.tokenClass== TokenClass.STRUCT)){ //if typecast
    				parseTypeCast();
    				parseExpAlt();
    			}
    			else {
	    			nextToken();
	    			parseExp();
	    			expect(TokenClass.RPAR);
	    			parseExpAlt();
    			}
    		}
    		if (accept(TokenClass.MINUS)){//"-" exp exp'
             	nextToken();
             	parseExp();
             	parseExpAlt();
           }
    		if (accept(TokenClass.INT_LITERAL)){
    			//System.out.println("recognised int literal");
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
    			TokenClass.NE, TokenClass.EQ, TokenClass.PLUS,TokenClass.MINUS,TokenClass.DIV,TokenClass.ASTERIX,TokenClass.REM,TokenClass.OR,TokenClass.AND)) {
    				//System.out.println("OPERATOR DETECTED");//for operators
    				nextToken();
    				//System.out.println(token);
    				//System.out.println("About to parse exp");
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
    		//System.out.println("finished fun call");
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
	    	//System.out.println(token);
	    	expect(TokenClass.RPAR);
	    	parseExp();
    }
    
}
