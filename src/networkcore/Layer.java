package networkcore;

import java.util.ArrayList;

public class Layer {
    private ArrayList<Neuron> neurons = new ArrayList<>();

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(ArrayList<Neuron> neurons) {
        this.neurons.clear();
        this.neurons.addAll(neurons);
    }

    public Layer(int size, int inputs) {
        for (int i = 0; i < size; i++) {
            neurons.add(new Neuron(inputs));
        }
    }

    public Layer(int size, int inputs, ArrayList input) {
        for (int i = 0; i < size; i++) {
            neurons.add(new Neuron(inputs));
            neurons.get(i).setInputs(input);
        }
    }

    public void updateInput(int neuron, int inputIndex, double value) {
        neurons.get(neuron).updateInput(inputIndex, value);
    }

    public void updateInput(ArrayList inputs) {
        for (Neuron n: neurons) {
            n.setInputs(inputs);
        }
    }
}
