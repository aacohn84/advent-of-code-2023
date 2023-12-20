package AoC23;

public abstract class AoC23Day {
    protected final String filename;

    public abstract void run();

    public AoC23Day(String filename) {
        this.filename = filename;
    }
}
