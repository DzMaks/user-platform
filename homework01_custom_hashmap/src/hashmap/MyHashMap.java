package hashmap;

import java.util.Objects;

public class MyHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private int size;
    private Node<K, V>[] buckets;

    public MyHashMap() {
        buckets = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int getIndex(K key) {
        if (key == null) {
           return 0;
        }
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public V put(K key, V value) {

        if ((float) size / buckets.length >= LOAD_FACTOR) {
            rehash();
        }

        int index = getIndex(key);

        Node<K, V> current = buckets[index];

        if (current == null) {
            buckets[index] = new Node<>(key, value);
            size++;
            return null;
        }
        while (true) {
            if (Objects.equals(current.key, key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }

            if (current.next == null) {
                break;
            }

            current = current.next;
        }
        current.next = new Node<>(key, value);
        size++;
        return null;
    }

    public V get(K key) {

        int index = getIndex(key);

        Node<K, V> current = buckets[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V remove(K key) {

        int index = getIndex(key);
        Node<K, V> current = buckets[index];
        Node<K, V> previous = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (previous == null) {buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null;

    }

    private void rehash() {
        Node<K, V>[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];
        int oldSize = size;
        size = 0;

        for (Node<K, V> bucket : oldBuckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
        size = oldSize;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}
