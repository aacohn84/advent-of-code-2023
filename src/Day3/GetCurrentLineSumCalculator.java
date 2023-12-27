package Day3;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.lang.Character.isDigit;

class GetCurrentLineSumCalculator implements LineSumCalculator {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("([0-9]+)");
    public int calculate(String currentLine, String adjLine) {
        return calculate(currentLine, adjLine, null);
    }

    public int calculate(String currentLine, String adjLine1, String adjLine2) {
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

    public int partNumber() {
        return 1;
    }

    private boolean isSymbol(char c) {
        return !(c == '.') && !isDigit(c);
    }

    /*
     * Returns true if there is a symbol adjacent to the number given by currentLine.substring(left, right + 1)
     * The range [left, right] defines the left and right boundaries of a substring of currentLine containing numbers
     * adjLine1 and adjLine2 are the lines immediately above or below currentLine in the file (order doesn't matter)
     */
    private boolean isAdjacentToSymbol(@NotNull String currentLine, String adjLine1, String adjLine2, int left, int right) {
        int min = Math.max(left - 1, 0);
        int max = Math.min(right + 1, currentLine.length() - 1);
        return isBoundaryAdjacentToSymbol(currentLine, min, max) ||
                isAnyInRangeAdjacentToSymbol(adjLine1, min, max) ||
                isAnyInRangeAdjacentToSymbol(adjLine2, min, max);
    }

    // returns true if the characters at min or max are symbols (any character other than 0-9 or '.')
    private boolean isBoundaryAdjacentToSymbol(@NotNull String line, int min, int max) {
        return isSymbol(line.charAt(min)) || isSymbol(line.charAt(max));
    }

    // Returns true if any character between min and max (inclusive) is a symbol
    private boolean isAnyInRangeAdjacentToSymbol(String line, int min, int max) {
        return line != null && IntStream.rangeClosed(min, max)
                .anyMatch(i -> !Character.isDigit(line.charAt(i)) && line.charAt(i) != '.');
    }
}