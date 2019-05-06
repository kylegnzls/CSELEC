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
{"1","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","5","6","8","","","9","16","33","41","42","17","","18","19","20","10","11","12","","","","13","25","26","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","48","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","51","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","58","8","","","9","16","33","41","42","17","","18","19","20","10","11","12","","","","13","25","26","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","63","70","76","82","83","71","","72","73","74","","","","","","","","","","","62"},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","85",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","93","94","95","","","","","","","","","","","","","","","",""},
{"","","","","","","","99","100","94","95","","","98","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","101","100","94","95","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","103","70","76","82","83","71","","72","73","74","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","104","105","","","","","","106"},
{"","","","","","","","","","107","42","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","113","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","117","70","76","82","83","71","","72","73","74","","","","","","","","","","","116"},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","121","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","122","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","124","125","126","132","138","139","127","","128","129","130","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","142","70","76","82","83","71","","72","73","74","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","63","70","76","82","83","71","","72","73","74","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","147","16","33","41","42","17","","18","19","20","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","148","149","150","","","","","","","","","","","","","","","",""},
{"","","","","","","","154","155","149","150","","","153","","","","","","","","","","","","",""},
{"","","","","","","","156","155","149","150","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","157","83","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","162","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","167","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","171","70","76","82","83","71","","72","73","74","","","","","","","","","","","170"},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","173","95","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","174","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","178","16","33","41","42","17","","18","19","20","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","182","70","76","82","83","71","","72","73","74","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","117","70","76","82","83","71","","72","73","74","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","183","70","76","82","83","71","","72","73","74","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","185","186","16","33","41","42","17","","18","19","20","","","","","","","","","","",""},
{"","","","","","","","","187","188","189","","","","","","","","","","","","","","","",""},
{"","","","","","","","193","194","188","189","","","192","","","","","","","","","","","","",""},
{"","","","","","","","195","194","188","189","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","196","139","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","201","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","202","8","","","9","16","33","41","42","17","","18","19","20","10","11","12","","","","13","25","26","","15"},
{"","","203","8","","","9","16","33","41","42","17","","18","19","20","10","11","12","","","","13","25","26","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","206","150","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","207","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","209",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","212","70","76","82","83","71","","72","73","74","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","171","70","76","82","83","71","","72","73","74","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","213","126","132","138","139","127","","128","129","130","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","216","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","218","189","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","219","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","225","8","","","9","16","33","41","42","17","","18","19","20","10","11","12","","","","13","25","26","","15"},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
{"","","","","","","","","","","","","","","","","","","","","","","","","","",""},
};


    public Parser(Scanner scan) {
        this.scan = scan;

        rules.put("r1", new ReductionRule(TokenType.SIMULA, 6, "<Program> := SIMULA ID BLOCKSTART <Statements> BLOCKEND WAKAS"));

        rules.put("r2", new ReductionRule(TokenType.STATEMENTS, 1, "<Statements> := <Statement>"));
        rules.put("r3", new ReductionRule(TokenType.STATEMENTS, 1, "<Statements> := ;"));
        
        rules.put("r4", new ReductionRule(TokenType.STATEMENT, 2, "<Statement> := <Decln> ;"));
        rules.put("r5", new ReductionRule(TokenType.STATEMENT, 2, "<Statement> := <Exp> ;"));
        rules.put("r6", new ReductionRule(TokenType.STATEMENT, 2, "<Statement> := <Decln> ;"));
        rules.put("r7", new ReductionRule(TokenType.STATEMENT, 1, "<Statement> := <StmntBlk>"));
        rules.put("r8", new ReductionRule(TokenType.STATEMENT, 1, "<Statement> := <Cond>"));
        rules.put("r9", new ReductionRule(TokenType.STATEMENT, 1, "<Statement> := <Loop>"));
        rules.put("r10", new ReductionRule(TokenType.STATEMENT, 2, "<Statement> := <InOut> ;"));
        rules.put("r11", new ReductionRule(TokenType.STATEMENT, 2, "<Statement> := BASAG ;"));
       

        rules.put("r12", new ReductionRule(TokenType.DECLARATION, 3, "<Decln> := <DType> <Declr> ; "));
        rules.put("r13", new ReductionRule(TokenType.DECLARATION, 5, "<Decln> := <DType> <Declr> ,<Declr>; "));

        rules.put("r14", new ReductionRule(TokenType.DECLARATOR, 1, "<Declr> := ID "));
        rules.put("r15", new ReductionRule(TokenType.DECLARATOR, 3, "<Declr> := ID = <VarInit> "));
        
        rules.put("r16", new ReductionRule(TokenType.VARINIT, 1, "<VarInit> := <Exp> "));
        
        rules.put("r17", new ReductionRule(TokenType.EXPRESSION, 1, "<Exp> := <ArithExp> "));
        rules.put("r18", new ReductionRule(TokenType.EXPRESSION, 1, "<Exp> := <LogicExp> "));
        rules.put("r19", new ReductionRule(TokenType.EXPRESSION, 1, "<Exp> := <RelExp> "));
        rules.put("r20", new ReductionRule(TokenType.EXPRESSION, 1, "<Exp> := <CastExp> "));
        rules.put("r21", new ReductionRule(TokenType.EXPRESSION, 1, "<Exp> := <LitExp> "));
        rules.put("r22", new ReductionRule(TokenType.EXPRESSION, 1, "<Exp> := ID "));

        rules.put("r23", new ReductionRule(TokenType.ARITH_EXPRESSION, 3, "<ArithExp> := <ArithExp> ADDSUB <Term>"));
        rules.put("r24", new ReductionRule(TokenType.ARITH_EXPRESSION, 1, "<ArithExp> := <Term> "));

        rules.put("r25", new ReductionRule(TokenType.TERM, 3, "<Term> := <Term> MULDIV <Factor>"));
        rules.put("r26", new ReductionRule(TokenType.TERM, 1, "<Term> := <Factor>"));

        rules.put("r27", new ReductionRule(TokenType.FACTOR, 3, "<Factor> := <Factor> ^ <Expow>"));
        rules.put("r28", new ReductionRule(TokenType.FACTOR, 1, "<Factor> := <Expow>"));

        rules.put("r29", new ReductionRule(TokenType.EXPOW, 3, "<Expow> := (<Exp>)"));
        rules.put("r30", new ReductionRule(TokenType.EXPOW, 1, "<Expow> := INTLIT"));

        rules.put("r31", new ReductionRule(TokenType.LOGIC_EXPRESSION, 3, "<LogicExp> := <LogicExp> <Logop> <RelExp>"));
        rules.put("r32", new ReductionRule(TokenType.LOGIC_EXPRESSION, 1, "<LogicExp> := <RelExp>"));
        rules.put("r33", new ReductionRule(TokenType.LOGIC_EXPRESSION, 1, "<LogicExp> := BOOLCONST"));

        rules.put("r34", new ReductionRule(TokenType.LOGIC_OPERATOR, 1, "<Logop> := AND"));
        rules.put("r35", new ReductionRule(TokenType.LOGIC_OPERATOR, 1, "<Logop> := IR"));
        rules.put("r36", new ReductionRule(TokenType.LOGIC_OPERATOR, 1, "<Logop> := NOT"));
        rules.put("r37", new ReductionRule(TokenType.LOGIC_OPERATOR, 1, "<Logop> := NAND"));
        rules.put("r38", new ReductionRule(TokenType.LOGIC_OPERATOR, 1, "<Logop> := NOR"));
        
        rules.put("r39", new ReductionRule(TokenType.REL_EXPRESSION, 3, "<RelExp> := <RelExp> RELOP <ArithExp>"));
        rules.put("r40", new ReductionRule(TokenType.REL_EXPRESSION, 1, "<RelExp> := <ArithExp>"));

        rules.put("r41", new ReductionRule(TokenType.CAST_EXPRESSION, 4, "<CastExp> := ( <Dtype> ) <Exp>"));

        rules.put("r42", new ReductionRule(TokenType.LIT_EXPRESSION, 1, "<LitExp> := INTLIT"));
        rules.put("r43", new ReductionRule(TokenType.LIT_EXPRESSION, 1, "<LitExp> := STRINGLIT"));
        rules.put("r44", new ReductionRule(TokenType.LIT_EXPRESSION, 1, "<LitExp> := FLOATLIT"));

        rules.put("r45", new ReductionRule(TokenType.STMNT_BLK, 3, "<StmntBlk> := { <Stmnt> }"));

        rules.put("r46", new ReductionRule(TokenType.CONDITION, 7, "<Cond> := KUNG ( <Exp> ) { <Stmnt> } "));
        rules.put("r47", new ReductionRule(TokenType.CONDITION, 11, "<Cond> := KUNG ( <Exp> ) { <Stmnt> } MAIBA { <Stmnt> } "));

        rules.put("r48", new ReductionRule(TokenType.LOOP, 7, "<Loop> := PARAIKOT ( <LoopCond> ) { <Stmnt> } "));

        rules.put("r49", new ReductionRule(TokenType.LOOP_CONDITION, 5, "<LoopCond> := <Init> ; <Exp> ; <UpDown> "));

        rules.put("r50", new ReductionRule(TokenType.INIT, 4, "<Init> := <Dtype> ID = INTLIT "));

        rules.put("r51", new ReductionRule(TokenType.UPDOWN, 2, "<UpDown> := ID INCREMENT"));
        rules.put("r52", new ReductionRule(TokenType.UPDOWN, 2, "<UpDown> := ID DECREMENT"));
        
        rules.put("r53", new ReductionRule(TokenType.INOUT, 1, "<InOut> := <In>"));
        rules.put("r54", new ReductionRule(TokenType.INOUT, 1, "<InOut> := <Out>"));

        rules.put("r55", new ReductionRule(TokenType.IN, 3, "<In> := PATINGS ID INPUT"));
        rules.put("r56", new ReductionRule(TokenType.OUT, 2, "<UpDown> := PAKITS  <StrPat>"));
        
        rules.put("r57", new ReductionRule(TokenType.STRING_PAT, 3, "<StrPat> := LPAREN STRINGLIT RPAREN "));
        rules.put("r58", new ReductionRule(TokenType.STRING_PAT, 1, "<StrPat> := ID "));
        rules.put("r59", new ReductionRule(TokenType.STRING_PAT, 5, "<StrPat> := LPAREN STRINGLIT RPAREN CONCAT <StrPat> "));

        rules.put("r60", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := INT "));
        rules.put("r61", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := BOOL "));
        rules.put("r62", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := LUTANGS "));
        rules.put("r63", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := CHAR "));
        rules.put("r64", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := CONST "));
        rules.put("r65", new ReductionRule(TokenType.DTYPE, 1, "<DTYPE> := STRINGTHO "));
    }

    String parseTable[][];

    public Node startParse() {
        ParseTab par = new ParseTab();
        this.parseTable = par.parseTable.clone();

        System.out.println(" \n==================================================STARTING PARSER PHASE ==================================================");
        Stack<Node> stack = new Stack<Node>();
        Node root = new Node();
        stack.add(new Node(new Token(TokenType.DOLLAR), 0));

        Token currentInputToken = scan.ConsumeNextToken();

        boolean repeat = true;
        boolean endoftheline = false;

        while (repeat) {
            int state = stack.peek().nodeState;
            int ordinal = currentInputToken.TokenType.ordinal();
            //  System.out.println(currentInputToken.getTokenType());
            if (ordinal < 52) {
                String action = parseTable[state][ordinal];
                //System.out.println(ordinal+"state" + state);
                //System.out.println(currentInputToken.getTokenType());
                //System.out.println(action);
                if (action.equals("")) {
                    System.out.println("==========ERROR IN THE PARSING==========");
                    System.out.println("CANNOT PARSE : " + currentInputToken.getTokenType());
                    System.out.println("State : " + state);
                    System.out.println("Ordinal : " + ordinal);
                    return null;
                } else if (action == "acc") {
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

                        if (!Scanner.IsInputEnd()) {
                            System.out.println("END OF SCANNER REACHED");
                            endoftheline = true;
                            currentInputToken = new Token(TokenType.DOLLAR);
                        }

                        break;
                    case 'r':
//                         System.out.println(stack.peek().nodeState + "hello");
                        ReductionRule rr = rules.get(action);
                        System.out.println(rr);
                        System.out.println("REDUCING BY " + action + " | " + rr.reductionGrammar);
                        Node m = new Node(new Token(rr.production), -1);

                        for (int i = 0; i < rr.popAmount; i++) {
                            m.nodeChildren.addFirst(stack.pop());
                        }
                        System.out.println(rr.production.ordinal());
                        System.out.println("Stack Peek state#: " + stack.peek().nodeState);
                        System.out.println(rr.production + " ORDINAL " +(rr.production.ordinal()-62));
                        String newState = gotoTable[stack.peek().nodeState][rr.production.ordinal()-50];

                        if (newState == "") {
                            System.out.println("GOTO TABLE ERROR");
                            return null;
                        }
                        m.nodeState = Integer.parseInt(newState);
                        stack.push(m);

                        break;

                    default:

                        // System.out.println(stack.size());
                        System.out.println("PARSING ERROR WEW LAD");
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
