package Day8;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Network {
    final Map<String, NetworkNode> nodes;
    final NetworkNavigatorProvider navProvider;
    List<NetworkNode> startNodes = new ArrayList<>();

    public Network(NetworkNavigatorProvider provider) {
        nodes = new HashMap<>();
        navProvider = provider;
    }

    public void addNode(String address, String left, String right) {
        NetworkNode n = new NetworkNode(address, left, right);
        nodes.put(address, n);
    }

    public long navigate(@NotNull String directions) {
        List<NetworkNavigator> navigators = new ArrayList<>();
        for (NetworkNode startNode : startNodes) {
            navigators.add(navProvider.getNewNavigator(this, startNode));
        }
        int numNavigators = navigators.size();
        long stepCount = 0;
        long numDirections = directions.length();
        int navigatorsAtDestination = 0;
        while (navigatorsAtDestination != numNavigators) {
            navigatorsAtDestination = 0;
            char d = directions.charAt((int) (stepCount % numDirections));
            for (int i = 0; i < numNavigators; i++) {
                NetworkNavigator navigator = navigators.get(i);
                navigator.navigate(d);
                if (navigator.isDestinationReached()) {
                    navigatorsAtDestination += 1;
                }
            }
            if (navigatorsAtDestination > 2) {
                System.out.println("Navigators: " + navigatorsAtDestination + ", stepCount: " + stepCount + 1);
            }
            stepCount++;
        }
        return stepCount;
    }

    public void setStartNodes() {
        for (String address : nodes.keySet()) {
            if (navProvider.isValidStartingPoint(address)) {
                startNodes.add(nodes.get(address));
            }
        }
    }

    NetworkNode getNodeByAddress(String address) {
        return nodes.get(address);
    }
}
