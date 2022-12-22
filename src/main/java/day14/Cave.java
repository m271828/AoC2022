package day14;

import utility.Associations;
import utility.Grid;

import java.util.List;

public class Cave {
    private Grid<AirContent> air;
    private int minRow = 0;
    private int maxRow = 0;
    private int minCol = 500;
    private int maxCol = 500;
    private Associations.Pair<Integer> sandEntry = new Associations.Pair<>(0, 500);;
    int sandCount = 0;
    int[][] sandMoves = {{1, 0}, {1,-1}, {1,1}};
    boolean filled = false;


    public Cave(List<Rock> rocks) {
        for (var rock : rocks) {
            var square = rock.getSurroundingSquare();
            minRow = Integer.min(minRow, square.first());
            maxRow = Integer.max(maxRow, square.second());
            minCol = Integer.min(minCol, square.third());
            maxCol = Integer.max(maxCol, square.fourth());
        }
        air = new Grid<>(maxRow-minRow+1, maxCol-minCol+1, () -> AirContent.EMPTY);
        for (var rock: rocks) {
            for (var point : rock.getRock()) {
                setAdjusted(point.first(), point.second(), AirContent.ROCK);
            }
        }
    }

    public boolean dropSand() {
        if (filled || getAdjusted(sandEntry.first(), sandEntry.second()) == AirContent.SAND) {
            return false;
        }
        var point = new Associations.Pair<>(sandEntry.first(), sandEntry.second());
        boolean finalDestination = false;
        while (!finalDestination) {
            var newPoint = testAndMove(point);
            if (newPoint == null) {
                filled = true;
                return false;
            } else if (newPoint.equals(point)) {
                finalDestination = true;
            } else {
                point = newPoint;
            }
        }
        setAdjusted(point.first(), point.second(), AirContent.SAND);
        sandCount++;
        return true;
    }

    public int getSandCount() {
        return sandCount;
    }

    private Associations.Pair<Integer> testAndMove(Associations.Pair<Integer> point) {
        for (var move : sandMoves) {
            try {
                if (getAdjusted(point.first() + move[0], point.second() + move[1]) == AirContent.EMPTY) {
                    return new Associations.Pair<>(point.first() + move[0], point.second() + move[1]);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return point;
    }

    private AirContent getAdjusted(int row, int col) {
        return air.get(row-minRow, col-minCol);
    }

    private void setAdjusted(int row, int col, AirContent value) {
        air.set(row-minRow, col-minCol, value);
    }

    @Override
    public String toString() {
        return air.toString();
    }
}
