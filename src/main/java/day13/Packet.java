package day13;

public class Packet {
    private Message part1, part2;

    public Packet(String str1, String str2) {
        part1 = new Message(str1);
        part2 = new Message(str2);
    }

    public int compareMessages() {
        return part1.compareTo(part2);
    }
}
