package day11;

public class Monkey {
    public enum Op {
        SUM,
        MULT
    }
    public final Op op;
    public final Integer opVal;
    public final Integer testVal;
    public final Integer ifTrue;
    public final Integer ifFalse;
    private int actions = 0;

    public Monkey(Op op, Integer opVal, Integer testVal, Integer ifTrue, Integer ifFalse) {
        this.op = op;
        this.opVal = opVal;
        this.testVal = testVal;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    public void print() {
        String opStart = op == Op.SUM ? "old + " : "old * ";
        String opEnd = opVal == null ? "old" : opVal.toString();
        String expr = opStart + opEnd;
        String testExpr = "If worry is divisible by " + testVal + " then pass to " + ifTrue + " otherwise " + ifFalse + ".";
        System.out.println("Increase worry by " + expr + ". " + testExpr);
    }

    public int calcWorry(int value) {
        int otherVal = (opVal == null) ? value : opVal;
        if (op == Op.SUM) {
            return value + otherVal;
        } else {
            return value * otherVal;
        }
    }

    public int pass(int value) {
        if (value % testVal == 0) {
            return ifTrue;
        }
        return ifFalse;
    }
}
