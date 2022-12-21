package day12;

import utility.Edge;
import utility.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private String path;
    private Vertex<GridLocation> lastVertex;

    public Path() {
        path = "";
        lastVertex = null;
    }

    public Path(Vertex<GridLocation> start) {
        path = start.value().value().toString();
        lastVertex = start;

    }

    public Path(Path path, Edge<GridLocation> edge) {
       this.path = path.getPath();
       this.path += edge.to().value().value().toString();
       lastVertex = edge.to();
    }

    public Vertex<GridLocation> getLastVertex() {
        return lastVertex;
    }

    public int length() {
        return path.length()-1;
    }

    public String getPath() {
        return path;
    }

    public boolean contains(Vertex<GridLocation> v) {
        return path.contains(v.value().value().toString());
    }
}
