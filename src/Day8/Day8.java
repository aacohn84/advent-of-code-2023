package Day8;

import AoC23.AoC23Day;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 extends AoC23Day {
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("[A-Z]+");

    public Day8(String filename) {
        super(filename);
    }

    @Override
    protected void part1(@NotNull BufferedReader br) throws IOException {
        // record the directions
        String directions = br.readLine();
        br.readLine(); // discard the blank line after the directions

        // create the network with the first node
        Network n = new Network();
        String nodeLine;
        while ((nodeLine = br.readLine()) != null && !nodeLine.isEmpty()) {
            String[] lineParts = parseNodeLine(nodeLine);
            n.addNode(lineParts[0], lineParts[1], lineParts[2]);
        }
        n.setStartNode("AAA");

        // navigate the network
        int steps = n.navigate(directions, "ZZZ");
        System.out.println("Part 1 step count: " + steps);
    }

    String[] parseNodeLine(String nodeLine) {
        Matcher m = ADDRESS_PATTERN.matcher(nodeLine);
        String address = null, left = null, right = null;
        if (m.find()) address = m.group();
        if (m.find()) left = m.group();
        if (m.find()) right = m.group();
        if (address != null && left != null && right != null) {
            return new String[] { address, left, right };
        } else {
            throw new RuntimeException("Error reading the file.");
        }
    }

    @Override
    protected void part2(BufferedReader br) throws IOException {

    }
}
