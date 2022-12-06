import day06.Radio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.FileUtil;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06 {
    private static String filename = "src/main/resources/day_06_input.txt";
    private static String input;

    @BeforeAll
    public static void configure() throws IOException {
        input = FileUtil.getLines(filename).get(0);
    }

    @Test
    public void examples() {
        assertEquals(7, new Radio("mjqjpqmgbljsphdztnvjfqwrcgsmlb").findFirstUniqueN(4));
        assertEquals(5, new Radio("bvwbjplbgvbhsrlpgdmjqwftvncz").findFirstUniqueN(4));
        assertEquals(6, new Radio("nppdvjthqldpwncqszvftbrmjlhg").findFirstUniqueN(4));
        assertEquals(10, new Radio("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg").findFirstUniqueN(4));
        assertEquals(11, new Radio("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw").findFirstUniqueN(4));
        assertEquals(19, new Radio("mjqjpqmgbljsphdztnvjfqwrcgsmlb").findFirstUniqueN(14));
        assertEquals(23, new Radio("bvwbjplbgvbhsrlpgdmjqwftvncz").findFirstUniqueN(14));
        assertEquals(23, new Radio("nppdvjthqldpwncqszvftbrmjlhg").findFirstUniqueN(14));
        assertEquals(29, new Radio("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg").findFirstUniqueN(14));
        assertEquals(26, new Radio("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw").findFirstUniqueN(14));
    }

    @Test
    public void partOne() {
        int loc = new Radio(input).findFirstUniqueN(4);
        System.out.println("First repeat of 4 unique characters is at " + loc + " of " + input.length() + " characters.");
    }

    @Test
    public void partTwo() {
        int loc = new Radio(input).findFirstUniqueN(14);
        System.out.println("First repeat of 14 unique characters is at " + loc + " of " + input.length() + " characters.");
    }
}
