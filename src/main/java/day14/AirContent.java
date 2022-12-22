package day14;

public enum AirContent {
    EMPTY,
    ROCK,
    SAND;

    @Override
    public String toString() {
        if (this == EMPTY) {
            return ".";
        } else if (this == ROCK) {
            return "#";
        } else {
            return "o";
        }
    }
}
