import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {

    private class Node {
        private T item;
        private Node prev;
        private Node next;
        Node(Node p, T x, Node n) {
            item = x;
            prev = p;
            next = n;
        }
    }

    private Node sentinel;
    private int size;
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
    }

    @Override
    public void addFirst(T x) {
        sentinel.next = new Node(sentinel, x, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        sentinel.prev = new Node(sentinel.prev, x, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node track = sentinel;
        while (track.next != sentinel) {
            track = track.next;
            returnList.add(track.item);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T num = sentinel.next.item;
        size -= 1;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return num;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T num = sentinel.prev.item;
        size -= 1;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        return num;
    }

    @Override
    public T get(int index) {
        int track1 = 0;
        Node track2 = sentinel;
        while (track2.next != null) {
            track2 = track2.next;
            if (track1 == index) {
                return track2.item;
            }
            track1++;
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {


        if (index < 0) {
            return null;
        }
        if (index >= size) {
            return null;
        }
        int track = 0;
        Node p = sentinel.next;
        return helper(index, track, p);
    }
    private T helper(int index, int track1, Node p) {
        if (index == track1) {
            return p.item;
        }
        return helper(index, track1 + 1, p.next);
    }
}
