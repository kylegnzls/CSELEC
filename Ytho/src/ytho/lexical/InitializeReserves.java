/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ytho.lexical;
import java.util.Hashtable;
import TokenLib.TokenType;
/**
 *
 * @author Blank
 */
public class InitializeReserves {
     Hashtable<TokenType, String> reservedWords = new Hashtable<TokenType, String>();

    Hashtable<TokenType, String> InitalizeReserves(){
        reservedWords.put(TokenType.SIMULA, "Simula");
        reservedWords.put(TokenType.WAKAS, "Wakas");
        return reservedWords;
    }

}
