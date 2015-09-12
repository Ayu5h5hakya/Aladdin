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

        <link href="css/bootstrap.min.css" rel="stylesheet"/>
        <link href="css/aladdin.css" rel="stylesheet"/>

        <script src="js/jquery-1.11.3.js"></script>
        <script src="js/bootstrap.js"></script>

            <?php 

                

                if(isset($_GET["title"])){
                    $testTitle = $_GET["title"];
                }

                if(!empty($testTitle)){

                    chdir('target/classes');

                    $command ='java -cp .:twitter4jcore.jar com.example.Aladdin.App -s "'.$testTitle.'" 2>&1'; 

                    exec($command,$titleOutput);

                    $titleResult = $titleOutput[0];
                }
                

                if(isset($_GET["keyword"])){
                    $keyword = $_GET["keyword"];
                }

                if(!empty($keyword)){

                    chdir('target/classes');

                    $command ='java -cp .:twitter4jcore.jar com.example.Aladdin.App -t "'.$keyword.'" 2>&1'; 

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

                /*
                $keyword = "Nepal";
                $positiveNews = [];
                $positiveNews[0] = "Nepal is free";
                $positiveNews[1] = "Kathmandu is free";

                $neutralNews = [];
                $neutralNews[0] = "Nepal is free";
                $neutralNews[1] = "Kathmandu is free";

                $negativeNews = [];
                $negativeNews[0] = "Nepal is lost";
                $negativeNews[1] = "Kathmandu isn't safe";
                 */
                
            ?>

    </head>

    <body>

            <div class="container">
                    <!-- Search Box: It posts to itself -->

                    <div class="row">
                            <div class="aladdin-title">
                                <h3>Aladdin</h3>
                                <p>A sentiment analyser</p>
                            </div>
                    </div>

                    <div class="row">
                            <div class="col-md-6 col-md-offset-3">
                                    <form class="form-group" action=<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?> method="get">

                                                <label for="keyword">Enter Keyword:</label> 
                                        <div class="row">
                                                <div class="col-md-9">
                                                        <input class="form-control" type="text" name="keyword"/>
                                                </div>

                                                <div class="col-md-3">
                                                        <input class="btn btn-primary" type="submit" value="Go"/>
                                                </div>

                                        </div>
                                    </form>

                                    <br>

                                    <form class="form-group" action=<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?> method="get">

                                        <label for="title">Enter title:</label>
                                        <div class="row">
                                                <div class="col-md-9">
                                                        <input  class="form-control" type="text" name="title"/>
                                                </div>

                                                <div class="col-md-3">
                                                        <input class="btn btn-primary" type="submit" value="Classify"/>
                                                </div>
                                        </div>

                                    </form>
                            </div>
                    </div>

<?php
                if(!empty($keyword)){

                        $emptyMessage = "-- No results found -- <br>";

?>

        <h3>
        Search Results for keyword: <a href="#"><u><?php echo $keyword; ?></u></a>
        </h3>

        <div class="panel-group">
          <div class="panel panel-default">

            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" href="#collapse1">Positive News</a>
                </h4>
            </div>

            <div id="collapse1" class="panel-collapse collapse">
                <div class="panel-body">
<?php 
                        if(count($positiveNews) == 0){
                                echo $emptyMessage;
                        }else{
                                echo '<ul class="list-group">';
                                foreach($positiveNews as $posnews){

                                        echo '<li class="list-group-item list-group-item-success">';
                                        echo $posnews;
                                        echo '</li>';

                                }
                                echo '</ul>';
                        }

?>
                </div>
            </div>

            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" href="#collapse2">Neutral News</a>
                </h4>
            </div>

            <div id="collapse2" class="panel-collapse collapse">
                <div class="panel-body">

<?php 
                        if(count($neutralNews) == 0){
                                echo $emptyMessage;
                        }else{
                                echo '<ul class="list-group">';
                                foreach($neutralNews as $neunews){

                                        echo '<li class="list-group-item list-group-item-info">';
                                        echo $neunews;
                                        echo '</li>';

                                }
                                echo '</ul>';
                        }

?>

                </div>
            </div>

            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" href="#collapse3">Negative News</a>
                </h4>
            </div>

            <div id="collapse3" class="panel-collapse collapse">
                <div class="panel-body">
<?php 
                        if(count($negativeNews) == 0){
                                echo $emptyMessage;
                        }else{
                                echo '<ul class="list-group">';
                                foreach($negativeNews as $negnews){

                                        echo '<li class="list-group-item list-group-item-danger">';
                                        echo $negnews;
                                        echo '</li>';

                                }
                                echo '</ul>';
                        }
                }

?>
                </div>
            </div>

          </div>
        </div>


<?php
                if(!empty($testTitle)){

                        echo "<br>";
                        echo $testTitle." = ".$titleResult; 
                        echo "<br>";

                }
?>
        </div>
    </body>
</html>
