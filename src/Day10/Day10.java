package Day10;

import AoC23.AoC23Day;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 extends AoC23Day {

    final static Map<Character, Tile> tiles = getTileEntryMap();
    final static Map<Character, Character> prettyPipeChars = getPrettyPipeCharMap();
    final static Set<Character> pipeChars = getPipeChars();
    final static Map<Set<Direction>, Character> reverseTileEntryMap = getReverseTileEntryMap();

    private static @NotNull Map<Character, Tile> getTileEntryMap() {
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

    private static Map<Set<Direction>, Character> getReverseTileEntryMap() {
        Map<Set<Direction>, Character> m = new HashMap<>();
        m.put(new HashSet<>(Arrays.asList(Direction.WEST, Direction.NORTH)), 'F');
        m.put(new HashSet<>(Arrays.asList(Direction.EAST, Direction.NORTH)), '7');
        m.put(new HashSet<>(Arrays.asList(Direction.WEST, Direction.SOUTH)), 'L');
        m.put(new HashSet<>(Arrays.asList(Direction.EAST, Direction.SOUTH)), 'J');
        m.put(new HashSet<>(Arrays.asList(Direction.SOUTH, Direction.NORTH)), '|');
        m.put(new HashSet<>(Arrays.asList(Direction.WEST, Direction.EAST)), '-');
        return m;
    }

    private static @NotNull Map<Character, Character> getPrettyPipeCharMap() {
        Map<Character, Character> m = new HashMap<>();
        m.put('F', '╔');
        m.put('7', '╗');
        m.put('L', '╚');
        m.put('J', '╝');
        m.put('|', '║');
        m.put('-', '═');
        m.put('.', '∙');
        m.put('S', 'S');
        m.put('O', 'O');
        m.put('I', 'I');
        return m;
    }

    private static @NotNull Set<Character> getPipeChars() {
        Set<Character> s = new HashSet<>();
        s.add('F');
        s.add('7');
        s.add('L');
        s.add('J');
        s.add('|');
        s.add('-');
        return s;
    }

    public Day10(String filename) {
        super(filename);
    }

    @Override
    protected void part1(BufferedReader br) throws IOException {
        // load the pipe map
        char[][] map = readMap(br);

        Set<TileLocation> tileLocations = getPipeLocations(map, getStartingPoint(map));
        System.out.println("Steps from start part 1: " + tileLocations.size() / 2);
    }

    @NotNull
    private Set<TileLocation> getPipeLocations(char[][] map, TileLocation startingPoint) {
        // record all parts of the pipe, beginning with the starting point
        Set<TileLocation> pipeLocations = new HashSet<>();
        pipeLocations.add(startingPoint);

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
        pipeLocations.add(n.location);

        // navigate the map while counting steps until we loop back to the start
        while (getTileChar(n.location, map) != 'S') {
            n = getNextPipeLocation(n, map);
            pipeLocations.add(n.location);
        }
        return pipeLocations;
    }

    private static char getTileChar(TileLocation l, char[][] map) {
        return map[l.row][l.col];
    }

    private static Tile getPipe(TileLocation l, char[][] map) {
        return tiles.get(getTileChar(l, map));
    }

    private static Move getNextPipeLocation(@NotNull Move from, char[][] map) {
        Direction d = getDirectionOut(from, map);
        TileLocation l = from.location;
        return getMove(l, d, map);
    }

    @Nullable
    private static Move getMove(TileLocation l, Direction d, char[][] map) {
        TileLocation to = l.moveTo(d);
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

    private TileLocation getStartingPoint(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'S') {
                    return new TileLocation(i, j);
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
        // load the pipe map
        char[][] map = readMap(br);
        printMap(map);

        // get all locations in the map
        Set<TileLocation> allMapLocations = getAllMapLocations(map);

        // find the pipe by navigating it, memorizing the location of every part along the way
        TileLocation startingPoint = getStartingPoint(map);
        Set<TileLocation> pipeLocations = getPipeLocations(map, startingPoint);

        // transform every non-pipe location into ground '.'
        Set<TileLocation> nonPipeLocations = new HashSet<>(allMapLocations);
        nonPipeLocations.removeAll(pipeLocations);
        for (TileLocation l : nonPipeLocations) {
            putToMap(map, l, '.');
        }

        // figure out what kind of pipe the starting point is
        transformStartPipe(map, startingPoint);

        printMap(map);

        System.out.println("Number of tiles falling inside the pipe perimeter (line counting):" +
                countInsideTiles(map));
    }

    private void transformStartPipe(char[][] map, TileLocation startingPoint) {
        Set<Direction> startingPointEntries = new HashSet<>();
        for (Direction d : Direction.values()) {
            Move m = getMove(startingPoint, d, map);
            if (m != null && pipeChars.contains(getFromMap(map, m.location))) {
                startingPointEntries.add(d.getOpposite());
            }
        }
        Character startingPipeChar = reverseTileEntryMap.get(startingPointEntries);
        putToMap(map, startingPoint, startingPipeChar);
    }

    private Character getFromMap(char[][] map, TileLocation loc) {
        return map[loc.row][loc.col];
    }

    private void putToMap(char[][] map, TileLocation loc, char c) {
        map[loc.row][loc.col] = c;
    }

    private int countInsideTiles(char[][] map) {
        int insideCount = 0;
        for (char[] chars : map) {
            boolean inside = false;
            Character patternStart = null;
            for (Character c : chars) {
                if (c == '.' && inside) {
                    insideCount += 1;
                } else if (isTransition(c, patternStart)) {
                    inside = !inside;
                    patternStart = null;
                } else if (patternStart == null && isPatternStart(c)) {
                    patternStart = c;
                } else if (isPatternBroken(c, patternStart)) {
                    patternStart = null;
                }
            }
        }
        return insideCount;
    }

    private boolean isTransition(Character c, Character patternStart) {
        return (c == '|') || isPatternComplete(c, patternStart);
    }

    private static boolean isPatternComplete(Character c, Character patternStart) {
        return patternStart != null && (
                (patternStart == 'L' && c == '7' || patternStart == '7' && c == 'L') ||
                (patternStart == 'F' && c == 'J' || patternStart == 'J' && c == 'F')
        );
    }

    private boolean isPatternStart(Character c) {
        return (c == 'F' || c == 'J' || c == 'L' || c == '7');
    }

    private boolean isPatternBroken(Character c, Character patternStart) {
        if (patternStart == null) return false;
        if (c == '|') return true;
        return (patternStart == 'F' && c == '7' || patternStart == 'L' && c == 'J');
    }

    private void printMap(char[][] map) {
        for (char[] chars : map) {
            for (char c : chars) {
                System.out.print(prettyPipeChars.get(c));
            }
            System.out.println();
        }
        System.out.println();
    }

    private @NotNull Set<TileLocation> getAllMapLocations(char[][] map) {
        Set<TileLocation> allMapLocations = new HashSet<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                allMapLocations.add(new TileLocation(i, j));
            }
        }
        return allMapLocations;
    }
}
