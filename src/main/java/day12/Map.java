package day12;

import day09.Grid;
import utility.Edge;
import utility.Graph;
import utility.Vertex;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class Map {
    private final List<List<Character>> map;
    private GridLocation start;
    private GridLocation end;
    private final int rows;
    private final int cols;

    private final int[][] cardinals = new int[][]{{-1,0},{0,1},{1,0},{0,-1}};

    public Map(List<List<Character>> map) {
        this.map = map;
        rows = map.size();
        cols = map.get(0).size();
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).size(); j++) {
                if (start == null && map.get(i).get(j).equals('S')) {
                    start = new GridLocation(i, j, 'S', 0);
                } else if (end == null && map.get(i).get(j).equals('E')) {
                    end = new GridLocation(i, j, 'E', Integer.MAX_VALUE);
                }
            }
        }
    }

    public GridLocation start() {
        return start;
    }

    public GridLocation end() {
        return end;
    }

    public GridLocation findEnd(GridLocation location, Optional<Integer> stepLimit) {
        Set<GridLocation> visited = new HashSet<>();
        Queue<GridLocation> queue = new LinkedBlockingQueue<>();
        visited.add(location);
        queue.add(location);
        while (!queue.isEmpty()) {
            var curr = queue.remove();
            for (var cardinal : cardinals) {
                int i = curr.row()+cardinal[0];
                int j = curr.col()+cardinal[1];
                if (isValidIndices(i, j)) {
                    GridLocation next = new GridLocation(i, j, map.get(i).get(j), curr.dist()+1);
                    if (!visited.contains(next) && (next.value() - curr.value() <= 1 || curr.value() == 'S') && next.dist() < stepLimit.orElse(Integer.MAX_VALUE)) {
                        if (next.value() == 'E') {
                            return next;
                        }
                        queue.add(next);
                        visited.add(next);
                    }
                }
            }
        }
        return null;
    }

    public int findShortestScenic() {
        Integer minDist = findEnd(start, Optional.empty()).dist();
        for (var location : startingLocations()) {
            var dist = findEnd(location, Optional.of(minDist));
            minDist = dist == null ? minDist : Integer.min(dist.dist(), minDist);
            System.gc();
        }
        return minDist;
    }

    private boolean isValidIndices(int i, int j) {
        boolean row = i >= 0 && i < rows;
        boolean col = j >= 0 && j < cols;
        return row && col;
    }

    public ArrayList<GridLocation> startingLocations() {
        ArrayList<GridLocation> locations = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            if (map.get(0).get(i) == 'a') {
                locations.add(new GridLocation(0, i, 'a', 0));
            }
            if (map.get(rows-1).get(i) == 'a') {
                locations.add(new GridLocation(rows-1, i, 'a', 0));
            }
        }
        for (int j = 0; j < rows; j++) {
            if (map.get(j).get(0) == 'a') {
                locations.add(new GridLocation(j, 0, 'a', 0));
            }
            if (map.get(j).get(cols-1) == 'a') {
                locations.add(new GridLocation(j, cols-1, 'a', 0));
            }
        }
        return locations;
    }
}
