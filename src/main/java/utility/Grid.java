package utility;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Grid<T> {
    private ArrayList<ArrayList<T>> grid;
    private int minX, maxX, minY, maxY;
    private int width, height;
    private Coords offset;
    private Supplier<T> generator;

    public Grid(int minX, int maxX, int minY, int maxY, Supplier<T> generator) {
        width = maxX - minX + 1;
        height = maxY - minY + 1;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.generator = generator;

        grid = newGrid(height, width);
        offset = new Coords(-minX, -minY);
    }

    public T get(Coords p) {
        var adjP = adjustCoords(p);
        return grid.get(adjP.getY()).get(adjP.getX());
    }

    public void set(Coords p, T val) {
        var adjP = adjustCoords(p);
        grid.get(p.getY()).set(adjP.getX(), val);
    }

    public void resize(int newMinX, int newMaxX, int newMinY, int newMaxY) {
        int newWidth = newMaxX - newMinX + 1;
        int newHeight = newMaxY - newMinY + 1;

        var newGrid = newGrid(newHeight, newWidth);

        int startCopyX, endCopyX, startCopyY, endCopyY;
        int startIdxX, startIdxY;

        if (newMinX > minX) {
            startCopyX = newMinX;
            startIdxX = 0;
        } else {
            startCopyX = minX;
            startIdxX = Math.abs(newMinX-minX);
        }

        if (newMaxX < maxX) {
            endCopyX = newMaxX;
        } else {
            endCopyX = maxX;
        }

        if (newMinY > minY) {
            startCopyY = newMinY;
            startIdxY = 0;
        } else {
            startCopyY = minY;
            startIdxY = Math.abs(newMinY-minY);
        }

        if (newMaxY < maxY) {
            endCopyY = newMaxY;
        } else {
            endCopyY = maxY;
        }

        for (int y = 0; y <= endCopyY-startCopyY; y++) {
            for (int x = 0; x <= endCopyX - startCopyX; x++) {
                var newP = new Coords(startIdxX + x, startIdxY + y);
                var oldP = adjustCoords(new Coords(startCopyX + x, startCopyY + y));
                newGrid.get(newP.getY()).set(newP.getX(), grid.get(oldP.getY()).get(oldP.getX()));
            }
        }

        grid = newGrid;
        minX = newMinX;
        maxX = newMaxX;
        minY = newMinY;
        maxY = newMaxY;
        offset = new Coords(-minX, -minY);
        width = maxX - minX + 1;
        height = maxY - minY + 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var row : grid) {
            sb.append(row.stream().map(T::toString).map(x -> x + " ").reduce("", String::concat)).append("\n");
        }
        return sb.toString();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private Coords adjustCoords(Coords p) {
        return Coords.add(p, offset);
    }

    private ArrayList<ArrayList<T>> newGrid(int height, int width) {
        var g = new ArrayList<ArrayList<T>>();
        for (int y = 0; y < height; y++) {
            var row = new ArrayList<T>();
            for (int x = 0; x < width; x++) {
                row.add(generator.get());
            }
            g.add(row);
        }
        return g;
    }
}
