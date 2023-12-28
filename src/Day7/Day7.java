package Day7;

import AoC23.AoC23Day;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * <a href="https://adventofcode.com/2023/day/7">Advent of Code 2023, Day 7</a>
 */
public class Day7 extends AoC23Day {
    public Day7(String filename) {
        super(filename);
    }

    @Override
    protected void part1(@NotNull BufferedReader br) throws IOException {
        SortedSet<CardBid> cardBids = new TreeSet<>();
        String bidLine;
        while ((bidLine = br.readLine()) != null && !bidLine.isEmpty()) {
            String[] parts = bidLine.trim().split("\\s+");
            String cards = parts[0];
            int bid = Integer.parseInt(parts[1]);
            cardBids.add(new CardBid(cards, bid));
        }
        int i = 0;
        int totalWinnings = 0;
        for (CardBid cb : cardBids) {
            totalWinnings += cb.getBid() * (i + 1);
            i++;
        }
        System.out.println("Part 1 winnings: " + totalWinnings);
    }

    @Override
    protected void part2(BufferedReader br) throws IOException {

    }
}
