package Day10;

import AoC23.AoC23Day;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 extends AoC23Day {

    final static Map<Character, Tile> tiles = getTileEntryMappings();

    private static Map<Character, Tile> getTileEntryMappings() {
        // Map of each type of pipe connector to the direction you must be travelling when you enter it
        Map<Character, Tile> m = new HashMap<>();
        m.put('F', new Tile('F', Direction.WEST, Direction.NORTH));
        m.put('7', new Tile('7', Direction.EAST, Direction.NORTH));
        m.put('L', new Tile('L', Direction.WEST, Direction.SOUTH));
        m.put('J', new Tile('J', Direction.EAST, Direction.SOUTH));
        m.put('|', new Tile('|', Direction.SOUTH, Direction.NORTH));
        m.put('-', new Tile('-', Direction.WEST, Direction.EAST));
        m.put('.', new Tile('.', null, null));
        m.put('S', new StartTile());
        return m;
    }

    public Day10(String filename) {
        super(filename);
    }

    @Override
    protected void part1(BufferedReader br) throws IOException {
        // load the pipe map
        char[][] map = readMap(br);

        // find the starting point
        PipeLocation startingPoint = getStartingPoint(map);

        // figure out where we can go from the start, since we don't know which kind of pipe we're sitting on
        Move n = null;
        for (Direction d : Direction.values()) {
            if ((n = getMove(startingPoint, d, map)) != null) {
                break;
            }
        }
        if (n == null) {
            throw new RuntimeException("Couldn't figure out where to go from the starting point ¯\\_(ツ)_/¯");
        }

        // navigate the map while counting steps until we loop back to the start
        int steps = 1;
        Move prev = null;
        while (n != null && getTileChar(n.location, map) != 'S') {
            prev = n;
            n = getNextPipeLocation(n, map);
            steps++;
        }
        if (n == null) {
            throw new RuntimeException(String.format("Ran into a problem at: %s", prev.location));
        }
        System.out.println("Steps from start part 1: " + steps / 2);
    }

    private static char getTileChar(PipeLocation l, char[][] map) {
        return map[l.row][l.col];
    }

    private static Tile getPipe(PipeLocation l, char[][] map) {
        return tiles.get(getTileChar(l, map));
    }

    private static Move getNextPipeLocation(@NotNull Move from, char[][] map) {
        Direction d = getDirectionOut(from, map);
        PipeLocation l = from.location;
        return getMove(l, d, map);
    }

    @Nullable
    private static Move getMove(PipeLocation l, Direction d, char[][] map) {
        PipeLocation to = l.moveTo(d);
        if (to.isWithinBoundsForMap(map)) {
            Tile tileType = getPipe(to, map);
            if (tileType != null && tileType.hasEntryFrom(d)) {
                return new Move(to, d);
            }
        }
        return null;
    }

    private static Direction getDirectionOut(@NotNull Move from, char[][] map) {
        return getPipe(from.location, map).getDirectionOut(from.direction);
    }

    private PipeLocation getStartingPoint(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    return new PipeLocation(i, j);
                }
            }
        }
        throw new RuntimeException("Starting point not found");
    }

    private char[][] readMap(@NotNull BufferedReader br) {
        List<String> lines = br.lines().collect(Collectors.toList());
        int n = lines.size();
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++) {
            map[i] = lines.get(i).toCharArray();
        }
        return map;
    }

    @Override
    protected void part2(BufferedReader br) throws IOException {

    }
}
