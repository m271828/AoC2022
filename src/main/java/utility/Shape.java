package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Shape {
    private final ArrayList<Cube> units = new ArrayList<>();

    public Shape(Cube c) {
        units.add(c);
    }

    public boolean add(Cube c) {
        if (units.stream().anyMatch(x -> x.connectsTo(c))) {
            units.add(c);
            return true;
        }
        return false;
    }

    public boolean add(Shape s) {
        if (units.stream().anyMatch(x -> s.units.stream().anyMatch(y -> y.connectsTo(x)))) {
            units.addAll(s.units);
            return true;
        }
        return false;
    }

    public long surfaceArea() {
        var surfaces = units.stream().flatMap(c -> c.surfaces().stream()).toList();
        var distinctSurfaces = surfaces.stream().distinct().toList();
        return distinctSurfaces.size() - (surfaces.size() - distinctSurfaces.size());
    }

    public long findExteriorArea() {
        var graph = new Graph<TrackingCube>(Graph.Type.UNDIRECTED);
        var mapping = new HashMap<String, Vertex<TrackingCube>>();
        var visited = new HashMap<String, Boolean>();
        var lbX = minX()-1;
        var ubX = maxX()+1;
        var lbY = minY()-1;
        var ubY = maxY()+1;
        var lbZ = minZ()-1;
        var ubZ = maxZ()+1;
        var count = 0;

        for (int x = lbX; x <= ubX; x++) {
            for (int y = lbY; y <= ubY; y++) {
                for (int z = lbZ; z <= ubZ; z++) {
                    var point = new Vertex<>(new TrackingCube(Space.EMPTY, new Cube(x, y, z)));
                    graph.addVertex(point);
                    mapping.put(point.value().toString(), point);
                    connectIfExists(x-1, y, z, point, mapping, graph);
                    connectIfExists(x+1, y, z, point, mapping, graph);
                    connectIfExists(x, y-1, z, point, mapping, graph);
                    connectIfExists(x, y+1, z, point, mapping, graph);
                    connectIfExists(x, y, z-1, point, mapping, graph);
                    connectIfExists(x, y, z+1, point, mapping, graph);
                }
            }
        }

        for (var u : units) {
            var string = "(" + u.x() + "," + u.y() + "," + u.z() + ")";
            if (mapping.containsKey(string)) {
                var point = mapping.get(string);
                point.value().setCube(u);
                point.value().setFilling(Space.LAVA);
                mapping.put(string, point);
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        var stack = new Stack<Vertex<TrackingCube>>();
        var start = "(" + lbX + "," + lbY + "," + lbZ + ")";
        stack.push(mapping.get(start));
        while (!stack.isEmpty()) {
            var point = stack.pop();
            if (visited.containsKey(point.toString())) {
                continue;
            } else {
                if (point.value().filling() == Space.LAVA) {
                    count++;
                } else if (point.value().filling() == Space.EMPTY) {
                    for (var e : point.outEdges()) {
                        var name = e.to().value().toString();
                        if (visited.containsKey(name)) {
                            continue;
                        }
                        stack.push(e.to());
                    }
                    point.value().setFilling(Space.FILLED);
                    mapping.put(point.value().toString(), point);
                    visited.put(point.value().toString(), true);
                }
            }
        }
        return count;
    }

    private void connectIfExists(int x, int y, int z, Vertex<TrackingCube> point, HashMap<String, Vertex<TrackingCube>> mapping, Graph<TrackingCube> graph) {
        var string = "(" + x + "," + y + "," + z + ")";
        if (mapping.containsKey(string)) {
            var neighbor = mapping.get(string);
            graph.addEdge(new Edge<>(point, neighbor, 0));
        }
    }

    private class TrackingCube {
        private Space space;
        private Cube cube;

        public TrackingCube(Space s, Cube c) {
            space = s;
            cube = c;
        }

        public void setFilling(Space space) {
            this.space = space;
        }

        public Space filling() {
            return space;
        }

        public void setCube(Cube cube) {
            this.cube = cube;
        }

        @Override
        public String toString() {
            return "(" + cube.x() + "," + cube.y() + "," + cube.z() + ")";
        }
    }

    private enum Space {
        EMPTY,
        FILLED,
        LAVA
    }

    private int minX() {
        return units.stream().map(Cube::x).min(Integer::compareTo).orElseThrow();
    }

    private int maxX() {
        return units.stream().map(Cube::x).max(Integer::compareTo).orElseThrow();
    }

    private int minY() {
        return units.stream().map(Cube::y).min(Integer::compareTo).orElseThrow();
    }

    private int maxY() {
        return units.stream().map(Cube::y).max(Integer::compareTo).orElseThrow();
    }

    private int minZ() {
        return units.stream().map(Cube::z).min(Integer::compareTo).orElseThrow();
    }

    private int maxZ() {
        return units.stream().map(Cube::z).max(Integer::compareTo).orElseThrow();
    }
}
