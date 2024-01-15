package Day10;

import AoC23.GridLocation;
import org.jetbrains.annotations.NotNull;

class TileLocation extends GridLocation {
    TileLocation(int row, int col) {
        super(row, col);
    }

    TileLocation moveTo(@NotNull Direction d) {
        return new TileLocation(row + d.row, col + d.col);
    }

    boolean isWithinBoundsForMap(char[][] map) {
        return !(row < 0 || col < 0 || row >= map.length || col >= map[row].length);
    }
}
