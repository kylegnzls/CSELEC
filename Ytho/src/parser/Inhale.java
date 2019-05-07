package parser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import TokenLib.TokenType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

public class Inhale{

    public static Hashtable<String, TokenType> ExhaleReserves(){
        Hashtable<String, TokenType> reservedWords = new  Hashtable<String, TokenType>();
        
        reservedWords.put("&_&", TokenType.AND);
        reservedWords.put("|_|", TokenType.OR);
        
        reservedWords.put("=_=", TokenType.RELOP);
        reservedWords.put("!_=", TokenType.RELOP);
        reservedWords.put("!_&", TokenType.RELOP);
        reservedWords.put("!_|", TokenType.RELOP);
        reservedWords.put("!_!", TokenType.RELOP);
        reservedWords.put("<", TokenType.RELOP);
        reservedWords.put(">", TokenType.RELOP);
        reservedWords.put("<_=", TokenType.RELOP);
        reservedWords.put(">_=", TokenType.RELOP);
        
        reservedWords.put("+", TokenType.ADDSUB);
        reservedWords.put("-", TokenType.ADDSUB);
        
        reservedWords.put("*", TokenType.MULDIV);
        reservedWords.put("/", TokenType.MULDIV);
        
        reservedWords.put("^", TokenType.EXPON);
        
        reservedWords.put("&+", TokenType.CONCAT);
        
        reservedWords.put("#>", TokenType.INPUT);
        return reservedWords;
    }

    public static PushbackInputStream ExhaleText(String pathDirectory){
        FileReader inputStream = null;
        FileWriter outputStream = null;

        PushbackInputStream fr = null;
        byte[] syntax;

        Path path = Paths.get("D:\\Github\\CSELEC\\Ytho\\src\\ytho\\TestFiles\\Test15.txt");
        try {
            syntax = Files.readAllBytes(path);
            ByteArrayInputStream array = new ByteArrayInputStream(syntax);
            PushbackInputStream push = new PushbackInputStream(array);

            return push;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}