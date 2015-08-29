package com.example;

import java.util.ArrayList;

/**
 * Created by Ayush on 8/28/2015.
 */
public class newPorter {
    private String input;
    private int j;
    private boolean exceptionfound;
    newPorter()
    {
        j=0;
        exceptionfound=false;
    }
    void exceptions()
    {
        if(input.equals("skis")) { input = "ski"; exceptionfound=true;}
        else if(input.equals("skies")) {input = "sky"; exceptionfound=true;}
        else if(input.equals("dying")) {input = "die";exceptionfound=true;}
        else if(input.equals("lying")) {input = "lie";exceptionfound=true;}
        else if(input.equals("tying")) {input = "tie";exceptionfound=true;}
        else if(input.equals("idly")) {input = "idl";exceptionfound=true;}
        else if(input.equals("gently")) {input = "gentl";exceptionfound=true;}
        else if(input.equals("ugly")) {input = "ugli";exceptionfound=true;}
        else if(input.equals("early")) {input = "earli";exceptionfound=true;}
        else if(input.equals("only")) {input = "onli";exceptionfound=true;}
        else if(input.equals("singly")) {input = "singl";exceptionfound=true;}
    }
    boolean consontant(String input,int index)
    {
        switch (input.charAt(index))
        {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return false;
            case 'y':
                if(index == 0) return true;
                else return !consontant(input,index-1);
            default:
                return true;
        }
        /**
         * if self.b[i] == 'a' or self.b[i] == 'e' or self.b[i] == 'i' or self.b[i] == 'o' or self.b[i] == 'u':
         return 0
         if self.b[i] == 'y':
            if i == self.k0:
            return 1
         else:
            return (not self.cons(i - 1))
         return 1

         */
    }

    int measure()
    {
        int i = 0, count = 0;
        int length = input.length();

        while(true)
        {
            if(i>j) return count;
            if(!consontant(input,i)) break;
            i+=1;
        }
        i+=1;
        while(true)
        {
            while (true)
            {
                if(i>j) return count;
                if(consontant(input,i)) break;
                i+=1;

            }
            i+=1;
            count+=1;
            while(true)
            {
                if(i>j) return count;
                if(!consontant(input,i)) break;
                i+=1;
            }
            i+=1;
        }
    }

    boolean containsvowel(String input)
    {
        for(int i=0;i<input.length();++i)
        {
            if(!consontant(input,i)) return true;
        }
        return false;
    }

    boolean containsvowel(String input,int x)
    {
        switch (input.charAt(x-1))
        {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:
                return false;
        }
    }
    boolean doublecons(int index)
    {
        if(index == 0) return false;
        if(input.length()<2) return false;
        else
        {
            if(input.charAt(index) != input.charAt(index-1)) return false;
            else if(input.charAt(index)=='b' ||
                    input.charAt(index)=='d' ||
                    input.charAt(index)=='f' ||
                    input.charAt(index)=='g' ||
                    input.charAt(index)=='m' ||
                    input.charAt(index)=='n' ||
                    input.charAt(index)=='t' ||
                    input.charAt(index)=='q' ||
                    input.charAt(index)=='r') return true;
        }
        return false;
    }

    boolean cvc(int index)
    {
        if(index<2) return false;
        else
        {
            if(!consontant(input,index-2) || consontant(input,index-1) || !consontant(input,index)) return false;
            else if(input.charAt(index) == 'w' || input.charAt(index) == 'x' || input.charAt(index) == 'y') return false;
            else return true;
        }
    }

    boolean endswith(String partial)
    {
        if(input.contains(partial))
        {
            String temp = input.substring(input.length()-partial.length());
            if(temp.equals(partial))
            {
                j = input.length()-partial.length()-1;
                return true;
            }
            else return false;
        }

        return false;
    }

    void replacesub(String replacement)
    {
        input = input.substring(0,j)+replacement+input.substring(j+replacement.length()+1);
    }

    void r(String string)
    {
        if(measure()>0) replacesub(string);
    }

    public void step1ab()
    {
        if(input.charAt(input.length()-1) == 's')
        {
            if(endswith("sses")) input = input.substring(0,input.length()-4)+"ss";
            else if(input.endsWith("ies"))
            {
                String temp = input.substring(0,input.length()-3);
                if(temp.length()>1) input = temp+"i";
                else if(temp.length() == 1) input = temp+"ie";
            }
            else if(input.endsWith("ss")) input = input.substring(0,input.length()-2)+"s";

        }
        if(input.endsWith("s") && containsvowel(input) && !containsvowel(input,input.length()-1))input = input.substring(0,input.length()-1);
        if(endswith("eed"))
        {
            if(input.equals("proceed") || input.equals("succeed") || input.equals("exceed")) return;
            if(measure()>0) input = input.substring(0,input.length()-1);
        }
        else
        if(endswith("ed") && containsvowel(input.substring(0,input.length()-2)))
        {
            input = input.substring(0,input.length()-2);
            if(endswith("at") || endswith("bl") || endswith("iz")) input+="e";
            else
            if(doublecons(input.length()-1))
                input = input.substring(0,input.length()-2);
            else
            if(measure() == 1 && cvc(input.length()-1))
                input+="e";
        }
        else
        if(endswith("ing") && containsvowel(input.substring(0,input.length()-3)))
        {
            if(input.equals("inning") || input.equals("outing") || input.equals("canning") || input.equals("herring") || input.equals("earring")) return;
            input = input.substring(0,input.length()-3);
            if(endswith("at") || endswith("bl") || endswith("iz")) input+="e";
            else
            if(doublecons(input.length()-1))
            {
                switch (input.charAt(input.length()-1))
                {
                    case 'l':
                    case 's':
                    case 'z':
                        break;
                    default:
                        input = input.substring(0,input.length()-1);

                }

            }
            else
            if(measure() == 1 && cvc(input.length()-1))
                input+="e";
        }
        else
        if(endswith("ingly") && containsvowel(input.substring(0,input.length()-5)))
        {
            input = input.substring(0,input.length()-5);
            if(endswith("at") || endswith("bl") || endswith("iz")) input+="e";
            else
            if(doublecons(input.length()-1))
                input = input.substring(0,input.length()-1);
            else
            if(measure() == 1 && cvc(input.length()-1))
                input+="e";
        }
        else
        if(endswith("edly") && containsvowel(input.substring(0,input.length()-4)))
        {
            input = input.substring(0,input.length()-4);
            if(endswith("at") || endswith("bl") || endswith("iz")) input+="e";
            else
            if(doublecons(input.length()-1))
                input = input.substring(0,input.length()-1);
            else
            if(measure() == 1 && cvc(input.length()-1))
                input+="e";
        }
    }

    public void step1c()
    {
        if(input.length()>2)
        {
            if(endswith("y") && !containsvowel(input,input.length()-1))
            {
                input = input.substring(0,input.length()-1)+"i";
            }
        }
    }

    public  void step2()
    {
        String[][] suffixes = {{"ational", "ate"},
                {"tional", "tion"},
                {"enci", "ence"},
                {"anci", "ance"},
                {"izer", "ize"},
                {"iser", "ize"},
                {"abli", "able"},
                {"alli", "al"},
                {"entli", "ent"},
                {"eli", "e"},
                {"ousli", "ous"},
                {"ization", "ize"},
                {"isation", "ize"},
                {"ation", "ate"},
                {"ator", "ate"},
                {"alism", "al"},
                {"iveness", "ive"},
                {"fulness", "ful"},
                {"ousness", "ous"},
                {"aliti", "al"},
                {"iviti", "ive"},
                {"biliti", "ble"}};
        for (int index = 0; index < suffixes.length; index++) {
            if(endswith(suffixes[index][0]))
            {
                input = input.substring(0,input.length()-suffixes[index][0].length())+suffixes[index][1];
                break;
            }
        }
    }

    public void step3()
    {
        String[][] suffixes = {{"icate", "ic"},
                {"ative", ""},
                {"alize", "al"},
                {"alise", "al"},
                {"iciti", "ic"},
                {"ical", "ic"},
                {"ful", ""},
                {"ness", ""}};
        for (int index = 0; index < suffixes.length; index++) {
            if(endswith(suffixes[index][0]))
            {
                input = input.substring(0,input.length()-suffixes[index][0].length())+suffixes[index][1];
                break;
            }
        }
    }

    public void step4()
    {
        String[] suffixes = {"al", "ance", "ence", "er", "ic", "able", "ible", "ant", "ement", "ment", "ent", "sion", "tion",
                "ou", "ism", "ate", "iti", "ous", "ive", "ize", "ise"};
        for (int i=0;i<suffixes.length;++i)
        {
            if (endswith(suffixes[i]))
            {
                input=input.substring(0,input.length()-suffixes[i].length());
            }
        }
    }

    public void step5()
    {
        int temp;
        if(endswith("e"))
        {
            temp= measure();
            if(temp>1 || (temp ==1 && !cvc(input.length()-2)))
            {
                input = input.substring(0,input.length()-1);
            }
        }
        else
        if(endswith("ll") && measure()>1)
        {
            input = input.substring(0,input.length()-2)+"l";
        }
    }
    public ArrayList<String> start(ArrayList<String> inputs)
    {
        ArrayList<String> result = new ArrayList<>();
        for(String input:inputs)
        {
            this.input=input;
            if(!this.input.contains("_NN"))
            {
                exceptionfound=false;
                j=0;
                exceptions();
                if(!exceptionfound)
                {
                    //System.out.println("Processing " + input);
                    step1ab();
                    step1c();
                    step2();
                    step3();
                    step4();
                    step5();
                }
            }
            result.add(this.input);
        }
        return result;
    }
}
