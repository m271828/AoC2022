package day17;

import utility.Coords;
import utility.Grid;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Chamber {
    private int width;
    private int startPosY;
    private int startPosX;
    private int yOffset;
    private int currentHigh;
    private List<Rock> rocks;
    int rockIdx;
    private List<Move> moves;
    int moveIdx;
    private Grid<Boolean> chamber;

    public Chamber(List<Rock> rocks, List<Move> moves, int width, int startPosX, int startPosY) {
        this.rocks = rocks;
        this.moves = moves;
        this.width = width;
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        yOffset = startPosY+1;
        currentHigh = 0;
        chamber = new Grid<>(0, width-1, 0, startPosY*10, () -> false);
    }

    public void dropRocks(int numOfRocks) {
        for (int i = 0; i < numOfRocks; i++) {
            simulateFalling();
            if (currentHigh + 8 > chamber.getHeight()) {
                chamber.resize(0, width-1, 0, currentHigh*2);
            }
        }
    }

    public long dropLotsOfRocks(long numOfRocks) {
        var states = new ArrayList<Status>();
        var cycle = new ArrayList<Status>();
        var cycleStartIdx = -1;
        var foundCycle = false;
        while (true) {
            var s = simulateFalling();
            if (currentHigh + 8 > chamber.getHeight()) {
                chamber.resize(0, width - 1, 0, currentHigh * 2);
            }
            if (cycle.isEmpty() && states.stream().anyMatch(x -> x.equals(s))) {
                cycleStartIdx = states.lastIndexOf(s);
                states.add(s);
                cycle.add(s);
                numOfRocks--;
            } else if (!cycle.isEmpty() && !states.stream().anyMatch(x -> x.equals(s))) {
                cycleStartIdx = -1;
                cycle.clear();
                states.add(s);
                numOfRocks--;
            } else if (!cycle.isEmpty()) {
                if (cycle.get(0).equals(s)) {
                    states.add(s);
                    numOfRocks--;
                    break;
                } else if (states.get(cycleStartIdx+cycle.size()).equals(s)) {
                    states.add(s);
                    cycle.add(s);
                    numOfRocks--;
                } else {
                    states.add(s);
                    cycle.clear();
                    cycleStartIdx = -1;
                    numOfRocks--;
                }
            } else {
                states.add(s);
                numOfRocks--;
            }
            if (numOfRocks == 0) {
                return height();
            }
        }
        long cycleDrops = cycle.size();
        long cycleHeight = cycle.stream().map(Status::delta).reduce(Integer::sum).orElseThrow();
        long remainingCycles = numOfRocks / cycleDrops;
        int leftOverDrops = (int) (numOfRocks % cycleDrops);
        var remainingHeight = cycle.subList(0, leftOverDrops).stream().map(Status::delta).reduce(Integer::sum).orElseThrow();
        long calcHeight = (long) height() + cycleHeight * remainingCycles + remainingHeight;
        return calcHeight;
    }

    public int height() {
        return currentHigh+1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = chamber.getHeight()-1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                if(chamber.get(new Coords(x, y))) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private record Status(int rockIdx, int startMoveIdx, int endMoveIdx, int endXPos, int delta) {
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (o == null) {
                return false;
            } else if (o.getClass() != this.getClass()) {
                return false;
            } else {
                var other = (Status) o;
                return rockIdx == other.rockIdx && startMoveIdx == other.startMoveIdx && endMoveIdx == other.endMoveIdx && endXPos == other.endXPos && delta == other.delta;
            }
        }
    }

    private Status simulateFalling() {
        var rock = createRock(rocks.get(rockIdx));
        rockIdx = (rockIdx + 1) % rocks.size();
        var startMoveIdx = moveIdx;
        while (true) {
            var move = moves.get(moveIdx);
            moveIdx = (moveIdx + 1) % moves.size();
            var moveOffset = move == Move.LEFT ? new Coords(-1, 0) : new Coords(1, 0);
            if (canMove(rock, moveOffset)) {
                rock.forEach(x -> x.add(moveOffset));
            }
            var down = new Coords(0, -1);
            if (canMove(rock, down)) {
                rock.forEach(x -> x.add(down));
            } else {
                break;
            }
        }
        for (var c : rock) {
            chamber.set(c, true);
        }
        var oldHigh = currentHigh;
        currentHigh = Integer.max(currentHigh, maxY(rock));
        startPosY = currentHigh + yOffset;
        int statusRockIdx = (rockIdx - 1) % rocks.size();
        if (statusRockIdx < 0) {
            statusRockIdx += rocks.size();
        }
        int statusMoveIdx = (moveIdx - 1) % moves.size();
        if (statusMoveIdx < 0) {
            statusMoveIdx += moves.size();
        }
        return new Status(statusRockIdx, startMoveIdx, statusMoveIdx, rock.stream().filter(x -> x.getY() == rock.stream().map(Coords::getY).min(Integer::compareTo).orElseThrow()).map(Coords::getX).min(Integer::compareTo).orElseThrow(), currentHigh - oldHigh);
    }

    private List<Coords> createRock(Rock r) {
        Coords offset = new Coords(startPosX, startPosY);
        return new ArrayList<>(r.shape().stream().map(x -> Coords.add(x, offset)).toList());
    }

    private boolean canMove(List<Coords> current, Coords offset) {
        List<Coords> movedValue = current.stream().map(x -> Coords.add(x, offset)).toList();
        if (minX(movedValue) < 0) {
            return false;
        } else if (maxX(movedValue) >= chamber.getWidth()) {
            return false;
        } else if (minY(movedValue) < 0) {
            return false;
        } else {
            for (var c : movedValue) {
                if (chamber.get(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int minX(List<Coords> coords) {
        return coords.stream().map(Coords::getX).min(Integer::compareTo).orElseThrow();
    }

    private int maxX(List<Coords> coords) {
        return coords.stream().map(Coords::getX).max(Integer::compareTo).orElseThrow();
    }

    private int minY(List<Coords> coords) {
        return coords.stream().map(Coords::getY).min(Integer::compareTo).orElseThrow();
    }

    private int maxY(List<Coords> coords) {
        return coords.stream().map(Coords::getY).max(Integer::compareTo).orElseThrow();
    }
}
