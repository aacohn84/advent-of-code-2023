package Day2;

import AoC23.AoC23Day;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 extends AoC23Day {
    private static final HashMap<String, Integer> colorLimitsMap = getColorCountMap(12, 13, 14);

    public Day2(String filename) {
        super(filename);
    }

    private static HashMap<String, Integer> getColorCountMap(int redCount, int greenCount, int blueCount) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("red", redCount);
        map.put("green", greenCount);
        map.put("blue", blueCount);
        return map;
    }

    private static final Pattern gameNumberPattern = Pattern.compile("^Game (\\d+):");
    private static final Pattern colorCountPattern = Pattern.compile("\\d+ [a-z]+");

    @Override
    protected void part1(BufferedReader br) {
        int sum = br.lines().mapToInt(line -> {
            Matcher gameNumberMatcher = gameNumberPattern.matcher(line);
            String gameNumberStr = gameNumberMatcher.find() ? gameNumberMatcher.group(1) : null;
            if (gameNumberStr == null) {
                throw new RuntimeException("Unexpected input encountered");
            }
            int gameNumber = Integer.parseInt(gameNumberStr);
            Matcher colorCountMatcher = colorCountPattern.matcher(line);
            while (colorCountMatcher.find()) {
                String[] countAndColor = colorCountMatcher.group().split(" ");
                int count = Integer.parseInt(countAndColor[0]);
                String color = countAndColor[1];
                if (count > colorLimitsMap.get(color)) {
                    return 0;
                }
            }
            return gameNumber;
        }).sum();
        System.out.println("Part 1 sum: " + sum);
    }

    @Override
    protected void part2(BufferedReader br) {
        int sum = br.lines().mapToInt(line -> {
            Map<String, Integer> colorMaxMap = getColorCountMap(0, 0, 0);
            Matcher colorCountMatcher = colorCountPattern.matcher(line);
            while (colorCountMatcher.find()) {
                String[] countAndColor = colorCountMatcher.group().split(" ");
                int count = Integer.parseInt(countAndColor[0]);
                String color = countAndColor[1];
                colorMaxMap.put(color, Math.max(colorMaxMap.get(color), count));
            }
            return colorMaxMap.values().stream().reduce(1, (acc, colorMax) -> acc * colorMax);
        }).sum();
        System.out.println("Part 2 sum: " + sum);
    }
}