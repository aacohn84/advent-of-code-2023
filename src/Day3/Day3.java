package Day3;

import AoC23.AoC23Day;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;

public class Day3 extends AoC23Day {

    // private static final Pattern SYMBOL_PATTERN = Pattern.compile("([^.0-9])");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("([0-9]+)");

    public Day3(String filename) {
        super(filename);
    }

    @Override
    public void run() {
        part1();
    }

    private void part1() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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

    private int getCurrentLineSum(String currentLine, String adjLine) {
        return getCurrentLineSum(currentLine, adjLine, null);
    }
    private int getCurrentLineSum(String currentLine, String adjLine1, String adjLine2) {
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

    private boolean isAdjacentToSymbol(@NotNull String currentLine, String adjLine1, String adjLine2, int left, int right) {
        int min = left > 0 ? left - 1 : left;
        int max = right < currentLine.length() - 1 ? right + 1 : right;
        return isAdjacentToSymbolSameLine(currentLine, min, max) ||
                isAdjacentToSymbolOtherLine(adjLine1, min, max) ||
                isAdjacentToSymbolOtherLine(adjLine2, min, max);
    }

    private static boolean isAdjacentToSymbolSameLine(@NotNull String currentLine, int min, int max) {
        return isSymbol(currentLine.charAt(min)) || isSymbol(currentLine.charAt(max));
    }

    private static boolean isAdjacentToSymbolOtherLine(String otherLine, int min, int max) {
        if (otherLine != null) {
            int i = min;
            while (i <= max) {
                if (isSymbol(otherLine.charAt(i))) {
                    return true;
                } else {
                    i = i + 1;
                }
            }
        }
        return false;
    }
}
