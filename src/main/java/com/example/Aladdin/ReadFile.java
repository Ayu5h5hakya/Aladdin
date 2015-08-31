package com.example.Aladdin;

import java.io.*;
import java.util.*;
import java.lang.*;

class ReadFile
{
   private String line;
   private String data;


            public String readFile(String filename)
            {
                    data="";  
            try
                {
                    FileReader fileReader = new FileReader(filename);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    while((line = bufferedReader.readLine())!=null)
                          data += line + '\n';

                    bufferedReader.close();

                
                }
                catch(FileNotFoundException ex)
                {
                     System.out.println("File not exist" + filename);
                }
                catch(IOException ex)
                {
                     System.out.println("Error reading to file " + filename);
                }
            
                return data;
            }
}


