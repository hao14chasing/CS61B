import deque.MaxArrayDeque;
import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    @Test
    public void testMaxSecond() {
        Comparator<String> cmp = new Comparator<>() {
            @Override
            public int compare(String strA, String strB) {
                return strB.compareTo(strA);
            }
        };
        MaxArrayDeque<String> ad1 = new MaxArrayDeque(cmp);

        assertTrue(ad1.isEmpty());
        ad1.addFirst("front");

        assertEquals(1, ad1.size());
        assertFalse(ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());
        assertTrue(ad1.max( cmp) == "back");
    }

    @Test
    public void testMaxFirst() {
        Comparator<Integer> cmp = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return 1;
                } else if (o1 < o2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        MaxArrayDeque<Integer> ad1 = new MaxArrayDeque(cmp);
        ad1.addFirst(1);
        ad1.addFirst(2);
        ad1.addFirst(0);
        assertTrue(ad1.max() == 2);
    }
}
