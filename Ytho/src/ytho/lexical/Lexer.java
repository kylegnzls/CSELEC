/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ytho.lexical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Blank
 */
public class Lexer {

    private BufferedReader stream; //input stream reader
    private Token nextToken;
    private int nextChar;
    private int lineNumber = 1; //current line number
    private int columnNumber = 1; //current column number

    private final static Map<String, TokenType> reservedWords; //reserved words dictionary
    private final static Map<Character, TokenType> punctuation; //punctuation characters dictionary
    private final static Map<String, TokenType> operators; //operator characters dictionary

    private final static Map<String, TokenType> comment; //operator characters dictionary

    private int errors; //number of errors

    static {
        reservedWords = new HashMap<String, TokenType>();
        reservedWords.put("kung", TokenType.KUNG);
        reservedWords.put("maiba", TokenType.MAIBA);
        reservedWords.put("paraikot", TokenType.PARAIKOT);

        reservedWords.put("Simula", TokenType.SIMULA);
        reservedWords.put("Wakas", TokenType.WAKAS);
        reservedWords.put("bool", TokenType.BOOLEAN);
        reservedWords.put("omsim", TokenType.OMSIM);
        reservedWords.put("deins", TokenType.DEINS);
        reservedWords.put("int", TokenType.INTEGER);
        reservedWords.put("pakits", TokenType.PAKITS);
        reservedWords.put("patings", TokenType.PATINGS);
        reservedWords.put("char", TokenType.CHAR);
        reservedWords.put("stringtho", TokenType.STRINGTHO);
        reservedWords.put("const", TokenType.CONST);
        reservedWords.put("lutangs", TokenType.LUTANGS);

        punctuation = new HashMap<Character, TokenType>();

        punctuation.put('=', TokenType.ASSIGN);
        //punctuation.put('-', TokenType.NEGATIVE);
        punctuation.put('!', TokenType.NOT);
        punctuation.put('(', TokenType.LPAREN);
        punctuation.put(')', TokenType.RPAREN);

        punctuation.put('{', TokenType.LCURLY);
        punctuation.put('}', TokenType.RCURLY);
        punctuation.put(';', TokenType.SCLON);
        punctuation.put(',', TokenType.COMMA);

        operators = new HashMap<String, TokenType>();
        operators.put("&_&", TokenType.AND);
        operators.put("|_|", TokenType.OR);
        operators.put("=_=", TokenType.RELOP);
        operators.put("!_=", TokenType.RELOP);
        operators.put("!_&", TokenType.RELOP);
        operators.put("!_|", TokenType.RELOP);
        operators.put("!_!", TokenType.RELOP);
        operators.put("<", TokenType.RELOP);
        operators.put(">", TokenType.RELOP);
        operators.put("<_=", TokenType.RELOP);
        operators.put(">_=", TokenType.RELOP);
        operators.put("+", TokenType.ADDSUB);
        operators.put("-", TokenType.ADDSUB);
        operators.put("*", TokenType.MULDIV);
        operators.put("/", TokenType.MULDIV);
        operators.put("^", TokenType.EXPON);
        operators.put("&+", TokenType.CONCAT);
        operators.put("#>", TokenType.INPUT);

        comment = new HashMap<String, TokenType>();
        comment.put("@@", TokenType.COMMENT);
    }

    public Lexer(FileReader file) throws FileNotFoundException {
        this.stream = new BufferedReader(file);
        nextChar = getChar();
    }

    public int getErrors() {
        return errors;
    }

    // handles I/O for char stream
    private int getChar() {
        try {
            return stream.read();
        } catch (IOException e) {
            System.err.print(e.getMessage());
            System.err.println("IOException occured in Lexer::getChar()");
            return -1;
        }
    }

    // detect and skip possible '\n', '\r' and '\rn' line breaks
    private boolean skipNewline() {

        if (nextChar == '\n') {
            lineNumber++;
            columnNumber = 1;
            nextChar = getChar();
            return true;
        }
        if (nextChar == '\r') {
            lineNumber++;
            columnNumber = 1;
            nextChar = getChar();

            // skip over next char if '\n'
            if (nextChar == '\n') {
                nextChar = getChar();
            }
            return true;
        }
        // newline char not found
        return false;
    }

    // return the next token without consuming it
    public Token peek() throws IOException {
        // advance token only if its been reset by getToken()
        if (nextToken == null) {
            nextToken = getToken();
        }

        return nextToken;
    }

    // return the next token in the input stream (EOF signals end of input)
    public Token getToken() throws IOException {
        // check if peek() was called
        if (nextToken != null) {
            Token token = nextToken;
            nextToken = null; // allow peek to call for next token
            return token;
        }

        // skip whitespace character
        while (Character.isWhitespace(nextChar)) {
            // check if whitespace char is a newline
            if (!skipNewline()) {
                columnNumber++;
                nextChar = getChar();
            }

            // offset colNum for tab chars
            if (nextChar == '\t') {
                columnNumber += 3;
            }
        }

        // identifier or reserved word ([a-zA-Z][a-zA-Z0-9_]*)
        if (Character.isLetter(nextChar)) {
            // create new idVal starting with first char of identifier
            String current = Character.toString((char) nextChar);
            columnNumber++;
            nextChar = getChar();

            // include remaining sequence of chars that are letters, digits, or _
            while (Character.isLetterOrDigit(nextChar)) {
                current += (char) nextChar;
                columnNumber++;
                nextChar = getChar();
            }

            // check if identifier is a reserved word
            TokenType type = reservedWords.get(current);

            if (type != null) {
                return new Token(type, new TokenAttribute(), lineNumber, columnNumber - current.length());
            }

            if (current.equals("true")) {
                return new Token(TokenType.BOOLEAN_CONST, new TokenAttribute(true), lineNumber, columnNumber - current.length());
            } else if (current.equals("false")) {
                return new Token(TokenType.BOOLEAN_CONST, new TokenAttribute(false), lineNumber, columnNumber - current.length());
            }

            // token is an identifier
            return new Token(TokenType.ID, new TokenAttribute(current), lineNumber, columnNumber - current.length());
        }

        // integer literal ([0-9]+) OR float literal ([0-9]+.[0-9]+)
        if (Character.isDigit(nextChar)) {

            // create string representation of number
            String numString = Character.toString((char) nextChar);
            columnNumber++;
            nextChar = getChar();

            // concatenate remaining sequence of digits
            while (Character.isDigit(nextChar)) {
                numString += (char) nextChar;
                columnNumber++;
                nextChar = getChar();
            }

            if (nextChar == '.') {
                //stream.mark(0);
                nextChar = getChar();
                columnNumber++;

                if (Character.isDigit(nextChar)) {
                    numString += '.';
                    // concatenate remaining sequence of digits
                    while (Character.isDigit(nextChar)) {
                        numString += (char) nextChar;
                        columnNumber++;
                        nextChar = getChar();
                    }

                    return new Token(TokenType.FLOAT_CONST, new TokenAttribute(Float.parseFloat(numString)), lineNumber, columnNumber - numString.length());
                }
                while (!Character.isWhitespace(nextChar)) {
                    columnNumber++;
                    numString += nextChar;
                    nextChar = getChar();
                }

                return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, columnNumber - numString.length() + 1);
            }

            // return integer literal token
            return new Token(TokenType.INT_CONST, new TokenAttribute(Integer.parseInt(numString)), lineNumber, columnNumber - numString.length());
        }

        if (nextChar == '"') {
            nextChar = getChar();
            columnNumber++;
            String x = "";
            String symbol = "!@#$%^&*()+-/";
            if (Character.isAlphabetic(nextChar) || Character.isWhitespace(nextChar) || symbol.contains((Character.toString((char) nextChar)))) {

                x += (char) nextChar;
                nextChar = getChar();
                while (nextChar != '"') {

                    columnNumber++;

                    x += (char) nextChar;
                    nextChar = getChar();
                }

                columnNumber++;

                nextChar = getChar();

                return new Token(TokenType.STRING_CONST, new TokenAttribute(x), lineNumber, columnNumber - x.length());
            }

            return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, columnNumber - 1);
        }
       
        if (nextChar == '\'') {
            nextChar = getChar();
            columnNumber++;

            if (Character.isAlphabetic(nextChar)) {
                char current = (char) nextChar;
                stream.mark(0);
                nextChar = getChar();
                columnNumber++;

                if (nextChar == '\'') {
                    nextChar = getChar();
                    columnNumber++;
                    return new Token(TokenType.CHAR_CONST, new TokenAttribute(current), lineNumber, columnNumber - 1);
                }
                stream.reset();
            }

            return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, columnNumber - 1);
        }

        // EOF reached
        if (nextChar == -1) {
            return new Token(TokenType.EOF, new TokenAttribute(), lineNumber, columnNumber);
        }

        // check for binops
        switch (nextChar) {

            case '&':
                columnNumber++;
                nextChar = getChar();

                // check if next char is '&' to match '&&' binop
                if (nextChar == '_') {
                    nextChar = getChar();
                    columnNumber++;
                    if (nextChar == '&') {
                        nextChar = getChar();
                        columnNumber++;
                        return new Token(TokenType.AND, new TokenAttribute(), lineNumber, columnNumber - 2);
                    } else {
                        return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, columnNumber - 1);
                    }
                } else if (nextChar == '+') {
                    columnNumber++;
                    nextChar = getChar();
                    return new Token(TokenType.CONCAT, new TokenAttribute(), lineNumber, columnNumber - 2);
                } else {
                    return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, columnNumber - 1);
                }

            case '|':
                columnNumber++;
                nextChar = getChar();

                // check if next char is '|' to match '||' binop
                if (nextChar == '_') {
                    columnNumber++;
                    nextChar = getChar();
                    if (nextChar == '|') {
                        columnNumber++;
                        nextChar = getChar();
                        return new Token(TokenType.OR, new TokenAttribute(), lineNumber, columnNumber - 2);
                    } else {
                        return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, columnNumber - 1);
                    }
                }

            case '=':
                columnNumber++;
                nextChar = getChar();

                // check if next char is '=' to match '==' binop
                if (nextChar == '_') {
                    columnNumber++;
                    nextChar = getChar();
                    if (nextChar == '=') {
                        columnNumber++;
                        nextChar = getChar();
                        return new Token(TokenType.RELOP, new TokenAttribute(), lineNumber, columnNumber - 2);

                    } else {
                        return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, columnNumber - 1);

                    }
                } else {
                    return new Token(TokenType.ASSIGN, new TokenAttribute(), lineNumber, columnNumber - 1);
                }

            case '!':
                columnNumber++;
                nextChar = getChar();

                // check if next char is '!' to match '!=' binop
                if (nextChar == '_') {
                    nextChar = getChar();
                    columnNumber++;
                    if (nextChar == '=') {
                        columnNumber++;
                        nextChar = getChar();
                        return new Token(TokenType.RELOP, new TokenAttribute(), lineNumber, columnNumber - 2);
                    }else if(nextChar =='&'){
                        columnNumber++;
                        nextChar = getChar();
                        return new Token(TokenType.NAND, new TokenAttribute(), lineNumber, columnNumber - 2);

                    }else if(nextChar =='|'){
                        columnNumber++;
                        nextChar = getChar();
                        return new Token(TokenType.NOR, new TokenAttribute(), lineNumber, columnNumber - 2);

                    }else if(nextChar =='!'){
                        columnNumber++;
                        nextChar = getChar();
                        return new Token(TokenType.NOT, new TokenAttribute(), lineNumber, columnNumber - 2);

                    }
                    
                    else {
                        return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, columnNumber - 1);
                    }
                }

            case '<':
                columnNumber++;
                nextChar = getChar();
                if (nextChar == '_') {
                    nextChar = getChar();
                    // check if next char is '<' to match '<=' binop
                    if (nextChar == '=') {
                        nextChar = getChar();
                        return new Token(TokenType.RELOP, new TokenAttribute(), lineNumber, columnNumber - 2);
                    } else {
                        return new Token(TokenType.RELOP, new TokenAttribute(), lineNumber, columnNumber - 1);
                    }
                }

            case '>':
                columnNumber++;
                nextChar = getChar();

                // check if next char is '<' to match '<=' binop
                if (nextChar == '_') {
                    columnNumber++;
                    nextChar = getChar();
                    if (nextChar == '=') {
                        columnNumber++;
                        nextChar = getChar();
                        return new Token(TokenType.RELOP, new TokenAttribute(), lineNumber, columnNumber - 2);
                    } else {
                        return new Token(TokenType.RELOP, new TokenAttribute(), lineNumber, columnNumber - 1);
                    }
                }

            case '+':
                columnNumber++;
                nextChar = getChar();

                if (nextChar == '+') {
                    columnNumber++;
                    nextChar = getChar();
                    return new Token(TokenType.INCREMENT, new TokenAttribute(), lineNumber, columnNumber - 2);
                } else {
                    return new Token(TokenType.ADDSUB, new TokenAttribute(), lineNumber, columnNumber - 1);
                }

            case '#':
                columnNumber++;
                nextChar = getChar();

                if (nextChar == '>') {
                    columnNumber++;
                    nextChar = getChar();
                    return new Token(TokenType.INPUT, new TokenAttribute(), lineNumber, columnNumber - 2);

                }

            case '-':
                columnNumber++;
                nextChar = getChar();

                if (nextChar == '-') {
                    columnNumber++;
                    nextChar = getChar();
                    return new Token(TokenType.DECREMENT, new TokenAttribute(), lineNumber, columnNumber - 2);
                } else {
                    return new Token(TokenType.ADDSUB, new TokenAttribute(), lineNumber, columnNumber - 1);
                }

            case '^':
                columnNumber++;
                nextChar = getChar();
                return new Token(TokenType.EXPON, new TokenAttribute(), lineNumber, columnNumber - 1);

            case '*':
                columnNumber++;
                nextChar = getChar();
                return new Token(TokenType.MULDIV, new TokenAttribute(), lineNumber, columnNumber - 1);

            case '/':
                columnNumber++;
                nextChar = getChar();
                return new Token(TokenType.MULDIV, new TokenAttribute(), lineNumber, columnNumber - 1);

            case '"':
                columnNumber++;
                nextChar = getChar();
                return new Token(TokenType.LQUOTE, new TokenAttribute(), lineNumber, columnNumber - 1);
            case '@':
                columnNumber++;
                nextChar = getChar();
                int count = 1;
                //Single Line Comment
                if (nextChar == '@') {
                    count++;
                    Token toks = new Token(TokenType.COMMENT, new TokenAttribute(), lineNumber, columnNumber - count);
                    nextChar = getChar();

                    while (getChar() != '\n') {
                        columnNumber++;
                        count++;
                    }
                    columnNumber++;
                    lineNumber++;
                    nextChar = getChar();

                    return toks;
                }/* else if (nextChar == '-') {
                    if (nextChar == '-') {
                        boolean endmulti = false;
                        Token toks = new Token(TokenType.COMMENT, new TokenAttribute(), lineNumber, columnNumber - 2);
                        while (!endmulti) {
                            columnNumber++;
                            nextChar = getChar();
                            if (nextChar == '\n') {

                                skipNewline();
                            } else if (nextChar == '-') {
                                if (nextChar == '-') {
                                    if (nextChar == '@') {
                                        endmulti = true;
                                    } else {
                                        continue;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                        columnNumber++;
                         lineNumber++;
                        nextChar = getChar();
                        return toks;
                    } else {
                        return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, columnNumber - 1);
                    }
                } */ else {
                    return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, columnNumber - count);
                }

        }

        // check for punctuation
        TokenType type = punctuation.get((char) nextChar);
        columnNumber++;
        nextChar = getChar();

        // found punctuation token
        if (type != null) {
            return new Token(type, new TokenAttribute(), lineNumber, columnNumber - 1);
        }

        // token type is unknown
        return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, columnNumber - 1);
    }
}
