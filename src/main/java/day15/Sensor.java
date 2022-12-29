package day15;

import utility.Coords;
import utility.Grid;
import utility.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Sensor {
    private Coords location;
    private Coords nearestBeacon;
    private int manhattanDistance;

    public Sensor(Coords location, Coords nearestBeacon) {
        this.location = location;
        this.nearestBeacon = nearestBeacon;
        manhattanDistance = Math.abs(location.getX()-nearestBeacon.getX()) + Math.abs(location.getY()-nearestBeacon.getY());
    }

    public Coords getLocation() {
        return location;
    }

    public Coords getNearestBeacon() {
        return nearestBeacon;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }

    public Set<Coords> coveredArea(int targetY) {
        HashSet<Coords> points = new HashSet<>();
        int y = location.getY() + manhattanDistance;
        int minX = location.getX();
        int maxX = location.getX();

        while (targetY >= location.getY() && y >= location.getY()) {
            if (y == targetY) {
                for (int x = minX; x <= maxX; x++) {
                    points.add(new Coords(x, y));
                }
                return points;
            }
            y--;
            minX--;
            maxX++;
        }

        y = location.getY() - manhattanDistance;
        minX = location.getX();
        maxX = location.getX();

        while(y < location.getY()) {
            if (y == targetY) {
                for (int x = minX; x <= maxX; x++) {
                    points.add(new Coords(x, y));
                }
                return points;
            }
            y++;
            minX--;
            maxX++;
        }

        return points;
    }

    public Set<Coords> coveredArea() {
        HashSet<Coords> points = new HashSet<>();
        int y = location.getY() + manhattanDistance;
        int minX = location.getX();
        int maxX = location.getX();

        while (y >= location.getY()) {
            for (int x = minX; x <= maxX; x++) {
                points.add(new Coords(x, y));
            }
            y--;
            minX--;
            maxX++;
        }

        y = location.getY() - manhattanDistance;
        minX = location.getX();
        maxX = location.getX();

        while (y < location.getY()) {
            for (int x = minX; x <= maxX; x++) {
                points.add(new Coords(x, y));
            }
            y++;
            minX--;
            maxX++;
        }

        return points;
    }

    private boolean isInRange(int point, int min, int max) {
        return min <= point && point <= max;
    }

    public Set<Coords> getBoundary(int min, int max) {
        HashSet<Coords> points = new HashSet<>();
        if (min > location.getY() + manhattanDistance + 1 || max < location.getY() - manhattanDistance - 1) {
            return points;
        }
        int y = location.getY() + manhattanDistance + 1;
        int minX = location.getX();
        int maxX = location.getX();

        while (y >= location.getY()) {
            if (isInRange(y, min, max)) {
                if (isInRange(minX, min, max)) {
                    points.add(new Coords(minX, y));
                }
                if (isInRange(maxX, min, max)) {
                    points.add(new Coords(maxX, y));
                }
            }
            y--;
            minX--;
            maxX++;
        }

        y = location.getY() - manhattanDistance - 1;
        minX = location.getX();
        maxX = location.getX();

        while (y < location.getY()) {
            if (isInRange(y, min, max)) {
                if (isInRange(minX, min, max)) {
                    points.add(new Coords(minX, y));
                }
                if (isInRange(maxX, min, max)) {
                    points.add(new Coords(maxX, y));
                }
            }
            y++;
            minX--;
            maxX++;
        }

        return points;
    }
}
