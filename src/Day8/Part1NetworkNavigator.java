package Day8;

public class Part1NetworkNavigator extends NetworkNavigator {
    public Part1NetworkNavigator(Network network, NetworkNode startNode) {
        super(network, startNode);
    }

    @Override
    public boolean isDestinationReached() {
        return currentNode.getAddress().equals("ZZZ");
    }
}
