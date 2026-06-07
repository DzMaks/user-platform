package hashmap;

public class MyHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
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
            throw new IllegalArgumentException("Ключ не может быть нулем");
        }
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public V put(K key, V value) {

        int index = getIndex(key);

        Node<K, V> current = buckets[index];

        if (current == null) {
            buckets[index] = new Node<>(key, value);
            size++;
            return null;
        }
        while (true) {
            if (current.key.equals(key)) {
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
            if (current.key.equals(key)) {
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
            if (current.key.equals(key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                V oldValue = current.value;
                size--;
                return oldValue;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}
