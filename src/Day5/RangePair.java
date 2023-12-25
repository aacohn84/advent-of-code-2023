package Day5;

import org.apache.commons.lang3.Range;

public class RangePair {
    private final Range<Long> destRange;
    private final Range<Long> sourceRange;

    public RangePair(Range<Long> destRange, Range<Long> sourceRange) {
        this.destRange = destRange;
        this.sourceRange = sourceRange;
    }

    public Range<Long> getDestRange() {
        return destRange;
    }

    public Range<Long> getSourceRange() {
        return sourceRange;
    }

    public boolean inSourceRange(long val) {
        return sourceRange.contains(val);
    }

    public long getDestVal(long sourceVal) {
        return destRange.getMinimum() + sourceVal - sourceRange.getMinimum();
    }
}
