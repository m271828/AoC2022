package day11;

import java.math.BigInteger;

public class Monkey {
    public enum Op {
        SUM,
        MULT
    }
    public final Op op;
    public final Long opVal;
    public final long testVal;
    public final int ifTrue;
    public final int ifFalse;

    public Monkey(Op op, Long opVal, long testVal, int ifTrue, int ifFalse) {
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

    public long calcWorry(long value) {
        long otherVal = (opVal == null) ? value : opVal;
        if (op == Op.SUM) {
            return value + otherVal;
        } else {
            return value*otherVal;
        }
    }

    public long calcWorry(long value, BigInteger base) {
        BigInteger safeVal = BigInteger.valueOf(value);
        BigInteger v2 = (opVal == null) ? safeVal : BigInteger.valueOf(opVal);
        BigInteger result;
        if (op == Op.SUM) {
            result = safeVal.add(v2).mod(base);
        } else {
            result = safeVal.multiply(v2).mod(base);
        }
        return result.longValue();
    }

    public int pass(long value) {
        if (value % testVal == 0) {
            return ifTrue;
        }
        return ifFalse;
    }
}
