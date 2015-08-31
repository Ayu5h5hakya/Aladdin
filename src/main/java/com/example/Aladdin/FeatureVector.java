package com.example.Aladdin;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;

class FeatureVector
{
    private HashMap<String,Integer>positiveVocabulary = new HashMap<String,Integer>();
    private HashMap<String,Integer>negativeVocabulary = new HashMap<String,Integer>();
    private HashMap<String,Integer>neutralVocabulary = new HashMap<String,Integer>();
    
    //to store the termFrequency
    //frequency of positive,negative,neutral
    private HashMap<String, LinkedList<Integer>> termFrequency = new HashMap<String,LinkedList<Integer>>();
    private HashMap<String,Integer> documentFrequency = new HashMap<String,Integer>(); 
    private HashMap<String,LinkedList<Double>> weight = new HashMap<String,LinkedList<Double>>();

    public FeatureVector(HashMap<String,Integer> positiveVocabulary ,HashMap<String,Integer> negativeVocabulary, HashMap<String,Integer> neutralVocabulary )
    {
      
      this.positiveVocabulary = positiveVocabulary;
      this.negativeVocabulary = negativeVocabulary;
      this.neutralVocabulary = neutralVocabulary;

      calculateTermFrequency();
      calculateDocumentFrequency();
      calculateWeight();
    }

    private void displayTermFrequency()
    {
       Set<Map.Entry<String,LinkedList<Integer>>>  set = termFrequency.entrySet();

       for(Map.Entry<String,LinkedList<Integer>> me: set)
       {
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
       }
    }


    private void displayDocumentFrequency()
    {
       Set<Map.Entry<String,Integer>> set = documentFrequency.entrySet();

       for(Map.Entry<String,Integer> me: set)
       {
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
       }
    }

    private void displayWeight()
    {

       Set<Map.Entry<String,LinkedList<Double>>>  set = weight.entrySet();

       for(Map.Entry<String,LinkedList<Double>> me: set)
       {
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
       }
    }

    private void putdata(HashMap<String,Integer> vocabulary, String flag)
    {

        Set<Map.Entry<String,Integer>> set = vocabulary.entrySet();

        if(flag.equals("positive"))
        {
                for(Map.Entry<String,Integer> me: set)
                {
                  LinkedList<Integer> temp = new LinkedList<Integer>();
                  temp.add(me.getValue());
                  temp.add(0);
                  temp.add(0);

                  termFrequency.put(me.getKey(),temp);
                }
        }
        else if(flag.equals("negative"))
         {
            for(Map.Entry<String,Integer> me: set)
             {
                if(termFrequency.containsKey(me.getKey()))
                 {
                  
                   LinkedList<Integer> temp = termFrequency.get(me.getKey());
                   temp.set(1,me.getValue());

                   termFrequency.put(me.getKey(),temp);
                 }
                else
                {
                  LinkedList<Integer> temp = new LinkedList<Integer>();
                  temp.add(0);
                  temp.add(me.getValue());
                  temp.add(0);

                  termFrequency.put(me.getKey(),temp);

                }
             }
         }
        
        else if(flag.equals("neutral"))
         {

            for(Map.Entry<String,Integer> me: set)
             {
                if(termFrequency.containsKey(me.getKey()))
                 {
                   LinkedList<Integer> temp = termFrequency.get(me.getKey());
                   temp.set(2,me.getValue());
                   
                   termFrequency.put(me.getKey(),temp);
                 }
                else
                {
                  LinkedList<Integer> temp = new LinkedList<Integer>();
                  temp.add(0);
                  temp.add(0);
                  temp.add(me.getValue());

                  termFrequency.put(me.getKey(),temp);

                }
             }

         }

    }

    private void calculateTermFrequency()
    {
         putdata(positiveVocabulary,"positive");
         putdata(negativeVocabulary,"negative");
         putdata(neutralVocabulary,"neutral");
 //        displayTermFrequency();
    }

    private void calculateDocumentFrequency()
    {
        Set<Map.Entry<String,LinkedList<Integer>>>  set = termFrequency.entrySet();
 
        int count = 0;

       for(Map.Entry<String,LinkedList<Integer>> me: set)
       {
             LinkedList<Integer> temp = termFrequency.get(me.getKey());

             for( Integer value: temp)
               if(value != 0)
                   ++count;

             documentFrequency.put(me.getKey(),count);
             count = 0;
       }        

//       displayDocumentFrequency();
    }

  private void calculateWeight()
  {
      Set<Map.Entry<String,LinkedList<Integer>>>  set = termFrequency.entrySet();

       for(Map.Entry<String,LinkedList<Integer>> me: set)
       {
            LinkedList<Integer> temp = me.getValue();
            LinkedList<Double> result = new LinkedList<Double>();

            int documentValue = documentFrequency.get(me.getKey());

             for(Integer value: temp)
             {
                if(value > 0)
                    result.add((1+Math.log10((double)value)) * Math.log10(3/(double)documentValue));                           
                else
                    result.add((double)0.0);
             }
 
             weight.put(me.getKey(),result);
       }

      // displayWeight();
  }
  
  public HashMap<String,LinkedList<Double>> getWeight()
            {
             return weight;
            }
}


