import day16.Valve;
import day16.Volcano;
import org.junit.jupiter.api.Test;
import utility.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16 {
    private String filename = "src/main/resources/day_16_input.txt";
    private static String testInput = "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB\n" +
            "Valve BB has flow rate=13; tunnels lead to valves CC, AA\n" +
            "Valve CC has flow rate=2; tunnels lead to valves DD, BB\n" +
            "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE\n" +
            "Valve EE has flow rate=3; tunnels lead to valves FF, DD\n" +
            "Valve FF has flow rate=0; tunnels lead to valves EE, GG\n" +
            "Valve GG has flow rate=0; tunnels lead to valves FF, HH\n" +
            "Valve HH has flow rate=22; tunnel leads to valve GG\n" +
            "Valve II has flow rate=0; tunnels lead to valves AA, JJ\n" +
            "Valve JJ has flow rate=21; tunnel leads to valve II";
    private static int budget = 30;
    private static int moveCost = 1;
    private static int openCost = 1;

    private Volcano parseLines(List<String> lines) {
        var nodes = new ArrayList<Volcano.Node>();
        for (var l : lines) {
            var strs = l.split("Valve | has flow rate=|; tunnels lead to valves |; tunnel leads to valve ");
            Volcano.Node node = new Volcano.Node();
            node.name = strs[1];
            node.flow = Integer.parseInt(strs[2]);
            node.neighbors = Arrays.stream(strs[3].split(", ")).toList();
            nodes.add(node);
        }
        return new Volcano(nodes);
    }

    @Test
    public void testInput() {
        var strs = Arrays.stream(testInput.split("\n")).toList();
        var volcano = parseLines(strs);
        assertEquals(1651, volcano.findMaxPressure("AA", 30));
        assertEquals(1707, volcano.twoPartyRelease("AA", 26));
    }

    @Test
    public void partOne() throws IOException {
        var volcano = parseLines(FileUtil.getLines(filename));
        assertEquals(1767, volcano.findMaxPressure("AA", 30));
    }

    @Test
    public void partTwo() throws IOException {
        var volcano = parseLines(FileUtil.getLines(filename));
        assertEquals(2528, volcano.twoPartyRelease("AA", 26));
    }
}
