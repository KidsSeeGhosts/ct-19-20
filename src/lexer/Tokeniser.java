package lexer;

import lexer.Token.TokenClass;

import java.io.EOFException;
import java.io.IOException;

/**
 * @author cdubach
 */
public class Tokeniser {

    private Scanner scanner;

    private int error = 0;
    public int getErrorCount() {
	return this.error;
    }

    public Tokeniser(Scanner scanner) {
        this.scanner = scanner;
    }

    private void error(char c, int line, int col) {
        System.out.println("Lexing error: unrecognised character ("+c+") at "+line+":"+col);
	error++;
    }


    public Token nextToken() {
        Token result;
        try {
             result = next();
        } catch (EOFException eof) {
            // end of file, nothing to worry about, just return EOF token
            return new Token(TokenClass.EOF, scanner.getLine(), scanner.getColumn());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // something went horribly wrong, abort
            System.exit(-1);
            return null;
        }
        return result;
    }

    /*
     * To be completed
     */
    private Token next() throws IOException {

        int line = scanner.getLine();
        int column = scanner.getColumn();

        // get the next character
        char c = scanner.next();

        // skip white spaces
        if (Character.isWhitespace(c))
            return next();

        // recognises the plus operator
        if (c == '+')
            return new Token(TokenClass.PLUS,"+", line, column);
     // operators
//        MINUS, // '-'
//        ASTERIX, // '*'  // can be used for multiplication or pointers
//        DIV,   // '/'
//        REM,   // '%'
        if (c == '-')
            return new Token(TokenClass.MINUS,"-",line, column);
        
        if (c == '*')
            return new Token(TokenClass.ASTERIX,"*", line, column);
        
        //comments and division
        if (c == '/') {
        		char peekChar = scanner.peek();
        		if (peekChar!='/' && peekChar!='*') {
                    return new Token(TokenClass.DIV,"/", line, column);
        		}
        		c=scanner.next();
        		if (c=='*') {
        			c=scanner.next();
        			peekChar = scanner.peek();
        			while (c!='*'||peekChar!='/') {//keep scanning until you get to newline
	    				c=scanner.next();
	    				peekChar=scanner.peek();
	    			}
        				c=scanner.next();
            			return next();
        		}
        		if (c=='/'){
        			c=scanner.next();
	    			while (c!='\n') {//keep scanning until you get to newline
	    				c=scanner.next();
	    			}
	    			return next();
	    		}
        }
        
        if (c == '%')
            return new Token(TokenClass.REM,"%", line, column);

        // ... to be completed
        if (c == '.')
            return new Token(TokenClass.DOT,".", line, column);
        
        if (c == ';')
            return new Token(TokenClass.SC,";", line, column);
        
        //COMMA, // ','
        if (c == ',')
            return new Token(TokenClass.COMMA,",", line, column);
        
        if (c == '}')
            return new Token(TokenClass.RBRA,"}", line, column);
        
        if (c == '{')
            return new Token(TokenClass.LBRA,"{", line, column);
        
        //LPAR,  // '(' // left parenthesis
        //RPAR,  // ')' // right parenthesis
        if (c == '(')
            return new Token(TokenClass.LPAR,"(", line, column);
        
        if (c == ')')
            return new Token(TokenClass.RPAR,")", line, column);
        //LSBR,  // '[' // left square brace
        //RSBR,  // ']' // left square brace
        if (c == '[')
            return new Token(TokenClass.LSBR,"[", line, column);
        
        if (c == ']')
            return new Token(TokenClass.RSBR,"]", line, column);
        
        
        // logical operators
        //AND, // "&&"
        //OR,  // "||"
        if (c == '&') {
    			if (scanner.peek() == '&'){
    				c=scanner.next();
    				return new Token(TokenClass.AND,"&&", line, column);
    			}
    			return new Token(TokenClass.INVALID, line, column);
		}
        
        if (c == '|') {
    			if (scanner.peek() == '|'){
    				c=scanner.next();
    				return new Token(TokenClass.OR,"||", line, column);
    			}
    			return new Token(TokenClass.INVALID, line, column);
		}
        
        //ASSIGN and EQ
        if (c == '=') {
        		if (scanner.peek() == '='){
        			c=scanner.next();
        			return new Token(TokenClass.EQ,"==", line, column);
        		}
            return new Token(TokenClass.ASSIGN,"=", line, column);
    		}
        //NE, // "!="
        if (c == '!') {
	    		if (scanner.peek() == '='){
	    			c=scanner.next();
	    			return new Token(TokenClass.NE,"!=", line, column);
	    		}
	    		return new Token(TokenClass.INVALID, line, column);
		}
        //LT, // '<'
//        GT, // '>'
//        LE, // "<="
//        GE, // ">="
        if (c == '<') {
	    		if (scanner.peek() == '='){
	    			c=scanner.next();
	    			return new Token(TokenClass.LE,"<=", line, column);
	    		}
	    		return new Token(TokenClass.LT,"<", line, column);
		}
        if (c == '>') {
	    		if (scanner.peek() == '='){
	    			c=scanner.next();
	    			return new Token(TokenClass.GE,">=", line, column);
	    		}
	    		return new Token(TokenClass.GT,">", line, column);
		}

        //Dealing with int literal
        if (Character.isDigit(c)) {//checking for int literal
        	StringBuilder sb = new StringBuilder (); 
    		sb.append(c);//first letter of string builder
            char peekChar=scanner.peek();
            while (Character.isDigit(peekChar)) {
            		sb.append(peekChar);
                c=scanner.next();
                peekChar=scanner.peek();
            }
            return new Token(TokenClass.INT_LITERAL,sb.toString(), line, column);
        }
        
        
        
        
        //INCLUDE, // "#include"
        if (c == '#' && scanner.peek()=='i') {
            int     counter=0;
            String word = "include";
            c=scanner.next();
            while (word.charAt(counter)==c) {
                if (c=='e' && counter== 6) {
                    return new Token(TokenClass.INCLUDE, line, column);
                }
                counter++;
                c=scanner.next();
            }
        }
        
        //String literal type
        if (c == '"') {
            char peekChar=scanner.peek();
            if (peekChar == '\n'){
        		error(c, line, column);
            return new Token(TokenClass.INVALID, line, column);
            }
            if (peekChar == '"'){//string literal fine for empty string
            		c=scanner.next();
                return new Token(TokenClass.STRING_LITERAL,"", line, column);
            }
            StringBuilder sb = new StringBuilder (); 
            while (peekChar!='"') {
                c=scanner.next();
                sb.append(c);
                peekChar = scanner.peek();
                if (c=='\\') {//if potentially start of escape character
                    c=scanner.next();
                    sb.append(c);//got the slash and letter in the string builder
                    peekChar=scanner.peek();
                    if (!(c=='t' || c=='b' || c=='n'
                            || c=='r' || c=='f' || c=='\''
                            || c=='"' || c=='\\' || c=='0')) {
                    		//while (!(peekChar=='\n' || peekChar=='"')) {//if we have an invalid escape character, get to the end of the string or hit new line and say invalid
                    		//		c=scanner.next();
                        //        peekChar=scanner.peek();
                    		//}
                    		error(c, line, column);
                        return new Token(TokenClass.INVALID, line, column);
                    }
                    if ((c=='t' || c=='b' || c=='n'
                            || c=='r' || c=='f' || c=='\''
                            || c=='"' || c=='\\' || c=='0') && peekChar=='"') {//
                    	    c=scanner.next();
                        return new Token(TokenClass.STRING_LITERAL, sb.toString(),line, column);
                    }
                }
                if (peekChar == '"'){
                		c=scanner.next();
                    return new Token(TokenClass.STRING_LITERAL, sb.toString(),line, column);
                }
                if (peekChar == '\n'){
	            		error(c, line, column);
	                return new Token(TokenClass.INVALID, line, column);
                }
            }
            error(c, line, column);
            return new Token(TokenClass.INVALID, line, column);
        }
        //NOT TESTED
        //char literal and int literal
        //CHAR_LITERAL,   // \'('a'|...|'z'|'A'|...|'Z'|'\t'|'\b'|'\n'|'\r'|'\f'|'\''|'\"'|'\\'|'\0'|'.'|','|'_'|...)\'  a character starts and end with a single quote '
        //INT_LITERAL,    // ('0'|...|'9')+
        
        if (c == '\''){ //if we have single quote
        	StringBuilder sb = new StringBuilder (); 
            char peekChar = scanner.peek();
            if (peekChar == '\n'){
	        		error(c, line, column);
	            return new Token(TokenClass.INVALID, line, column);
            }
            c=scanner.next();
            sb.append(c);//this is the first character in the char literal
            peekChar = scanner.peek();
            if (c=='\'') {//if you have '' this is invalid
                error(c, line, column);
                return new Token(TokenClass.INVALID, line, column);
            }
            if (c=='\\') { //if c is a \ then dealing with escape character
                peekChar = scanner.peek();
                if (peekChar == '\n'){
		        		error(c, line, column);
		            return new Token(TokenClass.INVALID, line, column);
                }
                c=scanner.next();
                sb.append(c);//appended the letter after slash
                peekChar = scanner.peek();
                if ((c=='t' || c=='b' || c=='n'
                     || c=='r' || c=='f' || c=='\''
                     || c=='"' || c=='\\' || c=='0') && peekChar=='\'') {//checks for a valid escape character else it's invalid
                    c = scanner.next();
                    return new Token(TokenClass.CHAR_LITERAL,sb.toString(), line, column);
                }
                if (c=='\'' && peekChar!='\'') {//means you can't have '\' as a char literal
                		error(c, line, column);
                    return new Token(TokenClass.INVALID, line, column);
                }
                else {
                    c=scanner.next();
                    error(c, line, column);
                    return new Token(TokenClass.INVALID, line, column);
                }
            }
            if (!Character.isDigit(c) && peekChar=='\'') {//if c is a letter and then second single quote
                c=scanner.next();
                return new Token(TokenClass.CHAR_LITERAL,sb.toString(), line, column);
            }
            
            if (!Character.isDigit(c) && peekChar!='\'') {//checking for multiple characters in single quotes
                //while (c != '\'') {
                //    c=scanner.next();
                //}
                error(c, line, column);//this one line should solve multi characters in single quotes test problem
                return new Token(TokenClass.INVALID, line, column);
            }
            if (Character.isDigit(c) && peekChar=='\'') {//checking for single integer in single quotes
                c=scanner.next();
                return new Token(TokenClass.CHAR_LITERAL,sb.toString(), line, column);//
            }
            if (Character.isDigit(c) && Character.isDigit(peekChar)) {//checking for int literal
                while (Character.isDigit(c) && peekChar!='\'') {
                    c=scanner.next();
                    peekChar=scanner.peek();
                }
                if (Character.isDigit(c) && peekChar=='\'') {
                    c=scanner.next();
                    error(c, line, column);
                    return new Token(TokenClass.INVALID, line, column);
                }
                else {
                    while(c!='\'') {//if we have a few numbers then a character it's no longer an int literal and is invalid
                        c=scanner.next();
                    }
                    error(c, line, column);
                    return new Token(TokenClass.INVALID, line, column);
                }
            }
        }
        
        
        
        
        //IDENTIFIER stuff and void and int and char
        if (Character.isLetterOrDigit(c) || c=='_'){
//        		char peekChar = scanner.peek();
//        		if (!Character.isLetterOrDigit(peekChar)) {//if next character not letter or digit we're done
//        			return new Token(TokenClass.IDENTIFIER, line, column);
//        		}
        		StringBuilder sb = new StringBuilder (); 
        		sb.append(c);//first letter of string builder
        		c = scanner.peek();
        			while (Character.isLetterOrDigit(c) || c=='_') {
        					sb.append(c); 
        					scanner.next();
        					c = scanner.peek();
        			}
        			//System.out.println(sb.toString());
        			//types
        			if ("void".equals(sb.toString())) {
        				return new Token(TokenClass.VOID,sb.toString(), line, column);
        			}
        			if ("int".equals(sb.toString())) {
        				return new Token(TokenClass.INT,sb.toString(), line, column);
        			}
        			if ("char".equals(sb.toString())) {
        				return new Token(TokenClass.CHAR,sb.toString(), line, column);
        			}
        			// keywords
//        	        IF,     // "if"
//        	        ELSE,   // "else"
//        	        WHILE,  // "while"
//        	        RETURN, // "return"
//        	        STRUCT, // "struct"
//        	        SIZEOF, // "sizeof"
        			if ("if".equals(sb.toString())) {
        				return new Token(TokenClass.IF,sb.toString(), line, column);
        			}
        			if ("else".equals(sb.toString())) {
        				return new Token(TokenClass.ELSE,sb.toString(), line, column);
        			}
        			if ("while".equals(sb.toString())) {
        				return new Token(TokenClass.WHILE,sb.toString(), line, column);
        			}
        			if ("return".equals(sb.toString())) {
        				return new Token(TokenClass.RETURN,sb.toString(), line, column);
        			}
        			if ("struct".equals(sb.toString())) {
        				return new Token(TokenClass.STRUCT,sb.toString(), line, column);
        			}
        			if ("sizeof".equals(sb.toString())) {
        				return new Token(TokenClass.SIZEOF,sb.toString(), line, column);
        			}
        			return new Token(TokenClass.IDENTIFIER,sb.toString(), line, column);
        }

        // if we reach this point, it means we did not recognise a valid token
        error(c, line, column);
        return new Token(TokenClass.INVALID, line, column);
    }

}
