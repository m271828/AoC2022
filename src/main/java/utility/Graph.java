package utility;

import java.util.ArrayList;
import java.util.List;

public class Graph<T> implements Comparable {
    public enum Type {
        UNDIRECTED,
        DIRECTED
    }

    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;
    private Type direction;

    public Graph(Type direction) {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        this.direction = direction;
    }

    public boolean containsVertex(Vertex<T> v) {
        return vertices.contains(v);
    }

    public void addVertex(Vertex<T> v) {
        if (!containsVertex(v)) {
            vertices.add(v);
            for (var e : v.outEdges()) {
                addEdge(e);
            }
        }
    }

    public boolean containsEdge(Edge<T> e) {
        return edges.contains(e);
    }

    public void addEdge(Edge<T> e) {
        if (!containsEdge(e)) {
            edges.add(e);
            if (!containsVertex(e.from())) {
                vertices.add(e.from());
            }
            if (!containsVertex(e.to())) {
                vertices.add(e.to());
            }
            if (direction == Type.UNDIRECTED) {
                addEdge(new Edge<>(e.to(), e.from(), e.weight()));
            }
        }
    }

    public String toString() {
        String s = "Vertices: ";
        for (var v : vertices) {
            s += v.value().toString() + ", ";
        }
        s += "Done";
        return s;
    }

    public int compareTo(Graph<T> other) {
        return toString().compareTo(other.toString());
    }

    @Override
    public int compareTo(Object o) {
        if(o.getClass() == this.getClass()) {
            return compareTo((Graph<T>) o);
        }
        return toString().compareTo(o.toString());
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }
}
