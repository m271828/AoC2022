package day10;

import java.util.ArrayList;
import java.util.List;

public class CRT {
    private ArrayList<ArrayList<Character>> crt;
    private int horz = 40;
    private int vert = 6;
    private int row = 0;
    private int col = 0;

    public CRT() {
        crt = new ArrayList<>();
        for (int i = 0; i < vert; i++) {
            ArrayList<Character> row = new ArrayList<>();
            for (int j = 0; j < horz; j++) {
                row.add(' ');
            }
            crt.add(row);
        }
    }

    public void refresh(int value) {
        if (Math.abs(col - value) <= 1) {
            crt.get(row).set(col, '#');
        } else {
            crt.get(row).set(col, '.');
        }
        col = (col + 1) % 40;
        if (col == 0) {
            row++;
        }
    }

    public List<List<Character>> getCrt() {
        return List.copyOf(crt);
    }
}
