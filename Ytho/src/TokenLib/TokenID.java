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
public class TokenID extends Token{
     public String idName = "";

    public TokenID(String name){
        super(TokenType.ID);
        idName = name;
}
}
