import com.opencsv.CSVReader;
import networkcore.Network;
//import networkcore.NetworkList;
import networkcore.Neuron;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String [] args) throws IOException {
        ArrayList<ArrayList<Double>> input = new ArrayList<>();
        ArrayList<ArrayList<Double>> output = new ArrayList<>();

        CSVReader reader = new CSVReader(new FileReader("train data/banana/banana_2.csv"));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            ArrayList<Double> currentInput = new ArrayList<>();
            ArrayList<Double> currentOutput = new ArrayList<>();
            currentInput.add(Double.valueOf(nextLine[0]) / 100);
            currentInput.add(Double.valueOf(nextLine[1]) / 100);
            currentOutput.add(Double.valueOf(nextLine[2]));

            input.add(currentInput);
            output.add(currentOutput);
        }

        ArrayList<ArrayList<Double>> testInput = new ArrayList<>();
        ArrayList<ArrayList<Double>> testOutput = new ArrayList<>();
        ArrayList<ArrayList<Double>> trainInput = new ArrayList<>(input);
        ArrayList<ArrayList<Double>> trainOutput = new ArrayList<>(output);

//        Random rand = new Random();
//        for (int i = trainInput.size()-1; i >= testInput.size(); i--) {
//            int a = rand.nextInt(i);
//            testInput.add(trainInput.get(a));
//            testOutput.add(trainOutput.get(a));
//            trainInput.remove(a);
//            trainOutput.remove(a);
//        }

        for (int i = 0; i < output.size(); i++) {
            if (i % 10 == 0) {
                trainInput.add(input.get(i));
                trainOutput.add(output.get(i));
            } else {
                testInput.add(input.get(i));
                testOutput.add(output.get(i));
            }
        }

        Network network = new Network(trainInput, trainOutput, 5,2, 5, 0.2, 0.05);
        network.feedForward();

        while (network.countError() > 0.01){
            network.backPropagation();
            network.feedForward();
        }
        network.analyze();

        System.out.println("\nTest Inputs/Outputs: ");
        network.setInput(testInput);
        network.setOutput(testOutput);
        network.feedForward();
        network.analyze();


    }
}
