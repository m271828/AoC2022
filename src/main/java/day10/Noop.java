package day10;

public class Noop implements Instruction {
    @Override
    public int getCycles() {
        return 1;
    }

    @Override
    public int performOp(int val) {
        return val;
    }

    @Override
    public boolean isDone() {
        return true;
    }
}
