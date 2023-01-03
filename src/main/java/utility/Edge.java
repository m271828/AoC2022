package utility;

public class Edge<T> {
    private Vertex<T> from, to;
    private int weight;

    public Edge(Vertex<T> from, Vertex<T> to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        from.addOutEdge(this);
        to.addInEdge(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (o.getClass() == this.getClass()) {
            var other = (Edge<T>) o;
            return to.equals(other.to) && from.equals(other.from) && weight == other.weight;
        }
        return false;
    }
}
