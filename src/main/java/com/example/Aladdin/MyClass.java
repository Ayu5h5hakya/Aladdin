package com.example;

import java.util.ArrayList;
import java.util.Scanner;

public class MyClass {
    public static void main(String[] args){
        String test=null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the sentence");
        test=scanner.nextLine();

        Tokenizer tokenizer = new Tokenizer(test);
        ArrayList<String> tokenizedtext = (ArrayList<String>) tokenizer.start();

        System.out.println("Tokenizer output:");
        for(String temp : tokenizedtext)
        {
            System.out.print(temp + " ");
        }

        boolean[] tokens = tokenizer.getTokens();

        System.out.println();
        System.out.println();
        System.out.println("Capitalizations");
        for(boolean temp: tokens)
        {
            System.out.print(temp+"\t");
        }
        System.out.println();
        System.out.println();
        Porter porter = new Porter();
        ArrayList<String> final_op = porter.start(tokenizedtext,tokens);

        System.out.println("Stemmer output:");
        for(String temp : final_op)
        {
            System.out.print(temp + " ");
        }
        System.out.println();

    }
}