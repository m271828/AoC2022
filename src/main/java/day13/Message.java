package day13;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

public class Message implements Comparable {
    private Object value;

    public Message(String str) {
        var json = JsonParser.parseString(str);
        value = parseArray(json.getAsJsonArray());
    }

    private List<Object> parseArray(JsonArray elements) {
        var array = new ArrayList<Object>();
        for (int i = 0; i < elements.size(); i++) {
            var elem = elements.get(i);
            if (elem.isJsonArray()) {
                array.add(parseArray(elem.getAsJsonArray()));
            } else {
                array.add(elem.getAsInt());
            }
        }
        return array;
    }

    @Override
    public int compareTo(Object o) {
        if (o == this) {
            return 0;
        }
        var msgO = (Message) o;
        return compare(value, msgO.value);
    }

    private int compare(Object left, Object right) {
        if (left instanceof Integer leftInt && right instanceof  Integer rightInt) {
            return leftInt - rightInt;
        } else if (left instanceof List<?> leftList && right instanceof List<?> rightList) {
            if (leftList.isEmpty()) {
                return rightList.isEmpty() ? 0 : -1;
            }

            var len = Integer.min(leftList.size(), rightList.size());
            for (int i = 0; i < len; i++) {
                var comp = compare(leftList.get(i), rightList.get(i));
                if (comp != 0) {
                    return comp;
                }
            }
            return leftList.size() - rightList.size();
        } else if (left instanceof Integer leftInt) {
            return compare(List.of(leftInt), right);
        } else if (right instanceof Integer rightInt) {
            return compare(left, List.of(rightInt));
        }

        throw new RuntimeException("Unreachable");
    }
}
