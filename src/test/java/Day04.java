import day04.Assignment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.Associations;
import utility.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04 {
    private static String fileName = "src/main/resources/day_04_input.txt";
    private static List<Associations.Pair<Assignment>> assignments = new ArrayList<>();

    @BeforeAll
    public static void configure() throws IOException {
        var quads = FileUtil.splitLinesBy(fileName, ",", "-");
        for (var q : quads) {
            Assignment a0, a1;
            a0 = new Assignment(Integer.valueOf(q.first()), Integer.valueOf(q.second()));
            a1 = new Assignment(Integer.valueOf(q.third()), Integer.valueOf(q.fourth()));
            assignments.add(new Associations.Pair<>(a0, a1));
        }
    }

    @Test
    public void partOne() {
        Integer count = 0;
        for (Associations.Pair<Assignment> pair : assignments) {
            if (pair.first().covers(pair.second()) || pair.second().covers(pair.first())) {
                count++;
            }
        }
        System.out.println(count + " pairs have assignments with idle elves");
        assertEquals(503, count);
    }

    @Test
    public void partTwo() {
        Integer count = 0;
        for (Associations.Pair<Assignment> pair : assignments) {
            if (pair.first().overlaps(pair.second()) || pair.second().overlaps(pair.first())) {
                count++;
            }
        }
        System.out.println(count + " pairs have overlaps");
        assertEquals(827, count);
    }
}
