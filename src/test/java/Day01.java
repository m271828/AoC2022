import day01.Calories;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01 {
    private Calories cals = new Calories("src/main/resources/day_01_input.txt");

    public Day01() throws IOException {
    }

    @Test
    public void part1() throws IOException {
        System.out.println("Number of elves: " + cals.numberOfElves());
        System.out.println("Least Calories: " + cals.getMin());
        System.out.println("Most Calories: " + cals.getMax());
        assertEquals(69912, cals.getMax());
    }

    @Test
    public void part2() {
        var threeMax = cals.getNMax(3);
        System.out.println("Top 3 Totals: " + threeMax[0] + ", " + threeMax[1] + ", " + threeMax[2]);
        System.out.println("Total Cals: " + cals.sumNMax(3));
        assertEquals(208180, cals.sumNMax(3));
    }
}
