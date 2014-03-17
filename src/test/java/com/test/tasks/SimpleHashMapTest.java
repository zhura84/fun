package com.test.tasks;

import com.test.task2.SimpleHashMap;
import com.test.task2.SimpleHashMapImpl;
import org.junit.*;
import static org.junit.Assert.*;



public class SimpleHashMapTest {

    @Test
    public void testPutGet() {
        SimpleHashMap<String, Integer> map = new SimpleHashMapImpl<String, Integer>();
        map.put("Andrey", 111);
        map.put("Max", 2234);
        map.put(null, 2234);

        assertNull(map.get("Igor"));
        assertEquals(111, map.get("Andrey"), 0);
        assertNotEquals(111, map.get("Max"), 0);
        assertEquals(2234, map.get(null), 0);
    }

    @Test
    public void testDoublePut() {
        SimpleHashMap<String, Integer> map = new SimpleHashMapImpl<String, Integer>();
        map.put("Andrey", 111);
        map.put("Ivan", 222);

        assertEquals(111, map.get("Andrey"), 0);
        assertEquals(222, map.get("Ivan"), 0);

        map.put("Andrey", 444);
        map.put("Ivan", 555);
        map.put("Max", 11111);

        assertEquals(444, map.get("Andrey"), 0);
        assertEquals(555, map.get("Ivan"), 0);
        assertEquals(11111, map.get("Max"), 0);
    }

    @Test
    public void testSize() {
        SimpleHashMap<String, Integer> map = new SimpleHashMapImpl<String, Integer>();
        map.put("Andrey", 111);
        map.put("Ivan", 222);
        map.put("Andrey", 444);
        map.put(null, 2234);
        map.put("Ivan", 555);
        map.put("Max", 222);
        map.put(null, 21);

        assertEquals(4, map.size(), 0);
    }

    @Test
    public void testRemove() {
        SimpleHashMap<String, Integer> map = new SimpleHashMapImpl<String, Integer>();
        map.put("Andrey", 111);
        map.put("Ivan", 222);
        map.put("Andrey", 444);
        map.put(null, 2234);
        map.put("Max", 222);

        assertTrue(map.remove("Andrey"));
        assertNull(map.get("Andrey"));
        assertTrue(map.remove(null));
        assertNull(map.get(null));
        assertFalse(map.remove("Andrey"));
        assertNull(map.get("Sasha"));
        assertEquals(222, map.get("Ivan"), 0);
    }

    @Test
    public void testManyEntries() {
        SimpleHashMap<String, Integer> map = new SimpleHashMapImpl<String, Integer>(100);
        int count = 200; // set this to big number
        for (int i = 0; i < count; i++) {
            map.put("Andrey" + String.valueOf(i), i);
        }
        for (int i = 0; i < count; i++) {
            assertEquals(map.get("Andrey" + String.valueOf(i)), i, 0);
        }
        assertEquals(count, map.size(), 0);
        for (int i = 0; i < count; i = i + 2) {
            assertTrue(map.remove("Andrey" + String.valueOf(i)));
        }
        assertEquals(100, map.size(), 0);
    }
}
