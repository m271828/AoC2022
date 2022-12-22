import day13.Message;
import day13.Packet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import utility.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Day13 {
    private static String filename = "src/main/resources/day_13_input.txt";

    private static String example = "[1,1,3,1,1]\n" +
            "[1,1,5,1,1]\n" +
            "\n" +
            "[[1],[2,3,4]]\n" +
            "[[1],4]\n" +
            "\n" +
            "[9]\n" +
            "[[8,7,6]]\n" +
            "\n" +
            "[[4,4],4,4]\n" +
            "[[4,4],4,4,4]\n" +
            "\n" +
            "[7,7,7,7]\n" +
            "[7,7,7]\n" +
            "\n" +
            "[]\n" +
            "[3]\n" +
            "\n" +
            "[[[]]]\n" +
            "[[]]\n" +
            "\n" +
            "[1,[2,[3,[4,[5,6,7]]]],8,9]\n" +
            "[1,[2,[3,[4,[5,6,0]]]],8,9]";

    public List<Packet> packetList;
    public List<Message> messageList;

    public static Message dividerOne = new Message("[[2]]");
    public static Message dividerTwo = new Message("[[6]]");

    @BeforeAll
    public void configure() throws IOException {
        ArrayList<Packet> packets = new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(dividerOne);
        messages.add(dividerTwo);
        var lines = FileUtil.groupLines(filename);
        for (var set : lines) {
            messages.add(new Message(set.get(0)));
            messages.add(new Message(set.get(1)));
            packets.add(new Packet(set.get(0), set.get(1)));
        }
        packetList = packets;
        messageList = messages;
    }

    @Test
    public void sampleTest() {
        ArrayList<Packet> packets = new ArrayList<>();
        var strArrs = example.split("\n");
        for (int i = 0; i+1 < strArrs.length; i += 3) {
            packets.add(new Packet(strArrs[i], strArrs[i+1]));
        }
        int total = 0;
        for (int j = 0; j < packets.size(); j++) {
            var value = packets.get(j).compareMessages();
            if (value <= 0) {
                total += j+1;
            }
        }
        assertEquals(13, total);
    }

    @Test
    public void partOne() {
        int total = 0;
        for (int i = 0; i < packetList.size(); i++) {
            var value = packetList.get(i).compareMessages();
            if (value <= 0) {
                total += i + 1;
            }
        }
        System.out.println("The indices of sorted packets sums to " + total + ".");
        assertEquals(5843, total);
    }

    @Test
    public void partTwo() {
        var sortedList = messageList.stream().sorted(Message::compareTo).toList();
        int divOneIdx = -1;
        int divTwoIdx = -1;
        for (int i = 0; i < sortedList.size(); i++) {
            if (divOneIdx == -1 && sortedList.get(i).compareTo(dividerOne) == 0) {
                divOneIdx = i + 1;
            }
            if (divTwoIdx == -1 && sortedList.get(i).compareTo(dividerTwo) == 0) {
                divTwoIdx = i + 1;
                break;
            }
        }
        System.out.println("Decoder is " + divOneIdx * divTwoIdx + ".");
        assertEquals(26289, divOneIdx * divTwoIdx);
    }
}
