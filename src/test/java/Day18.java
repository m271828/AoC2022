import day18.LavaField;
import org.junit.jupiter.api.Test;
import utility.Cube;
import utility.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day18 {
    private static final String filename = "src/main/resources/day_18_input.txt";
    private List<List<Integer>> testCubes = List.of(
            List.of(2,2,2),
            List.of(1,2,2),
            List.of(3,2,2),
            List.of(2,1,2),
            List.of(2,3,2),
            List.of(2,2,1),
            List.of(2,2,3),
            List.of(2,2,4),
            List.of(2,2,6),
            List.of(1,2,5),
            List.of(3,2,5),
            List.of(2,1,5),
            List.of(2,3,5)
    );

    private List<Cube> createCubes(List<List<Integer>> points) {
        return points.stream().map(x -> new Cube(x.get(0), x.get(1), x.get(2))).toList();
    }

    private List<Cube> parseFile(String filename) throws IOException {
        ArrayList<Cube> results = new ArrayList<>();
        var lines = FileUtil.splitLines(filename, ",");
        for (var line : lines) {
            results.add(new Cube(Integer.valueOf(line.get(0)), Integer.valueOf(line.get(1)), Integer.valueOf(line.get(2))));
        }
        return results;
    }

    @Test
    public void simpleTest() {
        var cubes = createCubes(List.of(List.of(1,1,1), List.of(2,1,1)));
        var lava = new LavaField();
        for (var c : cubes) {
            lava.add(c);
        }
        assertEquals(10L, lava.surfaceArea());
    }

    @Test
    public void testData() {
        var cubes = createCubes(testCubes);
        var lava = new LavaField();
        for (var c : cubes) {
            lava.add(c);
        }
        assertEquals(64, lava.surfaceArea());
        assertEquals(58, lava.exteriorSurfaceArea());
    }

    @Test
    public void partOne() throws IOException {
        var cubes = parseFile(filename);
        var lava = new LavaField();
        for (var c : cubes) {
            lava.add(c);
        }
        assertEquals(3466, lava.surfaceArea());
    }

    @Test
    public void partTwo() throws IOException {
        var cubes = parseFile(filename);
        var lava = new LavaField();
        for (var c : cubes) {
            lava.add(c);
        }
        var area = lava.exteriorSurfaceArea();
        assertTrue(1376 < area);
        assertTrue(3316 > area);
        assertTrue(1711 != area);
        assertTrue(2351 != area);
        assertTrue(2826 != area);
        System.out.println("Exterior area is " + area + ".");
    }
}
