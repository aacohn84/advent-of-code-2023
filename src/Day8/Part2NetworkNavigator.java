package Day8;

public class Part2NetworkNavigator extends NetworkNavigator {
    public Part2NetworkNavigator(Network network, NetworkNode startNode) {
        super(network, startNode);
    }

    @Override
    public boolean isDestinationReached() {
        String address = currentNode.getAddress();
        int last = address.length() - 1;
        return address.charAt(last) == 'Z';
    }
}
