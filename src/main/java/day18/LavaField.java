package day18;

import utility.Cube;
import utility.Shape;

import java.util.ArrayList;

public class LavaField {
    ArrayList<Shape> lava = new ArrayList<>();

    public void add(Cube c) {
        boolean added = false;
        int idx = -1;
        for (int i = lava.size()-1; i >= 0; i--) {
            if (!added && lava.get(i).add(c)) {
                added = true;
                idx = i;
            } else if (added) {
                if (lava.get(i).add(lava.get(idx))) {
                    lava.remove(idx);
                    idx = i;
                }
            }
        }
        if (!added) {
            lava.add(new Shape(c));
        }
    }

    public long surfaceArea() {
        return lava.stream().map(Shape::surfaceArea).reduce(Long::sum).orElse(0L);
    }

    public long exteriorSurfaceArea() {
        return lava.stream().map(Shape::findExteriorArea).reduce(Long::sum).orElse(0L);
    }
}
