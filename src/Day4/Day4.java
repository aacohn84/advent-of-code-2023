package Day4;

import AoC23.AoC23Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 extends AoC23Day {

    public Day4(String filename) {
        super(filename);
    }

    @Override
    public void run() {
        part1();
    }

    private void part1() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            int sum = br.lines().mapToInt(line -> {
                String[] lists = parseLists(line);
                Set<Integer> winningNumbers = getNumbersSet(lists[0]);
                Set<Integer> numbersYouHave = getNumbersSet(lists[1]);
                winningNumbers.retainAll(numbersYouHave);
                return (int) Math.pow(2, winningNumbers.size() - 1);
            }).sum();
            System.out.println("Part 1 sum: " + sum);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Given a string like the following: Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
     * Return an array of 2 strings containing the 1st and 2nd set of numbers:
     * ex: [0]: "41 48 83 86 17"
     *     [1]: "83 86  6 31 17  9 48 53"
     */
    private String[] parseLists(String line) {
        String[] lists = line.split("\\|");
        String winningNumbers = lists[0].split(":")[1].trim();
        String numbersYouHave = lists[1].trim();
        return new String[] {winningNumbers, numbersYouHave};
    }

    /*
     * Takes a string of the format: 83 86  6 31 17  9 48 53
     * Returns a Set of Integers found within the string.
     */
    private static Set<Integer> getNumbersSet(String numbersString) {
        String[] numbersSplit = numbersString.split("\\s+");
        return Arrays.stream(numbersSplit).mapToInt(Integer::parseInt)
                .boxed().collect(Collectors.toSet());
    }
}
