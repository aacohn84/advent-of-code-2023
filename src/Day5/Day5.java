package Day5;

import AoC23.AoC23Day;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 extends AoC23Day {

    public Day5(String filename) {
        super(filename);
    }

    @Override
    public void run() {
        part1();
    }

    private void part1() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            long minLocationVal = Long.MAX_VALUE;
            List<Long> seeds = parseSeedsLine(br.readLine()); // first line contains the seeds list
            br.readLine(); // skip the second line, which is blank
            Map<GardenCategory, List<RangePair>> rangesByCategory = getSourceToDestRangeMappings(br);
            for (long seed : seeds) {
                long transformVal = seed;
                for (GardenCategory category : GardenCategory.values()) {
                    for (RangePair r : rangesByCategory.get(category)) {
                        if (r.inSourceRange(transformVal)) {
                            transformVal = r.getDestVal(transformVal);
                            break;
                        }
                    }
                }
                minLocationVal = Math.min(minLocationVal, transformVal);
            }
            System.out.println("Minimum location value: " + minLocationVal);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static @NotNull Map<GardenCategory, List<RangePair>> getSourceToDestRangeMappings(BufferedReader br)
            throws IOException {
        Map<GardenCategory, List<RangePair>> rangesByCategory = new HashMap<>();
        for (GardenCategory category : GardenCategory.values()) {
            List<String> rangeLines = new ArrayList<>();
            br.readLine(); // skip past the header line

            // read each range line
            String rangeLine;
            while ((rangeLine = br.readLine()) != null && !rangeLine.isEmpty()) {
                rangeLines.add(rangeLine);
            }
            // create dest/src range pairs and map to the garden category
            rangesByCategory.put(category, getRangePairs(rangeLines));
        }
        return rangesByCategory;
    }

    private static List<RangePair> getRangePairs(@NotNull List<String> rangeLines) {
        return rangeLines.stream().map(rangeLine -> {
            long[] rangeValues = parseRangeLine(rangeLine);
            long destRangeStart = rangeValues[0];
            long sourceRangeStart = rangeValues[1];
            long rangeLength = rangeValues[2];
            Range<Long> destRange = getRange(destRangeStart, rangeLength);
            Range<Long> sourceRange = getRange(sourceRangeStart, rangeLength);
            return new RangePair(destRange, sourceRange);
        }).collect(Collectors.toList());
    }

    private static long[] parseRangeLine(@NotNull String rangeLine) {
        return Arrays.stream(rangeLine.trim().split("\\s+")).mapToLong(Long::parseLong).toArray();
    }

    private static @NotNull Range<Long> getRange(long rangeStart, long rangeLength) {
        return Range.of(rangeStart, rangeStart + rangeLength - 1);
    }

    private static List<Long> parseSeedsLine(@NotNull String seedsLine) {
        String[] seeds = seedsLine.split(":")[1].trim().split("\\s+");
        return Arrays.stream(seeds).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
    }
}
