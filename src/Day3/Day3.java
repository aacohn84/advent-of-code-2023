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

    private static final Pattern SYMBOL_PATTERN = Pattern.compile("([^.0-9])");

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
            String currentLine = br.readLine();
            String belowLine = br.readLine();
            Matcher symbolMatcher = SYMBOL_PATTERN.matcher(currentLine);
            while (symbolMatcher.find()) {
                int i = symbolMatcher.start(1);
                sum += getLineSum(currentLine, i, true);
                sum += getLineSum(belowLine, i, false);
            }

            String aboveLine = null;
            while ((currentLine = br.readLine()) != null) {
                symbolMatcher = SYMBOL_PATTERN.matcher(currentLine);
                while (symbolMatcher.find()) {
                    int i = symbolMatcher.start(1);

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Returns a string containing the adjacent integer values to the left of the symbol index (symIdx).
     * Returns empty string if no digits are found adjacent to the symbol index.
     */
    private @NotNull String getDigitsLeft(@NotNull String line, int symIdx) {
        if (symIdx > 0) {
            // search for integers to the left of the symbol
            if (isDigit(line.charAt(symIdx - 1))) {
                int leftBound = symIdx - 1;
                while (leftBound - 1 > 0 && isDigit(line.charAt(leftBound - 1))) {
                    leftBound = leftBound - 1;
                }
                return line.substring(leftBound, symIdx);
            }
        }
        return "";
    }

    /*
     * Returns a string containing the adjacent integer values to the right of the symbol index (symIdx).
     * Returns empty string if no digits are found adjacent to the symbol index.
     */
    private @NotNull String getDigitsRight(@NotNull String line, int symIdx) {
        if (symIdx < line.length() - 1) {
            // search for integers to the right of the symbol
            if (isDigit(line.charAt(symIdx + 1))) {
                int leftBound = symIdx + 1;
                int rightBound = leftBound + 1;
                while (rightBound < line.length() && isDigit(line.charAt(rightBound))) {
                    rightBound = rightBound + 1;
                }
                return line.substring(leftBound, rightBound);
            }
        }
        return "";
    }

    /*
     * Returns the total value of numbers adjacent to the index of the symbol (symIdx).
     * Ex: Symbol is on the same line as the numbers (sameLine == true)
     *      ...123@456...
     *     Result: 123 + 456 = 579
     *
     * Ex: Symbol is NOT on the same line as the numbers (sameLine == false)
     *      ...123456...        ...12.345               ..12.....
     *      .....@......        .....@...               ....@....
     *     Result: 123456      Result: 12 + 345 = 357  Result: 12
     */
    private int getLineSum(@NotNull String line, int symIdx, boolean sameLine) {
        String digitsLeft = getDigitsLeft(line, symIdx);
        String digitsRight = getDigitsRight(line, symIdx);
        if (!sameLine) {
            char middleChar = line.charAt(symIdx);
            if (isDigit(middleChar)) {
                return Integer.parseInt(digitsLeft + middleChar + digitsRight);
            }
        }
        int intLeft = digitsLeft.isEmpty() ? 0 : Integer.parseInt(digitsLeft);
        int intRight = digitsRight.isEmpty() ? 0 : Integer.parseInt(digitsRight);

        return intLeft + intRight;
    }
}
