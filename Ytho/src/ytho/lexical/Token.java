/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TokenLib;

/**
 *
 * @author Blank
 */
public class Token {

    private TokenType type; //token type
    private TokenAttribute attribute; //token attribute 
    private int lineNumber; //token line number
    private int columnNumber; //token column number
    public TokenType TokenType;

    public Token(TokenType type, TokenAttribute attribute, int lineNumber, int columnNumber) {
        this.type = type;
        this.attribute = attribute;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public Token(TokenType TokenType) {
     this.TokenType = TokenType;
    }

    public TokenType getType() {
        return type;
    }

    public TokenAttribute getAttribute() {
        return attribute;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
    public TokenType getTokenType(){
        return TokenType;
    }
}
