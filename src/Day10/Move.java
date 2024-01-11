package Day10;

class Move {
    TileLocation location;
    Direction direction; // enables us to avoid going where we've already been

    Move(TileLocation toLocation, Direction fromDirection) {
        location = toLocation;
        direction = fromDirection;
    }

    public String toString() {
        return String.format("%s %s", location, direction);
    }
}
