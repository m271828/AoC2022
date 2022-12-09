package day09;

import utility.Associations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bridge {
    private class Knot {
        public int row = 0;
        public int col = 0;
    }
    private Grid grid;
    private ArrayList<Knot> knots;
    private List<Associations.Pair<String>> moves;

    public Bridge(List<Associations.Pair<String>> moves, int knots) {
        grid = new Grid(moves);
        grid.markLocation(0,0);
        this.moves = moves;
        this.knots = new ArrayList<>();
        for (int i = 0; i < knots; i++) {
            this.knots.add(new Knot());
        }
    }

    public void steps() {
        for (int stepIdx = 0; stepIdx < moves.size(); stepIdx++) {
            var step = moves.get(stepIdx);
            if (step.first().equalsIgnoreCase("L")) {
                moveCol(Integer.valueOf(step.second()), -1);
            } else if (step.first().equalsIgnoreCase("R")) {
                moveCol(Integer.valueOf(step.second()), 1);
            } else if (step.first().equalsIgnoreCase("U")) {
                moveRow(Integer.valueOf(step.second()), 1);
            } else if (step.first().equalsIgnoreCase("D")) {
                moveRow(Integer.valueOf(step.second()), -1);
            }
        }
    }

    public void steps(List<Integer> marks) {
        for (int stepIdx = 0; stepIdx < moves.size(); stepIdx++) {
            var step = moves.get(stepIdx);
            var expected = marks.get(stepIdx);
            if (step.first().equalsIgnoreCase("L")) {
                moveCol(Integer.valueOf(step.second()), -1);
            } else if (step.first().equalsIgnoreCase("R")) {
                moveCol(Integer.valueOf(step.second()), 1);
            } else if (step.first().equalsIgnoreCase("U")) {
                moveRow(Integer.valueOf(step.second()), 1);
            } else if (step.first().equalsIgnoreCase("D")) {
                moveRow(Integer.valueOf(step.second()), -1);
            }
            if (grid.countMarks() != expected) {
                System.out.println("Step " + stepIdx + ": " + step.first() + " " + step.second());
                System.out.println("Expected " + expected + " marks but found " + grid.countMarks() + ".");
            }
        }
    }

    public int countVisits() {
        return grid.countMarks();
    }

    private void adjust(Knot k, int r, int c) {
        k.row += r;
        k.col += c;
        mark(k);
    }

    private void mark(Knot k) {
        if (knots.get(knots.size()-1) == k) {
            grid.markLocation(k.row, k.col);
        }
    }

    private void moveNextKnot(int idx) {
        Knot p = knots.get(idx-1);
        Knot k = knots.get(idx);
        int rAdj = p.row > k.row ? 1 : -1;
        int cAdj = p.col > k.col ? 1 : -1;
        var rowDiff = Math.abs(p.row - k.row);
        var colDiff = Math.abs(p.col - k.col);
        if (rowDiff <= 1 && colDiff <= 1) {
            return;
        } else if (rowDiff > colDiff) {
            while (colDiff != 0) {
                adjust(k, rAdj, cAdj);
                colDiff--;
                rowDiff--;
            }
            while (rowDiff != 1) {
                adjust(k, rAdj, 0);
                rowDiff--;
            }
        } else if (colDiff > rowDiff) {
            while (rowDiff != 0) {
                adjust(k, rAdj, cAdj);
                colDiff--;
                rowDiff--;
            }
            while (colDiff != 1) {
                adjust(k, 0, cAdj);
                colDiff--;
            }
        } else {
            while (rowDiff != 1) {
                adjust(k, rAdj, cAdj);
                rowDiff--;
            }
        }
    }

    private void moveRow(int steps, int inc) {
        int size = knots.size();
        for (int i = 0; i < steps; i++) {
            knots.get(0).row += inc;
            for (int j = 1; j < size; j++) {
                moveNextKnot(j);
            }
        }
    }

    private void moveCol(int steps, int inc) {
        int size = knots.size();
        for (int i = 0; i < steps; i++) {
            knots.get(0).col += inc;
            for (int j = 1; j < size; j++) {
                moveNextKnot(j);
            }
        }
    }
}
