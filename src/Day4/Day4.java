package Day4;

import AoC23.AoC23Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 extends AoC23Day {

    public Day4(String filename) {
        super(filename);
    }

    @Override
    public void run() {
        part1();
        part2();
    }

    private void part1() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            int sum = br.lines().mapToInt(line -> {
                String[] parsedCardLine = parseCardLine(line);
                Set<Integer> winningNumbers = getNumbersSet(parsedCardLine[1]);
                Set<Integer> numbersYouHave = getNumbersSet(parsedCardLine[2]);
                winningNumbers.retainAll(numbersYouHave);
                return (int) Math.pow(2, winningNumbers.size() - 1);
            }).sum();
            System.out.println("Part 1 sum: " + sum);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void part2() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            // copyCountsMap is a map of each line number to the count of times the line has been copied
            Map<Integer, Integer> copyCountsMap = new HashMap<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parsedCardLine = parseCardLine(line);
                int cardNumber = Integer.parseInt(parsedCardLine[0]);

                // If current line is not in the map, give it a count of 1, else add 1 to its count
                Integer currentLineCopyCountOld = copyCountsMap.putIfAbsent(cardNumber, 1);
                if (currentLineCopyCountOld != null) {
                    copyCountsMap.put(cardNumber, currentLineCopyCountOld + 1);
                }

                // compare the winning numbers to the numbers you have to get the matchCount
                Set<Integer> winningNumbers = getNumbersSet(parsedCardLine[1]);
                Set<Integer> numbersYouHave = getNumbersSet(parsedCardLine[2]);
                winningNumbers.retainAll(numbersYouHave);

                // matchCount is the number of subsequent lines we will copy
                int matchCount = winningNumbers.size();

                // currentLineCopyCount is the number of times we'll copy each of the subsequent lines
                final Integer currentLineCopyCount = copyCountsMap.get(cardNumber);

                // starting from the next line down, iterate through each of the lines to copy
                IntStream.rangeClosed(cardNumber + 1, cardNumber + matchCount).forEach(cardToCopy -> {
                    // if the line to be copied hasn't been copied before, set its copy count directly
                    Integer otherLineCopyCount = copyCountsMap.putIfAbsent(cardToCopy, currentLineCopyCount);
                    if (otherLineCopyCount != null) {
                        // otherwise, add to it
                        copyCountsMap.put(cardToCopy, otherLineCopyCount + currentLineCopyCount);
                    }
                });
            }
            // sum up all the copy counts
            int sum = copyCountsMap.values().stream().mapToInt(Integer::intValue).sum();
            System.out.println("Part 2 sum: " + sum);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Given a string like the following: Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
     * Return an array of 3 strings containing the card number, and the 1st and 2nd sets of numbers:
     * ex: [0]: "1"
     *     [1]: "41 48 83 86 17"
     *     [2]: "83 86  6 31 17  9 48 53"
     */
    private String[] parseCardLine(String line) {
        String[] lists = line.split("\\|");
        String[] cardNumAndWinningNums = lists[0].split(":");
        String cardNumber = cardNumAndWinningNums[0].split("\\s+")[1];
        String winningNumbers = cardNumAndWinningNums[1].trim();
        String numbersYouHave = lists[1].trim();
        return new String[] {cardNumber, winningNumbers, numbersYouHave};
    }

    /*
     * Given a string like the following: 83 86  6 31 17  9 48 53
     * Returns a Set of Integers found within the string.
     */
    private static Set<Integer> getNumbersSet(String numbersString) {
        String[] numbersSplit = numbersString.split("\\s+");
        return Arrays.stream(numbersSplit).mapToInt(Integer::parseInt)
                .boxed().collect(Collectors.toSet());
    }
}
