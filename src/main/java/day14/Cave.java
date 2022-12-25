package day14;

import utility.Coords;

import java.util.List;

public class Cave {
    private AirContent[][] cave;
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

        cave = new AirContent[maxX-minX+1][maxY+1];
        for (int i = 0; i <= maxX-minX; i++) {
            for (int j = 0; j <=maxY; j++) {
                cave[i][j] = AirContent.EMPTY;
            }
        }

        // Add rocks
        for (var rock: rocks) {
            for (var point : rock.getRock()) {
                cave[point.getX()-minX][point.getY()] = AirContent.ROCK;
            }
        }

        sandEntry = new Coords(500-minX, 0);
        maxX -= minX;
        minX = 0;
        hasFloor = false;
    }

    public Cave(List<Rock> rocks, int floor) {
        minX = rocks.stream().flatMap(x -> x.getRock().stream()).map(Coords::getX).min(Integer::compareTo).orElseThrow();
        maxX = rocks.stream().flatMap(x -> x.getRock().stream()).map(Coords::getX).max(Integer::compareTo).orElseThrow();
        minY = 0;
        maxY = floor;

        int diff = maxX-minX+1;
        minX -= diff;
        maxX += diff;


        cave = new AirContent[maxX-minX+1][maxY+1];
        for (int i = 0; i <= maxX-minX; i++) {
            for (int j = 0; j <=maxY; j++) {
                cave[i][j] = AirContent.EMPTY;
            }
        }

        // Add rocks
        for (var rock: rocks) {
            for (var point : rock.getRock()) {
                cave[point.getX()-minX][point.getY()] = AirContent.ROCK;
            }
        }

        // Add floor
        for (int i = 0; i <= maxX-minX; i++) {
            cave[i][maxY] = AirContent.ROCK;
        }

        sandEntry = new Coords(500-minX, 0);
        maxX -= minX;
        minX = 0;
        hasFloor = true;
    }

    public void fillWithSand() {
        Coords fellLeft = null;
        Coords fellRight = null;
        while(true) {
            Coords location = new Coords(sandEntry.getX(), sandEntry.getY());
            if (cave[location.getX()][location.getY()] == AirContent.SAND) {
                return;
            }
            while (true) {
                var downState = Coords.add(location, moveDown);
                if (downState.getY() > maxY) {
                    // Infinity
                    return;
                } else if (cave[downState.getX()][downState.getY()] == AirContent.EMPTY) {
                    location.add(moveDown);
                } else {
                    var leftDownState = Coords.add(location, moveDownLeft);
                    if (leftDownState.getX() < 0) {
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
                    } else if (cave[leftDownState.getX()][leftDownState.getY()] == AirContent.EMPTY) {
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
                        } else if (cave[rightDownState.getX()][rightDownState.getY()] == AirContent.EMPTY) {
                            location.add(moveDownRight);
                        } else {
                            cave[location.getX()][location.getY()] = AirContent.SAND;
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
                count += cave[x][y] == AirContent.SAND ? 1 : 0;
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
        int width = maxX+1;
        int newWidth = width*3;
        AirContent[][] newCave = new AirContent[newWidth][maxY+1];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < maxY; j++) {
                newCave[i][j] = AirContent.EMPTY;
            }
            newCave[i][maxY] = AirContent.ROCK;
        }

        for (int i = width; i < width*2; i++) {
            for (int j = 0; j <= maxY; j++) {
                newCave[i][j] = cave[i-width][j];
            }
        }

        for (int i = width*2; i < newWidth; i++) {
            for (int j = 0; j < maxY; j++) {
                newCave[i][j] = AirContent.EMPTY;
            }
            newCave[i][maxY] = AirContent.ROCK;
        }
        cave = newCave;
        maxX = newWidth - 1;
        sandEntry.add(new Coords(width, 0));
    }
}
