/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ytho.lexical;

import TokenLib.*;
import java.io.PushbackInputStream;
import java.util.Hashtable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {

    public static PushbackInputStream bitSyntax;
    Hashtable<String, TokenType> reservedWords;
    Hashtable<String, TokenID> identifiers;
    int currBit = 0;
    static int line = 1;

    public Scanner(Hashtable<String, TokenType> reservedWords, Hashtable<String, TokenID> identifiers, String fileNameUrl) {
        this.reservedWords = reservedWords;
        this.identifiers = identifiers;

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

            switch (CURRENT_STATE) {
                case 0:

                    if (currBit == 10) {
                        return new Token(TokenType.NEWLINE);
                    }
                    //End of the line
                    if (c == '$') {
                        tokenS = Remove(tokenS);
                        return new Token(TokenType.WHITESPACE);
                    }
                    //SIMULA
                    else if (c == 'S') {
                        CURRENT_STATE = 1;
                    }
                    //Whitespace
                    else if (c == ' ' || currBit == 9) {
                        CURRENT_STATE = 6;
                    }
                    //End
                    else if (c == 'W') {
                        CURRENT_STATE = 7;
                    }
                    //Endline
                    else if (c == '.') {
                        CURRENT_STATE = 10;
                    }
                    //Program name
                    else if (c == 'P') {
                        CURRENT_STATE = 11;
                    }
                    //Identifier or Integer
                    else if (c == 'I') {
                        CURRENT_STATE = 15;
                    }
                    //Decimal
                    else if (c == 'D') {
                        CURRENT_STATE = 20;
                    }
                    //Logical or Listen
                    else if (c == 'L') {
                        CURRENT_STATE = 23;
                    }
                    //Sentence or Say
                    else if (c == 'S') {
                        CURRENT_STATE = 30;
                    }
                    //Constant Data Types or Consider
                    else if (c == 'C') {
                        CURRENT_STATE = 34;
                    }
                    //Otherwise or Or
                    else if (c == 'O') {
                        CURRENT_STATE = 55;
                    }
                    //Repeat
                    else if (c == 'R') {
                        CURRENT_STATE = 60;
                    }
                    // [ bracket left
                    else if (c == '[') {
                        CURRENT_STATE = 68;
                    }
                    // ] bracket right
                    else if (c == ']') {
                        CURRENT_STATE = 69;
                    }
                    // < block start
                    else if (c == '<') {
                        CURRENT_STATE = 70;
                    }
                    // > block end
                    else if (c == '>') {
                        CURRENT_STATE = 71;
                    }
                    //Increase Decrease
                    else if (c == '#') {
                        CURRENT_STATE = 72;
                    }
                    //Multiply
                    else if (c == 'M') {
                        CURRENT_STATE = 79;
                    }
                    //Neq
                    else if (c == 'N') {
                        CURRENT_STATE = 88;
                    }
                    //And
                    else if (c == 'A') {
                        CURRENT_STATE = 89;
                    }
                    //Xor
                    else if (c == 'X') {
                        CURRENT_STATE = 92;
                    }
                    // , seperator
                    else if (c == ',') {
                        CURRENT_STATE = 99;
                    }
                    // ' letter
                    else if (c == '\'') {
                        CURRENT_STATE = 100;
                    }
                    // " sentence
                    else if (c == '\"') {
                        CURRENT_STATE = 102;
                    }
                    // " bool literal true and false
                    else if (c == 't') {
                        CURRENT_STATE = 110;
                    }
                    else if (c == 'f') {
                        CURRENT_STATE = 114;
                    }
                    //Single line
                    else if (c == ':') {
                        CURRENT_STATE = 106;
                    } else if (IsDigit(c)) {
                        CURRENT_STATE = 104;
                    } else {
                        if (currBit != 13 && currBit != 255) {
                            System.out.println(c + " " + currBit);
                            return new Token(TokenTypes.ERROR);
                        }
                        else{
                            line++;
                        }
                    }
                    break;
                case 1:
                    if (c == 'e') {
                        CURRENT_STATE = 2;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 2:
                    if (c == 'g') {
                        CURRENT_STATE = 3;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }

                    break;
                case 3:
                    if (c == 'i') {
                        CURRENT_STATE = 4;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 4:
                    if (c == 'n') {
                        CURRENT_STATE = 5;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }

                    break;
                case 5:
                    if (!IsAlphabet(c)/*c == ' ' || c == '$' || c == '.'*/) { // Line enders //If its alphabets, bad, if not, good
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenTypes.BEGIN_PROGRAM);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 6:
                    if (c == ' ') {
                        CURRENT_STATE = 6;
                    } else {
                        Pushback();
                        return new Token(TokenTypes.WHITESPACE);
                    }
                    break;
                case 7:
                    if (c == 'n') {
                        CURRENT_STATE = 8;
                    } else if (c == 'q') {
                        CURRENT_STATE = 85;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 8:
                    if (c == 'd') {
                        CURRENT_STATE = 9;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 9:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        tokenS = Remove(tokenS);
                        return new Token(TokenTypes.END_PROGRAM);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 10:
                    Pushback();
                    return new Token(TokenTypes.ENDLINE);
                //=====PROGRAM NAME=======
                case 11:
                    if (c == 'N') {
                        CURRENT_STATE = 12;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 12:
                    if (c == '_') {
                        CURRENT_STATE = 13;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 13:
                    if (AlphaOrDigit(c)) {
                        CURRENT_STATE = 14;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 14:
                    if (AlphaOrDigit(c)) {
                        CURRENT_STATE = 14;
                    } else {
                        Pushback();
                        return new Token(TokenTypes.PROGRAM_NAME);
                    }
                    break;
                //=====IDentifier=======
                case 15:
                    if (c == 'D') {
                        CURRENT_STATE = 16;
                    } else if (c == 'n') {
                        CURRENT_STATE = 18;
                    } else if (c == 's') {
                        CURRENT_STATE = 95;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 16:
                    if (AlphaOrDigit(c)) {
                        CURRENT_STATE = 17;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 17:
                    if (AlphaOrDigit(c)) {
                        CURRENT_STATE = 17;
                    } else {
                        Pushback();
                        tokenS = Remove(tokenS);
                        if(!identifiers.containsKey(tokenS)){
                            identifiers.put(tokenS, new TokenIdentifier(tokenS));
                        }
                        return new TokenIdentifier(tokenS);
                    }
                    break;
                case 18:
                    if (c == 't') {
                        CURRENT_STATE = 19;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 19:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        //return new Token(TokenTypes.DATA_TYPE);
                        return new TokenDataType("INTEGER");
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 20:
                    if (c == 'e') {
                        CURRENT_STATE = 21;
                    } else if (c == 'i') {
                        CURRENT_STATE = 82;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 21:
                    if (c == 'c') {
                        CURRENT_STATE = 22;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 22:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new TokenDataType("DECIMAL");
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 23:
                    if (c == 'o') {
                        CURRENT_STATE = 24;
                    } else if (c == 'e') {
                        CURRENT_STATE = 28;
                    } else if (c == 'i') {
                        CURRENT_STATE = 65;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 24:
                    if (c == 'g') {
                        CURRENT_STATE = 25;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 25:
                    if (c == 'i') {
                        CURRENT_STATE = 26;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 26:
                    if (c == 'c') {
                        CURRENT_STATE = 27;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 27:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new TokenDataType("LOGICAL");
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 28:
                    if (c == 't') {
                        CURRENT_STATE = 29;
                    } else if (c == 's') {
                        CURRENT_STATE = 83;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 29:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new TokenDataType("LETTER");
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 30:
                    if (c == 'e') {
                        CURRENT_STATE = 31;
                    } else if (c == 'a') {
                        CURRENT_STATE = 63;
                    } else if (c == 'u') {
                        CURRENT_STATE = 98;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 31:
                    if (c == 'n') {
                        CURRENT_STATE = 32;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 32:
                    if (c == 't') {
                        CURRENT_STATE = 33;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 33:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new TokenDataType("SENTENCE");
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 34:
                    if (c == 'I') {
                        CURRENT_STATE = 35;
                    } else if (c == 'D') {
                        CURRENT_STATE = 38;
                    } else if (c == 'L') {
                        CURRENT_STATE = 41;
                    } else if (c == 'S') {
                        CURRENT_STATE = 48;
                    } else if (c == 'o') {
                        CURRENT_STATE = 52;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 35:
                    if (c == 'n') {
                        CURRENT_STATE = 36;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 36:
                    if (c == 't') {
                        CURRENT_STATE = 37;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 37:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new TokenDataType("CONSTANTINTEGER");
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 38:
                    if (c == 'e') {
                        CURRENT_STATE = 39;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 39:
                    if (c == 'c') {
                        CURRENT_STATE = 40;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 40:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new TokenDataType("CONSTANTDECIMAL");
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }

                case 41:
                    if (c == 'o') {
                        CURRENT_STATE = 42;
                    } else if (c == 'e') {
                        CURRENT_STATE = 46;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 42:
                    if (c == 'g') {
                        CURRENT_STATE = 43;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 43:
                    if (c == 'i') {
                        CURRENT_STATE = 44;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 44:
                    if (c == 'c') {
                        CURRENT_STATE = 45;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 45:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new TokenDataType("CONSTANTLOGICAL");
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }

                case 46:
                    if (c == 't') {
                        CURRENT_STATE = 47;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 47:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new TokenDataType("CONSTANTLETTER");
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }

                case 48:
                    if (c == 'e') {
                        CURRENT_STATE = 49;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 49:
                    if (c == 'n') {
                        CURRENT_STATE = 50;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 50:
                    if (c == 't') {
                        CURRENT_STATE = 51;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 51:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new TokenDataType("CONSTANTSENTENCE");
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 52:
                    if (c == 'n') {
                        CURRENT_STATE = 53;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 53:
                    if (c == 's') {
                        CURRENT_STATE = 54;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 54:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.COND_CONSIDER);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }

                case 55:
                    if (c == 't') {
                        CURRENT_STATE = 56;
                    } else if (c == 'r') {
                        CURRENT_STATE = 91;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 56:
                    if (c == 'h') {
                        CURRENT_STATE = 57;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 57:
                    if (c == 'e') {
                        CURRENT_STATE = 58;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 58:
                    if (c == 'r') {
                        CURRENT_STATE = 59;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 59:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.COND_OTHERW);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 60:
                    if (c == 'e') {
                        CURRENT_STATE = 61;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 61:
                    if (c == 'p') {
                        CURRENT_STATE = 62;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 62:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.LOOP_REPEAT);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 63:
                    if (c == 'y') {
                        CURRENT_STATE = 64;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 64:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.SET_SAY);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 65:
                    if (c == 's') {
                        CURRENT_STATE = 66;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 66:
                    if (c == 't') {
                        CURRENT_STATE = 67;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 67:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.GET_LISTEN);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 68:
                    Pushback();
                    return new Token(TokenTypes.LBRACKET);
                case 69:
                    Pushback();
                    return new Token(TokenTypes.RBRACKET);
                case 70:
                    Pushback();
                    return new Token(TokenTypes.BLOCKSTART);
                case 71:
                    Pushback();
                    return new Token(TokenTypes.BLOCKEND);
                case 72:
                    if (c == 'I') {
                        CURRENT_STATE = 73;
                    } else if (c == 'D') {
                        CURRENT_STATE = 76;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 73:
                    if (c == 'n') {
                        CURRENT_STATE = 74;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 74:
                    if (c == 'c') {
                        CURRENT_STATE = 75;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 75:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.INDEC_OP);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }

                    //Decrease
                case 76:
                    if (c == 'e') {
                        CURRENT_STATE = 77;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 77:
                    if (c == 'c') {
                        CURRENT_STATE = 78;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 78:
                    if (c == 'r') {
                        CURRENT_STATE = 75;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 79:
                    if (c == 'u') {
                        CURRENT_STATE = 80;
                    } else if (c == 'o') {
                        CURRENT_STATE = 86;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 80:
                    if (c == 'l') {
                        CURRENT_STATE = 81;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 81:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.MULDIV_AROP);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 82:
                    if (c == 'v') {
                        CURRENT_STATE = 81;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;

                case 83:
                    if (c == 'E') {
                        CURRENT_STATE = 84;
                    } else if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.RELOP);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 84:
                    if (c == 'q') {
                        CURRENT_STATE = 85;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 85:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.RELOP);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 86:
                    if (c == 'r') {
                        CURRENT_STATE = 87;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 87:
                    if (c == 'e') {
                        CURRENT_STATE = 83;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 88:
                    if (c == 'E') {
                        CURRENT_STATE = 84;
                    } else if (c == 'a') {
                        CURRENT_STATE = 89;
                    } else if (c == 'o') {
                        CURRENT_STATE = 94;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 89:
                    if (c == 'n') {
                        CURRENT_STATE = 90;
                    } else if (c == 'd') {
                        CURRENT_STATE = 96;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 90:
                    if (c == 'd') {
                        CURRENT_STATE = 91;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 91:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.LOGICALOP);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 92:
                    if (c == 'o') {
                        CURRENT_STATE = 93;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 93:
                    if (c == 'r') {
                        CURRENT_STATE = 91;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 94:
                    if (c == 't') {
                        CURRENT_STATE = 91;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 95:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.ASSIGN_OP);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 96:
                    if (c == 'd') {
                        CURRENT_STATE = 97;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 97:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.ADDSUB_AROP);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 98:
                    if (c == 'b') {
                        CURRENT_STATE = 97;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 99:
                    Pushback();
                    return new Token(TokenTypes.SEPARATOR_ID);
                case 100:
                    if (AlphaOrDigit(c)) {
                        CURRENT_STATE = 101;
                    } else if (c == '\'') {
                        return new TokenChar(tokenS.charAt(1)); //TODO must change this to Char literal
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 101:
                    if (c == '\'') {
                        return new TokenChar(tokenS.charAt(1));//TODO must change this to Char literal
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 102:
                    if (c == '\"') {
                        //Empty String
                        //return new Token(TokenTypes.STRINGLITERAL);
                        return new TokenString(tokenS);
                    } else {
                        CURRENT_STATE = 103;
                    }
                    break;
                case 103:
                    if (c == '$' && currBit == -1) {
                        return new Token(TokenTypes.ERROR);
                    } else if (c != '\"') {
                        CURRENT_STATE = 103;
                        break;
                    } else if (c == '\"') {
                        return new TokenString(tokenS);
                    }
                    break;
                case 104:
                    if (IsDigit(c)) {
                        CURRENT_STATE = 104;
                        break;
                    } else if (!IsAlphabet(c)/*c == ' ' || currBit == 13 || c == '$' ||*/ ) {
                        Pushback();
                        return new Token(TokenTypes.NUMCONSTANTS);
                    } else if (c == 'd') {
                        CURRENT_STATE = 105;
                        break;
                    } else if (!IsDigit(c)) {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 105:
                    if (IsDigit(c)) {
                        CURRENT_STATE = 105;
                        break;
                    } else if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.NUMCONSTANTS);
                    } else if (!IsDigit(c)) {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 106:
                    if (c != ':') {
                        CURRENT_STATE = 107;
                    } else if (c == ':') {
                        CURRENT_STATE = 108;
                    }
                    break;
                case 107:
                    if (c != ':') {
                        CURRENT_STATE = 107;
                    } else if (c == ':' || currBit == 13) {
                        return new Token(TokenTypes.SINGLECOMMENT);
                    }
                    break;
                    //Double comment
                case 108:
                    if (c != ':') {
                        CURRENT_STATE = 108;
                    } else if (c == ':') {
                        CURRENT_STATE = 109;
                    }
                    break;
                case 109:
                    if (c == ':') {
                        return new Token(TokenTypes.MULTICOMMENT);
                    } else if (c != ':') {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 110:
                    if (c == 'r') {
                        CURRENT_STATE = 111;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 111:
                    if (c == 'u') {
                        CURRENT_STATE = 112;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 112:
                    if (c == 'e') {
                        CURRENT_STATE = 113;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 113:
                    if (!IsAlphabet(c)) {
                        Pushback();
                        return new Token(TokenTypes.BOOLLITERAL);
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                case 114:
                    if (c == 'a') {
                        CURRENT_STATE = 115;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 115:
                    if (c == 'l') {
                        CURRENT_STATE = 116;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 116:
                    if (c == 's') {
                        CURRENT_STATE = 117;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
                case 117:
                    if (c == 'e') {
                        CURRENT_STATE = 113;
                    } else {
                        return new Token(TokenTypes.ERROR);
                    }
                    break;
            }
        }

        return new Token(TokenTypes.ERROR);

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