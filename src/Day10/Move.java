package Day10;

class Move {
    PipeLocation location;
    Direction direction; // enables us to avoid going where we've already been

    Move(PipeLocation toLocation, Direction fromDirection) {
        location = toLocation;
        direction = fromDirection;
    }

    public String toString() {
        return String.format("%s %s", location, direction);
    }
}
