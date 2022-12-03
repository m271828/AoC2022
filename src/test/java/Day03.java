import day03.RuckSack;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03 {
    private static String v1 = "vJrwpWtwJgWrhcsFMMfFFhFp";
    private static String v2 = "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL";
    private static String v3 = "PmmdzqPrVvPwwTWBwg";

    @Test
    public void splitString() {
        assertEquals("vJrwpWtwJgWr", v1.substring(0, v1.length()/2));
        assertEquals("hcsFMMfFFhFp", v1.substring(v1.length()/2));

        assertEquals("jqHRNqRjqzjGDLGL", v2.substring(0, v2.length()/2));
        assertEquals("rsFMfFZSrLrFZsSL", v2.substring(v2.length()/2));

        assertEquals("PmmdzqPrV", v3.substring(0, v3.length()/2));
        assertEquals("vPwwTWBwg", v3.substring(v3.length()/2));
    }

    @Test
    public void findCommonItem() {
        RuckSack sack1 = new RuckSack(v1.substring(0, v1.length()/2), v1.substring(v1.length()/2));
        assertEquals('p', sack1.findCommonItem());
        RuckSack sack2 = new RuckSack(v2.substring(0, v2.length()/2), v2.substring(v2.length()/2));
        assertEquals('L', sack2.findCommonItem());
        RuckSack sack3 = new RuckSack(v3.substring(0, v3.length()/2), v3.substring(v3.length()/2));
        assertEquals('P', sack3.findCommonItem());
    }

    @Test
    public void score() {
        assertEquals(1, RuckSack.score('a'));
        assertEquals(26, RuckSack.score('z'));
        assertEquals(27, RuckSack.score('A'));
        assertEquals(52, RuckSack.score('Z'));
    }

    @Test
    public void runPartOne() throws IOException {
        var lines = Files.readAllLines(Path.of("src/main/resources/day_03_input.txt"));
        Integer prioritySum = 0;
        for (String line : lines) {
            RuckSack sack = new RuckSack(line.substring(0, line.length()/2), line.substring(line.length()/2));
            prioritySum += RuckSack.score(sack.findCommonItem());
        }

        System.out.println("Sum of priority scores for sacks is: " + prioritySum);
    }

    @Test
    public void findCommonAmongSacks() {
        RuckSack sack1 = new RuckSack(v1.substring(0, v1.length()/2), v1.substring(v1.length()/2));
        RuckSack sack2 = new RuckSack(v2.substring(0, v2.length()/2), v2.substring(v2.length()/2));
        RuckSack sack3 = new RuckSack(v3.substring(0, v3.length()/2), v3.substring(v3.length()/2));

        assertEquals('r', RuckSack.teamCommonItem(sack1, sack2, sack3));
    }

    @Test
    public void runPartTwo() throws IOException {
        ArrayList<RuckSack> sacks = new ArrayList<>();
        var lines = Files.readAllLines(Path.of("src/main/resources/day_03_input.txt"));
        Integer prioritySum = 0;
        for (String line : lines) {
            sacks.add(new RuckSack(line.substring(0, line.length()/2), line.substring(line.length()/2)));
        }
        for (int i = 0; i < sacks.size(); i += 3) {
            var s1 = sacks.get(i);
            var s2 = sacks.get(i+1);
            var s3 = sacks.get(i+2);
            prioritySum += RuckSack.score(RuckSack.teamCommonItem(s1, s2, s3));
        }

        System.out.println("Sum of team priority scores is: " + prioritySum);
    }
}
