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
public class TokenNum extends Token{
     public int num = 0;

   
    public TokenNum(int num){
        super(TokenLib.TokenType.INT_CONST);
        this.num = num;
}
}
