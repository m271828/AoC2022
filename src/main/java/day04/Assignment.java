package day04;

public class Assignment {
    private Integer start;
    private Integer end;

    public Assignment(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public boolean covers(Assignment other) {
        return (start <= other.start) && (other.end <= end);
    }

    public boolean overlaps(Assignment other) {
        return (start <= other.start) && (other.start <= end);
    }
}
