package AoC23;

public class GridLocation {
    public int row;
    public int col;

    public GridLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof GridLocation)) return false;
        GridLocation l = (GridLocation) other;
        return (this.row == l.row && this.col == l.col);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
