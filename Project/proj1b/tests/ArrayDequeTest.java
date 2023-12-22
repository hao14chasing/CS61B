import org.junit.jupiter.api.Test;


import static com.google.common.truth.Truth.assertThat;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    public void testaddFirstAndaddlast() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertTrue(ad.isEmpty());
        ad.addLast(0); //[0]
        assertEquals(ad.size(), 1);
        assertTrue(!ad.isEmpty());
        ad.addLast(1);  //[0,1]
        ad.addLast(2);  //[0,1,2]
        ad.addLast(3);  //[0,1,2,3]
        assertThat(ad.toList()).containsExactly(0, 1, 2, 3).inOrder();
        ad.addFirst(4); //[0,1,2,3,4]  //[4,0,1,2,3]
        assertThat(ad.toList()).containsExactly(4, 0, 1, 2, 3).inOrder();
        ad.addFirst(5); //[0,1,2,3,5,4]  //[5,4,0,1,2,3]
        assertThat(ad.toList()).containsExactly(5, 4, 0, 1, 2, 3).inOrder();
        ad.addFirst(6); //[0,1,2,3,6,5,4]  //[6,5,4,0,1,2,3]
        ad.addFirst(7); //[0,1,2,3,7,6,5,4] //[7,6,5,4,0,1,2,3]
        assertEquals(ad.size(), 8);
        ad.addLast(8); // [0,1,2,3,7,6,5,4,8]  //[7,6,5,4,0,1,2,3,8]
        ad.addFirst(9); // [0,1,2,3,7,6,5,4,8,9] //[9,7,6,5,4,0,1,2,3,8]
        assertEquals(ad.size(), 10);
        assertThat(ad.toList()).containsExactly(9, 7, 6, 5, 4, 0, 1, 2, 3, 8).inOrder();
    }

    @Test
    public void testget() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addLast(0); //[0]
        assertTrue(ad.get(0) == 0);
        ad.addLast(1);  //[0,1]
        ad.addLast(2);  //[0,1,2]
        ad.addLast(3);  //[0,1,2,3]
        assertTrue(ad.get(2) == 2);
        assertTrue(ad.get(4) == null);
        assertTrue(ad.get(-1) == null);
        ad.addFirst(4); //[0,1,2,3,4]  //[4,0,1,2,3]
        assertTrue(ad.get(0) == 4);
        ad.addFirst(5); //[0,1,2,3,5,4]  //[5,4,0,1,2,3]
        ad.addFirst(6); //[0,1,2,3,6,5,4]  //[6,5,4,0,1,2,3]
        ad.addFirst(7); //[0,1,2,3,7,6,5,4] //[7,6,5,4,0,1,2,3]
        assertTrue(ad.get(2) == 5);
        assertTrue(ad.get(4) == 0);
        ad.addLast(8); // [0,1,2,3,7,6,5,4,8]  //[7,6,5,4,0,1,2,3,8]
        ad.addFirst(9); // [0,1,2,3,7,6,5,4,8,9] //[9,7,6,5,4,0,1,2,3,8]
        assertTrue(ad.get(9) == 8);
        assertTrue(ad.get(0) == 9);
    }

    @Test
    public void testremove() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertThat(ad.removeLast() == null);
        assertThat(ad.removeLast() == null);
        assertThat(ad.size() == 0); //size_after_remove_from_empty
        ad.addFirst(0); //[0]
        assertTrue(ad.removeLast() == 0); //[]
        assertThat(ad.toList()).containsExactly().inOrder(); //to_list_empty test
        ad.addLast(0);
        assertThat(ad.removeFirst() == 0);
        assertThat(ad.toList()).containsExactly().inOrder(); // one_element_edge_case
        assertThat(ad.size() == 0); //size_after_remove_to_empty
        ad.addLast(0); //[0]
        assertTrue(ad.removeFirst() == 0); //[]
        ad.addFirst(0);
        ad.addLast(1);  //[0,1]
        assertTrue(ad.removeLast() == 1); //remove_last_to_1 //[0]
        ad.addLast(1); //[0,1]
        assertTrue(ad.removeFirst() == 0); //remove_first_to_1  //[1]
        ad.addFirst(0); //[0,1]
        ad.addLast(2);  //[0,1,2]
        ad.addLast(3);  //[0,1,2,3]
        assertTrue(ad.removeLast() == 3); //[0,1,2]
        assertThat(ad.toList()).containsExactly(0, 1, 2).inOrder();
        ad.addLast(3); //[0,1,2,3]
        ad.addFirst(4); //[0,1,2,3,4]  //[4,0,1,2,3]
        ad.addFirst(5); //[0,1,2,3,5,4]  //[5,4,0,1,2,3]
        ad.addFirst(6); //[0,1,2,3,6,5,4]  //[6,5,4,0,1,2,3]
        ad.addFirst(7); //[0,1,2,3,7,6,5,4] //[7,6,5,4,0,1,2,3]
        ad.addLast(8); // [0,1,2,3,7,6,5,4,8]  //[7,6,5,4,0,1,2,3,8]
        ad.addFirst(9); // [0,1,2,3,7,6,5,4,8,9] //[9,7,6,5,4,0,1,2,3,8]
        assertTrue(ad.removeLast() == 8); //[9,7,6,5,4,0,1,2,3]
        assertTrue(ad.removeFirst() == 9); //[7,6,5,4,0,1,2,3]
        assertThat(ad.toList()).containsExactly(7, 6, 5, 4, 0, 1, 2, 3).inOrder();
    }

    @Test
    public void testremoveLastandresize() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addLast(1); //[0,1]
        ad.addFirst(0); //[0,1]
        ad.addLast(2);  //[0,1,2]
        ad.addLast(3);  //[0,1,2,3]
        ad.addFirst(4); //[0,1,2,3,4]  //[4,0,1,2,3]
        ad.addFirst(5); //[0,1,2,3,5,4]  //[5,4,0,1,2,3]
        ad.addFirst(6); //[0,1,2,3,6,5,4]  //[6,5,4,0,1,2,3]
        ad.addFirst(7); //[0,1,2,3,7,6,5,4] //[7,6,5,4,0,1,2,3]
        ad.addLast(8); // [0,1,2,3,7,6,5,4,8]  //[7,6,5,4,0,1,2,3,8]
        ad.addFirst(9); // [0,1,2,3,7,6,5,4,8,9] //[9,7,6,5,4,0,1,2,3,8]
        for (int i = 10; i < 17; i++) {
            ad.addLast(i);
        }
        assertThat(ad.toList()).containsExactly(9, 7, 6, 5, 4, 0, 1, 2, 3, 8, 10, 11,
                12, 13, 14, 15, 16).inOrder();
        for (int i = 0; i < 11; i++) {
            ad.removeLast();
        }
        assertThat(ad.toList()).containsExactly(9, 7, 6, 5, 4, 0).inOrder();
    }

    @Test
    public void testremoveFirstandresize() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addLast(1); //[0,1]
        ad.addFirst(0); //[0,1]
        ad.addLast(2);  //[0,1,2]
        ad.addLast(3);  //[0,1,2,3]
        ad.addFirst(4); //[0,1,2,3,4]  //[4,0,1,2,3]
        ad.addFirst(5); //[0,1,2,3,5,4]  //[5,4,0,1,2,3]
        ad.addFirst(6); //[0,1,2,3,6,5,4]  //[6,5,4,0,1,2,3]
        ad.addFirst(7); //[0,1,2,3,7,6,5,4] //[7,6,5,4,0,1,2,3]
        ad.addLast(8); // [0,1,2,3,7,6,5,4,8]  //[7,6,5,4,0,1,2,3,8]
        ad.addFirst(9); // [0,1,2,3,7,6,5,4,8,9] //[9,7,6,5,4,0,1,2,3,8]
        for (int i = 10; i < 17; i++) {
            ad.addFirst(i);
        }
        assertThat(ad.toList()).containsExactly(16, 15, 14, 13, 12, 11,
                10, 9, 7, 6, 5, 4, 0, 1, 2, 3, 8).inOrder();
        for (int i = 0; i < 11; i++) {
            ad.removeFirst();
        }
        assertThat(ad.toList()).containsExactly(4, 0, 1, 2, 3, 8).inOrder();
    }
    @Test
    public void testThreePointTwo() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(6);
        ad.addFirst(8);
        ad.addFirst(9);
        ad.addFirst(10);
        ad.addFirst(11);
        ad.addFirst(12);
        ad.addFirst(13);
        ad.addFirst(15); //[15,13,12,11,10,9,8,6]
        ad.addLast(16); //[15,13,12,11,10,9,8,6,16]
        ad.removeLast();
        assertTrue(ad.removeFirst() == 15);
    }
    @Test
    public void testOnlyAddlastAndRemove() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertTrue(ad.isEmpty());
        ad.addLast(1);
        ad.addLast(2); //[1,2]
        assertTrue(ad.removeFirst() == 1); //[2]
        assertTrue(ad.get(0) == 2);
        assertTrue(ad.removeLast() == 2);
    }

    @Test
    public void testOnlyAddFirstAndRemoveLast() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertTrue(ad.isEmpty());
        ad.addFirst(1);
        ad.addFirst(2); //[2,1]
        assertTrue(ad.removeLast() == 1); //[2]
        assertTrue(ad.get(0) == 2);
    }
    @Test
    public void testAddLastedgecase() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 8; i++) {
            ad.addLast(i);
        } //[0,1,2,3,4,5,6,7]
        ad.addFirst(8);
        ad.removeFirst();
        assertTrue(ad.removeFirst() == 0);
    }

    /*@Test
    public void testget2() {
        ArrayDeque<Integer> ArrayDeque = new ArrayDeque<>();
        ArrayDeque.addLast(0);
        assertTrue(ArrayDeque.removeFirst() == 0);
        ArrayDeque.addLast(2);
        ArrayDeque.addLast(3);
        assertTrue(ArrayDeque.removeFirst() == 2);
        assertTrue(ArrayDeque.removeFirst() == 3);
        ArrayDeque.addFirst(5);
        assertTrue(ArrayDeque.removeFirst() == 5);
        ArrayDeque.addLast(7);
        ArrayDeque.addFirst(8); //[8,7]
        assertTrue(ArrayDeque.removeFirst() == 8);
        assertTrue(ArrayDeque.removeFirst() == 7);
        ArrayDeque.addFirst(11);
        ArrayDeque.addFirst(12);
        ArrayDeque.addFirst(13);
        ArrayDeque.addFirst(14); //[14,13,12,11]
        assertTrue(ArrayDeque.removeLast() == 11);
        assertTrue(ArrayDeque.removeLast() == 12);
        ArrayDeque.addFirst(17); //[17,14,13]
        assertTrue(ArrayDeque.removeLast() == 13);
        assertTrue(ArrayDeque.removeFirst() == 17);
        assertTrue(ArrayDeque.get(0) == 14);
        assertTrue(ArrayDeque.get(1) == null);
    }*/

    @Test
    public void testadd() {
        ArrayDeque<String> ad = new ArrayDeque<>();
        ad.addFirst("back");
        ad.addFirst("middle");
        ad.addFirst("front");
        assertThat(ad.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void testadd1() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 6; i++) {
            ad.addLast(i);
        }
        ad.addFirst(6);
        ad.addFirst(7);
        ad.addFirst(8);
        for (int i = 0; i < 5; i++) {
            ad.removeFirst();
        }
        assertThat(ad.toList()).containsExactly(2, 3, 4, 5).inOrder();
    }
}
