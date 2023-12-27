package Day5;

import AoC23.AoC23Day;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 extends AoC23Day {
    private static class GardenMap extends HashMap<GardenCategory, List<RangePair>> {}

    public Day5(String filename) {
        super(filename);
    }

    @Override
    protected void part1(BufferedReader br) throws IOException {
        long minLocationVal = Long.MAX_VALUE;
        List<Long> seeds = parseSeedsLinePart1(br.readLine()); // first line contains the seeds list
        br.readLine(); // skip the second line, which is blank
        GardenMap rangesByCategory = getSourceToDestRangeMappings(br);
        for (long seed : seeds) {
            long transformVal = seed;
            for (GardenCategory category : GardenCategory.values()) {
                for (RangePair r : rangesByCategory.get(category)) {
                    if (r.inSourceRange(transformVal)) {
                        transformVal = r.mapToDestVal(transformVal);
                        break;
                    }
                }
            }
            minLocationVal = Math.min(minLocationVal, transformVal);
        }
        System.out.println("Part 1 Min Location Value: " + minLocationVal);
    }

    @Override
    protected void part2(BufferedReader br) throws IOException {
        long minLocationVal = Long.MAX_VALUE;
        RangeList seedRanges = parseSeedsLinePart2(br.readLine());
        br.readLine();
        GardenMap gardenMap = getSourceToDestRangeMappings(br);
        for (Range<Long> seedRange : seedRanges) {
            RangeList rangesToTransform = new RangeList();
            rangesToTransform.add(seedRange);
            RangeList transformedRanges = new RangeList();
            for (GardenCategory category : GardenCategory.values()) {
                rangesToTransform.addAll(transformedRanges);
                transformedRanges.clear();
                for (RangePair p : gardenMap.get(category)) {
                    for (int i = 0; i < rangesToTransform.size(); i++) {
                        Range<Long> r = rangesToTransform.get(i);
                        if (p.isSourceRangeContainsRange(r)) {
                            transformedRanges.add(p.mapToDestRange(r));
                            rangesToTransform.remove(r);
                            i--; // the removed range may have been replaced by one coming after it in the list
                        } else if (p.isSourceRangeOverlappedBy(r)) {
                            Range<Long> intersectingRange = p.getSourceRangeIntersection(r);
                            transformedRanges.add(p.mapToDestRange(intersectingRange));
                            rangesToTransform.remove(r);
                            RangeList remainder = p.getSourceRangeIntersectionRemainder(r);
                            rangesToTransform.addAll(remainder);
                            i--; // the removed range may have been replaced by one coming after it in the list
                        }
                    }
                }
            }
            transformedRanges.addAll(rangesToTransform); // capture any unmapped humidity ranges
            long rangeMin = transformedRanges.stream().mapToLong(Range::getMinimum).min().orElse(0);
            minLocationVal = Math.min(minLocationVal, rangeMin);
        }
        System.out.println("Part 2 Min Location Value: " + minLocationVal);
    }

    private static @NotNull GardenMap getSourceToDestRangeMappings(BufferedReader br)
            throws IOException {
        GardenMap rangesByCategory = new GardenMap();
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

    private static List<Long> parseSeedsLinePart1(@NotNull String seedsLine) {
        String[] seeds = seedsLine.split(":")[1].trim().split("\\s+");
        return Arrays.stream(seeds).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
    }

    private @NotNull RangeList parseSeedsLinePart2(@NotNull String seedsLine) {
        String[] seeds = seedsLine.split(":")[1].trim().split("\\s+");
        RangeList seedRanges = new RangeList();
        for (int i = 0; i < seeds.length - 1; i += 2) {
            long seedRangeStart = Long.parseLong(seeds[i]);
            long seedRangeEnd = seedRangeStart + Long.parseLong(seeds[i + 1]) - 1;
            seedRanges.add(Range.of(seedRangeStart, seedRangeEnd));
        }
        return seedRanges;
    }
}
