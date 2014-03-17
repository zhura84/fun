package com.test.task2;

public class SimpleHashMapImpl<K, V> implements SimpleHashMap<K, V>{

    private final static int DEFAULT_CAPACITY = 16;

    private final Entry<K, V>[] table;

    private int size = 0;

    public SimpleHashMapImpl() {
        this(DEFAULT_CAPACITY);
    }

    public SimpleHashMapImpl(int capacity) {
        table = new Entry[capacity < DEFAULT_CAPACITY ? DEFAULT_CAPACITY : capacity];
    }

    @Override
    public void put(K key, V value) {
        int index = (key != null) ? getIndex(key.hashCode()) : 0;
        Entry<K, V> current = table[index];
        if (null == current) {
            table[index] = new Entry<K, V>(key, value);
            size++;
            return;
        }
        if (equalsKeys(key, current.getKey())) {
            current.setValue(value);
            return;
        }
        while (!current.isLast()) {
            current = current.getNext();
            if (equalsKeys(key, current.getKey())) {
                current.setValue(value);
                return;
            }
        };
        current.setNext(new Entry<K, V>(key, value));
        size++;
    }

    @Override
    public boolean remove(K key) {
        int index = (key != null) ? getIndex(key.hashCode()) : 0;
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;
        while (null != current) {
            if (equalsKeys(key, current.getKey())) {
                if (previous == null) {
                    table[index] = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                size--;
                return true;
            }
            previous = current;
            current = current.getNext();
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = (key != null) ? getIndex(key.hashCode()) : 0;
        Entry<K, V> current = table[index];
        while (null != current) {
            if (equalsKeys(key, current.getKey())) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private int getIndex(int hash) {
        if (hash < 0) {
            hash = -hash;
        }
        return (hash % table.length);
    }

    private boolean equalsKeys(K key1, K key2) {
        return key1 != null ? key1.equals(key2) : key2 == null;
    }

    private class Entry<K, V> {

        private final K key;
        private V value;
        private Entry<K, V> next = null;

        public Entry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(final V value) {
            this.value = value;
        }

        public void setNext(final Entry<K, V> newEntry) {
            next = newEntry;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public boolean isLast() {
            return next == null;
        }
    }
}
