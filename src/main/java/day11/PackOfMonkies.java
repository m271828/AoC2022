package day11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackOfMonkies {
    private final ArrayList<Monkey> monkeys;
    private final ArrayList<ArrayList<Integer>> allItems;
    private final ArrayList<Integer> actions;

    public PackOfMonkies(List<Monkey> monkeys, List<List<Integer>> allItems) {
        this.monkeys = new ArrayList<>(monkeys);
        this.allItems = new ArrayList<>();
        this.actions = new ArrayList<>();
        System.out.println("Pack of Monkeys Starting Havoc, Initial State:");
        for (int i = 0; i < allItems.size(); i++) {
            this.allItems.add(new ArrayList<>(allItems.get(i)));
            actions.add(0);
            System.out.println("Monkey " + i + ":");
            this.monkeys.get(i).print();
            System.out.print("Current items: ");
            for(var item : this.allItems.get(i)) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }

    public void roundOfKeepAway() {
        int moves = 0;
        var oldSum = actions.stream().reduce(Integer::sum).orElseThrow();
        for (int i = 0; i < monkeys.size(); i++) {
            var m = monkeys.get(i);
            System.out.print("Monkey " + i + ": ");
            m.print();
            var items = allItems.get(i);
            actions.set(i, actions.get(i) + items.size());
            moves += items.size();
            for (var item : items) {
                int worry = m.calcWorry(item);
                System.out.println(" " +item + " changed to " + worry);
                worry /= 3;
                System.out.println(" Then reduced to " + worry);
                int newMonkey = m.pass(worry);
                System.out.println(" Given to Monkey " + newMonkey);
                allItems.get(newMonkey).add(worry);
            }
            allItems.set(i, new ArrayList<>());
        }
        System.out.print("Current number of actions for " + allItems.stream().map(ArrayList::size).reduce(Integer::sum).orElseThrow() + " items: ");
        for (int i = 0; i < actions.size(); i++) {
            System.out.print(actions.get(i) + " ");
        }
        var newSum = actions.stream().reduce(Integer::sum).orElseThrow();
        var diff = newSum - oldSum;
        System.out.println(" (Previously " + oldSum + " actions total, now " + newSum + ". Moves made was " + moves + " and diff is " + diff + ".");
    }

    public int monkeyBusiness() {
        ArrayList<Integer> localActions = new ArrayList<>(actions);
        Collections.sort(localActions);
        Collections.reverse(localActions);
        int m1 = localActions.get(0);
        int m2 = localActions.get(1);
        return m1*m2;
    }
}
