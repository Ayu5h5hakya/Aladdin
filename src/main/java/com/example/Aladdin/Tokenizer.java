package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ayush on 7/15/2015.
 */
public class Tokenizer {
    private String input;
    ArrayList<String> stopwordslist, finaltext;
    String[] stopwords;
    HashMap<String,Integer> adjectives,adverbs,conjuction,determiners,nouns,preposition,prodeterminer,verbs;
    Tagger tagger;
    Tokenizer(String input)
    {
        finaltext = new ArrayList<>();
        this.input = input;
        tagger= new Tagger();
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
            result[i]=result[i].replaceAll("[^a-zA-Z]","");
        }
        result = tagger.SetUpTagger(result);
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
            if(Arrays.binarySearch(stopwords,finaltext.get(counter))>0 || finaltext.get(counter).equals(""))
            {

                finaltext.remove(counter);
            }
            else
            {
                counter++;
            }
        }
    return finaltext;
    }

    private class Tagger
    {
        Tagger()
        {
            nouns=new HashMap<>();
        }
        public String[] SetUpTagger(String[] input)
        {
            System.out.println(input.length);
            String[] temp = new String[2];
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("Nouns.txt"));
                String line=null;
                while ((line = bufferedReader.readLine())!=null)
                {
                    temp = line.split("\\s+");
                    nouns.put(temp[0],Integer.parseInt(temp[1]));
                }
                for(int i=0;i<input.length;++i)
                {
                    if(input[i].equals(""))continue;
                    if(nouns.containsKey(input[i]) || Character.isUpperCase(input[i].charAt(0))) input[i]+="_NN";
                }
            }
            catch (FileNotFoundException e) {}
            catch (IOException e) {}

            return input;
        }
    }
}
