package utility;

public class Edge<T> {
    private Vertex<T> from, to;
    private int weight;

    public Edge(Vertex<T> from, Vertex<T> to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int weight() {
        return weight;
    }

    public Vertex<T> from() {
        return from;
    }

    public Vertex<T> to() {
        return to;
    }
}
