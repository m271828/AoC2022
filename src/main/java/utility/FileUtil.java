package utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<String> getLines(String filename) throws IOException {
        return Files.readAllLines(Path.of(filename));
    }

    public static List<List<String>> groupLines(String filename) throws IOException {
        var lines = getLines(filename);
        ArrayList<List<String>> groups = new ArrayList<>();
        ArrayList<String> current = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).isBlank()) {
                groups.add(current);
                current = new ArrayList<>();
            } else {
                current.add(lines.get(i));
            }
        }
        groups.add(current);
        return groups;
    }

    public static List<Associations.Pair<String>> splitLinesByHalf(String filename) throws IOException {
        var lines = getLines(filename);
        ArrayList<Associations.Pair<String>> pairs = new ArrayList<>();
        for (var line : lines) {
            pairs.add(new Associations.Pair(line.substring(0, line.length()/2), line.substring(line.length()/2)));
        }
        return pairs;
    }

    public static List<Associations.Pair<String>> splitLinesBy(String filename, String regexp) throws IOException {
        var lines = getLines(filename);
        ArrayList<Associations.Pair<String>> pairs = new ArrayList<>();
        for (String line : lines) {
            pairs.add(new Associations.Pair(line.split(regexp)));
        }
        return pairs;
    }

    public static List<Associations.Quad<String>> splitLinesBy(String filename, String outerSplit, String innerSplit) throws IOException {
        var lines = getLines(filename);
        ArrayList<Associations.Quad<String>> quads = new ArrayList<>();
        for (String line : lines) {
            quads.add(new Associations.Quad(line.split("[" + innerSplit + outerSplit + "]")));
        }
        return quads;
    }
}
