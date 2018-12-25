package networkcore;

import java.util.ArrayList;

public class Network {
    private ArrayList<Double> input = new ArrayList<>();
    private ArrayList<Double> output = new ArrayList<>();
    private ArrayList<Layer> hidden = new ArrayList<>();

    private int layers;
    private int layerNeurons;
    private Neuron neuronOutput;
    private double neuralOutput = 0;

    private double epsilon;
    private double alpha;

    public Network(ArrayList<Double> input, ArrayList<Double> output, int layers, int inputCount, int layerNeurons, double epsilon, double alpha) {
        this.input.addAll(input);
        this.output.addAll(output);
        this.layers = layers;
        this.layerNeurons = layerNeurons;
        this.epsilon = epsilon;
        this.alpha = alpha;
        neuronOutput = new Neuron(layerNeurons);
        hidden.add(new Layer(layerNeurons, inputCount, input));

        ArrayList defaultInput = new ArrayList();
        for (int j = 0; j < layerNeurons; j++) defaultInput.add(0.0);

        for (int i = 1; i < layers; i++) {
            hidden.add(new Layer(layerNeurons, layerNeurons, defaultInput));
        }


    }

    public void feedForward() {
        ArrayList<Double> currentOutput = new ArrayList<>(input);
        for (Layer layer: hidden) {
            layer.updateInput(currentOutput);
            currentOutput.clear();
            for (Neuron neuron: layer.getNeurons()) {
                currentOutput.add(neuron.output());
            }
        }

        neuronOutput.setInputs(currentOutput);
        neuralOutput = neuronOutput.output();
        System.out.println("Error: " + countError());
    }

    public void backPropagation() {
        double outDelta = neuronOutput.countOutputDelta(output.get(0));
        Neuron lastNeuron = null;
        ArrayList<Double> lastDelta = new ArrayList<>();
        for (int i = hidden.size()-1; i >= 0; i--) {
            for (int j = hidden.get(i).getNeurons().size()-1; j >= 0; j--) {
                Neuron currentNeuron = hidden.get(i).getNeurons().get(j);
                double currentNeuralOutput = currentNeuron.output();
                double currentDelta = currentNeuron.sigmoidDerivative(currentNeuralOutput);
                double newValue = 0;
                if(i == hidden.size()-1) {
                    for (int k = 0; k < layerNeurons; k++) {
                        currentDelta *= neuronOutput.getWeights().get(k) * currentNeuralOutput;
                        double currentGrad = currentNeuralOutput * outDelta;
                        double biasGrad = outDelta * 1;
                        double newBias = epsilon * biasGrad + alpha * currentNeuron.getBias();
                        newBias += neuronOutput.getBias();
                        newValue = epsilon * currentGrad + alpha * neuronOutput.getOldWeights().get(k);
                        newValue += neuronOutput.getWeights().get(k);
                        neuronOutput.setWeightsAt(k, newValue);
                        neuronOutput.setBias(newBias);
                    }
                } else {
                    for (int k = 0; k < currentNeuron.getWeights().size(); k++) {
                        currentDelta *= lastNeuron.getWeights().get(k) * currentNeuralOutput;
                        double currentGrad = currentNeuralOutput * lastDelta.get(k);
                        newValue = epsilon * currentGrad + alpha * lastNeuron.getOldWeights().get(k);
                        newValue += currentNeuron.getWeights().get(k);
                        currentNeuron.setWeightsAt(k, newValue);

                        double biasGrad = lastDelta.get(k) * 1;
                        double newBias = epsilon * biasGrad + alpha * currentNeuron.getBias();
                        newBias += lastNeuron.getBias();
                        currentNeuron.setBias(newBias);
                    }
                }
                lastNeuron = currentNeuron;
                lastDelta.add(currentDelta);
            }
            if(i != hidden.size()-1) {
                for (int j = 0; j < hidden.get(i).getNeurons().size()-1; j++) {
                    lastDelta.remove(0);
                }
            }
        }
    }

    public double getNeuralOutput() {
        return neuralOutput;
    }

    public ArrayList<Double> getOutput() {
        return output;
    }

    public double countError() {
        return Math.pow(output.get(0) - neuralOutput, 2);
    }
}
