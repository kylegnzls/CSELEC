/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ytho.lexical;

/**
 *
 * @author Blank
 */
public enum TokenType {
    ID, // [a-zA-Z][a-zA-Z0-9_]*
    INT_CONST, // [0-9]+
    FLOAT_CONST,
    CHAR_CONST, //'ASCII Char'
    BOOLEAN_CONST,
    EOF, // input stream has been consumed
    UNKNOWN, // character/token could not be processed

    // binary operators
    AND, // &&
    OR, // ||
    
    
   
    ADDSUB, // + AND -
    MULDIV, //* AND /
    EXPON, //^
    RELOP,//

    INCREMENT,
    DECREMENT,
    INPUT,
    CONCAT,
    LQUOTE,
    RQUOTE,
    IGNORE,
    // reserved words
    MAIN, // main - relegate as ID (?)
    INT, // int
    CHAR, // char
    BOOLEAN, // boolean
    IF, // if
    ELSE, // else
    WHILE, // while
    FOR, //for
    SWITCH,//switch
    CASE,//case
    DEFAULT,//default
    BREAK,//break
    START,
    END,
    TRUE,
    FALSE,
    PRINT,
    SCAN,
    CONST,
    RETURN,
    STRINGTHO,
    
    
    //separator
     LPAREN, // (
      RPAREN, // )
      LCURLY, // BRACES{
    RCURLY, //BRACES }
    SCLON, // ;
    COMMA, // ,
    // punctuation
   
   
    LBRACKET, // [
    RBRACKET, // ]
    
    
    ASSIGN, // =
    NEGATIVE, // -
    NOT, // !
    NAND,
    NOR,
    COMMENT,
    // for error reporting
    STATEMENT,
    EXPRESSION,
    OPERATOR,
    TYPE
}
