package day16;

import utility.Edge;
import utility.Graph;
import utility.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Volcano {
    private final Graph<Valve> volcano;
    private Map<String, Vertex<Valve>> vertices;
    private Map<String, Map<String, Integer>> fm;

    public static class Node {
        public String name;
        public int flow;
        public List<String> neighbors;
    }

    public Volcano(List<Node> nodes) {
        volcano = new Graph<>(Graph.Type.UNDIRECTED);
        vertices = new HashMap<>();
        for (var n : nodes) {
            var v = new Vertex<>(new Valve(n.name, n.flow));
            vertices.put(n.name, v);
            volcano.addVertex(v);
        }
        for (var n : nodes) {
            var v = vertices.get(n.name);
            for (var neighbor : n.neighbors) {
                var nV = vertices.get(neighbor);
                var e = new Edge<>(v, nV, 1);
                volcano.addEdge(e);
            }
        }
        fm = constructFloydWarshall();
    }

    public int findMaxPressure(String start, int time) {
        var arr = new ArrayList<String>();
        arr.add(start);
        return pressureRelease(start, time, arr);
    }

    public int twoPartyRelease(String start, int time) {
        var arr = new ArrayList<String>();
        arr.add(start);
        return pressureRelease(LastMove.Start, start, time, start, time, arr);
    }

    private enum LastMove {
        Start,
        One,
        Two
    }

    private Map<String, Map<String, Integer>> constructFloydWarshall() {
        var fullMap = new HashMap<String, Map<String, Integer>>();
        for (var outerK : vertices.keySet()) {
            var row = new HashMap<String, Integer>();
            for (var innerK : vertices.keySet()) {
                row.put(innerK, Integer.MAX_VALUE);
            }
            fullMap.put(outerK, row);
        }

        var edges = volcano.getEdges();
        for (var e : edges) {
            fullMap.get(e.from().value().getName()).put(e.to().value().getName(), e.weight());
        }
        for (var k : vertices.keySet()) {
            fullMap.get(k).put(k, 0);
        }
        for (var kk : vertices.keySet()) {
            for (var ki : vertices.keySet()) {
                for (var kj : vertices.keySet()) {
                    var ijV = fullMap.get(ki).get(kj);
                    var ikV = fullMap.get(ki).get(kk);
                    var kjV = fullMap.get(kk).get(kj);
                    if (ikV != Integer.MAX_VALUE && kjV != Integer.MAX_VALUE && ijV > ikV + kjV) {
                        fullMap.get(ki).put(kj, ikV + kjV);
                    }
                }
            }
        }

        return fullMap;
    }

    private int pressureRelease(String node, int time, ArrayList<String> visited) {
        int gain = vertices.get(node).value().getFlowRate()*time;
        int maxGain = 0;
        for (var v : vertices.values().stream().map(Vertex::value).toList()) {
            if (v.getFlowRate() == 0 || visited.contains(v.getName())) {
                continue;
            } else if (fm.get(node).get(v.getName()) > time) {
                continue;
            } else {
                visited.add(v.getName());
                int childGain = pressureRelease(v.getName(), time-fm.get(node).get(v.getName())-1, visited);
                maxGain = Math.max(childGain, maxGain);
                visited.remove(v.getName());
            }
        }
        return gain + maxGain;
    }

    private int pressureRelease(LastMove move, String loc1, int tic1, String loc2, int tic2, ArrayList<String> visited) {
        int gain = 0;
        if (move == LastMove.One) {
            gain = vertices.get(loc1).value().getFlowRate()*tic1;
        } else if (move == LastMove.Two) {
            gain = vertices.get(loc2).value().getFlowRate()*tic2;
        }
        LastMove next = tic1 >= tic2 ? LastMove.One : LastMove.Two;
        String node = tic1 >= tic2 ? loc1 : loc2;
        int time = tic1 >= tic2 ? tic1 : tic2;
        int maxGain = 0;
        for (var v : vertices.values().stream().map(Vertex::value).toList()) {
            if (v.getFlowRate() == 0 || visited.contains(v.getName())) {
                continue;
            } else if (fm.get(node).get(v.getName()) > time) {
                continue;
            } else {
                visited.add(v.getName());
                if (next == LastMove.One) {
                    int childGain = pressureRelease(next, v.getName(), time-fm.get(node).get(v.getName())-1, loc2, tic2, visited);
                    maxGain = Math.max(childGain, maxGain);
                } else {
                    int childGain = pressureRelease(next, loc1, tic1, v.getName(), time - fm.get(node).get(v.getName()) - 1, visited);
                    maxGain = Math.max(childGain, maxGain);
                }
                visited.remove(v.getName());
            }
        }
        return gain + maxGain;
    }
}
