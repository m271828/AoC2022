package day11;

import java.math.BigInteger;

public class Monkey {
    public enum Op {
        SUM,
        MULT
    }
    public final Op op;
    public final BigInteger opVal;
    public final BigInteger testVal;
    public final int ifTrue;
    public final int ifFalse;

    public Monkey(Op op, Long opVal, long testVal, int ifTrue, int ifFalse) {
        this.op = op;
        this.opVal = opVal == null ? null : BigInteger.valueOf(opVal);
        this.testVal = BigInteger.valueOf(testVal);
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

    public BigInteger calcWorry(BigInteger value) {
        BigInteger otherVal = (opVal == null) ? value : opVal;
        if (op == Op.SUM) {
            return value.add(otherVal);
        } else {
            return value.multiply(otherVal);
        }
    }

    public BigInteger calcWorry(BigInteger value, BigInteger base) {
        BigInteger v2 = (opVal == null) ? value : opVal;
        if (op == Op.SUM) {
            return value.add(v2).mod(base);
        } else {
            return value.multiply(v2).mod(base);
        }
    }

    public int pass(BigInteger value) {
        if (value.mod(testVal).equals(BigInteger.ZERO)) {
            return ifTrue;
        }
        return ifFalse;
    }
}
