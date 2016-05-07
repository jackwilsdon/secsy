package me.jackwilsdon.secsy;

import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IndexedMapTest {

    @Test
    public void testPutIncrementsIndex() {
        final IndexedMap<Object> indexedMap = new IndexedMap<>(new HashMap<Integer, Object>());

        for (int i = 0; i < 5; i++) {
            final Object object = new Object();

            indexedMap.put(null, object);

            final int key = indexedMap.getKey(object);

            assertEquals(key, i + 1);
        }

        assertEquals(indexedMap.getNextIndex(), 6);
    }

    @Test
    public void testRemoveDoesNotChangeIndex() {
        final IndexedMap<Object> indexedMap = new IndexedMap<>(new HashMap<Integer, Object>());

        for (int i = 0; i < 5; i++) {
            final Object object = new Object();

            indexedMap.put(null, object);

            final int key = indexedMap.getKey(object);

            indexedMap.remove(key);
        }

        assertEquals(indexedMap.getNextIndex(), 6);
    }
}
