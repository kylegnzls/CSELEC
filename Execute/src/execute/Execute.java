/*
C:\Users\MILTON\Desktop
 */
package execute;

import java.io.*;
/**
 *
 * @author MILTON
 */
public class Execute {

    public static void main(String[] args) {

        Lexer lexer = new Lexer("C:\\Users\\MILTON\\Desktop\\Program.txt");

        System.out.println("Lexical Analysis");
        System.out.println("-----------------");
        while (!lexer.isExausthed()) {
            System.out.printf("%-18s :  %s \n",lexer.currentLexema() , lexer.currentToken());
            lexer.moveAhead();
        }

        if (lexer.isSuccessful()) {
            System.out.println("Ok! :D");
        } else {
            System.out.println(lexer.errorMessage());
        }
    }
    
}
