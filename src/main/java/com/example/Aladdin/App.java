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

        SentenceProcessing sentenceProcessing = new SentenceProcessing();

        String test=null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the sentence");

        test=scanner.nextLine();
        test += '\n'; 
        
        DataSet neutralDataSet = new DataSet(sentenceProcessing.sentenceProcessor(test));
        naiveBayes.getOutput(neutralDataSet);
    }
}
