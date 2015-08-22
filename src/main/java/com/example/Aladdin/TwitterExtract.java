package com.example.Aladdin;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.regex.*;
import java.io.*;
import twitter4j.*;
import twitter4j.conf.*;


class TwitterExtract{
        private FileOutputStream fout;
        private String date;
        

        public void getDate(){
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH,-100);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                date = format1.format(cal.getTime());
        }
        private String[] inputTokenizer(String input){
                String searchkeyword;
               
                searchkeyword = input;

                int i=0;
                String normalTokens[] = searchkeyword.split(" "); String[] hashTokens = new String[normalTokens.length];
                for(String token : normalTokens){
                        hashTokens[i] = "#"+token;
                        i++;
                }
                String merged[] = new String[normalTokens.length+hashTokens.length];
                System.arraycopy(hashTokens,0,merged,0,hashTokens.length);
                System.arraycopy(normalTokens,0,merged,hashTokens.length,normalTokens.length);
                return merged;
        }

        
        public void extractStatus(String input){
                try{ 
                        try{
                                System.out.println(input);
                                String token_access="2784327338-kvhDBvfDZs5lNueguNfT8RRujaSJaa9ZGOoyoYZ";
                                String token_secret="8orAqubKXLtKqVcDTVwcCrFrc6bVAhV83TNxuj6iVgYhH";
                                String consumer_secret="WhQ4Fs2f5s3nvKAxYOfv5MYIeBi8D66ogPp1eaHBM66ZS9ZB34";
                                String consumer_key="yR0EfSogckPm4VUvdZ7vMcIVP";
                                this.fout = new FileOutputStream("../../src/resources/Twitter.txt");
                                ConfigurationBuilder cb=new ConfigurationBuilder();
                                cb.setDebugEnabled(true)
                                        .setOAuthConsumerKey(consumer_key)
                                        .setOAuthConsumerSecret(consumer_secret)
                                        .setOAuthAccessToken(token_access)
                                        .setOAuthAccessTokenSecret(token_secret);
                                TwitterFactory tf = new TwitterFactory(cb.build());//access to twitter via our account
                                Twitter twitter = tf.getInstance();// get an instance of twitter
                                Paging page = new Paging(1,100); //page number, number per page
                   
                                String[] searchuser = new String[] {"kathmandupost","BBC","CNN","BBCWorld","nytimes","BBCSport"};  
                                ResponseList<User> users = twitter.lookupUsers(searchuser);// looks only at the timeline of specifed user

                                String keywords[] = inputTokenizer(input);

                                for (User user : users)
                                {
                                       // System.out.println("Friend's name ; "+user.getName());
                                        if(user.getStatus()!=null)
                                        {
                                        //        System.out.println("Timeline");
                                         //       System.out.println(user.getName());
                                                List<Status>statusess = twitter.getUserTimeline(user.getId(),page);
                                                for(Status status3 : statusess)
                                                {
                                                        boolean validStatus = false;
                                                                for(String temp : keywords){
                                                                        validStatus = (validStatus || matching(status3.getText(),temp));
                                                                }
                                                                if(validStatus && (status3.getLang().matches("en")))
                                                                {
                                          //                      System.out.println(status3.getText());//get the status in timeline
                                                                fout.write(new String("->"+status3.getText()+"\n").replaceAll("(((https?|ftp|gopher|telnet|file):((//)|(\\\\))|(www.))+[A-Za-z\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)","").replaceAll("#[^\\s]+","").replaceAll("RT","").replaceAll("http[^\\s]+","").replaceAll("\\...","").replaceAll("\\r\\n|\\r|\\n"," ").replaceAll("@[^\\s]+","").replaceAll(" +"," ").replaceAll("[!|*|\\\\|$|%|^|&|(|)|.|,|>|<|_|\\-|;|:|+|=|~|`|#|@|{|}|\\[|\\]]*","").trim().getBytes());
                                                                fout.write(new String("\n").getBytes());
                                                                }
                                                }
                                        }
                                }
                                
                          //  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        
                        }catch (TwitterException te)
                        {
                                te.printStackTrace();
                                System.out.println("Failed to search tweets:"+te.getMessage());
                                System.exit(-1);
                        }

                        fout.close();

                }
                catch(IOException e){
                        e.printStackTrace(); 
                        System.exit(-1); 
                } 


        } 

        private static boolean matching(String source,String subItem){
                String pattern = "\\b"+subItem+"\\b";
                Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(source);
                return m.find();
        }
        
}
