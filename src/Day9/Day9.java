package Day9;

import AoC23.AoC23Day;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day9 extends AoC23Day {
    public Day9(String filename) {
        super(filename);
    }

    @Override
    protected void part1(@NotNull BufferedReader br) throws IOException {
        int sum = 0;
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            // each line represents a sequence. for each sequence, we'll find the next value and add it to the sum.
            // we start by recording the sequence into a list
            String[] values = line.split("\\s+");
            List<Integer> sequence = new ArrayList<>();
            for (String value : values) {
                sequence.add(parseVal(value));
            }
            // for each PAIR of values, we need to get the difference and store that in a sub-sequence.
            // if that sequence is all zeroes, then we can move on to the next step, otherwise we have to create another
            // subsequence
            List<Integer> lasts = new ArrayList<>(); // the last value of each sequence
            lasts.add(sequence.get(sequence.size() - 1)); // add the last value of the main sequence
            List<Integer> subsequence;
            do {
                subsequence = new ArrayList<>();
                int seqLen = sequence.size();
                for (int i = 0, j = 1; j < seqLen; i++, j++) {
                    int iVal = sequence.get(i);
                    int jVal = sequence.get(j);
                    subsequence.add(jVal - iVal);
                }
                lasts.add(subsequence.get(subsequence.size() - 1));
                sequence = new ArrayList<>(subsequence);
            } while (!subsequence.stream().allMatch(x -> x == 0));
            int seqNext = lasts.stream().mapToInt(Integer::intValue).sum();
            sum += seqNext;
        }
        System.out.println("Part 1 sum: " + sum);
    }

    private int parseVal(@NotNull String val) {
        return val.contains("-") ? Integer.parseInt(val.substring(1)) * -1 : Integer.parseInt(val);
    }

    @Override
    protected void part2(@NotNull BufferedReader br) throws IOException {
        int sum = 0;
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            // each line represents a sequence. for each sequence, we'll find the next value and add it to the sum.
            // we start by recording the sequence into a list
            String[] values = line.split("\\s+");
            List<Integer> sequence = new ArrayList<>();
            for (String value : values) {
                sequence.add(parseVal(value));
            }
            // for each PAIR of values, we need to get the difference and store that in a sub-sequence.
            // if that sequence is all zeroes, then we can move on to the next step, otherwise we have to create another
            // subsequence
            List<Integer> firsts = new ArrayList<>(); // the last value of each sequence
            firsts.add(sequence.get(0)); // add the last value of the main sequence
            List<Integer> subsequence;
            do {
                subsequence = new ArrayList<>();
                int seqLen = sequence.size();
                for (int i = 0, j = 1; j < seqLen; i++, j++) {
                    int iVal = sequence.get(i);
                    int jVal = sequence.get(j);
                    subsequence.add(jVal - iVal);
                }
                firsts.add(subsequence.get(0));
                sequence = new ArrayList<>(subsequence);
            } while (!subsequence.stream().allMatch(x -> x == 0));
            int seqNext = 0;
            for (int i = firsts.size() - 2; i >= 0; i--) {
                seqNext = firsts.get(i) - seqNext;
            }
            sum += seqNext;
        }
        System.out.println("Part 2 sum: " + sum);
    }
}
