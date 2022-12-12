import day11.Monkey;
import day11.PackOfMonkeys;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.FileUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11 {
    private static final String filename = "src/main/resources/day_11_input.txt";
    private static final List<Monkey> testMonkeys = List.of(
            new Monkey(Monkey.Op.MULT, 19L, 23L, 2, 3),
            new Monkey(Monkey.Op.SUM, 6L, 19L, 2, 0),
            new Monkey(Monkey.Op.MULT, null, 13L, 1, 3),
            new Monkey(Monkey.Op.SUM, 3L, 17L, 0, 1)
    );

    private static final List<List<Long>> testItems = List.of(
            List.of(79L, 98L),
            List.of(54L, 65L, 75L, 74L),
            List.of(79L, 60L, 97L),
            List.of(74L)
    );

    private static PackOfMonkeys readMonkeysFromFile(String filename, Long worryFactor) throws IOException {
        var groupLines = FileUtil.groupLines(filename);
        ArrayList<Monkey> monkeys = new ArrayList<>();
        ArrayList<List<Long>> allItems = new ArrayList<>();
        for (var group : groupLines) {
            ArrayList<Long> items = new ArrayList<>();
            Monkey.Op op;
            Long opVal = null;
            Long testVal;
            int ifTrueIdx;
            int ifFalseIdx;
            assertEquals(6, group.size());
            var l2 = group.get(1).split("[a-zA-Z:, ]");
            var l3 = group.get(2);
            var l4 = group.get(3).split("[a-zA-Z: ]");
            var l5 = group.get(4).split("[a-zA-Z: ]");
            var l6 = group.get(5).split("[a-zA-Z: ]");
            for (var l : l2) {
                if (!l.isEmpty()) {
                    items.add(Long.parseLong(l));
                }
            }
            int firstOld = l3.indexOf("old");
            int secondOld = l3.lastIndexOf("old");
            int plusIdx = l3.indexOf("+");
            if (firstOld == secondOld) {
                String[] aVal = l3.split("[a-zA-Z:=*+ ]");
                var sVal = aVal[aVal.length-1];
                opVal = Long.parseLong(sVal);
                if (plusIdx != -1) {
                    op = Monkey.Op.SUM;
                } else {
                    op = Monkey.Op.MULT;
                }
            } else {
                if (plusIdx != -1) {
                    op = Monkey.Op.SUM;
                } else {
                    op = Monkey.Op.MULT;
                }
            }
            testVal = Long.parseLong(l4[l4.length-1]);
            ifTrueIdx = Integer.parseInt(l5[l5.length-1]);
            ifFalseIdx = Integer.parseInt(l6[l6.length-1]);
            monkeys.add(new Monkey(op, opVal, testVal, ifTrueIdx, ifFalseIdx));
            allItems.add(items);
        }
        return new PackOfMonkeys(monkeys, allItems, worryFactor);
    }

    @Test
    public void testMonkeys() {
        System.out.println();
        var pack = new PackOfMonkeys(testMonkeys, testItems, 3L);
        for (int i = 0; i < 20; i++) {
            pack.roundOfKeepAway();
        }
        assertEquals(new BigInteger("10605"), pack.monkeyBusiness());

        var partTwoPack = new PackOfMonkeys(testMonkeys, testItems, null);
        for (int i = 0; i < 10000; i++) {
            partTwoPack.roundOfKeepAway();
        }
        assertEquals(new BigInteger("2713310158"), partTwoPack.monkeyBusiness());
    }

    @Test
    public void partOne() throws IOException {
        var monkeys = readMonkeysFromFile(filename, 3L);
        for (int i = 0; i < 20; i++) {
            monkeys.roundOfKeepAway();
        }
        var mb = monkeys.monkeyBusiness();
        System.out.println("Monkey Business is: " + mb + ".");
        assertEquals(new BigInteger("69918"), mb);
    }

    @Test
    public void partTwo() throws IOException {
        /*PackOfMonkeys<BigInteger> monkeys = readMonkeysFromFile(filename, BigInteger::new, BigInteger::add, BigInteger::multiply, (x, y) -> x.mod(y).equals(BigInteger.ZERO));
        for (int i = 0; i < 10000; i++) {
            monkeys.roundOfKeepAway();
        }
        var mb = monkeys.monkeyBusiness();
        assertEquals(1, mb.compareTo(new BigInteger("17748207116")));
        System.out.println("Monkey business is: " + mb + ".");*/
    }
}
