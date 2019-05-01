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

        while (!returnString) {
            char c = Consume();
            tokenS += c;

            String sym = "!@#$%^&*()_+[{}]|:'<>?,./";
           // System.out.println(c);
            switch (CURRENT_STATE) {
                case 0:

                    if (currBit == 10) {
                        return new Token(TokenType.NEWLINE);
                    }
                    //End of the line
                    if (c == '$') {
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.WHITESPACE);
                    } //SIMULA
                    else if (c == 'S') {
                        CURRENT_STATE = 1;
                    } //Whitespace
                    else if (c == ' ' || currBit == 9) {
                        CURRENT_STATE = 7;
                    } //End  - WAKAS //DONE
                    else if (c == 'W') {
                        CURRENT_STATE = 8;
                    } //Endline
                    else if (c == ';') {
                        CURRENT_STATE = 13;
                        //RELOP
                    } else if (c == '<' || c == '>' || c == '=') {
                        CURRENT_STATE = 14;
                    }//AND
                    else if (c == '&') {
                        CURRENT_STATE = 17;
                    }//OR
                    else if (c == '|') {
                        CURRENT_STATE = 20;
                    }//NOR NAND 
                    else if (c == '!') {
                        CURRENT_STATE = 23;

                    } else if (c == 'k') {//kung
                        CURRENT_STATE = 28;
                    } else if (c == 'm') {//maiba
                        CURRENT_STATE = 32;
                    } else if (c == 'p') {//paraikot,pakits
                        CURRENT_STATE = 37;
                    } else if (c == 'b') {//basag
                        CURRENT_STATE = 47;
                    } else if (c == 'i')//ibalik , int
                    {
                        CURRENT_STATE = 45;
                    } else if (c == 'l')//lutangs
                    {
                        CURRENT_STATE = 75;
                    } else if (c == 'c')//char,const
                    {
                        CURRENT_STATE = 61;
                    } else if (c == 's')//stringtho
                    {
                        CURRENT_STATE = 88;
                    } else if (c == 'd')//lutangs
                    {
                        CURRENT_STATE = 97;
                    } else if (c == 'o')//lutangs
                    {
                        CURRENT_STATE = 102;
                    } else if (c == '@')//lutangs
                    {
                        CURRENT_STATE = 69;
                    } else if (c == '{')//lutangs
                    {
                        CURRENT_STATE = 110;
                    } else if (IsAlphabet(c)) {

                        CURRENT_STATE = 108;
                    } else if (IsDigit(c)) {

                        CURRENT_STATE = 111;
                    } else if (c == '"' || currBit == 39) {

                        CURRENT_STATE = 112;
                    } else if (c == '}')//lutangs
                    {
                        CURRENT_STATE = 113;
                    } else if (c == '(')//lutangs
                    {
                        CURRENT_STATE = 114;
                    } else if (c == ')')//lutangs
                    {
                        CURRENT_STATE = 115;
                    } else if (c == '+' || c == '-')//lutangs
                    {
                        CURRENT_STATE = 116;
                    } else if (c == '*' || c == '/')//lutangs
                    {
                        CURRENT_STATE = 117;
                    } else if (c == '^')//lutangs
                    {
                        CURRENT_STATE = 118;
                    } else if (c == '#') {
                        CURRENT_STATE = 126;
                    } else if (c == ',') {

                        tokenS = Remove(tokenS);
                        break;
                    }  else {
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
                        CURRENT_STATE = 108;
                    }
                    break;
                case 2:
                    if (c == 'm') {
                        CURRENT_STATE = 3;
                    } else {

                        return new Token(TokenType.ERROR);
                    }

                    break;
                case 3:
                    if (c == 'u') {
                        CURRENT_STATE = 4;
                    } else {

                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 4:
                    if (c == 'l') {
                        CURRENT_STATE = 5;
                    } else {

                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 5:
                    if (c == 'a') {
                        CURRENT_STATE = 6;
                    } else {

                        return new Token(TokenType.ERROR);
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
                //wakas
                case 8:
                    if (c == 'a') {
                        CURRENT_STATE = 9;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 9:
                    if (c == 'k') {
                        CURRENT_STATE = 10;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 10:
                    if (c == 'a') {
                        CURRENT_STATE = 11;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 11:
                    if (c == 's') {
                        CURRENT_STATE = 12;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 12:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.WAKAS);
                    } else {
                        return new Token(TokenType.ERROR);
                        //end line

                    }
                case 13:
                    Pushback();
                    return new Token(TokenType.ENDLINE);
//<=
                case 14:
                    if (c == ' ') {
                        return new Token(TokenType.RELOP);
                    }
                    if (c == '_') {
                        CURRENT_STATE = 15;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 15:

                    if (c == '=') {
                        CURRENT_STATE = 16;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 16:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.RELOP);
                    } else {
                        return new Token(TokenType.ERROR);
                        //end line

                    }
                case 17:

                    if (c == '_') {
                        CURRENT_STATE = 18;
                    } else if (c == '+') {
                        CURRENT_STATE = 127;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 18:

                    if (c == '&') {
                        CURRENT_STATE = 19;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 19:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.AND);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 20:
                    if (c == '_') {
                        CURRENT_STATE = 21;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 21:

                    if (c == '|') {
                        CURRENT_STATE = 22;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 22:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.OR);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 23:
                    if (c == '_') {
                        CURRENT_STATE = 24;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 24:

                    if (c == '!') {
                        CURRENT_STATE = 25;
                    } else if (c == '&') {
                        CURRENT_STATE = 26;

                    } else if (c == '|') {
                        CURRENT_STATE = 27;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 25:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.NOT);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 26:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.NAND);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 27:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.NOR);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 28:
                    if (c == 'u') {
                        CURRENT_STATE = 29;
                    } else {
                        CURRENT_STATE = 108;
                    }

                    break;
                case 29:
                    if (c == 'n') {
                        CURRENT_STATE = 30;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 30:
                    if (c == 'g') {
                        CURRENT_STATE = 31;
                    } else {
                        return new Token(TokenType.ERROR);
                    }

                    break;
                case 31:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.KUNG);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 32:
                    if (c == 'a') {
                        CURRENT_STATE = 33;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 33:
                    if (c == 'i') {
                        CURRENT_STATE = 34;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 34:
                    if (c == 'b') {
                        CURRENT_STATE = 35;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 35:
                    if (c == 'a') {
                        CURRENT_STATE = 36;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 36:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.MAIBA);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

                case 37:
                    if (c == 'a') {
                        CURRENT_STATE = 38;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 38:
                    if (c == 'r') {
                        CURRENT_STATE = 39;
                    } else if (c == 'k') {
                        CURRENT_STATE = 57;
                    } else if (c == 't') {
                        CURRENT_STATE = 120;
                    } else {
                        return new Token(TokenType.ERROR);
                    }

                    break;
                case 39:
                    if (c == 'a') {
                        CURRENT_STATE = 40;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 40:
                    if (c == 'i') {
                        CURRENT_STATE = 41;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 41:
                    if (c == 'k') {
                        CURRENT_STATE = 42;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 42:
                    if (c == 'o') {
                        CURRENT_STATE = 43;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 43:
                    if (c == 't') {
                        CURRENT_STATE = 44;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;

                case 44:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.PARAIKOT);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 45:
                    if (c == 'n') {
                        CURRENT_STATE = 46;
                    } else if (c == 'b') {
                        CURRENT_STATE = 83;
                    } else {
                        CURRENT_STATE = 108;
                    }
                    break;
                case 46:
                    if (c == 't') {
                        CURRENT_STATE = 56;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;

                case 47:
                    if (c == 'a') {
                        CURRENT_STATE = 48;
                    } else if (c == 'o') {
                        CURRENT_STATE = 53;
                    } else {
                        Pushback();
                        tokenS = Remove(tokenS);
                        if (!identifiers.containsKey(tokenS)) {
                            identifiers.put(tokenS, new TokenID(tokenS));
                        }

                        return new TokenID(tokenS);
                    }
                    break;
                case 48:
                    if (c == 's') {
                        CURRENT_STATE = 49;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 49:
                    if (c == 'a') {
                        CURRENT_STATE = 50;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 50:
                    if (c == 'g') {
                        CURRENT_STATE = 51;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 51:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.BREAK);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 52:

                    if (c == 'o') {
                        CURRENT_STATE = 53;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 53:

                    if (c == 'o') {
                        CURRENT_STATE = 54;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 54:
                    if (c == 'l') {
                        CURRENT_STATE = 55;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 55:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.BOOLEAN);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 56:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.INTEGER);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 57:
                    if (c == 'i') {
                        CURRENT_STATE = 58;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 58:
                    if (c == 't') {
                        CURRENT_STATE = 59;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 59:
                    if (c == 's') {
                        CURRENT_STATE = 60;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 60:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.PAKITS);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 61:
                    if (c == 'h') {
                        CURRENT_STATE = 62;
                    } else if (c == 'o') {//not done
                        CURRENT_STATE = 65;
                    } else {
                        CURRENT_STATE = 108;
                    }
                    break;
                case 62:
                    if (c == 'a') {
                        CURRENT_STATE = 63;
                    } else {
                        CURRENT_STATE = 108;
                    }
                    break;
                case 63:
                    if (c == 'r') {
                        CURRENT_STATE = 64;
                    } else {
                        CURRENT_STATE = 108;
                    }
                    break;
                case 64:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.CHAR);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 65:
                    if (c == 'n') {
                        CURRENT_STATE = 66;
                    } else {
                        CURRENT_STATE = 108;
                    }
                    break;
                case 66:
                    if (c == 's') {
                        CURRENT_STATE = 67;
                    } else {
                        CURRENT_STATE = 108;
                    }
                    break;
                case 67:
                    if (c == 't') {
                        CURRENT_STATE = 68;
                    } else {
                        CURRENT_STATE = 108;
                    }
                    break;
                case 68:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.CONST);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 69:
                    if (c == '-') {
                        CURRENT_STATE = 70;
                    } else if (c == '@') {
                        CURRENT_STATE = 107;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 70:
                    if (c == '-') {
                        CURRENT_STATE = 71;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 71:
                    if (c == '-') {
                        CURRENT_STATE = 72;
                    } else {
                        CURRENT_STATE = 71;
                    }
                    break;
                case 72:
                    if (c == '-') {
                        CURRENT_STATE = 73;
                    } else {
                        CURRENT_STATE = 71;
                    }
                    break;
                case 73:
                    if (c == '@') {
                        CURRENT_STATE = 74;
                    }
                    break;
                case 74:
                    if (!IsAlphabet(c)) {

                        tokenS = Remove(tokenS);
                        return new Token(TokenType.COMMENT);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

//lutangs 75-81
                case 75:
                    if (c == 'u') {
                        CURRENT_STATE = 76;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 76:
                    if (c == 't') {
                        CURRENT_STATE = 77;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 77:
                    if (c == 'a') {
                        CURRENT_STATE = 78;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 78:
                    if (c == 'n') {
                        CURRENT_STATE = 79;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 79:
                    if (c == 'g') {
                        CURRENT_STATE = 80;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 80:
                    if (c == 's') {
                        CURRENT_STATE = 81;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 81:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.LUTANGS);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

//ibalik 82-87
                case 82:
                    if (c == 'b') {
                        CURRENT_STATE = 83;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 83:
                    if (c == 'a') {
                        CURRENT_STATE = 84;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 84:
                    if (c == 'l') {
                        CURRENT_STATE = 85;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 85:
                    if (c == 'i') {
                        CURRENT_STATE = 86;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 86:
                    if (c == 'k') {
                        CURRENT_STATE = 87;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 87:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.IBALIK);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

//stringtho 88-96
                case 88:
                    if (c == 't') {
                        CURRENT_STATE = 89;
                    } else {
                        CURRENT_STATE = 108;
                    }
                    break;
                case 89:
                    if (c == 'r') {
                        CURRENT_STATE = 90;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 90:
                    if (c == 'i') {
                        CURRENT_STATE = 91;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 91:
                    if (c == 'n') {
                        CURRENT_STATE = 92;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 92:
                    if (c == 'g') {
                        CURRENT_STATE = 93;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 93:
                    if (c == 't') {
                        CURRENT_STATE = 94;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 94:
                    if (c == 'h') {
                        CURRENT_STATE = 95;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 95:
                    if (c == 'o') {
                        CURRENT_STATE = 96;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;

                case 96:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.STRINGTHO);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

//deins 97-101
                case 97:
                    if (c == 'e') {
                        CURRENT_STATE = 98;
                    } else {
                        CURRENT_STATE = 108;
                    }
                    break;
                case 98:
                    if (c == 'i') {
                        CURRENT_STATE = 99;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 99:
                    if (c == 'n') {
                        CURRENT_STATE = 100;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 100:
                    if (c == 's') {
                        CURRENT_STATE = 101;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;

                case 101:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.DEINS);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

//omsim 102-106
                case 102:
                    if (c == 'm') {
                        CURRENT_STATE = 103;
                    } else {
                        CURRENT_STATE = 108;
                    }
                    break;
                case 103:
                    if (c == 's') {
                        CURRENT_STATE = 104;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 104:
                    if (c == 'i') {
                        CURRENT_STATE = 105;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 105:
                    if (c == 'm') {
                        CURRENT_STATE = 106;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;

                case 106:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.OMSIM);
                    } else {
                        return new Token(TokenType.ERROR);
                    }

                case 107:
                    if (currBit == 10) {
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.COMMENT);
                    } else {
                        CURRENT_STATE = 107;
                    }
                    break;
                case 108:
                    if (AlphaOrDigit(c) || currBit == 58 || c == '-' || c == ':' || currBit == 58 || sym.indexOf(c) != -1) {

                        CURRENT_STATE = 108;

                    } else {
                        Pushback();
                        tokenS = Remove(tokenS);
                        if (!identifiers.containsKey(tokenS)) {
                            identifiers.put(tokenS, new TokenID(tokenS));
                        }

                        return new TokenID(tokenS);
                    }
                    break;
                case 110:
                    tokenS = Remove(tokenS);
                    return new Token(TokenType.LCURLY);
                case 111:
                    if (IsDigit(c)) {
                        CURRENT_STATE = 111;
                        break;
                    } else if (!IsAlphabet(c) /*c == ' ' || currBit == 13 || c == '$' ||*/) {
                        Pushback();
                        tokenS = Remove(tokenS);
                        if (!numeric.containsKey(Integer.parseInt(tokenS))) {
                            numeric.put(Integer.parseInt(tokenS), new TokenNum(Integer.parseInt(tokenS)));
                        }

                        return new TokenID(tokenS);
                    } else if (!IsDigit(c)) {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 112:
                    tokenS = Remove(tokenS);
                    return new Token(TokenType.LQUOTE);

                case 113:
                    tokenS = Remove(tokenS);
                    return new Token(TokenType.RCURLY);
                case 114:
                    tokenS = Remove(tokenS);
                    return new Token(TokenType.LPAREN);
                case 115:
                    tokenS = Remove(tokenS);
                    return new Token(TokenType.RPAREN);
                case 116:
                    if (c == '+') {
                        CURRENT_STATE = 119;
                    }
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenType.ADDSUB);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 117:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenType.MULDIV);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 118:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenType.EXPON);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 119:
                    tokenS = Remove(tokenS);
                    return new Token(TokenType.INCREMENT);
                case 120:
                    if (c == 'i') {
                        CURRENT_STATE = 121;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 121:
                    if (c == 'n') {
                        CURRENT_STATE = 123;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;

                case 123:
                    if (c == 'g') {
                        CURRENT_STATE = 124;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;
                case 124:
                    if (c == 's') {
                        CURRENT_STATE = 125;
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                    break;

                case 125:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.PATINGS);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 126:
                    if (c == '>') {
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.INPUT);
                    } else {
                        return new Token(TokenType.ERROR);
                    }
                case 127:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.CONCAT);
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
