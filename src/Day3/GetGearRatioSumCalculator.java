package Day3;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;

class GetGearRatioSumCalculator implements LineSumCalculator {
    private static final Pattern GEAR_PATTERN = Pattern.compile("\\*");
    public int calculate(@NotNull String currentLine, @NotNull String adjLine) {
        return calculate(currentLine, adjLine, null);
    }

    public int calculate(@NotNull String currentLine, @NotNull String adjLine1, String adjLine2) {
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

    public int partNumber() {
        return 1;
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