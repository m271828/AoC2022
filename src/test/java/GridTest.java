import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.Coords;
import utility.Counter;
import utility.Grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridTest {
    Counter<Integer> generator = new Counter<>();

    @BeforeEach
    public void reset() {
        generator.reset();
    }

    @Test
    public void simpleIndices() {
        var grid = new Grid<Integer>(0, 3, 0, 2, generator);
        String expected = "0 1 2 3 \n4 5 6 7 \n8 9 10 11 \n";
        assertEquals(0, grid.toString().compareTo(expected));
        assertEquals(5, grid.get(new Coords(1, 1)));
    }

    @Test
    public void complexIndices() {
        var grid = new Grid(-2, 5, -3,  1, generator);
        String expected = "0 1 2 3 4 5 6 7 \n8 9 10 11 12 13 14 15 \n16 17 18 19 20 21 22 23 \n24 25 26 27 28 29 30 31 \n32 33 34 35 36 37 38 39 \n";
        assertEquals(0, grid.toString().compareTo(expected));
        assertEquals(1, grid.get(new Coords(-1, -3)));
        assertEquals(27, grid.get(new Coords(1, 0)));
    }

    @Test
    public void expandLarger() {
        var grid = new Grid(2,4, 2, 4, generator);
        String expected = "0 1 2 \n3 4 5 \n6 7 8 \n";
        assertEquals(0, grid.toString().compareTo(expected));
        assertEquals(1, grid.get(new Coords(3, 2)));
        grid.resize(0, 6, 0, 6);
        String expandedExpected = "9 10 11 12 13 14 15 \n16 17 18 19 20 21 22 \n23 24 0 1 2 28 29 \n30 31 3 4 5 35 36 \n37 38 6 7 8 42 43 \n44 45 46 47 48 49 50 \n51 52 53 54 55 56 57 \n";
        assertEquals(0, grid.toString().compareTo(expandedExpected));
        assertEquals(17, grid.get(new Coords(1, 1)));
        assertEquals(57, grid.get(new Coords(6, 6)));
    }

    @Test
    public void expandSmaller() {
        var grid = new Grid(0, 3, 0, 3, generator);
        String expected = "0 1 2 3 \n4 5 6 7 \n8 9 10 11 \n12 13 14 15 \n";
        assertEquals(0, grid.toString().compareTo(expected));
        assertEquals(6, grid.get(new Coords(2, 1)));
        grid.resize(1, 2, 1, 2);
        String shrunkExpected = "5 6 \n9 10 \n";
        assertEquals(0, grid.toString().compareTo(shrunkExpected));
        assertEquals(5, grid.get(new Coords(1, 1)));
        assertEquals(10, grid.get(new Coords(2, 2)));
    }

    @Test
    public void shiftLeft() {
        var grid = new Grid(0, 3, 0, 3, generator);
        String expected = "0 1 2 3 \n4 5 6 7 \n8 9 10 11 \n12 13 14 15 \n";
        assertEquals(0, grid.toString().compareTo(expected));
        assertEquals(6, grid.get(new Coords(2, 1)));
        grid.resize(-1, 3, 0, 3);
        String leftExpected = "16 0 1 2 3 \n21 4 5 6 7 \n26 8 9 10 11 \n31 12 13 14 15 \n";
        assertEquals(0, grid.toString().compareTo(leftExpected));
        assertEquals(16, grid.get(new Coords(-1, 0)));
        assertEquals(15, grid.get(new Coords(3,3)));
    }

    @Test
    public void shiftRight() {
        var grid = new Grid(0, 3, 0, 3, generator);
        String expected = "0 1 2 3 \n4 5 6 7 \n8 9 10 11 \n12 13 14 15 \n";
        assertEquals(0, grid.toString().compareTo(expected));
        assertEquals(6, grid.get(new Coords(2, 1)));
        grid.resize(0, 4, 0, 3);
        String rightExpected = "0 1 2 3 20 \n4 5 6 7 25 \n8 9 10 11 30 \n12 13 14 15 35 \n";
        assertEquals(0, grid.toString().compareTo(rightExpected));
        assertEquals(0, grid.get(new Coords(0, 0)));
        assertEquals(35, grid.get(new Coords(4, 3)));
    }

    @Test
    public void shiftUp() {
        var grid = new Grid(0, 3, 0, 3, generator);
        String expected = "0 1 2 3 \n4 5 6 7 \n8 9 10 11 \n12 13 14 15 \n";
        assertEquals(0, grid.toString().compareTo(expected));
        assertEquals(6, grid.get(new Coords(2, 1)));
        grid.resize(0, 3, 1, 3);
        String upExpected = "4 5 6 7 \n8 9 10 11 \n12 13 14 15 \n";
        assertEquals(0, grid.toString().compareTo(upExpected));
        assertEquals(4, grid.get(new Coords(0,1)));
        assertEquals(15, grid.get(new Coords(3, 3)));
    }

    @Test
    public void shiftDown() {
        var grid = new Grid(0, 3, 0, 3, generator);
        String expected = "0 1 2 3 \n4 5 6 7 \n8 9 10 11 \n12 13 14 15 \n";
        assertEquals(0, grid.toString().compareTo(expected));
        assertEquals(6, grid.get(new Coords(2, 1)));
        grid.resize(0, 3, -1, 3);
        String downExpected = "16 17 18 19 \n0 1 2 3 \n4 5 6 7 \n8 9 10 11 \n12 13 14 15 \n";
        assertEquals(0, grid.toString().compareTo(downExpected));
        assertEquals(16, grid.get(new Coords(0, -1)));
        assertEquals(15, grid.get(new Coords(3, 3)));
    }
}
