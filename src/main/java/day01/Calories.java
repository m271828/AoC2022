package day01;

import utility.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Calories {
    ArrayList<ArrayList<Integer>> supplies = new ArrayList<>();
    ArrayList<Integer> totalSupplies = new ArrayList<>();

    public Calories(String dataFile) throws IOException {
        var supplies = FileUtil.groupLines(dataFile);

        for (var sack : supplies) {
            Integer calories = 0;
            for (var item : sack) {
                calories += Integer.valueOf(item);
            }
            totalSupplies.add(calories);
        }
    }

    public Integer numberOfElves() {
        return supplies.size();
    }

    public Integer getMax() {
        return Collections.max(totalSupplies);
    }

    public Integer getMin() {
        return Collections.min(totalSupplies);
    }

    public Integer[] getNMax(int num) {
        var sorted = totalSupplies.stream().sorted().toList();
        return sorted.subList(sorted.size()-3, sorted.size()).toArray(Integer[]::new);
    }

    public Integer sumNMax(int num) {
        return Arrays.stream(getNMax(num)).reduce((Integer i, Integer j) -> i + j).get();
    }
}
