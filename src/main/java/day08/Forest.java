package day08;

import utility.Associations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Forest {
    private final List<List<Integer>> heights;
    private ArrayList<ArrayList<Boolean>> visible;
    private Integer rows;
    private Integer cols;

    public Forest(List<List<Integer>> heights) {
        this.heights = List.copyOf(heights);
        rows = heights.size();
        cols = heights.get(0).size();
        visible = new ArrayList<>();
        var row = new ArrayList<Boolean>();
        for (int i = 0; i < cols; i++) {
            row.add(true);
        }
        visible.add(row);
        for (int i = 1; i < rows-1; i++) {
            row = new ArrayList<>();
            row.add(true);
            for (int j = 1; j < cols-1; j++) {
                row.add(false);
            }
            row.add(true);
            visible.add(row);
        }
        row = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            row.add(true);
        }
        visible.add(row);
    }

    private void visibility(BiFunction<Integer, Integer, Associations.Pair<Integer>> indexUpdate, ArrayList<ArrayList<Boolean>> visible, Associations.Pair<Integer> start) {
        Integer max = 0;
        var indices = start;
        while (indices != null) {
            if (max < heights.get(indices.first()).get(indices.second())) {
                visible.get(indices.first()).set(indices.second(), true);
                max = heights.get(indices.first()).get(indices.second());
            }
            indices = indexUpdate.apply(indices.first(), indices.second());
        }
    }

    private void setLeftVisible() {
        for (int i = 0; i < rows; i++) {
            visibility((r, c) -> {
                c++;
                if (c < cols) {
                    return new Associations.Pair<>(r, c);
                }
                return null;
            }, visible,  new Associations.Pair<>(i,0));
        }
    }

    private void setRightVisible() {
        for (int i = 0; i < rows; i++) {
            visibility((r, c) -> {
                c--;
                if (c >= 0) {
                    return new Associations.Pair<>(r, c);
                }
                return null;
            }, visible, new Associations.Pair<>(i,cols-1));
        }
    }

    private void setTopVisible() {
        for (int i = 0; i < cols; i++) {
            visibility((r, c) -> {
                r++;
                if (r < rows) {
                    return new Associations.Pair<>(r, c);
                }
                return null;
            }, visible, new Associations.Pair<>(0, i));
        }
    }

    private void setDownVisible() {
        for (int i = 0; i < cols; i++) {
            visibility((r, c) -> {
                r--;
                if (r >= 0) {
                    return new Associations.Pair<>(r, c);
                }
                return null;
            }, visible, new Associations.Pair<>(rows-1, i));
        }
    }

    private void findVisibility() {
        setLeftVisible();
        setRightVisible();
        setTopVisible();
        setDownVisible();
    }

    public int countVisibleTrees() {
        findVisibility();

        Integer count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (visible.get(i).get(j)) {
                    count++;
                }
            }
        }

        return count;
    }

    private int scoreDir(int row, int rItr, int col, int cItr) {
        int height = heights.get(row).get(col);
        int score = 0;

        int r = row + rItr;
        int c = col + cItr;

        while (0<= r && r < rows && 0<= c && c < cols) {
            if (height <= heights.get(r).get(c)) {
                score++;
                break;
            } else {
                score++;
                r += rItr;
                c += cItr;
            }
        }

        return score;
    }

    private int scenicScore(int row, int col) {
        int left = scoreDir(row, 0, col, -1);
        int right = scoreDir(row, 0, col, 1);
        int up = scoreDir(row, -1, col, 0);
        int down = scoreDir(row, 1, col, 0);

        return left * right * up * down;
    }

    public int findMostScenicTreeScore() {
        int max = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                max = Math.max(max, scenicScore(i, j));
            }
        }
        return max;
    }
}
