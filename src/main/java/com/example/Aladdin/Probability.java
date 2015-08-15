package com.example.Aladdin;

import java.util.*;
import java.io.*;
import java.lang.*;


class Probability
{

private int noOfTimeClassPositiveOccur;
private int noOfTimeClassNegativeOccur;
private int noOfTimeClassNeutralOccur;
private int totalClassOfTrainingSet;

private HashMap<String, Integer> positiveVocabulary = new HashMap<String, Integer>();
private HashMap<String, Integer> negativeVocabulary = new HashMap<String, Integer>();
private HashMap<String, Integer> neutralVocabulary = new HashMap<String, Integer>();

private Set<String> tempWordList = new HashSet<String>();

private int totalVocabulary = 0;
private int totalPositiveVocabulary = 0;
private int totalNegativeVocabulary = 0;
private int totalNeutralVocabulary = 0;

//to hold the result of per word probability
private HashMap<String, Float> positiveWordProbability = new HashMap<String,Float>();
private HashMap<String, Float> negativeWordProbability = new HashMap<String,Float>();
private HashMap<String, Float> neutralWordProbability = new HashMap<String,Float>();

private float probOfPositive;
private float probOfNegative;
private float probOfNeutral;


    public void probabilityCalculation(DataSet positiveDataSet, DataSet negativeDataSet, DataSet neutralDataSet)
        {
            noOfTimeClassPositiveOccur = positiveDataSet.getTotaldata();
            noOfTimeClassNegativeOccur = negativeDataSet.getTotaldata();
            noOfTimeClassNeutralOccur = neutralDataSet.getTotaldata();

            totalClassOfTrainingSet = noOfTimeClassPositiveOccur + noOfTimeClassNegativeOccur + noOfTimeClassNeutralOccur;

            calculatePriors();

            positiveVocabulary = positiveDataSet.getVocabulary();
            negativeVocabulary = negativeDataSet.getVocabulary();
            neutralVocabulary = neutralDataSet.getVocabulary();
      
            calculateTotalVocubulary();
            calculateConditionalProbabilites();
        }

        private int getTotalVocabulary(Set<Map.Entry<String,Integer>> set )
        {
             int temp = 0;

             for(Map.Entry<String , Integer> me : set)
              {
                   tempWordList.add(me.getKey());
                   temp += me.getValue();
              }

             return temp;

        }
      
        private void calculateTotalVocubulary()
        {
         
          totalPositiveVocabulary = getTotalVocabulary(positiveVocabulary.entrySet());
          totalNegativeVocabulary = getTotalVocabulary(negativeVocabulary.entrySet());
          totalNeutralVocabulary = getTotalVocabulary(neutralVocabulary.entrySet());

          totalVocabulary = tempWordList.size();    
        
        }
        
        private void calculatePriors()
        {
            probOfPositive = (float)noOfTimeClassPositiveOccur/totalClassOfTrainingSet;
            probOfNegative = (float)noOfTimeClassNegativeOccur/totalClassOfTrainingSet;
            probOfNeutral = (float)noOfTimeClassNeutralOccur/totalClassOfTrainingSet;
           
        }

        private void calculateProblility(HashMap<String, Integer> vocabulary, HashMap<String, Float> wordProbability, int count)
        {

                for(String temp: tempWordList)
                {
                       if(vocabulary.containsKey(temp))
                       {

                            float value = (float)(vocabulary.get(temp) + 1 )/(count + totalVocabulary);
                            wordProbability.put(temp, value);

                       }
                       else
                       {
                            
                            float value = (float)1/(count + totalVocabulary);
                            wordProbability.put(temp, value);

                       }
                }
        }


        private void displayResult(Set<Map.Entry<String,Integer>> set )
        {

             for(Map.Entry<String , Integer> me : set)
              {
                     System.out.println(me.getKey() + "   " + me.getValue());
              }
             System.out.println();

        }
       
        private void calculateConditionalProbabilites()
        {
              calculateProblility(positiveVocabulary, positiveWordProbability, totalPositiveVocabulary );
              calculateProblility(negativeVocabulary, negativeWordProbability, totalNegativeVocabulary);
              calculateProblility(neutralVocabulary, neutralWordProbability, totalNeutralVocabulary);
        }

        public void getResult(DataSet testDataSet)
        {

            HashMap<String, Integer> testVocabulary = new HashMap<String, Integer>();
           
            testVocabulary = testDataSet.getVocabulary();

              float p1 = probOfPositive;
              float p2 = probOfNegative;
              float p3 = probOfNeutral;

            Set<Map.Entry<String, Integer>> set = testVocabulary.entrySet();


             for(Map.Entry<String , Integer> me : set)
              {

                  if(positiveWordProbability.get(me.getKey()) != null )
                      p1 *= Math.pow(positiveWordProbability.get(me.getKey()),me.getValue());
                   else
                     p1 *= Math.pow(1,me.getValue());

                 
                 if(negativeWordProbability.get(me.getKey()) != null )
                      p2 *= Math.pow(negativeWordProbability.get(me.getKey()),me.getValue());
                 else
                      p2 *= Math.pow(1,me.getValue());

                 if(neutralWordProbability.get(me.getKey()) != null )
                    p3 *= Math.pow(neutralWordProbability.get(me.getKey()),me.getValue());
                 else
                    p3 *= Math.pow(1,me.getValue());
              }

             if(p1>p2 && p1>p3)
                     System.out.println("Positive");
        
             else if(p2>p3)
                    System.out.println("Negative");
         
             else
                     System.out.println("Neutral");

        }
 
}
