package com.example.Aladdin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ayush on 7/4/2015.
 */
public class Mystemmer {

    private String input;
    private int index=0;
    File file=null;
    private ArrayList<String> stopwords,testdata;
    private String[] testarray;
    Mystemmer(String input)
    {
        this.input=input;
        stopwords=new ArrayList<String>();
        testdata=new ArrayList<String>(Arrays.asList(input.split(" ")));
        if(stopwordsloader())
        {
            System.out.println("file loaded successfully");
        }
        else
        {
            System.out.println("Something went wrong");
        }
    }

    private boolean stopwordsloader()
    {
        file = new File("stopwords.txt");
        if(file == null) return false;
        else return true;
    }

    public void begin()
    {
        try {
            FileReader filereader=new FileReader(file);
            BufferedReader bufferedreader = new BufferedReader(filereader);
            String string=null;
            while((string=bufferedreader.readLine())!=null)
            {
                stopwords.add(string);
            }
        }
        catch (FileNotFoundException e) {}
        catch (IOException e) {}
        testarray=stopwords.toArray(new String[stopwords.size()]);
        while(true)
        {
            if(Arrays.binarySearch(testarray,testdata.get(index))>0)
            {
                testdata.remove(index);
            }
            else index+=1;
            if(index>=testdata.size()) break;
        }
    }
    public void printparsedstring()
    {
        System.out.println(testdata);
    }
}
