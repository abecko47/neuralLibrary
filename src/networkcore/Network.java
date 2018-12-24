package networkcore;

import java.util.ArrayList;
import java.util.Random;

public class Network {
    private ArrayList<Double> input = new ArrayList();
    private ArrayList<Double> output = new ArrayList();
    private ArrayList<ArrayList<Neuron>> hidden = new ArrayList<ArrayList<Neuron>>();

    private int hiddenCount;
    private Neuron neuralOutput = new Neuron(1);
    private double outputNeural = 0;

    public Network(ArrayList input, ArrayList output, int hiddens, int inputs) {
        this.input = input;
        this.output = output;
        this.hiddenCount = hiddens;
        Random rand = new Random();


        hidden.add(new ArrayList<Neuron>());
        for (int i = 0; i < 5; i++) {
            hidden.get(0).add(new Neuron(inputs));
            hidden.get(0).get(i).setBias(rand.nextDouble());
            for (int j = 0; j < inputs; j++)
            hidden.get(0).get(i).addInput((Double) input.get(j));
        }
        for (int i = 1; i < hiddens; i++) {
            hidden.add(new ArrayList<Neuron>());
            for (int j = 0; j < 5; j++) {
                hidden.get(i).add(new Neuron(5));
                hidden.get(i).get(j).setBias(rand.nextDouble());
                for (int k = 0; k < 5; k++)
                hidden.get(i).get(j).addInput(0);
            }
        }

    }

    public void feedForward() {
        for (int i = 0; i < 5; i++) {
            hidden.get(0).get(i).setInputs(input);
        }

        for (int i = 0; i < hiddenCount-1; i++) {
            ArrayList currentInputs = new ArrayList();
            for (int j = 0; j < hidden.get(i).size(); j++){
                currentInputs.add(hidden.get(i).get(j).output());
            }

            for (int j = 0; j < hidden.get(i).size(); j++){
                hidden.get(i+1).get(j).setInputs(currentInputs);
            }

        }

        ArrayList lastInputs = new ArrayList();
        for (int j = 0; j < hidden.get(hiddenCount-1).size(); j++){
            lastInputs.add(hidden.get(hiddenCount-1).get(j).output());
        }
        neuralOutput.setInputs(lastInputs);
        outputNeural = neuralOutput.output();
        System.out.println(outputNeural);
    }

    public void backPropagation() {

    }

    public double getOutputNeural() {
        return outputNeural;
    }

    public ArrayList<Double> getOutput() {
        return output;
    }
}
