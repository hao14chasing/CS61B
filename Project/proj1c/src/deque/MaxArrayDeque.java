package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> cmp;
    public MaxArrayDeque(Comparator<T> c) {
        cmp = c;
    }
    public T max() {
        if (isEmpty()) {
            return null;
        }
        T currentMax = get(0);
        for (T i: this) {
            if (cmp.compare(i, currentMax) > 0) {
                currentMax = i;
            }
        }
        return currentMax;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T currentMax = get(0);
        for (T i: this) {
            if (c.compare(i, currentMax) > 0) {
                currentMax = i;
            }
        }
        return currentMax;
    }

}
