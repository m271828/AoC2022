package day10;

public class AddX implements Instruction {
    private int add;
    private int calls = 0;

    public AddX(int val) {
        add = val;
    }

    @Override
    public int getCycles() {
        return 2;
    }

    @Override
    public int performOp(int val) {
        calls++;
        if (calls != getCycles()) {
            return val;
        }
        return val+add;
    }

    @Override
    public boolean isDone() {
        return calls == getCycles();
    }
}
