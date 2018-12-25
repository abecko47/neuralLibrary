package networkcore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Network {
    private ArrayList<Double> input = new ArrayList();
    private ArrayList<Double> output = new ArrayList();
    private ArrayList<Layer> hidden = new ArrayList();

    private int hiddenCount;
    private Neuron neuralOutput = new Neuron(5);
    private double outputNeural = 0;

    private final double epsilon = 0.2;
    private final double alpha = 0.5;

    public Network(ArrayList input, ArrayList output, int layers, int inputCount, int layerNeurons) {
        this.input.addAll(input);
        this.output.addAll(output);
        hidden.add(new Layer(layerNeurons, inputCount, input));

        ArrayList defaultInput = new ArrayList();
        for (int j = 0; j < layerNeurons; j++) defaultInput.add(0.0);

        for (int i = 1; i < layers; i++) {
            hidden.add(new Layer(layerNeurons, layerNeurons, defaultInput));
        }

    }

    public void feedForward() {
        ArrayList output = new ArrayList();
        output.addAll(input);
        for (Layer layer: hidden) {
            layer.updateInput(input);
            output.clear();
            for (Neuron neuron: layer.getNeurons()) {
                output.add(neuron.output());
            }
        }

        neuralOutput.setInputs(output);
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
