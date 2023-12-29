package Day8;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Network {
    final Map<String, NetworkNode> nodes;
    NetworkNode startNode;

    public Network() {
        nodes = new HashMap<>();
    }

    public void addNode(String address, String left, String right) {
        NetworkNode n = new NetworkNode(address, left, right);
        nodes.put(address, n);
    }

    public int navigate(@NotNull String directions, @NotNull String destinationAddress) {
        NetworkNode n = startNode;
        int stepCount = 0;
        int numDirections = directions.length();
        while (!n.getAddress().equals(destinationAddress)) {
            char d = directions.charAt(stepCount % numDirections);
            switch (d) {
                case 'L':
                    n = nodes.get(n.getLeft());
                    break;
                case 'R':
                    n = nodes.get(n.getRight());
                    break;
            }
            stepCount++;
        }
        return stepCount;
    }

    public void setStartNode(String startAddress) {
        startNode = nodes.get(startAddress);
    }
}
