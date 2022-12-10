package day10;

import java.util.ArrayList;
import java.util.List;

public class Communicator {
    private Processor proc;
    private CRT crt;
    private boolean finished;
    private ArrayList<Integer> signalStrengths;

    public Communicator(List<Instruction> instructions) {
        proc = new Processor(instructions);
        crt = new CRT();
        finished = false;
        signalStrengths = new ArrayList<>();
    }

    public void run() {
        while (!finished) {
            int val = proc.cycle();
            log(val);
            finished = proc.finished();
            if(!finished) {
                crt.refresh(val);
            }
        }
    }

    public int getSignalSum() {
        return signalStrengths.stream().reduce(Integer::sum).orElseThrow();
    }

    public List<List<Character>> getImage() {
        return crt.getCrt();
    }

    private void log(int value) {
        if (proc.getTicks() % 20 == 0 && proc.getTicks() % 40 != 0) {
            signalStrengths.add(proc.getTicks()*value);
        }
    }
}
