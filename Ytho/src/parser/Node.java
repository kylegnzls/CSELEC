/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;
import java.util.ArrayList;
import java.util.LinkedList;
import TokenLib.Token;
/**
 *
 * @author Blank
 */
public class Node {
    Token nodeToken;
    int nodeState;
    public LinkedList<Node> nodeChildren;

    public Node(){
            nodeChildren = new LinkedList<>();
    }

    public Node(Token nodeToken, int nodeState){
        this.nodeToken = nodeToken;
        this.nodeState = nodeState;
        nodeChildren = new LinkedList<>();

    }
}
