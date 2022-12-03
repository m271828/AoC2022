package day03;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RuckSack {
    private HashMap<Character, Integer> pocketOne;
    private HashMap<Character, Integer> pocketTwo;

    public RuckSack(String pocketOne, String pocketTwo) {
        this.pocketOne = new HashMap<>();
        for (int i = 0; i < pocketOne.length(); i++) {
            var count = this.pocketOne.getOrDefault(pocketOne.charAt(i), 0);
            this.pocketOne.put(pocketOne.charAt(i), count + 1);
        }
        this.pocketTwo = new HashMap<>();
        for (int i = 0; i < pocketTwo.length(); i++) {
            var count = this.pocketTwo.getOrDefault(pocketTwo.charAt(i), 0);
            this.pocketTwo.put(pocketTwo.charAt(i), count + 1);
        }
    }

    public Character findCommonItem() {
        var setOne = pocketOne.keySet();
        var setTwo = pocketTwo.keySet();
        setOne.retainAll(setTwo);
        if (setOne.size() == 1) {
            return setOne.toArray(Character[]::new)[0];
        }
        throw new RuntimeException("No or more than one character found in common between pockets.");
    }

    public static int score(Character c) {
        if (c.compareTo('a') >= 0) {
            return c.compareTo('a') + 1;
        } else {
            return c.compareTo('A') + 27;
        }
    }

    public static Character teamCommonItem(RuckSack sack1, RuckSack sack2, RuckSack sack3) {
        HashSet<Character> combinedSack1 = new HashSet<>();
        combinedSack1.addAll(sack1.pocketOne.keySet());
        combinedSack1.addAll(sack1.pocketTwo.keySet());
        HashSet<Character> combinedSack2 = new HashSet<>();
        combinedSack2.addAll(sack2.pocketOne.keySet());
        combinedSack2.addAll(sack2.pocketTwo.keySet());
        HashSet<Character> combinesSack3 = new HashSet<>();
        combinesSack3.addAll(sack3.pocketOne.keySet());
        combinesSack3.addAll(sack3.pocketTwo.keySet());

        combinedSack1.retainAll(combinedSack2);
        combinedSack1.retainAll(combinesSack3);

        if (combinedSack1.size() == 1) {
            return combinedSack1.toArray(Character[]::new)[0];
        }
        throw new RuntimeException("No or more than one item found in common among sacks.");
    }
}
