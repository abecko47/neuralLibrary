package networkcore;

import java.util.ArrayList;

public class NetworkList {
    private double currentError;

    public double countError(ArrayList<Network> networks){
        for (Network n: networks) {
            currentError += Math.pow(n.getOutputNeural() - n.getOutput().get(0), 2);
        }
        return currentError / networks.size();
    }
}
