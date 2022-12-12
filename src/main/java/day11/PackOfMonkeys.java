package day11;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackOfMonkeys {
    private final ArrayList<Monkey> monkeys;
    private final ArrayList<ArrayList<Long>> allItems;
    private final ArrayList<Long> actions;
    private final Long worryReductionFactor;
    private final BigInteger base;
    private final boolean debug = false;

    public PackOfMonkeys(List<Monkey> monkeys, List<List<Long>> allItems, Long worryReductionFactor) {
        this.monkeys = new ArrayList<>(monkeys);
        this.worryReductionFactor = worryReductionFactor;
        this.allItems = new ArrayList<>();
        this.actions = new ArrayList<>();
        if (debug) {
            System.out.println("Pack of Monkeys Starting Havoc, Initial State:");
        }
        var calcBase = BigInteger.ONE;
        for (int i = 0; i < allItems.size(); i++) {
            this.allItems.add(new ArrayList<>(allItems.get(i)));
            actions.add(0L);
            calcBase = calcBase.multiply(BigInteger.valueOf(this.monkeys.get(i).testVal));
            if (debug) {
                System.out.println("Monkey " + i + ":");
                this.monkeys.get(i).print();
                System.out.print("Current items: ");
                for (var item : this.allItems.get(i)) {
                    System.out.print(item + " ");
                }
                System.out.println();
            }
        }
        base = calcBase;
    }

    public void roundOfKeepAway() {
        int moves = 0;
        var oldSum = actions.stream().reduce(Long::sum).orElseThrow();
        for (int i = 0; i < monkeys.size(); i++) {
            var m = monkeys.get(i);
            if (debug) {
                System.out.print("Monkey " + i + ": ");
                m.print();
            }
            var items = allItems.get(i);
            actions.set(i, actions.get(i) + items.size());
            moves += items.size();
            for (var item : items) {
                int newMonkey;
                long worry;
                if (worryReductionFactor == null) {
                    worry = m.calcWorry(item, base);
                } else {
                    worry = m.calcWorry(item) / worryReductionFactor;
                }
                newMonkey = m.pass(worry);
                allItems.get(newMonkey).add(worry);
            }
            allItems.set(i, new ArrayList<>());
        }
        if (debug) {
            System.out.print("Current number of actions for " + allItems.stream().map(ArrayList::size).reduce(Integer::sum).orElseThrow() + " items: ");
            for (var action : actions) {
                System.out.print(action + " ");
            }
            var newSum = actions.stream().reduce(Long::sum).orElseThrow();
            var diff = newSum - oldSum;
            System.out.println(" (Previously " + oldSum + " actions total, now " + newSum + ". Moves made was " + moves + " and diff is " + diff + ".");
        }
    }

    public BigInteger monkeyBusiness() {
        ArrayList<Long> localActions = new ArrayList<>(actions);
        Collections.sort(localActions);
        Collections.reverse(localActions);
        BigInteger m1 = BigInteger.valueOf(localActions.get(0));
        BigInteger m2 = BigInteger.valueOf(localActions.get(1));
        if (localActions.get(localActions.size()-1) < 0) {
            throw new RuntimeException("Action count wrapped");
        }
        return m1.multiply(m2);
    }
}
