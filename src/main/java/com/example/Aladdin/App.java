package com.example.Aladdin;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import twitter4j.*;
import java.text.SimpleDateFormat;
import java.util.regex.*;
import twitter4j.conf.*;
import java.lang.*;

class Message
{

  String news;

  ArrayList<String> positiveList = new ArrayList<String>();
  ArrayList<String> neutralList = new ArrayList<String>();
  ArrayList<String> negativeList = new ArrayList<String>();
  

  boolean valueSetNews = false;
  boolean valueSetResultPositive = false;
  boolean valueSetResultNegative = false;
  boolean valueSetResultNeutral = false;

  synchronized void putNews(String news)
  {
    while(valueSetNews)
      try
      {
         wait();
      }
      catch(InterruptedException e)
      {
        System.out.println("InterruptedException caught");
      }

      this.news = news;
      valueSetNews = true;
      notify();
  }

  synchronized String getNews()
  {
     while(!valueSetNews)
     try{
           wait();
        }
     catch(InterruptedException e)
     {
        System.out.println("InterruptedException caught");
     }

     valueSetNews = false;
     notify();
     return news;
  }


  synchronized void putResult( ArrayList<String> positiveList, ArrayList<String> neutralList, ArrayList<String>negativeList)
  {
    while(valueSetResultNeutral && valueSetResultPositive && valueSetResultNegative)
      try
      {
         wait();
      }
      catch(InterruptedException e)
      {
        System.out.println("InterruptedException caught");
      }

      this.positiveList = positiveList;
      this.negativeList = negativeList;
      this.neutralList = neutralList;
      
      valueSetResultPositive = true;
      valueSetResultNegative = true;
      valueSetResultNeutral = true;

      notify();
  }

  synchronized ArrayList<String> getPositiveResult()
  {
     while(!valueSetResultPositive)
     try{
           wait();
        }
     catch(InterruptedException e)
     {
        System.out.println("InterruptedException caught");
     }

     valueSetResultPositive= false;
     notify();
   
     return positiveList;
  }

  synchronized ArrayList<String> getNegativeResult()
  {
     while(!valueSetResultNegative)
     try{
           wait();
        }
     catch(InterruptedException e)
     {
        System.out.println("InterruptedException caught");
     }

     valueSetResultNegative= false;
     notify();
   
     return negativeList;
  }


  synchronized ArrayList<String> getNeutralResult()
  {
     while(!valueSetResultNeutral)
     try{
           wait();
        }
     catch(InterruptedException e)
     {
        System.out.println("InterruptedException caught");
     }

     valueSetResultNeutral = false;
     notify();
   
     return neutralList;
  }

}

class UI implements Runnable
{

 TwitterExtract extract = new TwitterExtract();
 
 Thread t; 
 JFrame mainFrame;
 JPanel controlPanel;
 JLabel positiveResultLabel; 
 JLabel negativeResultLabel; 
 JLabel neutralResultLabel; 

 JTextField textField;
 JButton runButton;

 Message message; 
 public UI( Message message)
 {
     this.message = message;
 
     extract.getDate();

     t = new Thread(this, "UI thread");

     mainFrame = new JFrame("Aladdin");
     mainFrame.setSize(1000,500);
     mainFrame.setResizable(false);
     mainFrame.setLayout(new GridLayout(1,1));

      positiveResultLabel = new JLabel(" ",JLabel.LEFT);
      neutralResultLabel = new JLabel(" ",JLabel.LEFT);
      negativeResultLabel = new JLabel(" ",JLabel.LEFT);
      
      textField = new JTextField(45);
      runButton = new JButton("Run");

     mainFrame.addWindowListener(new WindowAdapter(){
          public void windowClosing(WindowEvent windowEvent){
              System.exit(0);
          }
     });

    controlPanel = new JPanel();
    controlPanel.setLayout(new GridLayout(1,1));

    mainFrame.add(controlPanel);
    mainFrame.setVisible(true);
 
    t.start(); 
 }

  public void run()
  {

   JPanel panel = new JPanel();

    GridBagLayout layout = new GridBagLayout();

    panel.setLayout(layout);
    GridBagConstraints  gbc = new GridBagConstraints();

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("Enter Tag Word"),gbc);
 
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel(" "),gbc);

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(textField,gbc);

     gbc.gridx = 1;
    gbc.gridy = 2;
    panel.add(new JLabel("   "),gbc);
    
    gbc.gridx = 2;
    gbc.gridy = 2;
    panel.add(runButton,gbc); 

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(new JLabel(" "),gbc);

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(new JLabel("Positive News "),gbc);

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 5;
    panel.add(positiveResultLabel,gbc);
 
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 6;
    panel.add(new JLabel(" "),gbc);


    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 7;
    panel.add(new JLabel("Neutral News "),gbc);

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 8;
    panel.add(neutralResultLabel,gbc);
 
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 9;
    panel.add(new JLabel(" "),gbc);


    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 10;
    panel.add(new JLabel("Negative News "),gbc);

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 11;
    panel.add(negativeResultLabel,gbc);
 
    controlPanel.add(panel);
    mainFrame.setVisible(true);


    runButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
            {

               extract.extractStatus(textField.getText());
               message.putNews(textField.getText());
            }
        });
 
while(true)
  {
       
        ArrayList<String> positiveList =  message.getPositiveResult();
        ArrayList<String> negativeList = message.getNegativeResult();
        ArrayList<String> neutralList = message.getNeutralResult();

        String positiveResult = " ";
        String neutralResult = " ";
        String negativeResult = " ";


        for(String temp : positiveList)
        {
             positiveResult += temp;
             positiveResult += '\n';
        }

        for(String temp : negativeList)
        {
               negativeResult += temp;
               negativeResult += '\n';
        }

        for(String temp : neutralList)
        {
             neutralResult += temp;
             neutralResult += '\n';
        }

        System.out.println("Positive");
        System.out.println(positiveResult);

        System.out.println("Negative");
        System.out.println(negativeResult);

        System.out.println("Neutral");
        System.out.println(neutralResult);

       // positiveResultLabel.setText(positiveResult);
       // neutralResultLabel.setText(neutralResult);
       // negativeResultLabel.setText(negativeResult);

        File file = new File("../../src/resources/Twitter.txt");
        file.delete();
   }
 }
}

class Background implements Runnable
{
  Thread t;
  Message message;
  String test;
  String result;

  ReadFile readFile=new ReadFile();
  NaiveBayes naiveBayes= new NaiveBayes();
  SentenceProcessing sentenceProcessing = new SentenceProcessing();
    
   public Background(Message message)
   {
       
      this.message = message;
      t = new Thread(this,"Background Thread");
      t.start();
   }


   public void run()
   {
    while(true)
    {
      
       test = message.getNews();
       test = readFile.readFile("../../src/resources/Twitter.txt");

       String [] testSplit = test.split("\n");
 
       ArrayList<String> positiveList = new ArrayList<String>();
       ArrayList<String> neutralList = new ArrayList<String>();
       ArrayList<String> negativeList = new ArrayList<String>();
   
       for(String val: testSplit)
         {
       
         val +='\n';

         DataSet testDataSet = new DataSet(sentenceProcessing.sentenceProcessor(val));
         result = naiveBayes.getOutput(testDataSet);
  
         if(result.equals("Positive"))
         {
           positiveList.add(val);
         }
         else if(result.equals("Neutral"))
         {
            neutralList.add(val);
         }
         else if(result.equals("Negative"))
         {
            negativeList.add(val);
         }
         
         }

         message.putResult(positiveList, neutralList, negativeList);
    } 
  }
}
public class App 
{
 public static void main( String[] args )
    { 
  
         Message messgae = new Message();
         new Background(messgae);
         new UI(messgae);
    }
}
