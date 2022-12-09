package day09;

import utility.Associations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grid {
    private ArrayList<ArrayList<String>> grid;
    private int rows;
    private int cols;
    private int colOffset;
    private int rowOffset;

    public Grid(List<Associations.Pair<String>> input) {
        int x = 0, y = 0, xMax = 0, yMax = 0, xMin = 0, yMin = 0;
        for(var move : input) {
            if (move.first().equalsIgnoreCase("L")) {
                x -= Integer.valueOf(move.second());
                if (x < xMin) {
                    xMin = x;
                }
            } else if (move.first().equalsIgnoreCase("R")) {
                x += Integer.valueOf(move.second());
                if (x > xMax) {
                    xMax = x;
                }
            } else if (move.first().equalsIgnoreCase("U")) {
                y += Integer.valueOf(move.second());
                if (y > yMax) {
                    yMax = y;
                }
            } else if (move.first().equalsIgnoreCase("D")) {
                y -= Integer.valueOf(move.second());
                if (y < yMin) {
                    yMin = y;
                }
            }
        }

        rows = yMax-yMin+1;
        cols = xMax-xMin+1;

        rowOffset = yMin;
        colOffset = xMin;

        grid = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            var row = new ArrayList<>(Collections.nCopies(cols, "."));
            grid.add(row);
        }
    }

    public void markLocation(int row, int col) {
        grid.get(row-rowOffset).set(col-colOffset, "#");
    }

    public int countMarks() {
        int count = 0;
        for (var row : grid) {
            for (var val : row) {
                if (val.equalsIgnoreCase("#")) {
                    count++;
                }
            }
        }
        return count;
    }
}
