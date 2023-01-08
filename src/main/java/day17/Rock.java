package day17;

import utility.Coords;

import java.util.LinkedHashSet;

public record Rock(LinkedHashSet<Coords> shape) {
    // (0,0) is the bottom left corner

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (var c : shape) {
            sb.append(c);

        }
        sb.append("}");
        return sb.toString();
    }
}
