package AoC23;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AoC23Day {
    protected final Path part1Filepath;
    protected final Path part2Filepath;

    public void run() {
        runPart1();
        runPart2();
    }

    public AoC23Day(String filename) {
        this.part1Filepath = Paths.get(filename);
        this.part2Filepath = part1Filepath;
    }

    public AoC23Day(String part1Filename, String part2Filename) {
        this.part1Filepath = Paths.get(part1Filename);
        this.part2Filepath = Paths.get(part2Filename);
    }

    private void runPart1() {
        try (BufferedReader br = Files.newBufferedReader(part1Filepath)) {
            part1(br);
        } catch (NoSuchFileException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected abstract void part1(BufferedReader br) throws IOException;

    private void runPart2() {
        try (BufferedReader br = Files.newBufferedReader(part2Filepath)) {
            part2(br);
        } catch (NoSuchFileException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected abstract void part2(BufferedReader br) throws IOException;
}
