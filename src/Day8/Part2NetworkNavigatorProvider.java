package Day8;

import org.jetbrains.annotations.NotNull;

public class Part2NetworkNavigatorProvider implements NetworkNavigatorProvider {

    @Override
    public NetworkNavigator getNewNavigator(Network n, NetworkNode startNode) {
        return new Part2NetworkNavigator(n, startNode);
    }

    @Override
    public boolean isValidStartingPoint(@NotNull String address) {
        return address.endsWith("A");
    }
}
