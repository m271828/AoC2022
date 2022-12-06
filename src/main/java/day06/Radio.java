package day06;

import java.util.Set;
import java.util.stream.Collectors;

public class Radio {
    private String input;

    public Radio(String data) {
        input = data;
    }

    public int findFirstUniqueN(int n) {
        int idx = n;
        while (idx < input.length()) {
            Set s = input.substring(idx-n, idx).chars().mapToObj(chr -> (char) chr).collect(Collectors.toSet());
            if (s.size() == n) {
                return idx;
            } else {
                idx++;
            }
        }
        return -1;
    }
}
