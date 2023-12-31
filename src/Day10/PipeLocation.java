package Day10;

import org.jetbrains.annotations.NotNull;

public class PipeLocation {
    public int row;
    public int col;

    public PipeLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    public PipeLocation moveTo(@NotNull Direction d) {
        return new PipeLocation(row + d.row, col + d.col);
    }

    public boolean isWithinBoundsForMap(char[][] map) {
        return !(row < 0 || col < 0 || row >= map.length || col >= map[row].length);
    }
}
