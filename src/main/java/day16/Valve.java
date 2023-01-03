package day16;

public class Valve {
    private int flowRate;
    private String name;

    public Valve(String name, int flowRate) {
        this.flowRate = flowRate;
        this.name = name;
    }

    public int getFlowRate() {
        return flowRate;
    }

    public String getName() {
        return name;
    }
}
