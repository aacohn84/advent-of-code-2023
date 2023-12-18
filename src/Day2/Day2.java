package Day2;

import AoC23.AoC23Day;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 implements AoC23Day {
    private final String filename;
    private static final HashMap<String, Integer> colorLimitsMap = getPart1ColorLimits();

    private static HashMap<String, Integer> getPart1ColorLimits() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("red", 12);
        map.put("green", 13);
        map.put("blue", 14);
        return map;
    }

    private static final Pattern gameNumberPattern = Pattern.compile("^Game (\\d+):");
    private static final Pattern part1ColorCountPattern = Pattern.compile("\\d+ [a-z]+");

    public Day2(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        Path filepath = Paths.get(filename);
        if (Files.exists(filepath)) {
            part1();
        } else {
            System.out.println("Couldn't find this file: " + filename);
        }
    }

    private void part1() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int sum = br.lines().mapToInt(line -> {
                Matcher gameNumberMatcher = gameNumberPattern.matcher(line);
                String gameNumberStr = gameNumberMatcher.find() ? gameNumberMatcher.group(1) : null;
                if (gameNumberStr == null) {
                    throw new RuntimeException("Unexpected input encountered");
                }
                int gameNumber = Integer.parseInt(gameNumberStr);
                Matcher part1ColorCountMatcher = part1ColorCountPattern.matcher(line);
                boolean limitExceeded = false;
                while (part1ColorCountMatcher.find()) {
                    String[] countAndColor = part1ColorCountMatcher.group().split(" ");
                    int count = Integer.parseInt(countAndColor[0]);
                    String color = countAndColor[1];
                    if (count > colorLimitsMap.get(color)) {
                        limitExceeded = true;
                        break;
                    }
                }
                return !limitExceeded ? gameNumber : 0;
            }).sum();
            System.out.println("Part 1 sum: " + sum);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
