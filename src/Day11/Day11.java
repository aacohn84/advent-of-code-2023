package Day11;

import AoC23.AoC23Day;
import AoC23.GridLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day11 extends AoC23Day {

    public Day11(String filename) {
        super(filename);
    }

    @Override
    protected void part1(BufferedReader br) throws IOException {
        System.out.println("Part 1 sum: " + getSum(br, 1));
    }

    private long getSum(BufferedReader br, Integer expansionFactor) throws IOException {
        // Read the galaxy positions
        Map<Integer, Set<GridLocation>> galaxiesByColumn = new HashMap<>();
        int rows = 0, cols = 0;
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            int len = line.length();
            boolean emptyRow = true;
            for (int i = 0; i < len; i++) {
                if (line.charAt(i) == '#') {
                    GridLocation g = new GridLocation(rows, i);
                    Set<GridLocation> galaxiesForCol = galaxiesByColumn.get(i);
                    if (galaxiesForCol == null) {
                        galaxiesByColumn.put(i, new HashSet<>(Collections.singletonList(g)));
                    } else {
                        galaxiesForCol.add(g);
                    }
                    emptyRow = false;
                    cols = Math.max(cols, i); // record the max column number encountered
                }
            }
            rows++;
            if (emptyRow) { // expand the empty rows as we go
                rows += expansionFactor;
            }
        }
        // Once we've read all the galaxies, we can check for empty columns
        // galaxies in subsequent columns need to shift over based on the count of empty columns preceding them
        Set<GridLocation> galaxies = new HashSet<>();
        int timesExpanded = 0;
        for (int i = 0; i <= cols; i++) {
            Set<GridLocation> galaxiesForColumn = galaxiesByColumn.get(i);
            if (galaxiesForColumn == null) {
                timesExpanded += expansionFactor;
            } else {
                for (GridLocation g : galaxiesForColumn) {
                    g.col += timesExpanded;
                    galaxies.add(g);
                }
                galaxiesByColumn.remove(i);
            }
        }
        // Now that we've expanded the space, we can calculate the distance between each pair of galaxies
        long sum = 0;
        while (galaxies.size() > 1) {
            GridLocation first = galaxies.iterator().next();
            galaxies.remove(first);
            for (GridLocation second : galaxies) {
                sum += calculateDistance(first, second);
            }
        }
        return sum;
    }

    // use the standard formula for calculating the magnitude of a vector
    private long calculateDistance(GridLocation first, GridLocation second) {
        return Math.abs(second.col - first.col) + Math.abs(second.row - first.row);
    }

    @Override
    protected void part2(BufferedReader br) throws IOException {
        System.out.println("Part 2 sum: " + getSum(br, 999999));
    }
}
