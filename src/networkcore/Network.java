package networkcore;

import java.util.ArrayList;

public class Network {
    private ArrayList<Double> input = new ArrayList();
    private ArrayList<Double> output = new ArrayList();
    private ArrayList<Layer> hidden = new ArrayList();

    private int layers;
    private Neuron neuronOutput;
    private double neuralOutput = 0;

    private final double epsilon = 0.2;
    private final double alpha = 0.5;

    public Network(ArrayList input, ArrayList output, int layers, int inputCount, int layerNeurons) {
        this.input.addAll(input);
        this.output.addAll(output);
        this.layers = layers;
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
    }

    public void backPropagation() {
        double outDelta = neuronOutput.countOutputDelta(output.get(0));
        System.out.println(outDelta);
    }

    public double getNeuralOutput() {
        return neuralOutput;
    }

    public ArrayList<Double> getOutput() {
        return output;
    }
}
