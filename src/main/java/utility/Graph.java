package utility;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph<T> implements Comparable {
    public enum Type {
        UNDIRECTED,
        DIRECTED
    }

    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;
    private HashMap<Vertex<T>, Integer> depths;
    private Type direction;

    public Graph(Type direction) {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        this.direction = direction;
        depths = new HashMap<>();
    }

    public boolean containsVertex(Vertex<T> v) {
        return vertices.contains(v);
    }

    public void addVertex(Vertex<T> v) {
        if (!containsVertex(v)) {
            vertices.add(v);
        }
    }

    public boolean containsEdge(Edge<T> e) {
        return edges.contains(e);
    }

    public void addEdge(Edge<T> e) {
        if (!containsEdge(e) && containsVertex(e.from()) && containsVertex(e.to())) {
            edges.add(e);
            var fromDepth = depths.getOrDefault(e.from(), 0);
            var toDepth = depths.getOrDefault(e.to(), Integer.MAX_VALUE);
            depths.put(e.to(), Integer.min(fromDepth+1, toDepth));
        }
    }

    public int distance(Vertex<T> dest) {
        if (!vertices.contains(dest)) {
            throw new RuntimeException("End not in graph");
        }
        return depths.get(dest);
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
}
