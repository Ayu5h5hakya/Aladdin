package com.example.Aladdin;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Message
{

  String news, result;
  boolean valueSetNews = false;
  boolean valueSetResult= false;

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


  synchronized void putResult(String result)
  {
    while(valueSetResult)
      try
      {
         wait();
      }
      catch(InterruptedException e)
      {
        System.out.println("InterruptedException caught");
      }

      this.result= result;
      valueSetResult = true;
      notify();
  }

  synchronized String getResult()
  {
     while(!valueSetResult)
     try{
           wait();
        }
     catch(InterruptedException e)
     {
        System.out.println("InterruptedException caught");
     }

     valueSetResult= false;
     notify();
     return result;
  }

}

class UI implements Runnable
{

 Thread t; 
 JFrame mainFrame;
 JPanel controlPanel;
 JLabel resultLabel; 
 JTextField textField;
 JButton runButton;

 Message message; 
 public UI( Message message)
 {
     this.message = message;

     t = new Thread(this, "UI thread");

     mainFrame = new JFrame("Aladdin");
     mainFrame.setSize(700,200);
     mainFrame.setResizable(false);
     mainFrame.setLayout(new GridLayout(1,1));

     resultLabel = new JLabel(" ",JLabel.CENTER);
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
    panel.add(new JLabel("Enter News Headling"),gbc);
 
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
    panel.add(resultLabel,gbc);
 
    controlPanel.add(panel);
    mainFrame.setVisible(true);


    runButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent ae)
            {
               
               message.putNews(textField.getText());
            }
        });
 
while(true)
  {
  
    resultLabel.setText("The sentiment of above news is " +  message.getResult());
  }
 }
}

class Background implements Runnable
{
  Thread t;
  Message message;
  String test;
  String result;

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
       test += '\n'; 

       DataSet neutralDataSet = new DataSet(sentenceProcessing.sentenceProcessor(test));
       result = naiveBayes.getOutput(neutralDataSet);
     
       message.putResult(result);
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
