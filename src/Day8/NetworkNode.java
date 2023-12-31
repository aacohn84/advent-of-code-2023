package Day8;

public class NetworkNode {

    private final String address;
    private final String left;
    private final String right;

    public NetworkNode(String address, String left, String right) {
        this.address = address;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return String.format("%s = (%s, %s)", address, left, right);
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public String getAddress() {
        return address;
    }
}
