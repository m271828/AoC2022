import day09.Bridge;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.Associations;
import utility.FileUtil;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Day09 {
    private static List<Associations.Pair<String>> sampleMoves = List.of(
            new Associations.Pair<>("R", "4"),
            new Associations.Pair<>("U", "4"),
            new Associations.Pair<>("L", "3"),
            new Associations.Pair<>("D", "1"),
            new Associations.Pair<>("R", "4"),
            new Associations.Pair<>("D", "1"),
            new Associations.Pair<>("L", "5"),
            new Associations.Pair<>("R", "2")
    );
    private static List<Associations.Pair<String>> longSampleMoves = List.of(
            new Associations.Pair<>("R", "5"),
            new Associations.Pair<>("U", "8"),
            new Associations.Pair<>("L", "8"),
            new Associations.Pair<>("D", "3"),
            new Associations.Pair<>("R", "17"),
            new Associations.Pair<>("D", "10"),
            new Associations.Pair<>("L", "25"),
            new Associations.Pair<>("U", "20")
    );

    private static String filename = "src/main/resources/day_09_input.txt";
    private static Bridge sampleBridge, sampleMultiBridge, problemBridge, multiKnotBridge;

    @BeforeAll
    public static void configure() throws IOException {
        var data = FileUtil.splitLinesBy(filename, " ");
        sampleBridge = new Bridge(sampleMoves, 2);
        sampleMultiBridge = new Bridge(longSampleMoves, 10);
        problemBridge = new Bridge(data, 2);
        multiKnotBridge = new Bridge(data, 10);
    }

    @Test
    public void sample() {
        sampleBridge.steps(List.of(4,7,9,9,10,10,13,13));
        assertEquals(13, sampleBridge.countVisits());
        sampleMultiBridge.steps();
        assertEquals(36, sampleMultiBridge.countVisits());
    }

    @Test
    public void partOne() {
        problemBridge.steps();
        System.out.println(problemBridge.countVisits() + " locations visited.");
        assertEquals(6391, problemBridge.countVisits());
    }

    @Test
    public void partTwo() {
        multiKnotBridge.steps();
        assertNotEquals(2701, multiKnotBridge.countVisits());
        assert(multiKnotBridge.countVisits() < 2701);
        System.out.println(multiKnotBridge.countVisits() + " locations visited by knot 9.");
        assertEquals(2593, multiKnotBridge.countVisits());
    }
}
