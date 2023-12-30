package Day8;

public abstract class NetworkNavigator {
    protected NetworkNode currentNode;
    protected Network network;

    public NetworkNavigator(Network network, NetworkNode startNode) {
        this.network = network;
        currentNode = startNode;
    }

    @Override
    public String toString() {
        return currentNode.toString();
    }

    public void navigate(char direction) {
        switch (direction) {
            case 'L':
                currentNode = network.getNodeByAddress(currentNode.getLeft());
                break;
            case 'R':
                currentNode = network.getNodeByAddress(currentNode.getRight());
                break;
        }
    }

    public abstract boolean isDestinationReached();
}
