package hashmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int INITIAL = 16;
    private static final double MAX = 0.75;
    private int size = 0;

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(INITIAL, MAX);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, MAX);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     private int initialCapacity
     * @param loadFactor maximum load factor
     */

    private int initialCapacity;
    private double loadFactor;
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return null;
    }


    // Your code won't compile until you do so!
    @Override
    public void put(K key, V value) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node i : buckets[index]) {
            if (i.key.equals(key)) {
                i.value = value;
                return;
            }
        }
        if (buckets[index].size() == 0) {
            buckets[index].add(createNode(key, value));
            size += 1;
        } else {
            buckets[index].add(createNode(key, value));
            size += 1;
        }

        if ((double) size / initialCapacity > loadFactor) {
            resize();
        }

    }

    private void resize() {
        MyHashMap<K, V> newBusket = new MyHashMap<>(buckets.length * 2);
        for (int i = 0; i < buckets.length; i++) {
            for (Node y: buckets[i]) {
                newBusket.put(y.key, y.value);
            }
        }
        this.initialCapacity = newBusket.initialCapacity;
        this.size = newBusket.size;
        this.buckets = newBusket.buckets;
    }
    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node i: buckets[index]) {
            if (i.key.equals(key)) {
                return i.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node i: buckets[index]) {
            if (i.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
