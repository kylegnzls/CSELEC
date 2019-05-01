/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TokenLib;

/**
 *
 * @author Blank
 */
public class Token {

  
    public TokenType TokenType;

   

      public Token(TokenType tokenType){
        this.TokenType = tokenType;
    }

    public TokenType getTokenType(){
        return TokenType;
}
}
