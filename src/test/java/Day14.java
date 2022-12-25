import day14.AirContent;
import day14.Cave;
import day14.Rock;
import org.junit.jupiter.api.Test;
import utility.Associations;
import utility.Coords;
import utility.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day14 {
    private static final String filename = "src/main/resources/day_14_input.txt";

    private static final String[] test = {"498,4 -> 498,6 -> 496,6", "503,4 -> 502,4 -> 502,9 -> 494,9"};

    private static List<Coords> parsePoints(String s) {
        var numberPairs = s.split(" -> ");
        ArrayList<Coords> points = new ArrayList<>();
        for (int i = 0; i < numberPairs.length; i++) {
            var numbers = numberPairs[i].split(",");
            points.add(new Coords(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])));
        }
        return points;
    }

    @Test
    public void test() {
        ArrayList<Rock> rocks = new ArrayList<>();
        for (var s : test) {
            rocks.add(new Rock(parsePoints(s)));
        }
        var cave = new Cave(rocks);
        cave.fillWithSand();
        assertEquals(24, cave.getSandCount());
    }

    @Test
    public void partOne() throws IOException {
        var rockStrings = FileUtil.getLines(filename);
        ArrayList<Rock> rocks = new ArrayList<>();
        for (var s : rockStrings) {
            rocks.add(new Rock(parsePoints(s)));
        }
        var cave = new Cave(rocks);
        cave.fillWithSand();
        System.out.println("Part One: " + cave.getSandCount() + " pieces of sand.");
        assertEquals(828, cave.getSandCount());
    }

    @Test
    public void partTwo() throws IOException {
        var rockStrings = FileUtil.getLines(filename);
        ArrayList<Rock> rocks = new ArrayList<>();
        for (var s : rockStrings) {
            rocks.add(new Rock(parsePoints(s)));
        }
        int maxY = rocks.stream().flatMap(y -> y.getRock().stream()).map(Coords::getY).max(Integer::compareTo).orElseThrow();
        var cave = new Cave(rocks, maxY+2);
        cave.fillWithSand();
        System.out.println("Part Two: " + cave.getSandCount() + " pieces of sand.");
        assertEquals(25500, cave.getSandCount());
    }
}
