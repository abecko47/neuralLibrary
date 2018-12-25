package networkcore;

import java.util.ArrayList;
import java.util.Random;

public class Neuron {
    private double bias = 0;
    private int inputsCount = 0;
    private double countedOutput;
    private ArrayList<Double> inputs = new ArrayList();
    private ArrayList<Double> weights = new ArrayList();
    private ArrayList<Double> oldWeights = new ArrayList();
    private double oldBias;

    public Neuron(int size) {
        this.inputsCount = size;
        Random rand = new Random();
        bias = rand.nextDouble();
        for (int i = 0; i < size; i++) {
            weights.add(rand.nextDouble());
            oldWeights.add(0.0);
        }
        oldWeights.set(0, 1.0);
    }

    public double sigmoid(double x) {
        return (1/( 1 + Math.pow(Math.E,(-1*x))));
    }

    public double sigmoidDerivative(double x) {
        return sigmoid(x) * (1 - sigmoid(x));
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public ArrayList getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<Double> inputs) {
        this.inputs.clear();
        this.inputs.addAll(inputs);
        inputsCount = inputs.size();
    }

    public void updateInput(int index, double value) {
        inputs.set(index, value);
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        this.weights.clear();
        this.weights.addAll(weights);
    }

    public void setWeightsAt(int index, double value) {
        weights.set(index, value);
    }

    public void addInput(double input) {
        inputs.add(input);
    }

    public double getCountedOutput() {
        return countedOutput;
    }

    public void setCountedOutput(double countedOutput) {
        this.countedOutput = countedOutput;
    }

    public ArrayList<Double> getOldWeights() {
        return oldWeights;
    }

    public double output() {
        double input = 0;
        for (int i = 0; i < inputsCount; i++) {
            input += inputs.get(i) * weights.get(i);
        }

        if(oldWeights.get(0) != 1.0) {
            oldWeights.clear();
            oldWeights.addAll(weights);
        } else oldWeights.set(0, 0.0);

        oldBias = bias;
        countedOutput = sigmoid(input + bias);
        return countedOutput;
    }

    public double countOutputDelta(double ideal) {
        return (ideal - countedOutput) * sigmoidDerivative(countedOutput);
    }

}
