package day10;

public interface Instruction {
    int getCycles();
    int performOp(int val);
    boolean isDone();
}
