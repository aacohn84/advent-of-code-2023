package Day3;

import AoC23.AoC23Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day3 extends AoC23Day {
    public Day3(String filename) {
        super(filename);
    }

    @Override
    public void run() {
        part1();
        part2();
    }

    private void part1() {
        calculateSum(new GetCurrentLineSumCalculator());
    }

    private void part2() {
        calculateSum(new GetGearRatioSumCalculator());
    }

    private void calculateSum(LineSumCalculator calculator) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            int sum = 0;
            // Stage 1 -- start at beginning of file (2 lines: first line and the line below it)
            // sum any of top line's digits that are adjacent to a symbol on either line
            String currentLine = br.readLine();
            String belowLine = br.readLine();
            sum += calculator.calculate(currentLine, belowLine);

            // Stage 2 -- move to middle of file (3 lines: current line, and the lines above and below it)
            String aboveLine = currentLine;
            currentLine = belowLine;
            while ((belowLine = br.readLine()) != null) {
                // sum any of current line's digits that are adjacent to a symbol on any of the 3 lines
                sum += calculator.calculate(currentLine, aboveLine, belowLine);

                // shift down one more line
                aboveLine = currentLine;
                currentLine = belowLine;
            }

            // Stage 3 -- move to end of the file (last line and the line above it)
            // sum any of bottom line's digits that are adjacent to a symbol on itself or the line above
            sum += calculator.calculate(currentLine, aboveLine);
            System.out.println("Part 1 sum: " + sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
