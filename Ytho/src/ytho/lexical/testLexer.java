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
		 String path = "C:\\Users\\Blank\\Desktop\\Sample codes\\code1.txt";
                 path = path.replaceAll("\\\\", "/");
                 int x = 1;
		if (args.length == 0)
			System.err.println("No file arguments given");
		else {
			// parse each file argument given
			for (int i = 0; i < args.length; i++) {
				FileReader file;
				// attempt to open file
				try {
					file = new FileReader("path");
				} catch (FileNotFoundException e) {
					System.err.println(args[i] + " was not found!");
					continue; // try next file
				}
				
				// create lexer
				Lexer lexer = new Lexer(file);
				
				// start tokenizing file
				System.out.println("Tokenizing " + args[i] + "...");
				long startTime = System.currentTimeMillis();
				int numTokens = 0;
				Token token;
				do {
					token = lexer.getToken();
					numTokens++;
					
					if(token.getType() == TokenType.UNKNOWN){
						// print token type and location
						System.err.print(token.getType());
						System.err.print(" (" + token.getLineNumber() + "," + token.getColumnNumber() + ")");
						System.out.println();
						continue;
					}
					
					System.out.print(token.getType());
					System.out.print(" (" + token.getLineNumber() + "," + token.getColumnNumber() + ")");
					
					// print out semantic values for ID and INT_CONST tokens
					if (token.getType() == TokenType.ID)
						System.out.println(": " + token.getAttribute().getIdVal());
					else if (token.getType() == TokenType.INT_CONST)
						System.out.println(": " + token.getAttribute().getIntVal());
					else if (token.getType() == TokenType.FLOAT_CONST)
						System.out.println(": " + token.getAttribute().getFloatVal());
					else if (token.getType() == TokenType.CHAR_CONST)
						System.out.println(": " + token.getAttribute().getCharVal());
					else if (token.getType() == TokenType.BOOLEAN_CONST)
						System.out.println(": " + token.getAttribute().getBooleanVal());
					else
						System.out.println();
					
				} while (token.getType() != TokenType.EOF);
				
				long endTime = System.currentTimeMillis();
				
				// print out statistics
				System.out.println("---");
				System.out.println("Number of tokens: " + numTokens);
				System.out.println("Execution time: " + (endTime - startTime) + "ms");
				System.out.println();
			}
		}
	}
}
