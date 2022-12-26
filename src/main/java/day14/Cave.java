package day14;

import utility.Coords;
import utility.Grid;

import java.util.List;

public class Cave {
    private Grid<AirContent> cave;
    private Coords sandEntry;
    private Coords moveDown = new Coords(0, 1);
    private Coords moveDownLeft = new Coords(-1, 1);
    private Coords moveDownRight = new Coords(1, 1);
    private int minX, maxX, minY, maxY;
    private boolean hasFloor;

    public Cave(List<Rock> rocks) {
        minX = rocks.stream().flatMap(x -> x.getRock().stream()).map(Coords::getX).min(Integer::compareTo).orElseThrow();
        maxX = rocks.stream().flatMap(x -> x.getRock().stream()).map(Coords::getX).max(Integer::compareTo).orElseThrow();
        minY = 0;
        maxY = rocks.stream().flatMap(y -> y.getRock().stream()).map(Coords::getY).max(Integer::compareTo).orElseThrow();

        cave = new Grid<>(minX, maxX, minY, maxY, () -> AirContent.EMPTY);

        // Add rocks
        for (var rock: rocks) {
            for (var point : rock.getRock()) {
                cave.set(point, AirContent.ROCK);
            }
        }

        sandEntry = new Coords(500, 0);
        hasFloor = false;
    }

    public Cave(List<Rock> rocks, int floor) {
        minX = rocks.stream().flatMap(x -> x.getRock().stream()).map(Coords::getX).min(Integer::compareTo).orElseThrow();
        maxX = rocks.stream().flatMap(x -> x.getRock().stream()).map(Coords::getX).max(Integer::compareTo).orElseThrow();
        minY = 0;
        maxY = floor;


        cave = new Grid<>(minX, maxX, minY, maxY, () -> AirContent.EMPTY);

        // Add rocks
        for (var rock: rocks) {
            for (var point : rock.getRock()) {
                cave.set(point, AirContent.ROCK);
            }
        }

        // Add floor
        for (int i = minX; i <= maxX; i++) {
            cave.set(new Coords(i, maxY), AirContent.ROCK);
        }

        sandEntry = new Coords(500, 0);
        hasFloor = true;
    }

    public void fillWithSand() {
        Coords fellLeft = null;
        Coords fellRight = null;
        while(true) {
            Coords location = new Coords(sandEntry.getX(), sandEntry.getY());
            if (cave.get(location) == AirContent.SAND) {
                return;
            }
            while (true) {
                var downState = Coords.add(location, moveDown);
                if (downState.getY() > maxY) {
                    // Infinity
                    return;
                } else if (cave.get(downState) == AirContent.EMPTY) {
                    location.add(moveDown);
                } else {
                    var leftDownState = Coords.add(location, moveDownLeft);
                    if (leftDownState.getX() < minX) {
                        if (hasFloor) {
                            expandFloor();
                            break;
                        }
                        // Off the edge midway
                        if (fellLeft == null) {
                            fellLeft = location;
                        } else if (fellLeft.equals(location)) {
                            return;
                        }
                        fellLeft = location;
                        break;
                    } else if (cave.get(leftDownState) == AirContent.EMPTY) {
                        location.add(moveDownLeft);
                    } else {
                        var rightDownState = Coords.add(location, moveDownRight);
                        if (maxX < rightDownState.getX()) {
                            if (hasFloor) {
                                expandFloor();
                                break;
                            }
                            // Off the edge midway
                            if (fellRight == null) {
                                fellRight = location;
                            } else if (fellRight.equals(location)) {
                                return;
                            }
                            fellRight = location;
                            break;
                        } else if (cave.get(rightDownState) == AirContent.EMPTY) {
                            location.add(moveDownRight);
                        } else {
                            cave.set(location, AirContent.SAND);
                            break;
                        }
                    }
                }
            }
        }
    }

    public int getSandCount() {
        int count = 0;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                count += cave.get(new Coords(x, y)) == AirContent.SAND ? 1 : 0;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = minX; i < 500; i++) {
            str += " ";
        }
        str += "+\n";
        return str + cave.toString();
    }

    private void expandFloor() {
        int diff = maxX - minX + 1;
        minX -= diff;
        maxX += diff;

        cave.resize(minX, maxX, minY, maxY);

        var point = new Coords(minX, maxY);
        var move = new Coords(1, 0);
        for (int x = minX; x <= maxX; x++) {
            cave.set(point, AirContent.ROCK);
            point.add(move);
        }
    }
}
