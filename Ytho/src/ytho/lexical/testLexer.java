/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ytho.lexical;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ytho.lexical.Lexer;
import ytho.lexical.Token;
import ytho.lexical.TokenType;

/**
 *
 * @author Blank
 */
public class testLexer {

    private static String fileName;

    public static void main(String[] args) throws IOException {
        String path = "D:\\GitHub\\CSELEC\\Ytho\\src\\ytho\\TestFiles\\Test1.txt";

        FileReader file;

        file = new FileReader(path);

        Lexer lexer = new Lexer(file);

        // start tokenizing file
        long startTime = System.currentTimeMillis();
        int numTokens = 0;
                int numError = 0;
        Token token;
        do {
            token = lexer.getToken();
            numTokens++;

            if (token.getType() == TokenType.UNKNOWN) {
                // print token type and location
            
                System.err.print("Error at Line"+" (" + token.getLineNumber() + "," + token.getColumnNumber() + ")");
                System.exit(0);
               // System.out.println();
               // numError++;
               
               // continue;
            }

            if(token.getType() == TokenType.LCURLY || token.getType() == TokenType.RCURLY
            || token.getType() == TokenType.SCLON
            ||token.getType() == TokenType.COMMENT        
            || token.getType() == TokenType.LPAREN  || token.getType() == TokenType.RPAREN        ){
                
            }else{
                 System.out.print(token.getType());
            System.out.print(" (" + token.getLineNumber() + "," + token.getColumnNumber() + ")");
            }
           

            // print out semantic values for ID and INT_CONST tokens
            if (token.getType() == TokenType.ID) {
                System.out.println(": " + token.getAttribute().getIdVal());
                
            } else if (token.getType() == TokenType.INT_CONST) {
                System.out.println(": " + token.getAttribute().getIntVal());
            } else if (token.getType() == TokenType.FLOAT_CONST) {
                System.out.println(": " + token.getAttribute().getFloatVal());
            } else if (token.getType() == TokenType.CHAR_CONST) {
                System.out.println(": " + token.getAttribute().getCharVal());
            }else if (token.getType() == TokenType.STRING_CONST) {
                System.out.println(": " + token.getAttribute().getStringVal());
            }
            else if (token.getType() == TokenType.BOOLEAN_CONST) {
                System.out.println(": " + token.getAttribute().getBooleanVal());
            } else {
                System.out.println();
            }

        } while (token.getType() != TokenType.EOF);

        long endTime = System.currentTimeMillis();

        // print out statistics
        System.out.println("---");
        System.out.println("Number of tokens: " + numTokens);
        System.out.println("Number of Errors: " + numError);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
        System.out.println();
    }
}
