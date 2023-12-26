package Day5;

import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;

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

    public boolean isSourceRangeOverlappedBy(Range<Long> otherRange) {
        return sourceRange.isOverlappedBy(otherRange);
    }

    public boolean isSourceRangeContainsRange(Range<Long> otherRange) {
        return sourceRange.containsRange(otherRange);
    }

    public Range<Long> getSourceRangeIntersection(Range<Long> otherRange) {
        return sourceRange.intersectionWith(otherRange);
    }

    public long mapToDestVal(long sourceVal) {
        return destRange.getMinimum() + sourceVal - sourceRange.getMinimum();
    }

    public Range<Long> mapToDestRange(@NotNull Range<Long> inputRange) {
        long startOffset = inputRange.getMinimum() - sourceRange.getMinimum();
        long endOffset = inputRange.getMaximum() - sourceRange.getMinimum();
        long destRangeMinimum = destRange.getMinimum() + startOffset;
        long destRangeMaximum = destRange.getMinimum() + endOffset;
        return Range.of(destRangeMinimum, destRangeMaximum);
    }

    /**
     * Returns the remainder of intersection between otherRange and the source range
     */
    public RangeList getSourceRangeIntersectionRemainder(@NotNull Range<Long> otherRange) {
        RangeList remainder = new RangeList();
        if (otherRange.containsRange(sourceRange)) {
            // case 1: otherRange contains sourceRange
            // the remainder is 2 ranges: the range to the left of sourceRange, and the range to the right
            remainder.add(getRemainderRangeLeft(otherRange));
            remainder.add(getRemainderRangeRight(otherRange));
        } else if (sourceRange.isOverlappedBy(otherRange)) {
            if (otherRange.getMinimum() < sourceRange.getMinimum()) {
                // case 2: the end of otherRange overlaps with the beginning of sourceRange
                // the remainder is the part of otherRange to the left of sourceRange
                remainder.add(getRemainderRangeLeft(otherRange));
            } else {
                // case 3: the beginning of otherRange overlaps with the end of SourceRange
                // the remainder is the part of otherRange to the right of sourceRange
                remainder.add(getRemainderRangeRight(otherRange));
            }
        }
        return remainder;
    }

    private Range<Long> getRemainderRangeRight(@NotNull Range<Long> otherRange) {
        long remainderRightMin = sourceRange.getMaximum() + 1;
        long remainderRightMax = otherRange.getMaximum();
        return Range.of(remainderRightMin, remainderRightMax);
    }

    private Range<Long> getRemainderRangeLeft(@NotNull Range<Long> otherRange) {
        long remainderLeftMin = otherRange.getMinimum();
        long remainderLeftMax = sourceRange.getMinimum() - 1;
        return Range.of(remainderLeftMin, remainderLeftMax);
    }
}
