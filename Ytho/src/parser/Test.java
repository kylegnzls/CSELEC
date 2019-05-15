/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Blank
 */
import TokenLib.*;
import grtree.NPLviewer;
import parser.Parser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;

public class Test {

    public static void main(String[] args) throws IOException {

        Hashtable<String, TokenType> reservedWords = new Hashtable<String, TokenType>();
        Hashtable<String, TokenID> identifiers = new Hashtable<String, TokenID>();
        Hashtable<Integer, TokenNum> numeric = new Hashtable<Integer, TokenNum>();
        Hashtable<Float, TokenFloat> floatie = new Hashtable<Float, TokenFloat>();
        reservedWords = Inhale.ExhaleReserves();
        Scanner scan = new Scanner(reservedWords, identifiers, numeric, floatie, "‪D:\\Github\\CSELEC\\Ytho\\src\\ytho\\TestFiles\\Test1.txt");

        while (Scanner.IsInputEnd()) {
            Token currentToken = scan.ConsumeNextToken();

            if (currentToken.getTokenType() == TokenType.ERROR) {
                System.out.println("ERROR IN THE SYNTAX AT LINE " + Scanner.line);
                break;
            } else {
                switch (currentToken.getTokenType()) {
                    case WHITESPACE:
                    case COMMENT:
                    case ENDLINE:
                        break;
                    case NEWLINE:
                        System.out.println();
                        break;
                    case STRINGLIT:
                        //System.out.println(currentToken.tokenType);
                        TokenString strT = (TokenString) currentToken;

                        System.out.print("[" + currentToken.getTokenType() + " = " + strT.literal + "] ");
                        break;
                    case CHARLITERAL:
                        TokenChar lit = (TokenChar) currentToken;
                        System.out.print("[" + currentToken.getTokenType() + " = " + lit.literal + "] ");
                        break;
                    case INTLIT:
                        TokenNum num = (TokenNum) currentToken;
                        System.out.print("[" + currentToken.getTokenType() + " = " + num.num + "] ");
                        break;
                    case FLOATLIT:
                        TokenFloat floati = (TokenFloat) currentToken;
                        System.out.print("[" + currentToken.getTokenType() + " = " + floati.num + "] ");
                        break;
                    case ID:
                        TokenID tid = (TokenID) currentToken;
                        System.out.print("[" + currentToken.getTokenType() + " = " + tid.idName + "] ");
                        break;
                    default:
                        System.out.print("[" + currentToken.getTokenType() + "] ");
                }
            }

        }
        Scanner scan2 = new Scanner(reservedWords, identifiers, numeric, floatie, "D:\\Github\\CSELEC\\Ytho\\src\\ytho\\TestFiles\\Test1.txt");

        Parser parse = new Parser(scan2);
        Node root = parse.startParse();

        if (root != null) {
            System.out.println("STARTING SEARCH");
            String treeSyntax = parse.DepthFirstTraversal(root);
            System.out.println(treeSyntax);
            try (FileWriter fw = new FileWriter("D:\\Github\\CSELEC\\Ytho\\src\\ytho\\TestFiles\\grtree.txt")) {
                fw.write(treeSyntax);
            }
            new NPLviewer("D:\\Github\\CSELEC\\Ytho\\src\\ytho\\TestFiles\\grtree.txt");
        }

    }

}
