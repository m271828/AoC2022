package utility;

import java.util.function.Supplier;

public class Counter<T> implements Supplier {
    int counter = 0;
    /**
     * Gets a result.
     *
     * @return a result
     */
    @Override
    public Object get() {
        return counter++;
    }

    public void reset() {
        counter = 0;
    }
}
