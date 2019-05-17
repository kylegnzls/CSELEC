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
    ADDSUB,// 10
    MULDIV,
    EXPON,
    LPAREN,
    RPAREN,
    INTLIT,
    BOOLCONST,
    AND,
    OR,
    NOT,
    NAND, //20
    NOR,
    RELOP,
    LQUOTE,
    STRINGLIT,
    RQUOTE,
    FLOATLIT,
    KUNG,
    MAIBA,
    PARAIKOT,
    INCREMENT,
    DECREMENT,
    PATINGS,//30
    INPUT,
    PAKITS,
    CONCAT,
    INTEGER,
    BOOL,
    LUTANGS,
    CHAR,
    CONST, //40
    STRINGTHO,
    DOLLAR,
    //42

    ERROR,
    WHITESPACE,
    NEWLINE,
    COMMENT,
    START,//46
    STMNTS,
    STMNT, //non terminals
    DECLN,
    DECLR,
    VARINIT,
    EXP,
    ARITHEXP,
    TERM,
    FACTOR,
    EXPOW,
    LOGICEXP,
    LOGOP,
    RELEXP,
    CASTEXP,
    LITEXP,
    STMNT_BLK,
    COND,
    LOOP,
    LOOPCOND,
    //INIT,
    UPDOWN,
    INOUT,
    IN,
    OUT,
    STRPAT,
    DTYPE,
    CHARLITERAL,
    TYPE,
    STRINGLITERAL

}
