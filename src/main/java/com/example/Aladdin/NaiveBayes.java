package com.example.Aladdin;

import java.util.*;


class NaiveBayes
{

 private ReadFile readFile=new ReadFile();
 private SentenceProcessing sentenceProcessing = new SentenceProcessing();
 private Probability probability = new Probability();

 private  String positiveData;
 private String negativeData;
 private String neutralData;

public NaiveBayes()
  {
  
      //read file
      String positiveData = readFile.readFile("PositiveNews.txt");
      String negativeData = readFile.readFile("NegativeNews.txt");
      String neutralData = readFile.readFile("NeutralNews.txt");
  
       //Find vocabulary 
       DataSet positiveDataSet = new DataSet(sentenceProcessing.sentenceProcessor(positiveData));
       DataSet negativeDataSet = new DataSet(sentenceProcessing.sentenceProcessor(negativeData));
       DataSet neutralDataSet = new DataSet(sentenceProcessing.sentenceProcessor(neutralData));
  
       //calculate probability
       probability.probabilityCalculation(positiveDataSet, negativeDataSet, neutralDataSet);
               
  }

 public void getOutput(DataSet testDataSet)
 {
      probability.getResult(testDataSet);
 
 }

}
