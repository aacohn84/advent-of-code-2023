package Day1;

import AoC23.AoC23Day;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 implements AoC23Day {
    private static final HashMap<String, Integer> wordToIntMap = buildWordToDigitMap();
    private static final Pattern digitsAndWordsPattern = Pattern.compile("\\d|one|two|three|four|five|six|seven|eight|nine");
    private static final Pattern digitsPattern = Pattern.compile("\\d");
    private final String filename;

    private static @NotNull HashMap<String, Integer> buildWordToDigitMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);
        map.put("six", 6);
        map.put("seven", 7);
        map.put("eight", 8);
        map.put("nine", 9);
        return map;
    }

    public Day1(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        Path filepath = Paths.get(filename);
        if (Files.exists(filepath)) {
            part1();
            part2();
        } else {
            System.out.println("Couldn't find this file: " + filename);
        }
    }

    private void part1() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int sum = br.lines().mapToInt(line -> {
                Matcher m = digitsPattern.matcher(line);
                int d1 = m.find() ? getDigit(m.group()) : 0;
                String last = null;
                while (m.find()) {
                    last = m.group();
                }
                int d2 = (last != null) ? getDigit(last) : d1;
                return (10 * d1) + d2;
            }).sum();
            System.out.println("Part 1 - The sum is:" + sum);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void part2() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int sum = br.lines().mapToInt(line -> {
                int d1, d2;
                if (line.length() == 1) {
                    d1 = d2 = Character.getNumericValue(line.charAt(0));
                } else {
                    d1 = getFirstDigit(line).orElseThrow(() -> new RuntimeException("Invalid input"));
                    d2 = getLastDigit(line).orElseThrow(() -> new RuntimeException("Invalid input"));
                }
                return (10 * d1) + d2;
            }).sum();
            System.out.println("Part 2 - The sum is:" + sum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static Optional<Integer> getFirstDigit(String line) {
        Matcher m = digitsAndWordsPattern.matcher(line);
        return m.find() ? Optional.of(getDigit(m.group())) : Optional.empty();
    }

    private static Optional<Integer> getLastDigit(@NotNull String line) {
        String last = null;
        for (int i = line.length() - 1; i >= 0; i--) {
            Matcher m2 = digitsAndWordsPattern.matcher(line.substring(i));
            if (m2.find()) {
                last = m2.group();
                break;
            }
        }
        return (last != null) ? Optional.of(getDigit(last)) : Optional.empty();
    }

    private static int getDigit(@NotNull String s) {
        if (digitsPattern.matcher(s).matches()) {
            return Character.getNumericValue(s.charAt(0));
        } else {
            return wordToIntMap.get(s);
        }
    }
}