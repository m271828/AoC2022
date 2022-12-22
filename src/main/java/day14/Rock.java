package day14;

import utility.Associations;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Rock {
    private List<Associations.Pair<Integer>> rockPoints;

    public Rock(List<Associations.Pair<Integer>> points) {
        if (points.size() < 2) {
            throw new IllegalArgumentException("Not enough rock points");
        }
        ArrayList<Associations.Pair<Integer>> tempSpace = new ArrayList<>();
        for (int i = 1; i < points.size(); i++) {
            tempSpace.addAll(generateFill(points.get(i-1), points.get(i)));
        }
        rockPoints = tempSpace;
    }

    public List<Associations.Pair<Integer>> getRock() {
        return rockPoints;
    }

    public Associations.Quad<Integer> getSurroundingSquare() {
        int minRow = rockPoints.stream().map(Associations.Pair::first).min(Integer::compareTo).orElseThrow();
        int maxRow = rockPoints.stream().map(Associations.Pair::first).max(Integer::compareTo).orElseThrow();
        int minCol = rockPoints.stream().map(Associations.Pair::second).min(Integer::compareTo).orElseThrow();
        int maxCol = rockPoints.stream().map(Associations.Pair::second).max(Integer::compareTo).orElseThrow();
        return new Associations.Quad<>(minRow, maxRow, minCol, maxCol);
    }

    private List<Associations.Pair<Integer>> generateFill(Associations.Pair<Integer> p1, Associations.Pair<Integer> p2) {
        ArrayList<Associations.Pair<Integer>> path = new ArrayList<>();
        if (p1.first() == p2.first()) {
            var minP = Integer.min(p1.second(), p2.second());
            var maxP = Integer.max(p1.second(), p2.second());
            for (int i = minP; i <= maxP; i++) {
                path.add(new Associations.Pair<>(p1.first(), i));
            }
        } else {
            var minP = Integer.min(p1.first(), p2.first());
            var maxP = Integer.max(p1.first(), p2.first());
            for (int i = minP; i <= maxP; i++) {
                path.add(new Associations.Pair<>(i, p1.second()));
            }
        }
        return path;
    }
}
