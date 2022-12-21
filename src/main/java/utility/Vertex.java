package utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Vertex<T> {
    private final T value;
    private final ArrayList<Edge<T>> edges;

    private Vertex() {
        this.value = null;
        this.edges = new ArrayList<>();
    }

    public Vertex(T value) {
        this.value = value;
        edges = new ArrayList<>();
    }

    public void addEdge(Edge<T> e) {
        if (!edges.contains(e)) {
            edges.add(e);
        }
    }

    public List<Edge<T>> edges() {
        return Collections.unmodifiableList(edges);
    }

    public T value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        } else if (this == o) {
            return true;
        } else if (o.getClass() != getClass()) {
            return false;
        } else {
            var v = (Vertex<T>) o;
            return Objects.equals(value, v.value) && Objects.equals(edges, v.edges);
        }
    }
}
