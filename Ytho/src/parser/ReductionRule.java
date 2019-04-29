/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import TokenLib.TokenType;

/**
 *
 * @author Blank
 */
public class ReductionRule {
      public TokenType production;
    public int popAmount;
    public String reductionGrammar;

    public  ReductionRule(TokenType production, int popAmount, String reductionGrammar) {
        this.production = production;
        this.popAmount = popAmount;
        this.reductionGrammar = reductionGrammar;
}
}
