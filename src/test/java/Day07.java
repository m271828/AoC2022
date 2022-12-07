import day07.NavigationParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.FileUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07 {
    private static String filename = "src/main/resources/day_07_input.txt";

    private static String test = "$ cd /\n" +
            "$ ls\n" +
            "dir a\n" +
            "14848514 b.txt\n" +
            "8504156 c.dat\n" +
            "dir d\n" +
            "$ cd a\n" +
            "$ ls\n" +
            "dir e\n" +
            "29116 f\n" +
            "2557 g\n" +
            "62596 h.lst\n" +
            "$ cd e\n" +
            "$ ls\n" +
            "584 i\n" +
            "$ cd ..\n" +
            "$ cd ..\n" +
            "$ cd d\n" +
            "$ ls\n" +
            "4060174 j\n" +
            "8033020 d.log\n" +
            "5626152 d.ext\n" +
            "7214296 k";
    private static int spaceNeeded = 30000000;
    private static int fsSize = 70000000;
    private static List<List<String>> input;
    private static NavigationParser parser;

    @BeforeAll
    public static void configure() throws IOException {
        input = FileUtil.splitLines(filename, " ");
        parser = new NavigationParser(input);
    }

    @Test
    public void parse() {
        var listedTest = test.lines().map(s -> s.split(" ")).map(l -> Arrays.stream(l).toList()).toList();
        NavigationParser parser = new NavigationParser(listedTest);
        var root = parser.getRoot();
        assertEquals(584, root.getChild("a").getChild("e").getSize());
        assertEquals(94853, root.getChild("a").getSize());
        assertEquals(24933642, root.getChild("d").getSize());
        assertEquals(48381165, root.getSize());
        assertEquals(95437, root.filterSize(100000));
        assertEquals(24933642, root.findMinCeilingDirectory(spaceNeeded - (fsSize - root.getSize())).getSize());
    }

    @Test
    public void partOne() {
        var total = parser.getRoot().filterSize(100000);
        System.out.println("Directories with size at most 100000 total: " + total);
        assertEquals(1084134, total);
    }

    @Test
    public void partTwo() {
        var root = parser.getRoot();
        int size = root.findMinCeilingDirectory(spaceNeeded - (fsSize - root.getSize())).getSize();
        System.out.println("Smallest directory to make space has size " + size);
        assertEquals(6183184, size);
    }
}
