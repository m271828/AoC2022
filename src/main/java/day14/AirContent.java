package day14;

public enum AirContent {
    EMPTY,
    ROCK,
    SAND,
    EDGE;

    @Override
    public String toString() {
        if (this == EMPTY) {
            return ".";
        } else if (this == ROCK) {
            return "#";
        } else if (this == SAND) {
            return "o";
        } else {
            return "E";
        }
    }
}
