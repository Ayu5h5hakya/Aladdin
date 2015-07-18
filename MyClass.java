package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyClass {
    public static void main(String[] args){
        String test=null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the sentence");
        test=scanner.nextLine();
        Tokenizer tokenizer = new Tokenizer(test);
        tokenizer.start();

    }
}