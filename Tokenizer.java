package com.example;

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

    private String Clean(String str) {
        int last = str.length();

        new Character(str.charAt(0));
        String temp = "";

        for (int i = 0; i < last; i++) {
            if (Character.isLetterOrDigit(str.charAt(i)))
                temp += str.charAt(i);
        }

        return temp;
    } //clean

    List<String> start()
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
            BufferedReader bufferedReader=new BufferedReader(new FileReader("C:\\Users\\Ayush\\Documents\\stopwords.txt"));
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                stopwordslist.add(line);
            }
            stopwords=stopwordslist.toArray(new String[stopwordslist.size()]);

        }
        catch (FileNotFoundException e) {}
        catch (IOException e) {System.out.println("Something happened");}
        int counter=0;
        while(counter<finaltext.size())
        {
            if(Arrays.binarySearch(stopwords,finaltext.get(counter))>0 || finaltext.get(counter).equals(""))
            {

                finaltext.remove(counter);
            }
            else  counter++;
        }


    return finaltext;
    }
}