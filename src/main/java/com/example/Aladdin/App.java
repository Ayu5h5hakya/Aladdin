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
          NeuralNetwork neuralNetwork = new NeuralNetwork();
          
          ArrayList<String> naiveBayesPositiveList = new ArrayList<String>();
          ArrayList<String> naiveBayesNeutralList = new ArrayList<String>();
          ArrayList<String> naiveBayesNegativeList = new ArrayList<String>();
   
          ArrayList<String> neuralNetworkPositiveList = new ArrayList<String>();
          ArrayList<String> neuralNetworkNeutralList = new ArrayList<String>();
          ArrayList<String> neuralNetworkNegativeList = new ArrayList<String>();
   
          extract.getDate();
          String resultNaiveBayes;
          String resultNeuralNetwork;
          
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
            
                //Naive Bayes

       System.out.println("<<--- Result From Naive Bayes -->>");

                DataSet singleTitle  = new DataSet(sentenceProcessing.sentenceProcessor(testTitle));
                resultNaiveBayes = naiveBayes.getOutput(singleTitle);
                System.out.println(resultNaiveBayes);
   

            //Neural Network

            System.out.println("<<--- Result From Neural Network -->>");

            resultNeuralNetwork = neuralNetwork.getResult(sentenceProcessing.sentenceProcessor(testTitle));
            System.out.println(resultNeuralNetwork);

              }
             
            else if(option.equals("-t")){


                
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
                     
                     resultNaiveBayes = naiveBayes.getOutput(testDataSet);
   
                     resultNeuralNetwork = neuralNetwork.getResult(sentenceProcessing.sentenceProcessor(val));

                     if(resultNaiveBayes.equals("Positive"))
                     {
                       naiveBayesPositiveList.add(val);
                     }
                     else if(resultNaiveBayes.equals("Neutral"))
                     {
                        naiveBayesNeutralList.add(val);
                     }
                     else if(resultNaiveBayes.equals("Negative"))
                     {
                        naiveBayesNegativeList.add(val);
                     }
                 
                     if(resultNeuralNetwork.equals("Positive"))
                     {
                       neuralNetworkPositiveList.add(val);
                     }
                     else if(resultNeuralNetwork.equals("Neutral"))
                     {
                        neuralNetworkNeutralList.add(val);
                     }
                     else if(resultNeuralNetwork.equals("Negative"))
                     {
                        neuralNetworkNegativeList.add(val);
                     }

                  }

                 File file = new File("../../src/resources/Twitter.txt");
                 file.delete();
  
            System.out.println("<<--- Result From Naive Bayes -->>");
                 
                 System.out.println("<positive>");

                 for(String temp: naiveBayesPositiveList)
                     System.out.print(temp);
    
                 System.out.println("</positive>");

                 System.out.println("<neutral>");

                 for(String temp: naiveBayesNeutralList)
                     System.out.print(temp);
    
                 System.out.println("</neutral>");

                 System.out.println("<negative>");

                 for(String temp: naiveBayesNegativeList)
                     System.out.print(temp);

                 System.out.println("</negative>");
          
                 
            System.out.println("<<--- Result From Neural Network -->>");
            
              System.out.println("<positive>");

                 for(String temp: neuralNetworkPositiveList)
                     System.out.print(temp);
    
                 System.out.println("</positive>");

                 System.out.println("<neutral>");

                 for(String temp: neuralNetworkNeutralList)
                     System.out.print(temp);
    
                 System.out.println("</neutral>");

                 System.out.println("<negative>");

                 for(String temp: neuralNetworkNegativeList)
                     System.out.print(temp);

                 System.out.println("</negative>");
         }
        
         else{

                  System.out.println("Invalid option: Use -s or -t ");
          }
        
    }
}
