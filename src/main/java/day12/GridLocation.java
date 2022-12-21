package day12;

public record GridLocation(int row, int col, Character value, int dist) {
    @Override
    public String toString() {
        return "[" + row + "," + col + "]=" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GridLocation other = (GridLocation) o;
        return row == other.row && col == other.col;
    }
}
