import day14.AirContent;
import day14.Cave;
import day14.Rock;
import org.junit.jupiter.api.Test;
import utility.Associations;
import utility.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day14 {
    private static final String filename = "src/main/resources/day_14_input.txt";

    private static final String[] test = {"498,4 -> 498,6 -> 496,6", "503,4 -> 502,4 -> 502,9 -> 494,9"};

    private static List<Associations.Pair<Integer>> parsePoints(String s) {
        var numberPairs = s.split(" -> ");
        ArrayList<Associations.Pair<Integer>> points = new ArrayList<>();
        for (int i = 0; i < numberPairs.length; i++) {
            var numbers = numberPairs[i].split(",");
            points.add(new Associations.Pair<>(Integer.valueOf(numbers[1]), Integer.valueOf(numbers[0])));
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
        while(cave.dropSand()) {
        }
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
        while(cave.dropSand()) {
        }
        System.out.println("Part One: " + cave.getSandCount() + " pieces of sand.");
        assertTrue(cave.getSandCount() > 477);
        assertTrue(cave.getSandCount() < 1043);
    }
}
