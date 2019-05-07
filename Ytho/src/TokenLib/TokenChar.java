/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TokenLib;

public class TokenChar extends Token{

    public char literal = 0;

    public TokenChar(char name){
        super(TokenLib.TokenType.CHARLITERAL);
        literal = name;
    }

}