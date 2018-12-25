/*
Neural network class
has forward and back propagation
*/

package networkcore;

import java.util.ArrayList;

public class Network {
    // just input and output data
    private ArrayList<ArrayList<Double>> input = new ArrayList<>();
    private ArrayList<ArrayList<Double>> output = new ArrayList<>();

    // create hidden layers
    private ArrayList<Layer> hidden = new ArrayList<>();

    //i have last output layer as other list
    private int layers;
    private int layerNeurons;
    private ArrayList<Neuron> neuronOutput = new ArrayList<>();
    private ArrayList<Double> neuralOutput = new ArrayList<>();

    private double epsilon;
    private double alpha;

    //init network
    public Network(ArrayList<ArrayList<Double>> input, ArrayList<ArrayList<Double>> output, int layers, int inputCount, int layerNeurons, double epsilon, double alpha) {
        this.input.addAll(input);
        this.output.addAll(output);
        this.layers = layers;
        this.layerNeurons = layerNeurons;
        this.epsilon = epsilon;
        this.alpha = alpha;

        //init output layer
        for (int n = 0; n < output.size(); n++) {
            neuronOutput.add(new Neuron(layerNeurons));
        }
        //init first hidden layer
        hidden.add(new Layer(layerNeurons, inputCount, input));

        //init neurons
        ArrayList defaultInput = new ArrayList();
        for (int j = 0; j < layerNeurons; j++) defaultInput.add(0.0);

        //init other hidden layers
        for (int i = 1; i < layers; i++) {
            hidden.add(new Layer(layerNeurons, layerNeurons, defaultInput));
        }


    }

    // forward propagation with oop
    public void feedForward() {
        neuralOutput.clear();
        for (int n = 0; n < output.size(); n++) {
            //neuron class has only input so we need to store output somewhere
            ArrayList<Double> currentOutput = new ArrayList<>(input.get(n));
            //for each on hidden layers
            for (Layer layer: hidden) {
                //when we changed wages or have no input we should init input
                layer.updateInput(currentOutput);
                currentOutput.clear();
                for (Neuron neuron: layer.getNeurons()) {
                    //for each neuron store output to next neuron
                    // neuron.output() count output by formula in class
                    currentOutput.add(neuron.output());
                }
            }

            //count output for output layer
            neuronOutput.get(n).setInputs(currentOutput);
            neuralOutput.add(neuronOutput.get(n).output());
        }

        System.out.println("Error: " + countError());
    }

    //bad backpropagation with using a lot of for's (hope it works right)
    public void backPropagation() {
        for (int n = 0; n < output.size(); n++) {
            //count out layer delta
            double outDelta = neuronOutput.get(n).countOutputDelta(output.get(n).get(0));
            //as i have only inputs and input wages in neuron class, i need to store it somewhere to have access
            //in other layers
            Neuron lastNeuron = null;
            //the same thong with delta
            ArrayList<Double> lastDelta = new ArrayList<>();
            //going from end to beginning
            for (int i = hidden.size()-1; i >= 0; i--) {
                //just goes on every neuron in layer
                for (int j = hidden.get(i).getNeurons().size()-1; j >= 0; j--) {
                    //getting instance of neuron and make some counts
                    Neuron currentNeuron = hidden.get(i).getNeurons().get(j);
                    double currentNeuralOutput = currentNeuron.output();
                    double currentDelta = currentNeuron.sigmoidDerivative(currentNeuralOutput);
                    double newValue = 0;
                    //if it is the last hidden and we change output layer we have other formula on delta
                    if(i == hidden.size()-1) {
                        for (int k = 0; k < layerNeurons; k++) {
                            currentDelta *= neuronOutput.get(n).getWeights().get(k) * currentNeuralOutput;
                            double currentGrad = currentNeuralOutput * outDelta;
                            double biasGrad = outDelta * 1;
                            double newBias = epsilon * biasGrad + alpha * currentNeuron.getBias();
                            newBias += neuronOutput.get(n).getBias();
                            newValue = epsilon * currentGrad + alpha * neuronOutput.get(n).getOldWeights().get(k);
                            newValue += neuronOutput.get(n).getWeights().get(k);
                            neuronOutput.get(n).setWeightsAt(k, newValue);
                            neuronOutput.get(n).setBias(newBias);
                        }
                        //other counts if we change wages on hidden layers
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
                    //storing current values
                    lastNeuron = currentNeuron;
                    lastDelta.add(currentDelta);
                }
                //clear unneed deltas
                if(i != hidden.size()-1) {
                    for (int j = 0; j < hidden.get(i).getNeurons().size()-1; j++) {
                        lastDelta.remove(0);
                    }
                }
            }
        }
    }
    //get neural output
    public ArrayList<Double> getNeuralOutput() {
        return neuralOutput;
    }

    // get gotten output
    public ArrayList<ArrayList<Double>> getOutput() {
        return output;
    }

    public double countError() {
        double error = 0;
        for (int n = 0; n < output.size(); n++) {
            error += Math.pow(output.get(n).get(0) - neuralOutput.get(n), 2);
        }
        return error / output.size();
    }
}
