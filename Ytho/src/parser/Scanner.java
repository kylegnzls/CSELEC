/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import TokenLib.*;
import java.io.PushbackInputStream;
import java.util.Hashtable;

public class Scanner {

    public static PushbackInputStream bitSyntax;
    Hashtable<String, TokenType> reservedWords;
    Hashtable<String, TokenID> identifiers;
    Hashtable<Integer, TokenNum> numeric;
    int currBit = 0;
    static int line = 1;

    public Scanner(Hashtable<String, TokenType> reservedWords, Hashtable<String, TokenID> identifiers, Hashtable<Integer, TokenNum> numeric, String fileNameUrl) {
        this.reservedWords = reservedWords;
        this.identifiers = identifiers;
        this.numeric = numeric;

        try {
            bitSyntax = Inhale.ExhaleText(fileNameUrl);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error in the source file");
        }

    }

    static boolean IsInputEnd() {
        try {
            int i = bitSyntax.read();

            if (i != -1) {
                bitSyntax.unread(i);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public Token ConsumeNextToken() {
        Token requestToken;

        int CURRENT_STATE = 0;
        boolean returnString = false;
        String tokenS = "";
        String sym = "!@#$%^&*()_+[{}]|:'<>?,./";
        while (!returnString) {
            char c = Consume();
            tokenS += c;
            //System.out.println(c);
            //  System.out.println(tokenS);
            switch (CURRENT_STATE) {
                case 0:

                    if (currBit == 10) {
                        return new Token(TokenType.NEWLINE);
                    }
                    //End of the line 
                    if (c == '$') {
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.WHITESPACE);
                    } //SIMULA 1 - 6
                    else if (c == 'S') {
                        CURRENT_STATE = 1;
                    }//Whitespace 7 
                    else if (c == '~') {
                        CURRENT_STATE = 93;
                    } else if (c == '"') {
                        tokenS = Remove(tokenS);

                        CURRENT_STATE = 95;
                    } else if (c == ' ' || currBit == 9) {
                        CURRENT_STATE = 7;
                    } //R CURLY  
                    //PAG MALI PUSHBACK TO TOKENS
                    else if (c == '{') {

                        return new Token(TokenType.LCURLY);
                    }//L CURLY  PAG MALI PUSHBACK TO TOKENS
                    else if (c == '}') {

                        return new Token(TokenType.RCURLY);
                    } //WAKAS 10 - 14
                    else if (c == 'W') {
                        CURRENT_STATE = 10;

                    } else if (c == ';') { //end line 15
                        CURRENT_STATE = 15;
                    } else if (c == 'b') {//basag 16 - 20
                        CURRENT_STATE = 16;

                    } else if (c == 'd') {
                        CURRENT_STATE = 107;
                    } else if (c == 'o') {
                        CURRENT_STATE = 112;
                    } else if (c == ',') {

                        return new Token(TokenType.COMMA);

                    } //asign or relop 29-30
                    else if (c == '=') {
                        CURRENT_STATE = 28;

                    }//asign or relop 29-30
                    else if (c == '<') {
                        CURRENT_STATE = 28;

                    } //
                    else if (c == '>') {
                        CURRENT_STATE = 32;

                    } //ADDSUB
                    else if (c == '+') {
                        CURRENT_STATE = 51;
                    } else if (c == '-') {
                        CURRENT_STATE = 52;
                    } // MULDIV
                    else if (c == '*' || c == '/') {

                        return new Token(TokenType.MULDIV);
                    } // EXPONENT
                    else if (c == '^') {

                        return new Token(TokenType.EXPON);
                    } //RPAREN
                    else if (c == '(') {

                        return new Token(TokenType.LPAREN);
                    }//LPAREN
                    else if (c == ')') {

                        return new Token(TokenType.RPAREN);
                    } // INTLIT 21
                    else if (IsDigit(c)) {
                        CURRENT_STATE = 105;
                    } // or , and 22-23
                    else if (c == '&') {
                        CURRENT_STATE = 22;
                    } //24-25
                    else if (c == '|') {
                        CURRENT_STATE = 24;
                    } //NOT NAND NOR 26-27
                    else if (c == '!') {
                        CURRENT_STATE = 26;
                    } //KUNG 33 - 37
                    else if (c == 'k') {
                        CURRENT_STATE = 34;
                    } //MAIBA 38 -42
                    else if (c == 'm') {
                        CURRENT_STATE = 38;
                    }//paraikot 43 - 50 PAKITS 56 PATINGS 57
                    else if (c == 'p') {
                        CURRENT_STATE = 43;
                    } //input 62
                    else if (c == '#') {
                        CURRENT_STATE = 62;
                    } else if (c == 'i') {
                        CURRENT_STATE = 63;
                    } //lutangs
                    else if (c == 'l') {
                        CURRENT_STATE = 69;
                    } //char
                    else if (c == 'c') {
                        CURRENT_STATE = 76;
                    }//stringtho 
                    else if (c == 's') {
                        CURRENT_STATE = 84;
                    } else if (c == '@') {
                        CURRENT_STATE = 97;
                    } //IDENTIFIER 8-9
                    else if (IsAlphabet(c)) {
                        CURRENT_STATE = 8;
                    } else {
                        if (currBit != 13 && currBit != 255) {
                            System.out.println(c + " " + currBit);
                            return new Token(TokenType.ERROR);
                        } else {
                            line++;
                        }
                    }
                    break;

                //27
                //simula
                case 1:
                    if (c == 'i') {
                        CURRENT_STATE = 2;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 2:
                    if (c == 'm') {
                        CURRENT_STATE = 3;
                    } else {

                        CURRENT_STATE = 8;
                    }

                    break;
                case 3:
                    if (c == 'u') {
                        CURRENT_STATE = 4;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 4:
                    if (c == 'l') {
                        CURRENT_STATE = 5;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 5:
                    if (c == 'a') {
                        CURRENT_STATE = 6;
                    } else {

                        CURRENT_STATE = 8;
                    }

                    break;
                case 6:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.SIMULA);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

                //whitespace
                case 7:
                    if (c == ' ') {
                        CURRENT_STATE = 7;
                    } else {
                        Pushback();
                        return new Token(TokenType.WHITESPACE);
                    }
                    break;
                //IDENTIFIER
                case 8:
                    if (AlphaOrDigit(c) || currBit == 58 || c == '-' || c == ':' || currBit == 58 || sym.indexOf(c) != -1) {

                        CURRENT_STATE = 8;

                    } else {
                        Pushback();
                        tokenS = Remove(tokenS);
                        if (!identifiers.containsKey(tokenS)) {
                            identifiers.put(tokenS, new TokenID(tokenS));
                        }

                        return new TokenID(tokenS);
                    }
                    break;
                case 9:
                    if (AlphaOrDigit(c)) {
                        CURRENT_STATE = 9;
                    } else {
                        if (!identifiers.containsKey(tokenS)) {
                            identifiers.put(tokenS, new TokenID(tokenS));
                        }
                        return new TokenID(tokenS);
                    }
                    break;
                case 10:
                    if (c == 'a') {
                        CURRENT_STATE = 11;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 11:
                    if (c == 'k') {
                        CURRENT_STATE = 12;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 12:
                    if (c == 'a') {
                        CURRENT_STATE = 13;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 13:
                    if (c == 's') {
                        CURRENT_STATE = 14;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 14:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good

                        return new Token(TokenType.WAKAS);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 15://end line
                    Pushback();
                    return new Token(TokenType.ENDLINE);

                case 16:
                    if (c == 'a') {
                        CURRENT_STATE = 17;
                    } else if (c == 'o') {
                        CURRENT_STATE = 66;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 17:
                    if (c == 's') {
                        CURRENT_STATE = 18;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 18:
                    if (c == 'a') {
                        CURRENT_STATE = 19;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 19:
                    if (c == 'g') {
                        CURRENT_STATE = 20;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 20:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.BASAG);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

                case 22:
                    if (c == '_') {
                        CURRENT_STATE = 23;
                    } else if (c == '+') {
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.CONCAT);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 23:
                    if (c == '&') {

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.AND);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

                case 24:
                    if (c == '_') {
                        CURRENT_STATE = 25;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 25:
                    if (c == '|') {

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.OR);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 26:
                    if (c == '_') {
                        CURRENT_STATE = 27;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 27:
                    if (c == '!') {

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.NOT);
                    } else if (c == '&') {

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.NAND);
                    } else if (c == '|') {

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.NOR);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

                case 28:
                    if (c == '_') {
                        CURRENT_STATE = 29;
                    } else if (c == ' ') {
                        System.out.println(tokenS);
                        return new Token(TokenType.ASSIGN);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 29:
                    if (c == '=') {
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.RELOP);
                    } else {

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.RELOP);
                    }
                case 30:
                    if (c == '_') {
                        CURRENT_STATE = 31;
                    } else if (c == ' ') {
                        Pushback();
                        return new Token(TokenType.RELOP);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 31:
                    if (c == '=') {
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.RELOP);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 32:
                    if (c == '_') {
                        CURRENT_STATE = 33;
                    } else if (c == ' ') {
                        Pushback();
                        return new Token(TokenType.RELOP);
                    } else {
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.RELOP);
                    }
                    break;
                case 33:
                    if (c == '=') {
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.RELOP);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 34://KUNG
                    if (c == 'u') {
                        CURRENT_STATE = 35;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 35:
                    if (c == 'n') {
                        CURRENT_STATE = 36;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 36:
                    if (c == 'g') {
                        CURRENT_STATE = 37;
                    } else {

                        CURRENT_STATE = 8;
                    }

                    break;
                case 37:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.KUNG);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 38:
                    if (c == 'a') {
                        CURRENT_STATE = 39;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 39:
                    if (c == 'i') {
                        CURRENT_STATE = 40;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 40:
                    if (c == 'b') {
                        CURRENT_STATE = 41;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 41:
                    if (c == 'a') {
                        CURRENT_STATE = 42;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 42:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.MAIBA);
                    } else {
                        CURRENT_STATE = 8;
                    }
                //paraikot
                case 43:
                    if (c == 'a') {
                        CURRENT_STATE = 44;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 44:
                    if (c == 'r') {
                        CURRENT_STATE = 45;
                    } else if (c == 'k') {
                        CURRENT_STATE = 53;
                    } else if (c == 't') {
                        CURRENT_STATE = 57;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 45:
                    if (c == 'a') {
                        CURRENT_STATE = 46;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 46:
                    if (c == 'i') {
                        CURRENT_STATE = 47;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 47:
                    if (c == 'k') {
                        CURRENT_STATE = 48;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 48:
                    if (c == 'o') {
                        CURRENT_STATE = 49;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 49:
                    if (c == 't') {
                        CURRENT_STATE = 50;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 50:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.PARAIKOT);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 51:
                    if (c == '>') {

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.INCREMENT);
                    } else if (c == ' ') {

                        return new Token(TokenType.ADDSUB);
                    } else {

                        return new Token(TokenType.ERROR);
                    }
                case 52:
                    if (c == '>') {
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.DECREMENT);
                    } else if (c == ' ') {

                        return new Token(TokenType.ADDSUB);
                    } else {

                        return new Token(TokenType.ERROR);
                    }
                case 53:
                    if (c == 'i') {
                        CURRENT_STATE = 54;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 54:
                    if (c == 't') {
                        CURRENT_STATE = 55;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 55:
                    if (c == 's') {
                        CURRENT_STATE = 56;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 56:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.PAKITS);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

                //patings
                case 57:
                    if (c == 'i') {
                        CURRENT_STATE = 58;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 58:
                    if (c == 'n') {
                        CURRENT_STATE = 59;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 59:
                    if (c == 'g') {
                        CURRENT_STATE = 60;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 60:
                    if (c == 's') {
                        CURRENT_STATE = 61;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 61:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.PATINGS);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 62:
                    if (c == '>') {
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.INPUT);
                    } else {

                        return new Token(TokenType.ERROR);
                    }
                case 63:
                    if (c == 'n') {
                        CURRENT_STATE = 64;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 64:
                    if (c == 't') {
                        CURRENT_STATE = 65;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 65:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.INTEGER);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 66:
                    if (c == 'o') {
                        CURRENT_STATE = 67;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 67:
                    if (c == 'l') {
                        CURRENT_STATE = 68;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 68:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.BOOL);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 69:
                    if (c == 'u') {
                        CURRENT_STATE = 70;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 70:
                    if (c == 't') {
                        CURRENT_STATE = 71;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 71:
                    if (c == 'a') {
                        CURRENT_STATE = 72;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 72:
                    if (c == 'n') {
                        CURRENT_STATE = 73;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 73:
                    if (c == 'g') {
                        CURRENT_STATE = 74;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 74:
                    if (c == 's') {
                        CURRENT_STATE = 75;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 75:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.LUTANGS);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 76:
                    if (c == 'h') {
                        CURRENT_STATE = 77;
                    } else if (c == 'o') {
                        CURRENT_STATE = 80;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 77:
                    if (c == 'a') {
                        CURRENT_STATE = 78;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 78:
                    if (c == 'r') {
                        CURRENT_STATE = 79;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 79:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.CHAR);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 80:
                    if (c == 'n') {
                        CURRENT_STATE = 81;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 81:
                    if (c == 's') {
                        CURRENT_STATE = 82;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 82:
                    if (c == 't') {
                        CURRENT_STATE = 83;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 83:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.CHAR);
                    } else {
                        CURRENT_STATE = 8;
                    }
                case 84:
                    if (c == 't') {
                        CURRENT_STATE = 85;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 85:
                    if (c == 'r') {
                        CURRENT_STATE = 86;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 86:
                    if (c == 'i') {
                        CURRENT_STATE = 87;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 87:
                    if (c == 'n') {
                        CURRENT_STATE = 88;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 88:
                    if (c == 'g') {
                        CURRENT_STATE = 89;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 89:
                    if (c == 't') {
                        CURRENT_STATE = 90;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 90:
                    if (c == 'h') {
                        CURRENT_STATE = 91;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 91:
                    if (c == 'o') {
                        CURRENT_STATE = 92;
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;
                case 92:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.STRINGTHO);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 93:
                    if (c == '"') {
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.LQUOTE);
                    } else {

                        CURRENT_STATE = 8;
                    }
                    break;

                case 95:

                    if (c == '~') {
                        CURRENT_STATE = 96;
                    } else if (AlphaOrDigit(c) || c == ' ') {

                        CURRENT_STATE = 106;
                    } else {

                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 96:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.RQUOTE);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 97: //single line
                    if (c == '@') {
                        CURRENT_STATE = 98;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 98:

                    if (currBit == 10) {

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.COMMENT);
                    } else {
                        CURRENT_STATE = 98;
                    }
                    break;//MULTI LINEbreak;
                case 99:
                    if (c == '-') {
                        CURRENT_STATE = 100;
                    } else {
                        return new Token(TokenType.ERROR);

                    }
                    break;
                case 100:
                    if (c == '-') {
                        CURRENT_STATE = 101;
                    } else {
                        return new Token(TokenType.ERROR);

                    }
                    break;
                case 101:
                    if (c == '-') {
                        CURRENT_STATE = 102;
                    } else {
                        CURRENT_STATE = 101;
                    }
                    break;
                case 102:
                    if (c == '-') {
                        CURRENT_STATE = 103;
                    } else {
                        CURRENT_STATE = 101;
                    }
                    break;
                case 103:
                    if (c == '@') {
                        CURRENT_STATE = 104;
                    } else {
                        CURRENT_STATE = 101;
                    }
                    break;
                case 104:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.WHITESPACE);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 105:

                    if (IsDigit(c)) {
                        CURRENT_STATE = 105;
                    } else if (c == '.') {//float di pa tapos
                        CURRENT_STATE = 107;
                    } else if (c == ' ' || c == ';') {
                        Pushback();
                        tokenS = Remove(tokenS);

                        if (!numeric.containsKey(Integer.parseInt(tokenS))) {
                            numeric.put(Integer.parseInt(tokenS), new TokenNum(Integer.parseInt(tokenS)));
                        }
                        return new TokenNum(Integer.parseInt(tokenS));
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 106:
                    if (c != '"') {
                        CURRENT_STATE = 106;
                    } else {
                       // System.out.println(tokenS);
                        Pushback();
                        tokenS = Remove(tokenS);
                       // System.out.println(tokenS);
                        return new TokenString(tokenS);
                    }
                    break;
                case 107:
                    if (c == 'e') {
                        CURRENT_STATE = 108;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 108:
                    if (c == 'i') {
                        CURRENT_STATE = 109;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 109:
                    if (c == 'n') {
                        CURRENT_STATE = 110;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 110:
                    if (c == 's') {
                        CURRENT_STATE = 111;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 111:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.BOOLCONST);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 112:
                    if (c == 'm') {
                        CURRENT_STATE = 113;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 113:
                    if (c == 's') {
                        CURRENT_STATE = 114;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 114:
                    if (c == 'i') {
                        CURRENT_STATE = 115;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                    case 115:
                    if (c == 'm') {
                        CURRENT_STATE = 116;
                    } else {
                        CURRENT_STATE = 8;
                    }
                    break;
                case 116:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.BOOLCONST);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

            }

        }

        return new Token(TokenType.ERROR);

    }

    char Consume() {
        try {
            currBit = bitSyntax.read();
            if (currBit == -1) {
                return '$';
            }

            return (char) currBit;

        } catch (Exception e) {
            System.out.println("CONSUME METHOD ERROR");
        }
        return '0';
    }

    void Pushback() {
        try {
            bitSyntax.unread(currBit);
        } catch (Exception e) {
            System.out.println("PUSHBACK METHOD ERROR");
        }
    }

    String Remove(String tokenString) {
        return tokenString.substring(0, tokenString.length() - 1);
    }

    boolean IsAlphabet(char c) {
        return Character.isLetter(c);
    }

    boolean IsDigit(char c) {
        return Character.isDigit(c);
    }

    boolean AlphaOrDigit(char c) {
        return (Character.isDigit(c) || Character.isLetter(c));
    }

    boolean AlphaAndDigit(char c) {
        return (Character.isDigit(c) && Character.isLetter(c));
    }
}
