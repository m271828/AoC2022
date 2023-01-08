package day17;

public enum Move {
    LEFT('<'),
    RIGHT('>');

    private final char move;

    Move(char s) {
        move = s;
    }

    public static Move getMove(char c) {
        if (c == '<') {
            return LEFT;
        } else if (c == '>') {
            return RIGHT;
        }
        throw new IllegalArgumentException();
    }
}
