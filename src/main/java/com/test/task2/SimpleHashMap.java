package com.test.task2;

public interface SimpleHashMap<K, V> {
    void put(K key, V value);
    V get(K key);
    boolean remove(K key);
    int size();
}

