package day07;

public class File {
    private Directory parent;
    private String name;
    private int size;

    public File(Directory parent, String name, int size) {
        this.parent = parent;
        this.name = name;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public String name() {
        return name;
    }
}
