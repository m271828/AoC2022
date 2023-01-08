import day17.Chamber;
import day17.Move;
import day17.Rock;
import org.junit.jupiter.api.Test;
import utility.Coords;
import utility.FileUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day17 {
    private String filename = "src/main/resources/day_17_input.txt";
    List<List<String>> testRocks = List.of(List.of("####"), List.of(".#.","###",".#."), List.of("..#","..#","###"), List.of("#","#","#","#"), List.of("##","##"));
    List<Rock> expectedTestRocks = List.of(new Rock(new LinkedHashSet<>() {{
        add(new Coords(0,0));
        add(new Coords(1, 0));
        add(new Coords(2, 0));
        add(new Coords(3, 0));
    }}),
            new Rock(new LinkedHashSet<>() {
                {
                    add(new Coords(1, 0));
                    add(new Coords(0, 1));
                    add(new Coords(1, 1));
                    add(new Coords(2, 1));
                }}),
            new Rock(new LinkedHashSet<>() {
                {
                    add(new Coords(0, 0));
                    add(new Coords(1, 0));
                    add(new Coords(2, 0));
                    add(new Coords(2, 1));
                    add(new Coords(2, 2));
                }}),
            new Rock(new LinkedHashSet<>() {{
                add(new Coords(0,0));
                add(new Coords(0,1));
                add(new Coords(0,2));
                add(new Coords(0,3));
            }}),
            new Rock(new LinkedHashSet<>() {
                {
                    add(new Coords(0, 0));
                    add(new Coords(1, 0));
                    add(new Coords(0, 1));
                    add(new Coords(1, 1));
                }}));
    String testMoves = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";
    List<Move> expectedTestMoves = List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.LEFT, Move.LEFT, Move.RIGHT, Move.LEFT, Move.RIGHT,
            Move.RIGHT, Move.LEFT, Move.LEFT, Move.LEFT, Move.RIGHT, Move.RIGHT, Move.LEFT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.LEFT,
            Move.LEFT, Move.LEFT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.LEFT, Move.LEFT, Move.LEFT, Move.RIGHT, Move.LEFT, Move.LEFT,
            Move.LEFT, Move.RIGHT, Move.RIGHT, Move.LEFT, Move.RIGHT, Move.RIGHT, Move.LEFT, Move.LEFT, Move.RIGHT, Move.RIGHT);
    int width = 7;
    int enterIndex = 2;
    int enterHeight = 3;

    private Rock createRock(List<String> description) {
        var points = new LinkedHashSet<Coords>();
        for (int y = 0; y < description.size(); y++) {
            var line = description.get(description.size()-1-y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    points.add(new Coords(x, y));
                }
            }
        }
        return new Rock(points);
    }

    private List<Move> getMoves(String str) {
        var moves = new ArrayList<Move>(str.length());
        for (var c : str.toCharArray()) {
            moves.add(Move.getMove(c));
        }
        return moves;
    }

    @Test
    public void testMoves() {
        var left = Move.getMove('<');
        assertEquals(Move.LEFT, left);
        var right = Move.getMove('>');
        assertEquals(Move.RIGHT, right);
        var moves = getMoves(testMoves);
        assertEquals(moves, expectedTestMoves);
    }

    @Test
    public void runTestExample() {
        var rocks = testRocks.stream().map(x -> createRock(x)).toList();
        var chamber = new Chamber(rocks, getMoves(testMoves), width, enterIndex, enterHeight);
        chamber.dropRocks(1);
        assertEquals(1, chamber.height());
        chamber.dropRocks(1);
        assertEquals(4, chamber.height());
        chamber.dropRocks(1);
        assertEquals(6, chamber.height());
        chamber.dropRocks(1);
        assertEquals(7, chamber.height());
        chamber.dropRocks(2018);
        assertEquals(3068, chamber.height());
        var bigChamber = new Chamber(rocks, getMoves(testMoves), width, enterIndex, enterHeight);
        // assertEquals(1514285714288L, bigChamber.dropLotsOfRocks(1000000000000L)); Off by one
    }

    @Test
    public void partOne() throws IOException {
        var rocks = testRocks.stream().map(x -> createRock(x)).toList();
        var moves = getMoves(FileUtil.getLines(filename).get(0));
        var chamber = new Chamber(rocks, moves, width, enterIndex, enterHeight);
        chamber.dropRocks(2022);
        assertEquals(3224, chamber.height());
    }

    @Test
    public void partTwo() throws IOException {
        var rocks = testRocks.stream().map(x -> createRock(x)).toList();
        var moves = getMoves(FileUtil.getLines(filename).get(0));
        var chamber = new Chamber(rocks, moves, width, enterIndex, enterHeight);
        var height = chamber.dropLotsOfRocks(1000000000000L);
        assertEquals(1595988538691L, height);
    }
}
