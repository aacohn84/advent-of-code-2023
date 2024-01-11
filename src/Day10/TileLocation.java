package Day10;

import org.jetbrains.annotations.NotNull;

class TileLocation {
    int row;
    int col;

    TileLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof TileLocation)) return false;
        TileLocation l = (TileLocation) other;
        return (this.row == l.row && this.col == l.col);
    }

    @Override
    public int hashCode() {
        return (String.valueOf(row) + col).hashCode();
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    TileLocation moveTo(@NotNull Direction d) {
        return new TileLocation(row + d.row, col + d.col);
    }

    boolean isWithinBoundsForMap(char[][] map) {
        return !(row < 0 || col < 0 || row >= map.length || col >= map[row].length);
    }
}
