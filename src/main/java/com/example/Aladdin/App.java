package com.example.Aladdin;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import twitter4j.*;
import java.text.SimpleDateFormat;
import java.util.regex.*;
import twitter4j.conf.*;
import java.lang.*;
 
public class App 
{

 public static void main( String[] args )
    { 
 
          ReadFile readFile=new ReadFile();
          NaiveBayes naiveBayes= new NaiveBayes();
          SentenceProcessing sentenceProcessing = new SentenceProcessing();
          TwitterExtract extract = new TwitterExtract();

          ArrayList<String> positiveList = new ArrayList<String>();
          ArrayList<String> neutralList = new ArrayList<String>();
          ArrayList<String> negativeList = new ArrayList<String>();
   
          extract.getDate();
          String result;

        /*
         *Testing the sentiments of single news


         DataSet hello = new DataSet(sentenceProcessing.sentenceProcessor("250 were killed\n"));
         result = naiveBayes.getOutput(hello);
         System.out.println(result);

         */

          System.out.println("Enter Tag");
          Scanner scanner = new Scanner(System.in);
          String tag = scanner.nextLine();

          extract.extractStatus(tag);

          String test = readFile.readFile("../../src/resources/Twitter.txt");
          String [] testSplit = test.split("\n");
   
       for(String val: testSplit)
         {
       
         val +='\n';

         DataSet testDataSet = new DataSet(sentenceProcessing.sentenceProcessor(val));
         result = naiveBayes.getOutput(testDataSet);
  
         if(result.equals("Positive"))
         {
           positiveList.add(val);
         }
         else if(result.equals("Neutral"))
         {
            neutralList.add(val);
         }
         else if(result.equals("Negative"))
         {
            negativeList.add(val);
         }
         
         }

        File file = new File("../../src/resources/Twitter.txt");
        file.delete();
   
        System.out.println();
        System.out.println("Positive News");

        for(String temp: positiveList)
            System.out.println(temp);
    
        System.out.println();
        System.out.println("Neutral News");

        for(String temp: neutralList)
            System.out.println(temp);
    
        System.out.println();
        System.out.println("Negative News");

        for(String temp: negativeList)
            System.out.println(temp);
 
            
    }
}
