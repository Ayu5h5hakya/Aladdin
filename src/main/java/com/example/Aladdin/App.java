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

/** 
 * Command line arguments are as:
 * args[0] is the option:
 *          -s for single title evaluation
 *          -t for keyword extraction on twitter
 *
 * args[1] is the actual keyword or title
 *
 */

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

          if(args.length != 2){

                  System.out.println("Invalid number of arguments");
                  System.exit(0);

          }

          
          /* Feature/web-interface *
           * Accept keywords from command line arguments instead of stdin
          System.out.println("Enter Tag");
          Scanner scanner = new Scanner(System.in);
          */

          String option = args[0];

          if(option.equals("-s")){

                  String testTitle = args[1];

                  testTitle += '\n';

                DataSet singleTitle  = new DataSet(sentenceProcessing.sentenceProcessor(testTitle));
                result = naiveBayes.getOutput(singleTitle);
                System.out.println(result);
          
          }else if(option.equals("-t")){


                String tag = args[1];

                extract.extractStatus(tag);

                String test = readFile.readFile("../../src/resources/Twitter.txt");

                String [] testSplit = test.split("\n");
  
                String noResult;

                for(String val: testSplit)
                  {
      
                     if(val.length() == 0)
                     {
                            break;
                     }

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
   
                 System.out.println("<positive>");

                 for(String temp: positiveList)
                     System.out.print(temp);
    
                 System.out.println("</positive>");

                 System.out.println("<neutral>");

                 for(String temp: neutralList)
                     System.out.print(temp);
    
                 System.out.println("</neutral>");

                 System.out.println("<negative>");

                 for(String temp: negativeList)
                     System.out.print(temp);

                 System.out.println("</negative>");
          }else{

                  System.out.println("Invalid option: Use -s or -t ");

          }

    //         NeuralNetwork neuralNetwork = new NeuralNetwork();

    }
}
