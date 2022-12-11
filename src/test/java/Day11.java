import day11.Monkey;
import day11.PackOfMonkies;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day11 {
    private static final String filename = "src/main/resources/day_11_input.txt";
    private static final List<Monkey> testMonkeys = List.of(
            new Monkey(Monkey.Op.MULT, 19, 23, 2, 3),
            new Monkey(Monkey.Op.SUM, 6, 19, 2, 0),
            new Monkey(Monkey.Op.MULT, null,13, 1, 3),
            new Monkey(Monkey.Op.SUM, 3, 17, 0, 1)
    );

    private static final List<List<Integer>> testItems = List.of(
            List.of(79, 98),
            List.of(54, 65, 75, 74),
            List.of(79, 60, 97),
            List.of(74)
    );

    private static final List<Monkey> actualMonkeys = List.of(
            new Monkey(Monkey.Op.MULT, 11, 19, 6, 7),
            new Monkey(Monkey.Op.SUM, 8, 2, 6, 0),
            new Monkey(Monkey.Op.SUM, 1, 3, 5, 3),
            new Monkey(Monkey.Op.MULT, 7, 17, 5, 4),
            new Monkey(Monkey.Op.SUM, 4, 13, 0, 1),
            new Monkey(Monkey.Op.SUM, 7, 7, 1, 4),
            new Monkey(Monkey.Op.MULT, null, 5, 7, 2),
            new Monkey(Monkey.Op.SUM, 6, 11, 2, 3)
    );

    private static final List<List<Integer>> actualItems = List.of(
            List.of(74, 73, 57, 77, 74),
            List.of(99, 77, 79),
            List.of(64, 67, 50, 96, 89, 82, 82),
            List.of(88),
            List.of(80, 66, 98, 83, 70, 63, 57, 66),
            List.of(81, 93, 90, 61, 62, 64),
            List.of(69, 97, 88, 93),
            List.of(59, 80)
    );

    private static PackOfMonkies monkeys;

    private static PackOfMonkies readMonkeysFromFile(String file) throws IOException {
        var groupLines = FileUtil.groupLines(filename);
        ArrayList<Monkey> monkeys = new ArrayList<>();
        ArrayList<List<Integer>> allItems = new ArrayList<>();
        for (var group : groupLines) {
            ArrayList<Integer> items = new ArrayList<>();
            Monkey.Op op;
            Integer opVal = null;
            Integer testVal;
            Integer ifTrueIdx;
            Integer ifFalseIdx;
            assertEquals(6, group.size());
            var l2 = group.get(1).split("[a-zA-Z:, ]");
            var l3 = group.get(2);
            var l4 = group.get(3).split("[a-zA-Z: ]");
            var l5 = group.get(4).split("[a-zA-Z: ]");
            var l6 = group.get(5).split("[a-zA-Z: ]");
            for (var l : l2) {
                if (!l.isEmpty()) {
                    items.add(Integer.parseInt(l));
                }
            }
            int firstOld = l3.indexOf("old");
            int secondOld = l3.lastIndexOf("old");
            int plusIdx = l3.indexOf("+");
            op = plusIdx != -1 ? Monkey.Op.SUM : Monkey.Op.MULT;
            if (firstOld == secondOld) {
                String[] aVal = l3.split("[a-zA-Z:=*+ ]");
                var sVal = aVal[aVal.length-1];
                opVal = Integer.parseInt(sVal);
            }
            testVal = Integer.parseInt(l4[l4.length-1]);
            ifTrueIdx = Integer.parseInt(l5[l5.length-1]);
            ifFalseIdx = Integer.parseInt(l6[l6.length-1]);
            monkeys.add(new Monkey(op, opVal, testVal, ifTrueIdx, ifFalseIdx));
            allItems.add(items);
        }
        return new PackOfMonkies(monkeys, allItems);
    }

    /*@BeforeAll
    public static void configure() throws IOException {
        monkeys = readMonkeysFromFile(filename);
    }*/

    /*@Test
    public void testMonkeys() {
        PackOfMonkies pack = new PackOfMonkies(testMonkeys, testItems);
        for (int i = 0; i < 20; i++) {
            pack.roundOfKeepAway();
        }
        assertEquals(10605, pack.monkeyBusiness());
    }

    @Test
    public void partOne() {
        for (int i = 0; i < 20; i++) {
            monkeys.roundOfKeepAway();
        }
        int mb = monkeys.monkeyBusiness();
        assertTrue(mb > 62901);
        assertTrue(mb < 70176);
        assertNotEquals(70176, mb);
        System.out.println("Monkey Business is: " + mb + ".");
    }*/

    @Test
    public void alternatePartOne() {
        PackOfMonkies pack = new PackOfMonkies(actualMonkeys, actualItems);
        for (int i = 0; i < 20; i++) {
            pack.roundOfKeepAway();
        }
        int mb = pack.monkeyBusiness();
        System.out.println("Monkey Business using manual actuals is: " + mb + ".");
    }
}
