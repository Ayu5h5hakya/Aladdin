# Aladdin

##Sentiment Analysis For News Titles

Aladdin is a sentiment analyzer that classifies news titles into three categories: positive, negative and neutral.
There are no target entities i.e it looks at the general sentiment.

###Positive
```
        -South Korea declares end to Mers
        -California Man helps homeless people
```

###Negative
```
        -Runaway lorry kills Mexico Pilgrims
        -49 people infected with the H1N1 virus
```

###Neutral
```
        -Sony's net profit more than triples
        -Zayn Malik signs solo record deal
```

##Compilation

In the project root i.e inside *Aladdin/*, run:
```
        mvn compile
```

##Execution

First move into the classes directory:
```
         cd Aladdin\target\classes
```

Run the program:
```
java -cp .:jaws-bin.jar:twitter4jcore.jar com.example.Aladdin.App -s "Single News Title"
java -cp .:jaws-bin.jar:twitter4jcore.jar com.example.Aladdin.App -t "dead"
```

Note:

The first argument is a compulsory option that specifies what the second argument is for:

            -s for single title classification

            -t for getting related news titles from Twitter

##Web application

To run the web application, you need a running php server.
Place the project under the server root.
You can then simply hit `http://localhost/Aladdin/index.php` on the browser.
