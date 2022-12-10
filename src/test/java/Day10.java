import day10.AddX;
import day10.CRT;
import day10.Communicator;
import day10.Instruction;
import day10.Noop;
import day10.Processor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10 {
    private static final List<List<String>> smallSample = List.of(
            List.of("noop"),
            List.of("addx", "3"),
            List.of("addx", "-5")
    );

    private static final List<List<String>> sample = List.<List<String>>of(
            List.of("addx", "15"),
            List.of("addx", "-11"),
            List.of("addx", "6"),
            List.of("addx", "-3"),
            List.of("addx", "5"),
            List.of("addx", "-1"),
            List.of("addx", "-8"),
            List.of("addx", "13"),
            List.of("addx", "4"),
            List.of("noop"),
            List.of("addx", "-1"),
            List.of("addx", "5"),
            List.of("addx", "-1"),
            List.of("addx", "5"),
            List.of("addx", "-1"),
            List.of("addx", "5"),
            List.of("addx", "-1"),
            List.of("addx", "5"),
            List.of("addx", "-1"),
            List.of("addx", "-35"),
            List.of("addx", "1"),
            List.of("addx", "24"),
            List.of("addx", "-19"),
            List.of("addx", "1"),
            List.of("addx", "16"),
            List.of("addx", "-11"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "21"),
            List.of("addx", "-15"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "-3"),
            List.of("addx", "9"),
            List.of("addx", "1"),
            List.of("addx", "-3"),
            List.of("addx", "8"),
            List.of("addx", "1"),
            List.of("addx", "5"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "-36"),
            List.of("noop"),
            List.of("addx", "1"),
            List.of("addx", "7"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "2"),
            List.of("addx", "6"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "1"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "7"),
            List.of("addx", "1"),
            List.of("noop"),
            List.of("addx", "-13"),
            List.of("addx", "13"),
            List.of("addx", "7"),
            List.of("noop"),
            List.of("addx", "1"),
            List.of("addx", "-33"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "2"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "8"),
            List.of("noop"),
            List.of("addx", "-1"),
            List.of("addx", "2"),
            List.of("addx", "1"),
            List.of("noop"),
            List.of("addx", "17"),
            List.of("addx", "-9"),
            List.of("addx", "1"),
            List.of("addx", "1"),
            List.of("addx", "-3"),
            List.of("addx", "11"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "1"),
            List.of("noop"),
            List.of("addx", "1"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "-13"),
            List.of("addx", "-19"),
            List.of("addx", "1"),
            List.of("addx", "3"),
            List.of("addx", "26"),
            List.of("addx", "-30"),
            List.of("addx", "12"),
            List.of("addx", "-1"),
            List.of("addx", "3"),
            List.of("addx", "1"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "-9"),
            List.of("addx", "18"),
            List.of("addx", "1"),
            List.of("addx", "2"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "9"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "-1"),
            List.of("addx", "2"),
            List.of("addx", "-37"),
            List.of("addx", "1"),
            List.of("addx", "3"),
            List.of("noop"),
            List.of("addx", "15"),
            List.of("addx", "-21"),
            List.of("addx", "22"),
            List.of("addx", "-6"),
            List.of("addx", "1"),
            List.of("noop"),
            List.of("addx", "2"),
            List.of("addx", "1"),
            List.of("noop"),
            List.of("addx", "-10"),
            List.of("noop"),
            List.of("noop"),
            List.of("addx", "20"),
            List.of("addx", "1"),
            List.of("addx", "2"),
            List.of("addx", "2"),
            List.of("addx", "-6"),
            List.of("addx", "-11"),
            List.of("noop"),
            List.of("noop"),
            List.of("noop")
    );

    private static final String filename = "src/main/resources/day_10_input.txt";
    private static List<List<String>> instructionsAsString;
    private static Processor smallSampleProc;
    private static Communicator sampleCommunicator;
    private static Communicator communicator;

    private static List<Instruction> parseInstructions(List<List<String>> instructions) {
        ArrayList<Instruction> parsed = new ArrayList<>();
        for (var instruction : instructions) {
            if (instruction.size() == 1) {
                parsed.add(new Noop());
            } else {
                parsed.add(new AddX(Integer.parseInt(instruction.get(1))));
            }
        }
        return parsed;
    }

    @BeforeAll
    public static void configure() throws IOException {
        instructionsAsString = FileUtil.splitLines(filename, " ");
        smallSampleProc = new Processor(parseInstructions(smallSample));
        sampleCommunicator = new Communicator(parseInstructions(sample));
        communicator = new Communicator(parseInstructions(instructionsAsString));
    }

    @Test
    public void testSmallExecution() {
        int[] expected = {1, 1, 1, 4, 4, -1};
        for (int i = 0; i < 6; i++) {
            assertEquals(expected[i], smallSampleProc.cycle());
        }
    }

    @Test
    public void testExecution() {
        sampleCommunicator.run();
        assertEquals(13140, sampleCommunicator.getSignalSum());

        List<String> expected = List.of(
                "[#, #, ., ., #, #, ., ., #, #, ., ., #, #, ., ., #, #, ., ., #, #, ., ., #, #, ., ., #, #, ., ., #, #, ., ., #, #, ., .]",
                "[#, #, #, ., ., ., #, #, #, ., ., ., #, #, #, ., ., ., #, #, #, ., ., ., #, #, #, ., ., ., #, #, #, ., ., ., #, #, #, .]",
                "[#, #, #, #, ., ., ., ., #, #, #, #, ., ., ., ., #, #, #, #, ., ., ., ., #, #, #, #, ., ., ., ., #, #, #, #, ., ., ., .]",
                "[#, #, #, #, #, ., ., ., ., ., #, #, #, #, #, ., ., ., ., ., #, #, #, #, #, ., ., ., ., ., #, #, #, #, #, ., ., ., ., .]",
                "[#, #, #, #, #, #, ., ., ., ., ., ., #, #, #, #, #, #, ., ., ., ., ., ., #, #, #, #, #, #, ., ., ., ., ., ., #, #, #, #]",
                "[#, #, #, #, #, #, #, ., ., ., ., ., ., ., #, #, #, #, #, #, #, ., ., ., ., ., ., ., #, #, #, #, #, #, #, ., ., ., ., .]"
        );
        var actual = sampleCommunicator.getImage();
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i).toString());
        }
    }

    @Test
    public void partOne() {
        ArrayList<Integer> interesting = new ArrayList<>();
        communicator.run();
        System.out.println("Sum is " + communicator.getSignalSum());
        assertEquals(14540, communicator.getSignalSum());
    }

    @Test
    public void partTwo() {
        System.out.println();
        var image = communicator.getImage();
        var expected = List.of(
                "[#, #, #, #, ., #, ., ., #, ., #, #, #, #, ., #, #, #, #, ., #, #, #, #, ., #, ., ., #, ., ., #, #, ., ., #, #, #, #, .]",
                "[#, ., ., ., ., #, ., ., #, ., ., ., ., #, ., #, ., ., ., ., ., ., ., #, ., #, ., ., #, ., #, ., ., #, ., ., ., ., #, .]",
                "[#, #, #, ., ., #, #, #, #, ., ., ., #, ., ., #, #, #, ., ., ., ., #, ., ., #, #, #, #, ., #, ., ., ., ., ., ., #, ., .]",
                "[#, ., ., ., ., #, ., ., #, ., ., #, ., ., ., #, ., ., ., ., ., #, ., ., ., #, ., ., #, ., #, ., ., ., ., ., #, ., ., .]",
                "[#, ., ., ., ., #, ., ., #, ., #, ., ., ., ., #, ., ., ., ., #, ., ., ., ., #, ., ., #, ., #, ., ., #, ., #, ., ., ., .]",
                "[#, #, #, #, ., #, ., ., #, ., #, #, #, #, ., #, ., ., ., ., #, #, #, #, ., #, ., ., #, ., ., #, #, ., ., #, #, #, #, .]"
        );
        for (int i = 0; i < image.size(); i++) {
            System.out.println(image.get(i).toString());
            assertEquals(expected.get(i), image.get(i).toString());
        }
        System.out.println();
    }
}
