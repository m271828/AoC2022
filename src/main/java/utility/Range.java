package utility;

public class Range {
    private int start, end;

    public Range(int start, int end) {
        this.start = Math.min(start, end);
        this.end = Math.max(start, end);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLength() {
        return end-start+1;
    }

    public boolean canMarge(Range other) {
        if (other.start <= start && start <= other.end) {
            return true;
        } else if (other.start <= end && end <= other.end) {
            return true;
        } else if (start <= other.start && other.start <= end) {
            return true;
        } else if (start <= other.end && other.end <= end) {
            return true;
        } else if (start == other.end + 1) {
            return true;
        } else if (end + 1 == other.start) {
            return true;
        } else {
            return false;
        }
    }

    public void merge(Range other) {
        start = Math.min(start, other.start);
        end = Math.max(end, other.end);
    }
}
