package Day8;

public class Part1NetworkNavigatorProvider implements NetworkNavigatorProvider {

    @Override
    public NetworkNavigator getNewNavigator(Network n, NetworkNode startNode) {
        return new Part1NetworkNavigator(n, startNode);
    }

    @Override
    public boolean isValidStartingPoint(String address) {
        return address.equals("AAA");
    }
}
