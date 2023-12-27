package AoC23;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AoC23Day {
    protected final Path filepath;

    public void run() {
        runPart1();
        runPart2();
    }

    public AoC23Day(String filename) {
        this.filepath = Paths.get(filename);
    }

    private void runPart1() {
        try (BufferedReader br = Files.newBufferedReader(filepath)) {
            part1(br);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected abstract void part1(BufferedReader br) throws IOException;

    private void runPart2() {
        try (BufferedReader br = Files.newBufferedReader(filepath)) {
            part2(br);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected abstract void part2(BufferedReader br) throws IOException;
}
