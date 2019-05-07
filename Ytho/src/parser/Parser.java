/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import TokenLib.TokenType;
import TokenLib.Token;
import parser.ParseTab;
import java.util.*;

/**
 *
 * @author Blank
 */
public class Parser {

    Scanner scan;

    HashMap<String, ReductionRule> rules = new HashMap<>();
    String gotoTable[][] = {
{"1","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","5","6","8","","","9","39","40","41","42","16","","31","17","18","10","11","12","","","13","23","24","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","44","6","8","","","9","39","40","41","42","16","","31","17","18","10","11","12","","","13","23","24","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","49","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","51","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","57","6","8","","","9","39","40","41","42","16","","31","17","18","10","11","12","","","13","23","24","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","62","79","80","81","82","69","","73","70","71","","","","","","","","","","61"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","84",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","39","40","41","42","","","93","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","97","79","80","81","82","69","","73","70","71","","","","","","","","","",""},
{"","","","99","","","","","","","","","","","","","","","","98","","","","","","15"},
{"","","","","","","","100","40","41","42","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","103","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","106","79","80","81","82","69","","73","70","71","","","","","","","","","","105"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","112","41","42","","","","","","","","","","","","","","",""},
{"","","","","","","","","","113","42","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","114","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","115","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","117","118","129","130","131","132","119","","123","120","121","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","62","79","80","81","82","69","","73","70","71","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","136","39","40","41","42","16","","31","17","18","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","79","80","81","82","","","137","","","","","","","","","","","",""},
{"","","","","","","","140","80","81","82","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","143","81","82","","","","","","","","","","","","","","",""},
{"","","","","","","","","","144","82","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","145","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","148","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","151","79","80","81","82","69","","73","70","71","","","","","","","","","","150"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","157","39","40","41","42","16","","31","17","18","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","106","79","80","81","82","69","","73","70","71","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","158","79","80","81","82","69","","73","70","71","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","160","161","39","40","41","42","16","","31","17","18","","","","","","","","","",""},
{"","","","","","","","129","130","131","132","","","162","","","","","","","","","","","",""},
{"","","","","","","","165","130","131","132","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","168","131","132","","","","","","","","","","","","","","",""},
{"","","","","","","","","","169","132","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","170","","","","","","","","","","","","","","",""},
{"","171","6","8","","","9","39","40","41","42","16","","31","17","18","10","11","12","","","13","23","24","","15"},
{"","172","6","8","","","9","39","40","41","42","16","","31","17","18","10","11","12","","","13","23","24","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","174",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","151","79","80","81","82","69","","73","70","71","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","175","129","130","131","132","119","","123","120","121","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","178","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","184","185","","","186","39","40","41","42","16","","31","17","18","187","188","189","","","190","23","24","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","200","6","8","","","9","39","40","41","42","16","","31","17","18","10","11","12","","","13","23","24","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","204","79","80","81","82","69","","73","70","71","","","","","","","","","",""},
{"","","","99","","","","","","","","","","","","","","","","205","","","","","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","210","6","8","","","9","39","40","41","42","16","","31","17","18","10","11","12","","","13","23","24","","15"},
{"","211","6","8","","","9","39","40","41","42","16","","31","17","18","10","11","12","","","13","23","24","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","216","185","","","186","39","40","41","42","16","","31","17","18","187","188","189","","","190","23","24","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","",""},
};




    public Parser(Scanner scan) {
        this.scan = scan;

        rules.put("r1", new ReductionRule(TokenType.START, 6, "<Program> := SIMULA ID BLOCKSTART <Statements> BLOCKEND WAKAS"));

        rules.put("r2", new ReductionRule(TokenType.STATEMENTS, 2, "<Statements> := <Statement> <Statements>"));
        rules.put("r3", new ReductionRule(TokenType.STATEMENTS, 1, "<Statements> := ;"));

        rules.put("r4", new ReductionRule(TokenType.STATEMENT, 2, "<Statement> := <Decln> ;"));
        rules.put("r5", new ReductionRule(TokenType.STATEMENT, 2, "<Statement> := <Exp> ;"));
        rules.put("r6", new ReductionRule(TokenType.STATEMENT, 1, "<Statement> := <StmntBlk>"));
        rules.put("r7", new ReductionRule(TokenType.STATEMENT, 1, "<Statement> := <Cond>"));
        rules.put("r8", new ReductionRule(TokenType.STATEMENT, 1, "<Statement> := <Loop>"));
        rules.put("r9", new ReductionRule(TokenType.STATEMENT, 2, "<Statement> := <InOut> ;"));
        rules.put("r10", new ReductionRule(TokenType.STATEMENT, 2, "<Statement> := BASAG ;"));

        rules.put("r11", new ReductionRule(TokenType.DECLARATION, 2, "<Decln> := <DType> <Declr>  "));
        rules.put("r12", new ReductionRule(TokenType.DECLARATION, 4, "<Decln> := <DType> <Declr> ,<Declr> "));

        rules.put("r13", new ReductionRule(TokenType.DECLARATOR, 1, "<Declr> := ID "));
        rules.put("r14", new ReductionRule(TokenType.DECLARATOR, 3, "<Declr> := ID = <VarInit> "));
        
        rules.put("r15", new ReductionRule(TokenType.VARINIT, 1, "<VarInit> := <Exp> "));

     
        rules.put("r16", new ReductionRule(TokenType.EXPRESSION, 1, "<Exp> := <LogicExp> "));
        rules.put("r17", new ReductionRule(TokenType.EXPRESSION, 1, "<Exp> := <CastExp> "));
        rules.put("r18", new ReductionRule(TokenType.EXPRESSION, 1, "<Exp> := <LitExp> "));
        rules.put("r19", new ReductionRule(TokenType.EXPRESSION, 1, "<Exp> := ID "));

        rules.put("r20", new ReductionRule(TokenType.ARITH_EXPRESSION, 3, "<ArithExp> := <ArithExp> ADDSUB <Term>"));
        rules.put("r21", new ReductionRule(TokenType.ARITH_EXPRESSION, 1, "<ArithExp> := <Term> "));

        rules.put("r22", new ReductionRule(TokenType.TERM, 3, "<Term> := <Term> MULDIV <Factor>"));
        rules.put("r23", new ReductionRule(TokenType.TERM, 1, "<Term> := <Factor>"));

        rules.put("r24", new ReductionRule(TokenType.FACTOR, 3, "<Factor> := <Factor> ^ <Expow>"));
        rules.put("r25", new ReductionRule(TokenType.FACTOR, 1, "<Factor> := <Expow>"));

        rules.put("r26", new ReductionRule(TokenType.EXPOW, 3, "<Expow> := (<Exp>)"));
        rules.put("r27", new ReductionRule(TokenType.EXPOW, 1, "<Expow> := INTLIT"));

        rules.put("r28", new ReductionRule(TokenType.LOGIC_EXPRESSION, 3, "<LogicExp> := <LogicExp> <Logop> <RelExp>"));
        rules.put("r29", new ReductionRule(TokenType.LOGIC_EXPRESSION, 1, "<LogicExp> := <RelExp>"));
        rules.put("r30", new ReductionRule(TokenType.LOGIC_EXPRESSION, 1, "<LogicExp> := BOOLCONST"));

        rules.put("r31", new ReductionRule(TokenType.LOGIC_OPERATOR, 1, "<Logop> := AND"));
        rules.put("r32", new ReductionRule(TokenType.LOGIC_OPERATOR, 1, "<Logop> := OR"));
        rules.put("r33", new ReductionRule(TokenType.LOGIC_OPERATOR, 1, "<Logop> := NOT"));
        rules.put("r34", new ReductionRule(TokenType.LOGIC_OPERATOR, 1, "<Logop> := NAND"));
        rules.put("r35", new ReductionRule(TokenType.LOGIC_OPERATOR, 1, "<Logop> := NOR"));

        rules.put("r36", new ReductionRule(TokenType.REL_EXPRESSION, 3, "<RelExp> := <RelExp> RELOP <ArithExp>"));
        rules.put("r37", new ReductionRule(TokenType.REL_EXPRESSION, 1, "<RelExp> := <ArithExp>"));

        rules.put("r38", new ReductionRule(TokenType.CAST_EXPRESSION, 4, "<CastExp> := ( <Dtype> ) <Exp>"));

        rules.put("r39", new ReductionRule(TokenType.LIT_EXPRESSION, 1, "<LitExp> := INTLIT"));
        rules.put("r40", new ReductionRule(TokenType.LIT_EXPRESSION, 1, "<LitExp> := STRINGLIT"));
        rules.put("r41", new ReductionRule(TokenType.LIT_EXPRESSION, 1, "<LitExp> := FLOATLIT"));

        rules.put("r42", new ReductionRule(TokenType.STMNT_BLK, 3, "<StmntBlk> := { <Stmnt> }"));

        rules.put("r43", new ReductionRule(TokenType.CONDITION, 7, "<Cond> := KUNG ( <Exp> ) { <Stmnt> } "));
        rules.put("r44", new ReductionRule(TokenType.CONDITION, 11, "<Cond> := KUNG ( <Exp> ) { <Stmnt> } MAIBA { <Stmnt> } "));

        rules.put("r45", new ReductionRule(TokenType.LOOP, 7, "<Loop> := PARAIKOT ( <LoopCond> ) { <Stmnt> } "));

        rules.put("r46", new ReductionRule(TokenType.LOOP_CONDITION, 5, "<LoopCond> := <Init> ; <Exp> ; <UpDown> "));

     

        rules.put("r47", new ReductionRule(TokenType.UPDOWN, 2, "<UpDown> := ID INCREMENT"));
        rules.put("r48", new ReductionRule(TokenType.UPDOWN, 2, "<UpDown> := ID DECREMENT"));

        rules.put("r49", new ReductionRule(TokenType.INOUT, 1, "<InOut> := <In>"));
        rules.put("r50", new ReductionRule(TokenType.INOUT, 1, "<InOut> := <Out>"));

        rules.put("r51", new ReductionRule(TokenType.IN, 3, "<In> := PATINGS ID INPUT"));
        rules.put("r52", new ReductionRule(TokenType.OUT, 2, "<UpDown> := PAKITS  <StrPat>"));

        rules.put("r53", new ReductionRule(TokenType.STRING_PAT, 3, "<StrPat> := LPAREN STRINGLIT RPAREN "));
        rules.put("r54", new ReductionRule(TokenType.STRING_PAT, 1, "<StrPat> := ID "));
        rules.put("r55", new ReductionRule(TokenType.STRING_PAT, 5, "<StrPat> := LPAREN STRINGLIT RPAREN CONCAT <StrPat> "));

        rules.put("r56", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := INT "));
        rules.put("r57", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := BOOL "));
        rules.put("r58", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := LUTANGS "));
        rules.put("r59", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := CHAR "));
        rules.put("r60", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := CONST "));
        rules.put("r61", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := STRINGTHO "));
    }

    String parseTable[][];

    public Node startParse() {
        ParseTab par = new ParseTab();
        this.parseTable = par.parseTable.clone();
        
        System.out.println(" \n================================================== STARTING PARSER PHASE ==================================================");
        Stack<Node> stack = new Stack<Node>();
        Node root = new Node();
        stack.add(new Node(new Token(TokenType.DOLLAR), 0));

        Token currentInputToken = scan.ConsumeNextToken();

        boolean repeat = true;
        boolean endoftheline = false;

        while (repeat) {
            int state = stack.peek().nodeState;
            
            int ordinal = currentInputToken.TokenType.ordinal();
//            System.out.println("TOKEN TYPE IS " + currentInputToken.TokenType);
            
            if (ordinal < 42) {
                String action = parseTable[state][ordinal];
                //System.out.println(ordinal+"state" + state);
                //System.out.println(currentInputToken.getTokenType());
                //System.out.println(action);
                 // System.out.println(Scanner.IsInputEnd());
                       System.out.println(currentInputToken.TokenType);
                if (action.equals("")) {
                    System.out.println("==========ERROR IN THE PARSING==========");
                    System.out.println("CANNOT PARSE : " + currentInputToken.getTokenType());
                    System.out.println("State : " + state);
                    System.out.println("Ordinal : " + ordinal);
                    return null;
                } else if (action.equals("acc")) {
                    System.out.println("PROGRAM ACCEPTED");
                    return stack.pop();
                }

                switch (action.charAt(0)) {
                    case 's':
                        System.out.println("Action : " + action + " Shifting " + currentInputToken.getTokenType() + " with State " + action.substring(1));
                        // System.out.println(action.substring(1));
                        Node n = new Node(currentInputToken, Integer.parseInt(action.substring(1)));
                        stack.push(n);
                        currentInputToken = scan.ConsumeNextToken();
                         
                         //debug
                       System.out.println("Scanner :" + Scanner.IsInputEnd());
                       System.out.println("Current Input Token:" + currentInputToken.TokenType);
                       
                        
                        
                        if (!Scanner.IsInputEnd()) {
                           
                            System.out.println("END OF SCANNER REACHED");
                            endoftheline = true;
                            currentInputToken = new Token(TokenType.DOLLAR);

                        }

                        break;
                    case 'r':
//                         System.out.println(stack.peek().nodeState + "hello");
                        ReductionRule rr = rules.get(action);

                        System.out.println("REDUCING BY " + action + " | " + rr.reductionGrammar);
                        Node m = new Node(new Token(rr.production), -1);

                        for (int i = 0; i < rr.popAmount ; i++) {
                            m.nodeChildren.addFirst(stack.pop());
                        }

                        System.out.println(rr.production + " ORDINAL " +(rr.production.ordinal()-46));
                        String newState = gotoTable[stack.peek().nodeState][rr.production.ordinal() - 46];
                        
                        if (newState.equals("")) {
                            System.out.println("GOTO TABLE ERROR");
                            return null;
                        }
                        
                        m.nodeState = Integer.parseInt(newState);
                       
                        stack.push(m);

                        break;

                    default:
                       
                        // System.out.println(stack.size());
                        System.out.println("ERROR IN THE PARSING!!");
                        return null;
                }

            } else {
                currentInputToken = scan.ConsumeNextToken();
                if (!Scanner.IsInputEnd()) {
                    repeat = false;
                }
            }
        }
        return root;
    }

    static Stack<Node> dfsStack = new Stack<Node>();
    static String treeVal = "";
    static int index = 0;

    public static String DepthFirstTraversal(Node parent) {

        //System.out.println("DEPTH TOKEN: " + parent.nodeToken.getTokenType());
        treeVal += "[";
        treeVal += parent.nodeToken.getTokenType().toString() + " # ";

        if (!parent.nodeChildren.isEmpty()) {
            treeVal += "[";
            for (Node child : parent.nodeChildren) {
                DepthFirstTraversal(child);
                treeVal += ",";
            }
            treeVal += "]";
        } else {
            treeVal += "[]";
        }
        treeVal += "]";
        return treeVal;
    }

}
