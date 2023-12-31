package Day10;

enum Direction {
    WEST(0, -1), NORTH(-1, 0), EAST(0, 1), SOUTH(1, 0);

    final int row;
    final int col;

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    Direction getOpposite() {
        switch (this) {
            case WEST: return EAST;
            case EAST: return WEST;
            case NORTH: return SOUTH;
            default: return NORTH;
        }
    }
}
