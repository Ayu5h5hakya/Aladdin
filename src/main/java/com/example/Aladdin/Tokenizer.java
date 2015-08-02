package com.example.Aladdin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ayush on 7/15/2015.
 */
public class Tokenizer {

    private String input;
    ArrayList<String> stopwordslist, finaltext;
    String[] stopwords;
    Tokenizer(String input)
    {
        this.input = input;
    }

  ArrayList<String> start()
    {
        stopwordslist=new ArrayList<String>();
        String[] result= input.split("\\s+");
        for(int i=0;i<result.length;++i)
        {
            result[i]=result[i].replaceAll("[^a-zA-Z]","").toLowerCase();
        }
        finaltext=new ArrayList<String>(Arrays.asList(result));
        try
        {
            BufferedReader bufferedReader=new BufferedReader(new FileReader("stopwords.txt"));
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                stopwordslist.add(line);
            }
            stopwords=stopwordslist.toArray(new String[stopwordslist.size()]);

        }
        catch (FileNotFoundException e) {}
        catch (IOException e) {}
        int counter=0;
        while(counter<finaltext.size())
        {
            if(Arrays.binarySearch(stopwords,finaltext.get(counter))>0)
            {
                finaltext.remove(counter);
            }
            else  counter++;
        }
   
    return finaltext;
 
    }
}
