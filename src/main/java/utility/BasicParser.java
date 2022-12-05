package utility;

import java.util.ArrayList;
import java.util.List;

public class BasicParser {
    public enum Identifier {
        SKIP,
        STRING,
        INTEGER
    }

    public static List<List<Object>> parse(List<List<String>> input, List<Identifier> spec) {
        if (input == null || input.size() == 0) {
            return null;
        }

        ArrayList<List<Object>> parsed = new ArrayList<>();
        for (var line : input) {
            if (line.size() != spec.size()) {
                throw new RuntimeException("Line and spec are mismatched in length for parsing");
            }
            ArrayList<Object> parsedLine = new ArrayList<>();
            for (int i = 0; i < spec.size(); i++) {
                if (spec.get(i) == Identifier.SKIP) {
                    ;
                } else if (spec.get(i) == Identifier.STRING) {
                    parsedLine.add(line.get(i));
                } else if (spec.get(i) == Identifier.INTEGER) {
                    parsedLine.add(Integer.valueOf(line.get(i)));
                }
            }
            parsed.add(parsedLine);
        }

        return parsed;
    }

    public static List<List<Integer>> parseOutIntegers(List<List<String>> input, List<Identifier> spec) {
        if (input == null || input.size() == 0) {
            return null;
        }

        ArrayList<List<Integer>> parsed = new ArrayList<>();
        for (var line : input) {
            if (line.size() != spec.size()) {
                throw new RuntimeException("Line and spec are mismatched in length for parsing");
            }
            ArrayList<Integer> parsedLine = new ArrayList<>();
            for (int i = 0; i < spec.size(); i++) {
                if (spec.get(i) == Identifier.SKIP) {
                    ;
                } else if (spec.get(i) == Identifier.STRING) {
                    ;
                } else if (spec.get(i) == Identifier.INTEGER) {
                    parsedLine.add(Integer.valueOf(line.get(i)));
                }
            }
            parsed.add(parsedLine);
        }

        return parsed;
    }
}
