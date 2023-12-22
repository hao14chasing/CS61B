import deque.ArrayDeque;
import deque.Deque;
import deque.LinkedListDeque;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class ArrayDequeTest {
    @Test
    public void testiterator() {
        Deque<String> lld1 = new ArrayDeque<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        for (String s : lld1) {
            System.out.println(s);
        }
        assertThat(lld1).containsExactly("front", "middle", "back");
    }
    @Test
    public void testequals() {
        Deque<String> lld1 = new ArrayDeque<>();
        Deque<String> lld2 = new ArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void testtoString() {
        Deque<String> lld1 = new ArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        System.out.println(lld1);
        assertThat(lld1.toString() == "[front, middle, back]");
    }

    @Test
    public void testTwoDeque() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        Deque<Integer> lld2 = new LinkedListDeque<>();
        lld1.addFirst(0);
        lld1.addFirst(1);
        lld1.addFirst(3);
        lld2.addFirst(0);
        lld2.addFirst(1);
        lld2.addFirst(3);
        assertThat(lld1).isEqualTo(lld2);
        assertThat(lld2).isEqualTo(lld1);
    }
}
