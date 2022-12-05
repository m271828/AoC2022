import day05.Cargo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.BasicParser;
import utility.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05 {
    private static List<List<String>> cargoInput = List.of(
            List.of("[W]", "[M]", "[L]", "[F]"),
            List.of("[B]", "[Z]", "[V]", "[M]", "[F]"),
            List.of("[H]", "[V]", "[R]", "[S]", "[L]", "[Q]"),
            List.of("[F]", "[S]", "[V]", "[Q]", "[P]", "[M]", "[T]", "[J]"),
            List.of("[L]", "[S]", "[W]"),
            List.of("[F]", "[V]", "[P]", "[M]", "[R]", "[J]", "[W]"),
            List.of("[J]", "[Q]", "[C]", "[P]", "[N]", "[R]", "[F]"),
            List.of("[V]", "[H]", "[P]", "[S]", "[Z]", "[W]", "[R]", "[B]"),
            List.of("[B]", "[M]", "[J]", "[C]", "[G]", "[H]", "[Z]", "[W]")
    );

    private static String file = "src/main/resources/day_05_input.txt";

    private Cargo cargo;

    private static List<List<Integer>> commands;

    @BeforeAll
    public static void setup() throws IOException {
        List<BasicParser.Identifier> spec = List.of(
                BasicParser.Identifier.SKIP,
                BasicParser.Identifier.INTEGER,
                BasicParser.Identifier.INTEGER,
                BasicParser.Identifier.INTEGER);
        commands = FileUtil.splitLinesForInts(file, "[a-z ]+", spec);
    }

    @BeforeEach
    public void configure() {
        cargo = new Cargo(cargoInput);
    }

    @Test
    public void partOne() {
        for (var cmd : commands) {
            cargo.move(cmd.get(0), cmd.get(1), cmd.get(2), false);
        }
        System.out.println("Top of stacks");
        cargo.printTopOfStacks();
        System.out.println();
        assertEquals("VRWBSFZWM", cargo.getMessage());
    }

    @Test
    public void partTwo() {
        for (var cmd : commands) {
            cargo.move(cmd.get(0), cmd.get(1), cmd.get(2), true);
        }
        System.out.println("Top of stacks");
        cargo.printTopOfStacks();
        System.out.println();
        assertEquals("RBTWJWMCF", cargo.getMessage());
    }
}
