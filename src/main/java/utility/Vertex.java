package utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Vertex<T> {
    private final T value;
    private final ArrayList<Edge<T>> inEdges;
    private final ArrayList<Edge<T>> outEdges;

    public Vertex(T value) {
        this.value = value;
        this.inEdges = new ArrayList<>();
        this.outEdges = new ArrayList<>();
    }

    public void addInEdge(Edge<T> e) {
        if (!inEdges.contains(e)) {
            inEdges.add(e);
            inEdges.sort(Comparator.comparingInt(Edge::weight));
        }
    }

    public void addOutEdge(Edge<T> e) {
        if (!outEdges.contains(e)) {
            outEdges.add(e);
            outEdges.sort(Comparator.comparingInt(Edge::weight));
        }
    }

    public List<Edge<T>> inEdges() {
        return Collections.unmodifiableList(inEdges);
    }

    public List<Edge<T>> outEdges() {
        return Collections.unmodifiableList(outEdges);
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
            return Objects.equals(value, v.value) && inEdges.equals(v.inEdges) && outEdges.equals(v.outEdges);
        }
    }
}
