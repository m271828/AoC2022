package day05;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Cargo {
    private ArrayList<Stack<String>> stacks;

    public Cargo(List<List<String>> stacks) {
        this.stacks = new ArrayList<>();
        for (int i = 0; i < stacks.size(); i++) {
            Stack<String> stack = new Stack<>();
            for (int j = 0; j < stacks.get(i).size(); j++) {
                stack.push(stacks.get(i).get(j));
            }
            this.stacks.add(stack);
        }
    }

    public void move(int number, int from, int to, boolean moveMany) {
        if (!moveMany) {
            for (int i = 0; i < number; i++) {
                stacks.get(to - 1).push(stacks.get(from - 1).pop());
            }
        } else {
            Stack<String> temp = new Stack<>();
            for (int i = 0; i < number; i++) {
                temp.push(stacks.get(from - 1).pop());
            }
            for (int i = 0; i < number; i++) {
                stacks.get(to - 1).push(temp.pop());
            }
        }
    }

    public void print() {
        for (int i = 0; i < stacks.size(); i++) {
            System.out.print("Stack " + i + ": ");
            for (int j = 0; j < stacks.get(i).size(); j++) {
                System.out.print(stacks.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public void printTopOfStacks() {
        for (int i = 0; i < stacks.size(); i++) {
            System.out.print(stacks.get(i).peek() + " ");
        }
        System.out.println();
    }

    public String getMessage() {
        String msg = "";
        for (int i = 0; i < stacks.size(); i++) {
            String s = stacks.get(i).peek();
            msg += s.substring(1,2);
        }
        return msg;
    }
}
