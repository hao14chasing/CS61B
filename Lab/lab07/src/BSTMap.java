

import com.github.javaparser.ast.Node;

import java.util.Iterator;
import java.util.Set;
public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {
    private Node tree;
    private int size;
    /*private K key;
    private V value;
    private Node right;
    private Node left;*/
    public BSTMap() {
        tree = null;
        this.clear();
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        if (!containsKey(key)) {
            size++;
        }
        tree = puthelper(tree, key, value);

    }

    @Override
    public V get(K key) {
        return gethelper(tree, key);
    }

    @Override
    public boolean containsKey(K key) {
        return containsKeyhelper(tree, key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        tree = null;
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

    //nested class helper
    private class Node {
        private K key;
        private V value;
        private Node right;
        private Node left;
        private Node(K key, V value) {
            this.value = value;
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }
    // puthelper
    private Node puthelper(Node n, K key, V value) {
        if (n == null) {
            return new Node(key, value);
        }
        int track = key.compareTo(n.key);
        if (track == 0) {
            n.value = value;
        } else if (track > 0) {
            n.right = puthelper(n.right, key, value);
        } else {
            n.left = puthelper(n.left, key, value);
        }
        return n;
    }

    //gethelper
    private V gethelper(Node n, K key) {
        if (n == null) {
            return null;
        }
        int track = key.compareTo(n.key);
        if (track == 0) {
            return n.value;
        } else if (track > 0) {
            return gethelper(n.right, key);
        } else {
            return gethelper(n.left, key);
        }
    }
    //containsKeyhelper
    private boolean containsKeyhelper(Node n, K key) {
        if (n == null) {
            return false;
        }
        int track = key.compareTo(n.key);
        if (track == 0) {
            return n.value == get(key);
        } else if (track > 0) {
            if (n.right == null) {
                return false;
            }
            return containsKeyhelper(n.right, key);
        } else {
            if (n.left == null) {
                return false;
            }
            return containsKeyhelper(n.left, key);
        }
    }
}
