package Day1;

import AoC23.AoC23Day;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

public class Day1 implements AoC23Day {
    private static final HashMap<String, Integer> wordToIntMap = buildWordToDigitMap();
    private static final Pattern digitsAndWordsPattern = Pattern.compile("\\d|one|two|three|four|five|six|seven|eight|nine");
    private static final Pattern digitsPattern = Pattern.compile("\\d");
    private final String filename;

    private static @NotNull HashMap<String, Integer> buildWordToDigitMap() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("one", 1);
        m.put("two", 2);
        m.put("three", 3);
        m.put("four", 4);
        m.put("five", 5);
        m.put("six", 6);
        m.put("seven", 7);
        m.put("eight", 8);
        m.put("nine", 9);
        return m;
    }

    public Day1(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        part1();
        part2();
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
            System.out.println("The sum is:" + sum);
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
                    d1 = getFirstDigit(line);
                    d2 = getLastDigit(line);
                }
                return (10 * d1) + d2;
            }).sum();
            System.out.println("The sum is:" + sum);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find the file: day1.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getFirstDigit(String line) {
        Matcher m = digitsAndWordsPattern.matcher(line);
        String first = m.find() ? m.group() : null;
        return getDigit(first);
    }

    private static int getLastDigit(@NotNull String line) {
        String last = null;
        for (int i = line.length() - 1; i >= 0; i--) {
            Matcher m2 = digitsAndWordsPattern.matcher(line.substring(i));
            if (m2.find()) {
                last = m2.group();
                break;
            }
        }
        return getDigit(last);
    }

    private static int getDigit(String s) {
        if (digitsPattern.matcher(s).matches()) {
            return Character.getNumericValue(s.charAt(0));
        } else {
            return wordToIntMap.get(s);
        }
    }
}