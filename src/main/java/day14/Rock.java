package day14;

import utility.Associations;
import utility.Coords;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Rock {
    private final List<Coords> rockPoints;

    public Rock(List<Coords> points) {
        if (points == null || points.size() == 0) {
            rockPoints = List.of();
            return;
        }
        ArrayList<Coords> tempSpace = new ArrayList<>();
        tempSpace.add(points.get(0));
        for (int i = 1; i < points.size(); i++) {
            tempSpace.addAll(generateFill(points.get(i-1), points.get(i)));
        }
        rockPoints = tempSpace;
    }

    public List<Coords> getRock() {
        return rockPoints;
    }

    private List<Coords> generateFill(Coords p1, Coords p2) {
        ArrayList<Coords> path = new ArrayList<>();
        if (p1.getX() == p2.getX()) {
            var diff = p2.getY() - p1.getY();
            Coords move = new Coords(0, diff/Math.abs(diff));
            Coords loc = new Coords(p1.getX(), p1.getY());
            while(loc.getY() != p2.getY()) {
                path.add(loc);
                loc = Coords.add(loc, move);
            }
            path.add(p2);
        } else {
            var diff = p2.getX() - p1.getX();
            Coords move = new Coords(diff/Math.abs(diff), 0);
            Coords loc = new Coords(p1.getX(), p1.getY());
            while (loc.getX() != p2.getX()) {
                path.add(loc);
                loc = Coords.add(loc, move);
            }
            path.add(p2);
        }
        return path;
    }
}
