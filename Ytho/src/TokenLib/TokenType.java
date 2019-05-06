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
public enum TokenType {

    SIMULA,
    ID,
    LCURLY,
    RCURLY,
    WAKAS,
    ENDLINE,
    BASAG,
    COMMA,
    ASSIGN,
    ADDSUB,//9 // + AND -
    MULDIV, //* AND /
    EXPON,
    LPAREN,
    RPAREN,
    INTLIT,
    BOOLCONST,
    AND,
    OR,
    NOT,
    NAND,//
    NOR,//20^
    RELOP,
    STRINGLIT,
    FLOATLIT,
    KUNG,
    MAIBA,
    PARAIKOT,
    INCREMENT,
    DECREMENT,
    PATINGS,//
    INPUT,// 30
    PAKITS,
    LQUOTE,
    RQUOTE,
    CONCAT,
    INTEGER,
    BOOL,
    LUTANGS,
    CHAR,
    CONST,//
    STRINGTHO,
    OMSIM,// 40
    DEINS,
    IBALIK,
    NUMCONSTANTS,
    FLOAT_CONST,
    CHAR_CONST, //'ASCII Char'
    BOOLEAN_CONST,
    LBRACKET, // [
    RBRACKET, // ]
    DOLLAR,// [0-9]+

    //50
    EOF, // input stream has been consumed
    ERROR, // character/token could not be processed // binary operators
    INT_CONST,
    WHITESPACE,
    IGNORE,   // reserved words
    //MAIN, // main - relegate as ID (?)
    NEWLINE,
    //break
    
    //SCLON, // ;
    NEGATIVE, // -
    COMMENT, // for error reporting
    
    //58
    START,
    STATEMENTS,
    STATEMENT, //non terminals
    DECLARATION,
    DECLARATOR,
    VARINIT,
    ARITH_EXPRESSION,
    TERM,
    FACTOR,
    EXPOW,
    LOGIC_EXPRESSION,
    LOGIC_OPERATOR,
    REL_EXPRESSION,
    CAST_EXPRESSION,
    LIT_EXPRESSION,
    STMNT_BLK,
    CONDITION,
    LOOP,
    LOOP_CONDITION,
    INIT,
    UPDOWN,
    INOUT,
    IN,
    OUT,
    STRING_PAT,
    DTYPE,
    EXPRESSION,
    OPERATOR,
    TYPE,
    LETTERLITERAL,
    STRINGLITERAL,  

}
