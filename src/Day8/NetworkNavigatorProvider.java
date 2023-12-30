package Day8;

public interface NetworkNavigatorProvider {
    NetworkNavigator getNewNavigator(Network n, NetworkNode startNode);
    boolean isValidStartingPoint(String address);
}
