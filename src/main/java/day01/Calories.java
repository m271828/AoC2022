package day01;

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
        var lines = Files.readAllLines(Path.of(dataFile));
        int elfIndex = 0;

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).isBlank()) {
                elfIndex++;
            } else {
                if (supplies.size() == elfIndex) {
                    supplies.add(new ArrayList<>());
                }
                supplies.get(elfIndex).add(Integer.valueOf(lines.get(i)));
            }
        }

        for (ArrayList<Integer> elf : supplies) {
            Integer total = 0;
            for (Integer i : elf) {
                if (i != null) {
                    total += i;
                }
            }
            totalSupplies.add(total);
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
