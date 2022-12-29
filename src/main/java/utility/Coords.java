package utility;

public class Coords {
    private int x;
    private int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void add(Coords other) {
        x += other.x;
        y += other.y;
    }

    public void subtract(Coords other) {
        x -= other.x;
        y -= other.y;
    }

    public void multiply(Coords other) {
        x *= other.x;
        y *= other.y;
    }

    public void divide(Coords other) {
        x /= other.x;
        y /= other.y;
    }

    public static Coords add(Coords p1, Coords p2) {
        return new Coords(p1.x + p2.x, p1.y + p2.y);
    }

    public static Coords subtract(Coords p1, Coords p2) {
        return new Coords(p1.x - p2.x, p1.y - p2.y);
    }

    public static Coords multiply(Coords p1, Coords p2) {
        return new Coords(p1.x * p2.x, p1.y * p2.y);
    }

    public static Coords divide(Coords p1, Coords p2) {
        return new Coords(p1.x / p2.x, p1.y / p2.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (o.getClass() != Coords.class) {
            return false;
        } else {
            var other = (Coords) o;
            return x == other.x && y == other.y;
        }
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
