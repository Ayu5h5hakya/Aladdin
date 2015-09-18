package com.example.Aladdin;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;

class NeuralNetwork 
{
     boolean isTrained = true;
    
     DecimalFormat df;
     DecimalFormat ef;
     
     Random rand = new Random();
     ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
     ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
     ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
     Neuron bias = new Neuron();
     int[] layers;
     int randomWeightMultiplier = 1;
 
     double epsilon = 0.00000000001;
 
     double learningRate = 0.1f;
     double momentum = 0.1f;
 
     //read data from file
      ReadFile readFile = new ReadFile();
      SentenceProcessing sentenceProcessing = new SentenceProcessing();
      
      //read file
      String positiveData = sentenceProcessing.sentenceProcessor(readFile.readFile("PositiveNewsNN.txt"));
      String negativeData = sentenceProcessing.sentenceProcessor(readFile.readFile("NegativeNewsNN.txt"));
      String neutralData = sentenceProcessing.sentenceProcessor(readFile.readFile("NeutralNewsNN.txt"));
      
    //   String testData = sentenceProcessing.sentenceProcessor("3 police was killed by Maoist\n");

       DataSet positiveDataSet = new DataSet(positiveData);
       DataSet negativeDataSet = new DataSet(negativeData);
       DataSet neutralDataSet = new DataSet(neutralData);
  
      FeatureVector featureVector = new FeatureVector(positiveDataSet.getVocabulary(),negativeDataSet.getVocabulary(),neutralDataSet.getVocabulary());
     
      HashMap<String,LinkedList<Double>> weight = featureVector.getWeight();

            
      ArrayList<double[]> inputs = new ArrayList<double[]>();
      ArrayList<double[]> expectedOutputs = new ArrayList<double[]>();
      ArrayList<double[]> resultOutputs = new ArrayList<double[]>();
     
      double[] tests;

      double output[];
 
      // for weight update all
      final HashMap<String, Double> weightUpdate = new HashMap<String, Double>();
 
      public NeuralNetwork()
              {
        
//         featureVectors(testData);
      
           //takeValues(positiveData,new double[]{1,0,0});
           //takeValues(neutralData, new double[]{0,1,0});
           //takeValues(negativeData,new double[]{0,0,1});

          NeuralNetwork(90, 90, 3);

          int maxRuns = 50000;

           double minErrorCondition = 0.001;
      
      //    run(maxRuns, minErrorCondition);

      }

void featureVectors(String data)
{

System.out.println(data);
String dat = data.substring(0,data.length()-1);
System.out.println(weight.get(dat));

}

    public void setInput(double inputs[]) {
        for (int i = 0; i < inputLayer.size(); i++) {
            inputLayer.get(i).setOutput(inputs[i]);
        }
    }

    public double[] getOutput() {
        double[] outputs = new double[outputLayer.size()];
        for (int i = 0; i < outputLayer.size(); i++)
            outputs[i] = outputLayer.get(i).getOutput();
        return outputs;
    }
 
   public void activate() {
        for (Neuron n : hiddenLayer)
            n.calculateOutput();
        for (Neuron n : outputLayer)
            n.calculateOutput();
    }
 
 public void applyBackpropagation(double expectedOutput[]) {
 
        // error check, normalize value ]0;1[
/*        for (int i = 0; i < expectedOutput.length; i++) {
            double d = expectedOutput[i];
            if (d <  || d > 1) {
                if (d < 0)
                    expectedOutput[i] = 0 + epsilon;
                else
                    expectedOutput[i] = 1 - epsilon;
            }
        }
*/
         
        int i = 0;
        for (Neuron n : outputLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double ak = n.getOutput();
                double ai = con.leftNeuron.getOutput();
                double desiredOutput = expectedOutput[i];
 
                double partialDerivative = -ak * (1 - ak) * ai
                        * (desiredOutput - ak);
                double deltaWeight = -learningRate * partialDerivative;
                double newWeight = con.getWeight() + deltaWeight;
                con.setDeltaWeight(deltaWeight);
                con.setWeight(newWeight + momentum * con.getPrevDeltaWeight());
            }
            i++;
        }
 
        // update weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double aj = n.getOutput();
                double ai = con.leftNeuron.getOutput();
                double sumKoutputs = 0;
                int j = 0;
                for (Neuron out_neu : outputLayer) {
                    double wjk = out_neu.getConnection(n.id).getWeight();
                    double desiredOutput = (double) expectedOutput[j];
                    double ak = out_neu.getOutput();
                    j++;
                    sumKoutputs = sumKoutputs
                            + (-(desiredOutput - ak) * ak * (1 - ak) * wjk);
                }
 
                double partialDerivative = aj * (1 - aj) * ai * sumKoutputs;
                double deltaWeight = -learningRate * partialDerivative;
                double newWeight = con.getWeight() + deltaWeight;
                con.setDeltaWeight(deltaWeight);
                con.setWeight(newWeight + momentum * con.getPrevDeltaWeight());
            }
        }
    }

public  String getResult(String testData)
{

    takeTestValues(testData);

    setInput(tests);

    activate();

    double [] finalResult = getOutput();

    if((finalResult[0] > finalResult[1]) && (finalResult[0]>finalResult[2]))
    {
       return "Positive";
    }

    else if(finalResult[2] > finalResult[1])
    {
        return "Negative";
    }

    else
    {

         return "Neutral";
    }

}

private void run(int maxSteps, double minError) {

        double error = 1;

    for (int i = 0; i < maxSteps && error > minError; i++) {
    
            error = 0;
            for (int p = 0; p < inputs.size(); p++) {
                 
                setInput(inputs.get(p));
                activate();
   
                output = getOutput();
           
                resultOutputs.set(p,output);
        
                double[] temp = expectedOutputs.get(p);

                for (int j = 0; j < temp.length; j++) {
                 
                    if(Math.abs(output[j] - temp[j])<0.00000001)
                    {
                         WeightUpdate();
                         System.exit(0);
                    }

                    double err = Math.pow(output[j] - temp[j], 2);
                    error += err;
       
                }
  
                applyBackpropagation(expectedOutputs.get(p));
        }
                 
                   System.out.println( error + " " +i);

    }
   
    WeightUpdate();
}

private void WeightUpdate()
{

       String fileName = "../../src/resources/WeightFile.txt";
       
       try
       {

       FileWriter fileWriter = new FileWriter(fileName);
       BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        // weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                
                String w = df.format(con.getWeight());
             
                bufferedWriter.write(ef.format(n.id));
                bufferedWriter.write(" ");
                bufferedWriter.write(ef.format(con.id));
                bufferedWriter.write(" ");
                bufferedWriter.write(w);
                bufferedWriter.newLine();
            }
        }
        // weights for the output layer
        for (Neuron n : outputLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                String w = df.format(con.getWeight());
          
                bufferedWriter.write(ef.format(n.id));
                bufferedWriter.write(" ");
                bufferedWriter.write(ef.format(con.id));
                bufferedWriter.write(" ");
                bufferedWriter.write(w);
                bufferedWriter.newLine();
            }
        }
       
            bufferedWriter.close(); 
        }
       catch(IOException ex)
       {
          
               System.out.println("Error writing to file " + fileName );
       }

}

private void NeuralNetwork(int input, int hidden, int output)
{

        this.layers = new int[] { input, hidden, output };
        df = new DecimalFormat("#0.000#");
        ef = new DecimalFormat("#");
 
        /**
         * Create all neurons and connections Connections are created in the
         * neuron class
         */
        for (int i = 0; i < layers.length; i++) {
            if (i == 0) { // input layer
                for (int j = 0; j < layers[i]; j++) {
                    Neuron neuron = new Neuron();
                    inputLayer.add(neuron);
                }
            } else if (i == 1) { // hidden layer
                for (int j = 0; j < layers[i]; j++) {
                    Neuron neuron = new Neuron();
                    neuron.addInConnectionsS(inputLayer);
                    neuron.addBiasConnection(bias);
                    hiddenLayer.add(neuron);
                }
            }
 
            else if (i == 2) { // output layer
                for (int j = 0; j < layers[i]; j++) {
                    Neuron neuron = new Neuron();
                    neuron.addInConnectionsS(hiddenLayer);
                    neuron.addBiasConnection(bias);
                    outputLayer.add(neuron);
                }
            } else {
                System.out.println("!Error NeuralNetwork init");
            }
        }

        // initialize random weights
        for (Neuron neuron : hiddenLayer) {
            ArrayList<Connection> connections = neuron.getAllInConnections();
            for (Connection conn : connections) {
                double newWeight = getRandom()/900.0;
                conn.setWeight(newWeight);
            }
        }

        for (Neuron neuron : outputLayer) {
            ArrayList<Connection> connections = neuron.getAllInConnections();
            for (Connection conn : connections) {
                double newWeight = getRandom()/900.0;
                conn.setWeight(newWeight);
            }
        }
 
        // reset id counters
        Neuron.counter = 0;
        Connection.counter = 0;

        if(isTrained)
        {
                 trainedWeights();
                 updateAllWeights();
        }
}

private String weightKey(int neuronId, int conId) {
        return "N" + neuronId + "_C" + conId;
    }
 
private void trainedWeights()
{

        weightUpdate.clear();

        try
        {

        String line;
       
        FileReader fileReader = new FileReader("../../src/resources/WeightFile.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null)
        {

              String[] lineSplit = line.split(" ");

              weightUpdate.put(weightKey(Integer.parseInt(lineSplit[0]), Integer.parseInt(lineSplit[1])), Double.parseDouble(lineSplit[2]));
        
        }

        bufferedReader.close();

        }
        catch(FileNotFoundException ex)
        {
            System.out.println("File Reading Error");
        }
        catch(IOException ex)
        {
              
               System.out.println("Error reading to file ");

        }

}

    /**
     * Take from hash table and put into all weights
     */
   private void updateAllWeights() {
        // update weights for the output layer
        for (Neuron n : outputLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                String key = weightKey(n.id, con.id);
                double newWeight = weightUpdate.get(key);
                con.setWeight(newWeight);
            }
        }
        // update weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                String key = weightKey(n.id, con.id);
                double newWeight = weightUpdate.get(key);
                con.setWeight(newWeight);
            }
        }
    }

// random
 private double getRandom() {
     return randomWeightMultiplier * (rand.nextDouble() * 2 -1) ; 
 }

private void takeTestValues(String data)
{

 String dataValue = data.substring(0,data.length()-1);

tests = new double[90];

 int i = 0;

 String[] dataSplit = dataValue.split(" ");
 
 for( String single : dataSplit)
 {

    if(weight.get(single) != null)
    {
      for(double value: weight.get(single))
      {
           tests[i] = value;
          ++i;
      }
    }
    else
    {

       for(int k=0; k<3; ++k)
       {
          tests[i] = 0.0;
          ++i;

       }
    }
 }

}

private void takeValues(String data, double[]output)
   {

      String[] dataSplit= data.split("\n");
    
      for(String temp : dataSplit)
      {
          String[] tempSplit = temp.split(" ");

             double[] result = new double[90];
             int i=0;
      
        for(String single : tempSplit)
          {

            if(weight.get(single) != null)
            { 

             for(double value: weight.get(single))
             {

                result[i] = value;
                ++i;
             }

            }
            else
            {
                 for(int k=0; k<3; ++k)
                   { 
                         result[i] = 0.0;
                         ++i;
                   
                   }

            }
          }

          inputs.add(result); 
          expectedOutputs.add(output);
          resultOutputs.add(new double[]{0,0,0}); 
     }

   }

}

