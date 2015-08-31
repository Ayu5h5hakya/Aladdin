<!DOCTYPE html>

<?php

/**  
 *  Simple Web Interface for Aladdin
 *
 *  This single file acts as a simple web interface for the sentiment analysis project Aladdin.
 *  A search-box allows keyword input and the results are displayed below.
 *
 *  @repo https://github.com/Ayu5h5hakya/Aladdin
 *
 */

?>

<html>

    <title>Aladdin | A news sentiment analyser</title>

    <head>
            <?php 

                if(isset($_GET["keyword"])){
                    $keyword = $_GET["keyword"];
                }

                if(!empty($keyword)){

                    chdir('target/classes');

                    $command ='java -cp .:twitter4jcore.jar com.example.Aladdin.App "'.$keyword.'" 2>&1'; 

                    exec($command,$output);

                    $positiveNews = [];
                    $negativeNews = [];
                    $neutralNews = [];

                    /** 
                     * The output produced by the java application is as:
                     *
                     * <positive>
                     * A positive news title
                     * </positive>
                     * <neutral>
                     * I am neutral
                     * Another neutral news title
                     * </neutral>
                     * <negative>
                     * This is bad 
                     * Shit just got real
                     * </negative>
                     *
                     * The tags classify the titles.
                     *
                     */

                    $running = "undefined";

                    foreach($output as $val){

                            if($val=="<positive>"){
                                    $running = "positive";
                                    continue;
                            }elseif($val=="</positive>"){
                                    $running = "undefined";
                                    continue;
                            }elseif($val == "<negative>"){
                                    $running = "negative";
                                    continue;
                            }elseif($val == "</negative>"){
                                    $running = "undefined";
                                    continue;
                            }elseif($val == "<neutral>"){
                                    $running = "neutral";
                                    continue;
                            }elseif($val == "</neutral>"){
                                    $running = "undefined";
                                    continue;
                            }

                            if($running == "positive"){
                                    $positiveNews[] = $val;
                            }elseif($running == "negative"){
                                    $negativeNews[] = $val;
                            }elseif($running == "neutral"){
                                    $neutralNews[] = $val;
                            }
                    }

                }
                
            ?>

    </head>

    <body>

            <!-- Search Box: It posts to itself -->

            <form action=<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?> method="get">

                Enter Keyword: <input type="text" name="keyword"/>

                <input type="submit" value="Go"/>

            </form>

                <?php
                    if(!empty($keyword)){

                            $emptyMessage = "-- No results found -- <br>";

                ?>

                        <h2> Positive news </h2>

                        <?php 
                            if(count($positiveNews) == 0){
                                    echo $emptyMessage;
                            }else{
                                foreach($positiveNews as $posnews){

                                        echo $posnews;
                                        echo '<br>';

                                }
                            }

                        ?>
                        
                        <h2> Neutral news </h2>

                        <?php 
                            if(count($neutralNews) == 0){
                                    echo $emptyMessage;
                            }else{
                                foreach($neutralNews as $neunews){

                                        echo $neunews;
                                        echo '<br>';

                                }
                            }

                        ?>
                        <h2> Negative news </h2>

                        <?php 
                            if(count($negativeNews) == 0){
                                    echo $emptyMessage;
                            }else{
                                foreach($negativeNews as $negnews){

                                        echo $negnews;
                                        echo '<br>';

                                }
                            }
                    }

                ?>
    </body>
</html>