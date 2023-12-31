package Day10;

import org.jetbrains.annotations.NotNull;

class StartTile extends Tile {
    StartTile() {
        super('S', null, null);
    }

    @Override
    boolean hasEntryFrom(@NotNull Direction d) {
        return true;
    }
}
