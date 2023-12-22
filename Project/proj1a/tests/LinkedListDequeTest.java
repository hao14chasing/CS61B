import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
        Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
        List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive()
                         || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

        assertWithMessage("Found fields that are not nodes or primitives").that(badFields)
                .isEmpty();
    }

    @Test
     /** In this test, we have three different assert statements that
      * verify that addFirst works correctly. */
     public void addFirstTestBasic() {
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary.
         For example, it's hard
            to imagine a bug in your code that would lead
            to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
    }

    @Test
     /** In this test, we use only one assertThat statement. IMO this test is just
      * as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat
      *  statements isn't worth it. */
     public void addLastTestBasic() {
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

     //Below, you'll write your own tests for LinkedListDeque.

    @Test
    public void testSizeAndIsEmpty() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertEquals(0, lld1.size());
        assertTrue(lld1.isEmpty());
        lld1.addFirst(0);
        assertFalse(lld1.isEmpty());
        assertEquals(1, lld1.size());
    }

    @Test
    public void testGet() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertEquals(null, lld1.get(2330));
        assertEquals(null, lld1.get(-2330));
        lld1.addFirst(0); //[0]
        lld1.addFirst(1); //[1,0]
        lld1.addLast(-1); //[1,0,-1]
        assertThat(lld1.get(2) == 0);
    }

    @Test
    public void testGetRecusive() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertEquals(null, lld1.getRecursive(2330));
        assertEquals(null, lld1.getRecursive(-2330));
        lld1.addFirst(0); //[0]
        lld1.addFirst(1); //[1,0]
        lld1.addLast(-1); //[1,0,-1]
        assertThat(lld1.getRecursive(2) == 0);
    }

    @Test
    public void testRemoveAndAdd() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertEquals(null, lld1.removeFirst());
        assertEquals(null, lld1.removeLast());
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]
        assertThat(lld1.removeFirst() == -2); // [-1,0,1,2]
        assertThat(lld1.removeLast() == 2); // [-1,0,1]
        assertThat(lld1.toList()).containsExactly(-1, 0, 1).inOrder();
        lld1.removeFirst(); // [0,1]
        lld1.removeLast();  // [0]
        assertThat(lld1.toList().size() == 1);
        lld1.addLast(2); // [0,2]
        lld1.removeFirst(); // [2]
        assertThat(lld1.toList().size() == 1);
        lld1.removeFirst(); // []
        assertTrue(lld1.isEmpty());
        lld1.addFirst(1); //[1]
        lld1.removeLast(); // []
        assertTrue(lld1.isEmpty());
        assertThat(lld1.toList().size() == 0);
    }

    @Test
    public void testAddfirstRemoveLastEdgeCase() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(-1);
        assertThat(lld1.removeLast() == -1);
    }

    @Test
    public void testAddafterRemove() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(-1);
        lld1.removeFirst();
        lld1.addFirst(1);
        assertThat(lld1.toList()).containsExactly(1).inOrder();
        lld1.removeLast();
        lld1.addLast(1);
        assertThat(lld1.toList()).containsExactly(1).inOrder();
    }
}



