import day15.Sensor;
import day15.SensorMap;
import org.junit.jupiter.api.Test;
import utility.Coords;
import utility.FileUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day15 {
    private static List<String> testInput = List.of("Sensor at x=2, y=18: closest beacon is at x=-2, y=15",
            "Sensor at x=9, y=16: closest beacon is at x=10, y=16",
            "Sensor at x=13, y=2: closest beacon is at x=15, y=3",
            "Sensor at x=12, y=14: closest beacon is at x=10, y=16",
            "Sensor at x=10, y=20: closest beacon is at x=10, y=16",
            "Sensor at x=14, y=17: closest beacon is at x=10, y=16",
            "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
            "Sensor at x=2, y=0: closest beacon is at x=2, y=10",
            "Sensor at x=0, y=11: closest beacon is at x=2, y=10",
            "Sensor at x=20, y=14: closest beacon is at x=25, y=17",
            "Sensor at x=17, y=20: closest beacon is at x=21, y=22",
            "Sensor at x=16, y=7: closest beacon is at x=15, y=3",
            "Sensor at x=14, y=3: closest beacon is at x=15, y=3",
            "Sensor at x=20, y=1: closest beacon is at x=15, y=3");

    private static String filename = "src/main/resources/day_15_input.txt";

    private static List<Sensor> parseInput(List<String> lines) {
        ArrayList<Sensor> sensors = new ArrayList<>();
        for (var line : lines) {
            var parts = line.split("[a-zA-Z=:, ]+");

            var sensorX = Integer.parseInt(parts[1]);
            var sensorY = Integer.parseInt(parts[2]);
            var beaconX = Integer.parseInt(parts[3]);
            var beaconY = Integer.parseInt(parts[4]);
            sensors.add(new Sensor(new Coords(sensorX, sensorY), new Coords(beaconX, beaconY)));
        }
        return sensors;
    }

    @Test
    public void testSensor() {
        var sensor = new Sensor(new Coords(1, 1), new Coords(3, 3));
        var expected = Set.of(new Coords(1, 5),
                new Coords(0,4), new Coords(1, 4), new Coords(2, 4),
                new Coords(-1, 3), new Coords(0, 3), new Coords(1, 3), new Coords(2, 3), new Coords(3, 3),
                new Coords(-2, 2), new Coords(-1, 2), new Coords(0, 2), new Coords(1, 2), new Coords(2, 2), new Coords(3, 2), new Coords(4, 2),
                new Coords(-3, 1), new Coords(-2, 1), new Coords(-1, 1), new Coords(0, 1), new Coords(1, 1), new Coords(2, 1), new Coords(3, 1), new Coords(4, 1), new Coords(5, 1),
                new Coords(-2, 0), new Coords(-1, 0), new Coords(0, 0), new Coords(1, 0), new Coords(2, 0), new Coords(3, 0), new Coords(4, 0),
                new Coords(-1, -1), new Coords(0, -1), new Coords(1, -1), new Coords(2, -1), new Coords(3, -1),
                new Coords(0, -2), new Coords(1, -2), new Coords(2, -2),
                new Coords(1, -3));
        var actual = sensor.coveredArea();
        assertEquals(expected.size(), actual.size());
        for (var e : expected) {
            assertEquals(1, actual.stream().filter(c -> c.getX() == e.getX() && c.getY() == e.getY()).toList().size());
        }
    }

    @Test
    public void testParseInput() {
        var sensors = parseInput(testInput);
        assertEquals(14, sensors.size());
        assertEquals(new Coords(2, 18), sensors.get(0).getLocation());
        assertEquals(new Coords(15, 3), sensors.get(13).getNearestBeacon());
    }

    @Test
    public void testSample() {
        var map = new SensorMap(parseInput(testInput));
        assertEquals(BigInteger.valueOf(26), map.getRowData(10).cellsWithSignal);
        var c = map.findBeacon(0, 20);
        assertEquals(new Coords(14, 11), c);
    }

    @Test
    public void partOne() throws IOException {
        var map = new SensorMap(parseInput(FileUtil.getLines(filename)));
        var stats = map.getRowData(2000000);
        assertEquals(BigInteger.valueOf(5256611), stats.cellsWithSignal);
    }

    @Test
    public void partTwo() throws IOException {
        var map = new SensorMap(parseInput(FileUtil.getLines(filename)));
        var c = map.findBeacon(0, 4000000);
        System.out.println(c);
        var bi = BigInteger.valueOf(4000000).multiply(BigInteger.valueOf(c.getX())).add(BigInteger.valueOf(c.getY()));
        assertEquals(BigInteger.valueOf(13337919186981L), bi);
    }
}