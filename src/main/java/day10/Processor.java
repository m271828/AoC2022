package day10;

import java.util.List;

public class Processor {
    private int x = 1;
    private int tick = 0;
    private List<Instruction> instructions;
    private int instructionIdx = -1;

    public Processor(List<Instruction> instructions) {
        this.instructions = List.copyOf(instructions);
    }

    public int cycle() {
        tick++;
        if (instructionIdx == -1) {
            instructionIdx++;
            return x;
        } else if (instructionIdx < instructions.size()) {
            Instruction curr = instructions.get(instructionIdx);
            x = curr.performOp(x);
            if (curr.isDone()) {
                instructionIdx++;
            }
        }
        return x;
    }

    public boolean finished() {
        return instructionIdx >= instructions.size();
    }

    public int getTicks() {
        return tick;
    }
}
