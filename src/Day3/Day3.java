package Day3;

import AoC23.AoC23Day;

import java.io.BufferedReader;
import java.io.IOException;

public class Day3 extends AoC23Day {
    public Day3(String filename) {
        super(filename);
    }

    @Override
    protected void part1(BufferedReader br) throws IOException {
        calculateSum(br, new GetCurrentLineSumCalculator());
    }

    @Override
    protected void part2(BufferedReader br) throws IOException {
        calculateSum(br, new GetGearRatioSumCalculator());
    }

    private void calculateSum(BufferedReader br, LineSumCalculator calculator) throws IOException {
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
            System.out.println("Part " + calculator.partNumber() + " sum: " + sum);
    }
}
