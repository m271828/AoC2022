package day07;

import java.util.HashMap;

public class Directory {
    private HashMap<String, Directory> children = new HashMap<>();
    private HashMap<String, File> files = new HashMap<>();
    private Directory parent;
    private String name;
    private Integer size;

    public Directory(String name) {
        parent = null;
        this.name = name;
    }

    public Directory(String name, Directory parent) {
        this.parent = parent;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public void addChild(String name) {
        if (!children.containsKey(name)) {
            children.put(name, new Directory(name, this));
        }
    }

    public Directory getChild(String name) {
        return children.get(name);
    }

    public void addFile(String name, int size) {
        if (!files.containsKey(name)) {
            files.put(name, new File(this, name, size));
        }
    }

    public int getSize() {
        if (size == null) {
            int total = 0;
            for (var child : children.values()) {
                total += child.getSize();
            }
            for (var file : files.values()) {
                total += file.getSize();
            }
            size = total;
        }
        return size;
    }

    public int filterSize(int max) {
        int total = 0;
        if (getSize() <= max) {
            total += getSize();
        }
        for (var dir : children.values()) {
            total += dir.filterSize(max);
        }
        return total;
    }

    public Directory findMinCeilingDirectory(int value) {
        var size = getSize();
        var dir = this;
        for (var child : children.values()) {
            var curr = child.findMinCeilingDirectory(value);
            if (curr != null) {
                if (value <= curr.getSize() && curr.getSize() < size) {
                    size = curr.getSize();
                    dir = curr;
                }
            }
        }
        if (value <= size) {
            return dir;
        }
        return null;
    }
}
