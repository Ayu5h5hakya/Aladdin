package com.example.Aladdin;

import java.io.*;
import java.util.*;
import java.lang.*;

class SentenceProcessing
{

 String sentenceProcessor(String inputData)
 {
      String resultToken = new String();

      while(inputData.length()!=0)
      {
           int nextLine = inputData.indexOf('\n');
           String temp = inputData.substring(0,nextLine);
           inputData = inputData.substring(nextLine+1,inputData.length());
      
           Tokenizer tokenizer = new Tokenizer(temp);
           ArrayList<String> tokenizedtext = (ArrayList<String>) tokenizer.start();
       
           Porter porter = new Porter();
           ArrayList<String> final_op = porter.start(tokenizedtext);

   
           for(String token : final_op)
              resultToken += token + ' ';

              resultToken = resultToken.substring(0,resultToken.length()-1)+'\n';
      }

    return resultToken;
 
  }
}


