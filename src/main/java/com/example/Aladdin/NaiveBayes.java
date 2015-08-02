package com.example.Aladdin;

import java.util.*;


class NaiveBayes
{
  private ReadFile readFile=new ReadFile();

  
 public  String positiveData;
 public String negativeData;
 public String neutralData;

public NaiveBayes()
  {
  
      //read file
      String positiveData = readFile.readFile("PositiveNews.txt");
      String negativeData = readFile.readFile("NegativeNews.txt");
      String neutralData = readFile.readFile("NeutralNews.txt");

        Tokenizer tokenizer = new Tokenizer(positiveData);
   
        ArrayList<String> tokenizedtext = (ArrayList<String>) tokenizer.start();

        System.out.println("Tokenizer output:");

        for(String temp : tokenizedtext)
        {
            System.out.print(temp + " ");
        }
 
/*
      //get vocabulary set
      DataSet positiveDataSet = new DataSet(positiveData);
      DataSet negativeDataSet = new DataSet(negativeData);
      DataSet neutralDataSet = new DataSet(neutralData);
*/
  }

}
