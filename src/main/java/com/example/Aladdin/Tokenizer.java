package com.example.Aladdin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

/**
 * Created by Ayush on 7/15/2015.
 */
public class Tokenizer {
    private String input;
    private int[] assignedtags;
    ArrayList<String> stopwordslist, finaltext;
    String[] stopwords;
    HashMap<String,Integer> adjectives,adverbs,conjuction,nouns,preposition,prodeterminer,verbs;
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
        result = tagger.applytagger(result);
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
            int stemmed;
            if(finaltext.get(counter).contains("_"))
            {
                stemmed = finaltext.get(counter).indexOf("_");
            }
            else stemmed = finaltext.get(counter).length();
            if(Arrays.binarySearch(stopwords,finaltext.get(counter).substring(0,stemmed))>0 || finaltext.get(counter).equals(""))
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
        WordNetDatabase wordNetDatabase;
        Tagger()
        {
            System.setProperty("wordnet.database.dir","/usr/local/WordNet-3.0/dict/");
            nouns=new HashMap<>();
            wordNetDatabase = WordNetDatabase.getFileInstance();

        }
        public String[] SetUpTagger(String[] input)
        {
/*
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
*/
            assignedtags = new int[input.length];
            String[] Determiners = new String[13];
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("Determiner.txt"));
                String line = null;
                int i=0;
                while((line = bufferedReader.readLine())!=null)
                {
                    Determiners[i++]=line;
                }

            }
            catch (FileNotFoundException e) {}
            catch (IOException e) {}
            for(int j=0;j<input.length;++j)
            {
                if(input[j].equals("")) continue;
                else
                {
                    if(Arrays.binarySearch(Determiners,input[j].toLowerCase())>0) input[j]+="_Article";
                    else if(Character.isUpperCase(input[j].charAt(0))) input[j]+="_Noun";
                 //   else if(input[j-1].equals("")) input[j]+="_Noun";
                    else {
                        Synset[] list = wordNetDatabase.getSynsets(input[j]);
                        assignedtags[j] = list.length;
                        Set<String> tags = new HashSet<>();
                        for (int i = 0; i < assignedtags[j]; ++i)
                            tags.add(list[i].toString().substring(0, list[i].toString().indexOf("@")));
                            //tags.add(list[i].toString());
                        Iterator iterator = tags.iterator();
                        while (iterator.hasNext()) {
                            String temp = (String) iterator.next();
                            input[j] += "_" + temp;
                        }
                    }
                }

            }

            return input;
        }

        public String[] applytagger(String[] input)
        {
            for(int i=0;i<input.length;++i)
            {
                if(assignedtags[i]>1)
                {
                    if(i==0) input[i] = input[i].substring(0,input[i].indexOf("_"))+"_Noun";
                    else if(input[i-1].contains("_Article")) input[i] = input[i].substring(0,input[i].indexOf("_"))+"_Noun";
                }
            }
/*
            POSModel model= new POSModelLoader().load(new File("en-pos-maxent.bin"));
            POSTaggerME tagger = new POSTaggerME(model);
            String[] tags = tagger.tag(input);
            POSSample posSample = new POSSample(input,tags);
            System.out.println(posSample.toString());
*/
            return input;
        }

        public void ignorerest(String[] input,int index)
        {
            for(int i=index+1;i<input.length;++i)
            {
                input[i] = input[i].substring(0,input[i].indexOf("_"))+"_Noun";
            }
        }


    }
}
