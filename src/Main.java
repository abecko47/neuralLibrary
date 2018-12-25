import networkcore.Network;
//import networkcore.NetworkList;
import networkcore.Neuron;

import java.util.ArrayList;

public class Main {
    public static void main(String [] args) {
        ArrayList<ArrayList<Double>> input = new ArrayList();
        ArrayList<ArrayList<Double>> output = new ArrayList();

        ArrayList<Network> networks = new ArrayList<>();

        input.add(new ArrayList());
        input.get(0).add((double) 0);
        input.get(0).add((double) 0);
        output.add(new ArrayList());
        output.get(0).add((double) 0);

        input.add(new ArrayList());
        input.get(1).add((double) 0.3);
        input.get(1).add((double) 0.17);
        output.add(new ArrayList());
        output.get(1).add((double) 1);

        input.add(new ArrayList());
        input.get(2).add((double) 1);
        input.get(2).add((double) 0);
        output.add(new ArrayList());
        output.get(2).add((double) 1);

        input.add(new ArrayList());
        input.get(3).add((double) 1);
        input.get(3).add((double) 1);
        output.add(new ArrayList());
        output.get(3).add((double) 0);


        Network network = new Network(input.get(3), output.get(3), 2,2, 5, 0.2, 0.05);
        network.feedForward();


        for (int i = 0; i < 10; i++) {
            network.backPropagation();
            network.feedForward();
        }
        System.out.println(network.getNeuralOutput());




//        for (int i = 0; i < input.size(); i++) {
//            networks.add(new Network(input.get(i), output.get(i), 2, 2));
//        }
//
//        NetworkList nlist = new NetworkList();
//
//        networks.forEach((n) -> n.feedForward());
//        System.out.println(nlist.countError(networks) + "\n");
////        networks.forEach((n) -> n.backPropagation());
//        networks.forEach((n) -> n.feedForward());
//        System.out.println(nlist.countError(networks));
    }
}
