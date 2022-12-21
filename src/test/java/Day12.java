import day12.GridLocation;
import day12.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.FileUtil;
import utility.Vertex;

import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day12 {
    private static List<List<Character>> test = List.of(
            List.of('S','a','b','q','p','o','n','m'),
            List.of('a','b','c','r','y','x','x','l'),
            List.of('a','c','c','s','z','E','x','k'),
            List.of('a','c','c','t','u','v','w','j'),
            List.of('a','b','d','e','f','g','h','i')
    );

    private static String filename = "src/main/resources/day_12_input.txt";
    private static ArrayList<GridLocation> locations;

    public static Map readMap(String file) throws IOException {
        var lines = FileUtil.getLines(file);
        ArrayList<List<Character>> map = new ArrayList<>();
        for (var l : lines) {
            ArrayList<Character> line = new ArrayList<>();
            for (var c : l.toCharArray()) {
                line.add(c);
            }
            map.add(line);
        }
        return new Map(map);
    }

    @BeforeAll
    public static void configure() throws IOException {
        var map = readMap(filename);
        locations = map.startingLocations();
    }

    @Test
    public void testSample() {
        Map map = new Map(test);
        assertEquals(31, map.findEnd(map.start(), Optional.empty()).dist());
    }

    @Test
    public void partOne() throws IOException {
        var map = readMap(filename);
        var len = map.findEnd(map.start(), Optional.empty()).dist();
        System.out.println("Shortest route is " + len + " steps.");
        assertEquals(len, 350);
    }

    @Test
    public void partTwo1() throws IOException {
        var map = readMap(filename);
        int minDist = map.findShortestScenic();
        System.out.println("Shortest route is " + minDist + " steps when you can start at a.");
        assertEquals(minDist, 349);
    }
}
