package utility;

import java.util.ArrayList;
import java.util.List;

public record Cube(int x, int y, int z) {
    public record Point(double x, double y, double z) implements Comparable<Point> {
        @Override
        public int compareTo(Point o) {
            if (x != o.x) {
                return (x - o.x) < 0 ? -1 : 1;
            } else if (y != o.y) {
                return (y - o.y) < 0 ? -2 : 2;
            } else if (z != o.z) {
                return (z - o.z) < 0 ? -3 : 3;
            }
            return 0;
        }
    }
    public record Side(Point p1, Point p2, Point p3, Point p4) {
        public Side(List<Point> points) {
            this(points.get(0), points.get(1), points.get(2), points.get(3));
        }
    }

    public boolean connectsTo(Cube other) {
        return (x == other.x && y == other.y) || (x == other.x && z == other.z) || (y == other.y && z == other.z);
    }

    public List<Side> surfaces() {
        var surfaces = new ArrayList<Side>();
        Point mmm = new Point(x - 0.5, y - 0.5, z - 0.5);
        Point mmp = new Point(x - 0.5, y - 0.5, z + 0.5);
        Point mpm = new Point(x - 0.5, y + 0.5, z - 0.5);
        Point mpp = new Point(x - 0.5, y + 0.5, z + 0.5);
        Point pmm = new Point(x + 0.5, y - 0.5, z - 0.5);
        Point pmp = new Point(x + 0.5, y - 0.5, z + 0.5);
        Point ppm = new Point(x + 0.5, y + 0.5, z - 0.5);
        Point ppp = new Point(x + 0.5, y + 0.5, z + 0.5);

        surfaces.add(new Side(List.of(mmm, mmp, mpm, mpp).stream().sorted().toList()));
        surfaces.add(new Side(List.of(pmm, pmp, ppm, ppp).stream().sorted().toList()));
        surfaces.add(new Side(List.of(mmm, mmp, pmm, pmp).stream().sorted().toList()));
        surfaces.add(new Side(List.of(mpm, mpp, ppm, ppp).stream().sorted().toList()));
        surfaces.add(new Side(List.of(mmm, mpm, pmm, ppm).stream().sorted().toList()));
        surfaces.add(new Side(List.of(mmp, mpp, pmp, ppp).stream().sorted().toList()));

        return surfaces;
    }
}
