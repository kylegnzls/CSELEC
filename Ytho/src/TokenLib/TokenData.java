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
public class TokenData extends Token{
     public String dataType;

    public TokenData(String dataType) {
        super(TokenLib.TokenType.DTYPE);
        this.dataType = dataType;
}
}
