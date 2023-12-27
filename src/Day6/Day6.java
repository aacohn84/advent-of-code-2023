package Day6;

import AoC23.AoC23Day;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day6 extends AoC23Day {
    public Day6(String filename) {
        super(filename);
    }

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    @Override
    protected void part1(@NotNull BufferedReader br) throws IOException {
        String timeLine = br.readLine();
        String distanceLine = br.readLine();
        Matcher timeMatcher = NUMBER_PATTERN.matcher(timeLine);
        Matcher distanceMatcher = NUMBER_PATTERN.matcher(distanceLine);
        long product = 1;
        while (timeMatcher.find() && distanceMatcher.find()) {
            int timeAllowed = Integer.parseInt(timeMatcher.group());
            int distance = Integer.parseInt(distanceMatcher.group());
            long waysToWin = getWaysToWin(timeAllowed, distance);
            product *= waysToWin;
        }
        System.out.println("Part 1 product: " + product);
    }

    protected void part2(@NotNull BufferedReader br) throws IOException {
        long timeAllowed = Long.parseLong(parsePart2Line(br.readLine()));
        long distance = Long.parseLong(parsePart2Line(br.readLine()));
        long waysToWin = getWaysToWin(timeAllowed, distance);
        System.out.println("Part 2 product: " + waysToWin);
    }

    private String parsePart2Line(@NotNull String s) {
        String[] numberStrs = s.split(":")[1].trim().split("\\s+");
        return Stream.of(numberStrs).reduce("", String::concat);
    }

    /*
     * Calculates the distance the boat can go depending on how long the button is pressed and the duration of the race.
     * Ex: time = 7
     * If timePressed = 3, then the boat has a speed of 3, and it travels for 4 seconds, so distance = 3*4 = 12
     */
    private double getDistance(double timePressed, double timeAllowed) {
        return timePressed * (timeAllowed - timePressed);
    }

    /*
     * Returns the number of choices for how long to hold down the button and while beating the given distance in the
     * allowed time.
     *
     * This function is the result of taking the Distance function and solving it for timePressed. That is, given the
     * distance and time allowed, we want to know how much time the button was pressed.
     *
     * In most cases, there are 2 solutions, a lower one and a higher one.
     * Ex: Given time = 7, there are 2 values of timePressed that would allow the boat to reach distance = 9
     *
     * Using the quadratic formula, we get t1 = (7 - 3.6056)/2 = 1.6972, and t2 = (7 + 3.6056)/2 = 5.3028
     *
     * Those are the exact solutions for distance == 9, but we want the number of ways to get distance > 9, and for our
     * inputs, the value of timePressed must always be an integer.
     *
     * So the number of ways to beat the given distance is equal to the number of integer values between t1 and t2.
     *
     * Looking at the same example, where t1 = 1.6972, and t2 = 5.3028, the integers between them are: 2, 3, 4, 5
     * So the number of ways to win in that case is 4.
     *
     * To get this number mathematically:
     *      we take floor(t1) + 1 -- this gets us the nearest integer to the right of t1,
     *  and we take  ceil(t2) - 1 -- this gets us the nearest integer to the left of t2.
     *
     * In the case above, t1 = 1.6972, floor(1.6972) + 1 = 2.
     *                    t2 = 5.3028, ceil(5.3028) - 1 = 5.
     *
     * To get the number of integer values from t1 to t2 (inclusive), we take (t2 - t1) + 1.
     * This method works even if t1 and t2 are integers.
     *
     * Ex: consider the case when time allowed = 30 and distance = 200
     * t1 = 10 and t2 = 20
     * The integers between 10 and 20 them are 11 to 19
     * floor(t1) + 1 = 11, and ceil(t2) - 1 = 19.
     * 19 - 11 + 1 = 9, so there are 9 ways to get a distance > 200.
     *
     * Now let's take care of an edge case. In the case where distance is maximal, we return 0.
     * Ex: time = 30, distance = 225
     *
     * In this case, 225 is the greatest distance the boat can travel when time = 30
     * the number of ways to get a distance > 225 is 0.
     */
    private static long getWaysToWin(double timeAllowed, double distance) {
        double sqrt = Math.sqrt(timeAllowed * timeAllowed - 4 * distance);
        if (sqrt == 0) {
            return 0;
        }
        int t1 = (int) Math.floor((timeAllowed - sqrt) / 2) + 1;
        int t2 = (int) Math.ceil((timeAllowed + sqrt) / 2) - 1;
        return t2 - t1 + 1;
    }
}
