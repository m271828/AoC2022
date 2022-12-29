package day15;

import utility.Coords;
import utility.Grid;
import utility.Range;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SensorMap {
    private List<Sensor> sensors;
    int minX, maxX, minY,maxY;

    public class RowStatus {
        public BigInteger cellsWithSignal = BigInteger.ZERO;
        public BigInteger cellsWithBeacon = BigInteger.ZERO;
        public BigInteger cellsWithSensor = BigInteger.ZERO;
        public BigInteger cellsUncovered = BigInteger.ZERO;
    }

    public SensorMap(List<Sensor> sensors) {
        this.sensors = sensors;
        minX = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        minY = Integer.MAX_VALUE;
        maxY = Integer.MIN_VALUE;
        for (var s : sensors) {
            minX = Math.min(minX, Math.min(s.getLocation().getX(), s.getNearestBeacon().getX()));
            maxX = Math.max(maxX, Math.max(s.getLocation().getX(), s.getNearestBeacon().getX()));
            minY = Math.min(minY, Math.min(s.getLocation().getY(), s.getNearestBeacon().getY()));
            maxY = Math.max(maxY, Math.max(s.getLocation().getY(), s.getNearestBeacon().getY()));
        }
    }

    public RowStatus getRowData(int y) {
        var cellsInRow = rowCellsCovered(y);
        var stats = new RowStatus();
        for (var c : cellsInRow.keySet()) {
            switch(cellsInRow.get(c)) {
                case SIGNAL -> stats.cellsWithSignal = stats.cellsWithSignal.add(BigInteger.ONE);
                case BEACON -> stats.cellsWithBeacon = stats.cellsWithBeacon.add(BigInteger.ONE);
                case SENSOR -> stats.cellsWithSensor = stats.cellsWithSensor.add(BigInteger.ONE);
            }
        }
        stats.cellsUncovered = BigInteger.valueOf(maxX).subtract(BigInteger.valueOf(minX)).add(BigInteger.ONE).subtract(stats.cellsWithSensor).subtract(stats.cellsWithBeacon).subtract(stats.cellsWithSignal);
        return stats;
    }

    public Coords findBeacon(int min, int max) {
        for (var s : sensors) {
            var points = s.getBoundary(min, max);
            for (var p : points) {
                boolean canBeSeen = false;
                for (var ss : sensors) {
                    if (s != ss) {
                        var ssC = ss.getLocation();
                        var dist = ss.getManhattanDistance();
                        if (Math.abs(ssC.getX() - p.getX()) + Math.abs(ssC.getY() - p.getY()) <= dist) {
                            canBeSeen = true;
                            break;
                        }
                    }
                }
                if (!canBeSeen) {
                    return p;
                }
            }
        }
        return null;
    }

    public HashMap<Integer, LocationStatus> rowCellsCovered(int y) {
        ArrayList<Sensor> withinDistance = new ArrayList<>();
        HashMap<Integer, LocationStatus> cellsInRow = new HashMap<>();

        for (var s : sensors) {
            if (s.getLocation().getY() - s.getManhattanDistance() <= y && y <= s.getLocation().getY() + s.getManhattanDistance()) {
                withinDistance.add(s);
                var points = s.coveredArea(y);
                for (var p : points) {
                    cellsInRow.put(p.getX(), LocationStatus.SIGNAL);
                }
            }
        }

        for (var s : withinDistance) {
            if (s.getNearestBeacon().getY() == y) {
                cellsInRow.put(s.getNearestBeacon().getX(), LocationStatus.BEACON);
            }
            if (s.getLocation().getY() == y) {
                cellsInRow.put(s.getLocation().getX(), LocationStatus.SENSOR);
            }
        }

        return cellsInRow;
    }
}
