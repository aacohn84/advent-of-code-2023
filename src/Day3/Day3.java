package Day3;

import AoC23.AoC23Day;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.lang.Character.isDigit;

public class Day3 extends AoC23Day {

    private static final Pattern GEAR_PATTERN = Pattern.compile("\\*");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("([0-9]+)");

    public Day3(String filename) {
        super(filename);
    }

    @Override
    public void run() {
        part1();
        part2();
    }

    private void part1() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            int sum = 0;
            // Stage 1 -- start at beginning of file (2 lines: first line and the line below it)
            // sum any of top line's digits that are adjacent to a symbol on either line
            String currentLine = br.readLine();
            String belowLine = br.readLine();
            sum += getCurrentLineSum(currentLine, belowLine);

            // Stage 2 -- move to middle of file (3 lines: current line, and the lines above and below it)
            String aboveLine = currentLine;
            currentLine = belowLine;
            while ((belowLine = br.readLine()) != null) {
                // sum any of current line's digits that are adjacent to a symbol on any of the 3 lines
                sum += getCurrentLineSum(currentLine, aboveLine, belowLine);

                // shift down one more line
                aboveLine = currentLine;
                currentLine = belowLine;
            }

            // Stage 3 -- move to end of the file (last line and the line above it)
            // sum any of bottom line's digits that are adjacent to a symbol on itself or the line above
            sum += getCurrentLineSum(currentLine, aboveLine);
            System.out.println("Part 1 sum: " + sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void part2() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            int sum = 0;
            // Stage 1 -- start at beginning of file, sum any gear ratios found between the first two lines
            String currentLine = br.readLine();
            String belowLine = br.readLine();
            sum += getGearRatioSum(currentLine, belowLine);

            // Stage 2 -- move to middle of file (3 lines: current line, and the lines above and below it)
            String aboveLine = currentLine;
            currentLine = belowLine;
            while ((belowLine = br.readLine()) != null) {
                // sum any gear ratios found on the 3 lines
                sum += getGearRatioSum(currentLine, aboveLine, belowLine);

                // shift down one more line
                aboveLine = currentLine;
                currentLine = belowLine;
            }

            // Stage 3 -- move to end of the file (last line and the line above it)
            // sum any gear ratios found between the last 2 lines
            sum += getGearRatioSum(currentLine, aboveLine);
            System.out.println("Part 2 sum: " + sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getCurrentLineSum(String currentLine, String adjLine) {
        return getCurrentLineSum(currentLine, adjLine, null);
    }
    private static int getCurrentLineSum(String currentLine, String adjLine1, String adjLine2) {
        int currentLineSum = 0;
        Matcher numberMatcher = NUMBER_PATTERN.matcher(currentLine);
        while (numberMatcher.find()) {
            int left = numberMatcher.start(1);
            int right = numberMatcher.end(1) - 1;
            if (isAdjacentToSymbol(currentLine, adjLine1, adjLine2, left, right)) {
                currentLineSum += Integer.parseInt(numberMatcher.group(1));
            }
        }
        return currentLineSum;
    }

    private static boolean isSymbol(char c) {
        return !(c == '.') && !isDigit(c);
    }

    /*
     * Returns true if there is a symbol adjacent to the number given by currentLine.substring(left, right + 1)
     * The range [left, right] defines the left and right boundaries of a substring of currentLine containing numbers
     * adjLine1 and adjLine2 are the lines immediately above or below currentLine in the file (order doesn't matter)
     */
    private static boolean isAdjacentToSymbol(@NotNull String currentLine, String adjLine1, String adjLine2, int left, int right) {
        int min = Math.max(left - 1, 0);
        int max = Math.min(right + 1, currentLine.length() - 1);
        return isBoundaryAdjacentToSymbol(currentLine, min, max) ||
                isAnyInRangeAdjacentToSymbol(adjLine1, min, max) ||
                isAnyInRangeAdjacentToSymbol(adjLine2, min, max);
    }

    // returns true if the characters at min or max are symbols (any character other than 0-9 or '.')
    private static boolean isBoundaryAdjacentToSymbol(@NotNull String line, int min, int max) {
        return isSymbol(line.charAt(min)) || isSymbol(line.charAt(max));
    }

    // Returns true if any character between min and max (inclusive) is a symbol
    private static boolean isAnyInRangeAdjacentToSymbol(String line, int min, int max) {
        return line != null && IntStream.rangeClosed(min, max)
                .anyMatch(i -> !Character.isDigit(line.charAt(i)) && line.charAt(i) != '.');
    }

    private static int getGearRatioSum(@NotNull String currentLine, @NotNull String adjLine) {
        return getGearRatioSum(currentLine, adjLine, null);
    }

    private static int getGearRatioSum(@NotNull String currentLine, @NotNull String adjLine1, String adjLine2) {
        int gearRatioSum = 0;
        Matcher gearMatcher = GEAR_PATTERN.matcher(currentLine);
        while (gearMatcher.find()) {
            int gearIdx = gearMatcher.start();
            List<Integer> partNumbers = new ArrayList<>(getAdjacentPartNumbers(currentLine, gearIdx, true));
            partNumbers.addAll(getAdjacentPartNumbers(adjLine1, gearIdx, false));
            if (adjLine2 != null) {
                partNumbers.addAll(getAdjacentPartNumbers(adjLine2, gearIdx, false));
            }
            if (partNumbers.size() == 2) {
                gearRatioSum += partNumbers.get(0) * partNumbers.get(1);
            }
        }
        return gearRatioSum;
    }

    /*
     * Returns a string containing the adjacent integer values to the left of the gear index (gearIdx).
     * Returns empty string if no digits are found adjacent to the gear index.
     */
    private static @NotNull String getDigitsLeft(@NotNull String line, int gearIdx) {
        if (gearIdx > 0) {
            // search for integers to the left of the gear
            if (isDigit(line.charAt(gearIdx - 1))) {
                int leftMost = gearIdx - 1;
                while (leftMost > 0 && isDigit(line.charAt(leftMost - 1))) {
                    leftMost = leftMost - 1;
                }
                return line.substring(leftMost, gearIdx);
            }
        }
        return "";
    }

    /*
     * Returns a string containing the adjacent integer values to the right of the gear index (gearIdx).
     * Returns empty string if no digits are found adjacent to the gear index.
     */
    private static @NotNull String getDigitsRight(@NotNull String line, int gearIdx) {
        if (gearIdx < line.length() - 1) {
            // search for integers to the right of the gear
            if (isDigit(line.charAt(gearIdx + 1))) {
                int leftMost = gearIdx + 1;
                int rightMost = leftMost + 1;
                while (rightMost < line.length() && isDigit(line.charAt(rightMost))) {
                    rightMost = rightMost + 1;
                }
                return line.substring(leftMost, rightMost);
            }
        }
        return "";
    }

    /*
     * Returns the part numbers adjacent to the gear indexed by gearIdx.
     * Ex: Searching on the line that contains the gear (isGearLine == true)
     *      ...123*456...
     *     Result: [123, 456]
     *
     * Ex: Searching on line that does NOT contain the gear (isGearLine == false)
     *      ...123456...        ...12.345               ..12.....
     *      .....*......        .....*...               ....*....
     *     Result: [123456]     Result: [12, 345]       Result: [12]
     */
    private static @NotNull List<Integer> getAdjacentPartNumbers(@NotNull String line, int gearIdx, boolean isGearLine) {
        List<Integer> partNumbers = new ArrayList<>();
        String digitsLeft = getDigitsLeft(line, gearIdx);
        String digitsRight = getDigitsRight(line, gearIdx);
        char middleChar = line.charAt(gearIdx);
        if (!isGearLine && isDigit(middleChar)) {
            partNumbers.add(Integer.parseInt(digitsLeft + middleChar + digitsRight));
        } else {
            if (!digitsLeft.isEmpty()) {
                partNumbers.add(Integer.parseInt(digitsLeft));
            }
            if (!digitsRight.isEmpty()) {
                partNumbers.add(Integer.parseInt(digitsRight));
            }
        }
        return partNumbers;
    }
}
