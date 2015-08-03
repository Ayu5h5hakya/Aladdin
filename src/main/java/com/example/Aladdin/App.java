package com.example.Aladdin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App 
{
    public static void main( String[] args )
    {
        //tranning of data set by using Naive Bayes Classifier

        NaiveBayes naiveBayes= new NaiveBayes();


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
        System.out.println();
        System.out.println();

        Porter porter = new Porter();
        ArrayList<String> final_op = porter.start(tokenizedtext);

        System.out.println("Stemmer output:");
        for(String temp : final_op)
        {
            System.out.print(temp + " ");
        }
        System.out.println();
 
    }
}
