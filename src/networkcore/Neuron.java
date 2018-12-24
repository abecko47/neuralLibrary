package networkcore;

import java.util.ArrayList;
import java.util.Random;

public class Neuron {
    private double bias = 0;
    private int inputsCount = 0;
    private ArrayList<Double> inputs = new ArrayList();
    private ArrayList<Double> weights = new ArrayList();

    public Neuron(int size) {
        this.inputsCount = size;
        Random rand = new Random();
        bias = rand.nextDouble();
        for (int i = 0; i < size; i++) {
            weights.add(rand.nextDouble());
        }
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

    public void setInputs(ArrayList inputs) {
        this.inputs = inputs;
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        this.weights = weights;
    }

    public void addInput(double input) {
        inputs.add(input);
    }

    public double output() {
        double input = 0;
        for (int i = 0; i < inputsCount; i++) {
            input += inputs.get(i) * weights.get(i);
        }
        return sigmoid(input + bias);
    }
}
