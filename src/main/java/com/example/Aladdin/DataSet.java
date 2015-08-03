package com.example.Aladdin;

import java.io.*;
import java.util.*;
import java.lang.*;

class DataSet
{

    //hold the word and frequency
    private HashMap<String, Integer> vocabulary = new HashMap<String, Integer>();
    private ArrayList<String> words = new ArrayList<String>();

    //no of training data for the class   
    private int totalData = 0; 

    public DataSet(String data)
    {
        setVocabulary(data);
    }
    public HashMap<String, Integer> getVocabulary()
    {
           return vocabulary;
    }

    public int getTotaldata()
    {
         return totalData;
    }

    //get the list of words
    public void getWords(String data)
    {

            String word = "";

            for(int i=0; i<data.length(); ++i)
            {

                if(data.charAt(i)== ' ' || data.charAt(i)== '\n')
                {
                    words.add(word); 
                    word = "";
                }
                else
                {
                   word +=data.charAt(i);
                }

            }


    }

    public void getTotalData(String data)
    {

       String[] splitByLine = data.split("\n");
   
        for(String temp : splitByLine)
            ++totalData;
    }

    public void displayVocabulary()
      {

     Set<Map.Entry<String,Integer>> set = vocabulary.entrySet();

      System.out.println("Data");
   
     for(Map.Entry<String , Integer> me : set)
      {
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());

      }

      System.out.println("total data: " + totalData);

      }

        
    public void setVocabulary(String data)
    {


            getTotalData(data);     
            getWords(data);

    for( String temp: words)
     {
          if( vocabulary.containsKey(temp))
          {
              int n = vocabulary.get(temp);
              ++n;
              vocabulary.put(temp,n);
          }
          else
          {
                vocabulary.put(temp,1); 
          }
     }

    //displayVocabulary();
  }

}
