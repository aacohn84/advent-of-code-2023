package Day10;

import org.jetbrains.annotations.NotNull;

class Tile {
    char pipeChar;
    Direction entry1;
    Direction entry2;

    Tile(char pipeChar, Direction entry1, Direction entry2) {
        this.pipeChar = pipeChar;
        this.entry1 = entry1;
        this.entry2 = entry2;
    }

    @Override
    public String toString() {
        return String.valueOf(pipeChar);
    }

    Direction getDirectionOut(@NotNull Direction in) {
        return (in == entry1) ? entry2.getOpposite() : entry1.getOpposite();
    }

    boolean hasEntryFrom(@NotNull Direction d) {
        return d == entry1 || d == entry2;
    }
}
