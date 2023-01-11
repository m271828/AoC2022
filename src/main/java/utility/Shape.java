package utility;

import java.util.ArrayList;
import java.util.List;
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
        var missingSpaces = new ArrayList<Shape>();
        for (int x = minX() + 1; x < maxX(); x++) {
            for (int y = minY() + 1; y < maxY(); y++) {
                for (int z = minZ() + 1; z < maxZ(); z++) {
                     var point = new Cube(x, y, z);
                     if (units.stream().noneMatch(c -> c.equals(point))) {
                         if (missingSpaces.stream().noneMatch(s -> s.add(point))) {
                             missingSpaces.add(new Shape(point));
                         }
                     }
                }
            }
        }

        // var actualSpaces = removeOpenEdges(missingSpaces);

        var faces = units.stream().flatMap(c -> c.surfaces().stream()).distinct().collect(Collectors.toCollection(ArrayList::new));
        var missingFaces = missingSpaces.stream().flatMap(s -> s.units.stream()).flatMap(c -> c.surfaces().stream()).distinct().toList();
        int interiorSides = 0;
        for (var m : missingFaces) {
            if (faces.stream().anyMatch(c -> c.equals(m))) {
                interiorSides++;
                faces.remove(m);
            }
        }
        var sides = units.stream().flatMap(c -> c.surfaces().stream()).count();
        var distinctSides = units.stream().flatMap(c -> c.surfaces().stream()).distinct().count();
        return distinctSides - (sides - distinctSides) - interiorSides;
    }

    private List<Cube> removeOpenEdges(ArrayList<Shape> missingSpaces) {
        var finalList = new ArrayList<Shape>();
        int minX = minX();
        int maxX = maxX();
        int minY = minY();
        int maxY = maxY();
        int minZ = minZ();
        int maxZ = maxZ();
        for (var space : missingSpaces) {
            if (space.units.stream().noneMatch(c -> (c.x() == minX || c.x() == maxX || c.y() == minY || c.y() == maxY || c.z() == minZ || c.z() == maxZ))) {
                finalList.add(space);
            }
        }
        return finalList.stream().flatMap(shape -> shape.units.stream()).toList();
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
