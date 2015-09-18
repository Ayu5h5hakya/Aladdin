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
 
                    $command ='java -cp .:jaws-bin.jar:twitter4jcore.jar com.example.Aladdin.App -s "'.$testTitle.'" 2>&1'; 

                    exec($command,$titleOutput);

                    $titleResultNB = $titleOutput[0];
                    $titleResultNN = $titleOutput[1];
                }
                

                if(isset($_GET["keyword"])){
                    $keyword = $_GET["keyword"];
                }

                if(!empty($keyword)){

                    chdir('target/classes');

                    $command ='java -cp .:jaws-bin.jar:twitter4jcore.jar com.example.Aladdin.App -t "'.$keyword.'" 2>&1'; 

                    exec($command,$output);


                    $positiveNewsNB = [];
                    $negativeNewsNB = [];
                    $neutralNewsNB = [];

                    $positiveNewsNN = [];
                    $negativeNewsNN = [];
                    $neutralNewsNN = [];

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
                    $sourceVal = "undefined";

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
                            }elseif($val == "<source>"){
                                    $running = "source";
                                    continue;
                            }elseif($val == "</source>"){
                                    $running = "undefined";
                                    continue;
                            }

                            if($running == "source"){
                                    $sourceVal = $val;
                                    continue;
                            }

                            if($sourceVal == "naive"){

                                if($running == "positive"){
                                        $positiveNewsNB[] = $val;
                                }elseif($running == "negative"){
                                        $negativeNewsNB[] = $val;
                                }elseif($running == "neutral"){
                                        $neutralNewsNB[] = $val;
                                }                            

                            } elseif($sourceVal == "neural"){

                                if($running == "positive"){
                                        $positiveNewsNN[] = $val;
                                }elseif($running == "negative"){
                                        $negativeNewsNN[] = $val;
                                }elseif($running == "neutral"){
                                        $neutralNewsNN[] = $val;
                                }                            
                            }
                    }

                }

                /*
                $keyword = "";
                $positiveNews = [];
                $positiveNews[0] = "Nepal is free";

                $neutralNews = [];
                $neutralNews[0] = "Nepal is free";
                $neutralNews[1] = "Kathmandu is free";

                $negativeNews = [];
                $negativeNews[0] = "Nepal is lost";
                $negativeNews[1] = "Kathmandu isn't safe";
                 */

                /*
                $testTitle = "Apple releases new Iphone";
                $titleResultNB = "Positive";
                $titleResultNN = "Neutral";
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
                            <div class="col-sm-10 col-sm-offset-1 col-md-6 col-md-offset-3">
                                    <form class="form-group" action=<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?> method="get">

                                                <label for="keyword">Enter Keyword:</label> 
                                        <div class="row">
                                                <div class="col-sm-9">
                                                        <input class="form-control" type="text" name="keyword"/>
                                                </div>

                                                <div class="col-sm-3">
                                                        <input class="btn btn-primary" type="submit" value="Go"/>
                                                </div>

                                        </div>
                                    </form>

                                    <br>

                                    <form class="form-group" action=<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?> method="get">

                                        <label for="title">Enter title:</label>
                                        <div class="row">
                                                <div class="col-sm-9">
                                                        <input  class="form-control" type="text" name="title"/>
                                                </div>

                                                <div class="col-sm-3">
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

        <h3> Naive Bayes Results </h3>

        <br>

        <!-- Naive Bayes Results -->
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
                        if(count($positiveNewsNB) == 0){
                                echo $emptyMessage;
                        }else{
                                echo '<ul class="list-group">';
                                foreach($positiveNewsNB as $posnews_nb){

                                        echo '<li class="list-group-item list-group-item-success">';
                                        echo $posnews_nb;
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
                        if(count($neutralNewsNB) == 0){
                                echo $emptyMessage;
                        }else{
                                echo '<ul class="list-group">';
                                foreach($neutralNewsNB as $neunews_nb){

                                        echo '<li class="list-group-item list-group-item-info">';
                                        echo $neunews_nb;
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
                        if(count($negativeNewsNB) == 0){
                                echo $emptyMessage;
                        }else{
                                echo '<ul class="list-group">';
                                foreach($negativeNewsNB as $negnews_nb){

                                        echo '<li class="list-group-item list-group-item-danger">';
                                        echo $negnews_nb;
                                        echo '</li>';

                                }
                                echo '</ul>';
                        }

?>
                </div>
            </div>

          </div>
        </div>

        <h3> Neural Network </h3>

        <br>

        <!-- Neural Network Results -->

        <div class="panel-group">
          <div class="panel panel-default">

            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" href="#collapse4">Positive News</a>
                </h4>
            </div>

            <div id="collapse4" class="panel-collapse collapse">
                <div class="panel-body">
<?php 
                        if(count($positiveNewsNN) == 0){
                                echo $emptyMessage;
                        }else{
                                echo '<ul class="list-group">';
                                foreach($positiveNewsNN as $posnews_nn){

                                        echo '<li class="list-group-item list-group-item-success">';
                                        echo $posnews_nn;
                                        echo '</li>';

                                }
                                echo '</ul>';
                        }

?>
                </div>
            </div>


            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" href="#collapse5">Neutral News</a>
                </h4>
            </div>

            <div id="collapse5" class="panel-collapse collapse">
                <div class="panel-body">

<?php 
                        if(count($neutralNewsNN) == 0){
                                echo $emptyMessage;
                        }else{
                                echo '<ul class="list-group">';
                                foreach($neutralNewsNN as $neunews_nn){

                                        echo '<li class="list-group-item list-group-item-info">';
                                        echo $neunews_nn;
                                        echo '</li>';

                                }
                                echo '</ul>';
                        }

?>

                </div>
            </div>

            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" href="#collapse6">Negative News</a>
                </h4>
            </div>

            <div id="collapse6" class="panel-collapse collapse">
                <div class="panel-body">
<?php 
                        if(count($negativeNewsNN) == 0){
                                echo $emptyMessage;
                        }else{
                                echo '<ul class="list-group">';
                                foreach($negativeNewsNN as $negnews_nn){

                                        echo '<li class="list-group-item list-group-item-danger">';
                                        echo $negnews_nn;
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

?>

                        <div class="row">

                            <div class="aladdin-single-result col-sm-12 col-md-8 col-md-offset-2">
                                    <p>
                                            "<?php echo $testTitle; ?>"
                                    </p>

                                    <div class="col-sm-6 col-md-6">

                                        <h4> <u>Naive Bayes</u> </h4>

                                        <h2><?php echo $titleResultNB; ?></h2>

                                    </div>

                                    <div class="col-sm-6 col-md-6">

                                        <h4><u>Neural Network</u></h4>

                                        <h2><?php echo $titleResultNN; ?></h2>

                                    </div>

                            </div>
                        </div>

<?php
                }
?>
        </div>
    </body>
</html>
