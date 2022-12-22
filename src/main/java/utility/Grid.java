package utility;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Grid<T> {
    private ArrayList<ArrayList<T>> grid;
    private int rows;
    private int cols;

    public Grid(int rows, int cols, Supplier<T> func) {
        grid = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            var row = new ArrayList<T>();
            for (int j = 0; j < cols; j++) {
                row.add(func.get());
            }
            grid.add(row);
        }
        this.rows = rows;
        this.cols = cols;
    }

    public T get(int row, int col) {
        if (validRow(row) && validCol(col)) {
            return grid.get(row).get(col);
        }
        throw new IndexOutOfBoundsException("(" + row + "," + col + ") is not in range 0<=r<" + rows + "and 0 <=c<" + cols + ".");
    }

    public void set(int row, int col, T value) {
        if (validRow(row) && validCol(col)) {
            grid.get(row).set(col, value);
            return;
        }
        throw new IndexOutOfBoundsException("(" + row + "," + col + ") is not in range 0<=r<" + rows + "and 0 <=c<" + cols + ".");
    }

    private boolean validRow(int row) {
        return 0 <= row && row < rows;
    }

    private boolean validCol(int col) {
        return 0 <= col && col < cols;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(grid.get(i).get(j).toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
