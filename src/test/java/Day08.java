import day08.Forest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08 {
    private static String filename = "src/main/resources/day_08_input.txt";
    private static List<List<Integer>> test = List.of(
            List.of(3,0,3,7,3),
            List.of(2,5,5,1,2),
            List.of(6,5,3,3,2),
            List.of(3,3,5,4,9),
            List.of(3,5,3,9,0)
    );

    private static List<List<Integer>> data;
    private static Forest f;

    @BeforeAll
    public static void configure() throws IOException {
        var lines = FileUtil.getLines(filename);
        var tempData = new ArrayList<List<Integer>>();
        for (var row : lines) {
            var len = row.length();
            var intRow = new ArrayList<Integer>();
            for (int i = 0; i < len; i++) {
                intRow.add(Integer.valueOf(row.substring(i,i+1)));
            }
            tempData.add(intRow);
        }
        data = tempData;
        f = new Forest(data);
    }

    @Test
    public void basicTest() {
        Forest f = new Forest(test);
        assertEquals(21, f.countVisibleTrees());
        assertEquals(8, f.findMostScenicTreeScore());
    }

    @Test
    public void partOne() {
        int visible = f.countVisibleTrees();
        System.out.println("There are " + visible + " trees visible.");
        assertEquals(1543, visible);
    }

    @Test
    public void partTwo() {
        int best = f.findMostScenicTreeScore();
        System.out.println("The most scenic view is " + best + ".");
        assertEquals(595080, best);
    }
}
