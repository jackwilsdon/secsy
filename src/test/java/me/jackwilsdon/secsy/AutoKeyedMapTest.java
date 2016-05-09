package me.jackwilsdon.secsy;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public final class AutoKeyedMapTest {
    private static final String TEST_VALUE = "Value";

    private final class HashCodeMap<V> extends AutoKeyedMap<Integer, V> {
        public HashCodeMap(final Map<Integer, V> map, final boolean preventOverwrite) {
            super(map, preventOverwrite);
        }

        public HashCodeMap(final Map<Integer, V> map) {
            super(map);
        }

        @Override
        public Integer getKey(final V value) {
            return value.hashCode();
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPutNonNullKey() {
        final Map<Integer, Object> autoKeyedMap = new HashCodeMap<>(new HashMap<Integer, Object>());

        autoKeyedMap.put(0, TEST_VALUE);
    }

    @Test
    public void testPut() {
        final AutoKeyedMap<Integer, Object> autoKeyedMap = new HashCodeMap<>(new HashMap<Integer, Object>());

        final String value = TEST_VALUE;
        final Integer key = autoKeyedMap.getKey(value);

        autoKeyedMap.put(null, value);

        assertTrue(autoKeyedMap.containsKey(key));
        assertTrue(autoKeyedMap.containsValue(value));
    }

    @Test
    public void testPutExisting() {
        final Map<Integer, Object> autoKeyedMap = new HashCodeMap<>(new HashMap<Integer, Object>());

        final String value = TEST_VALUE;

        autoKeyedMap.put(null, value);

        final Object originalValue = autoKeyedMap.put(null, value);

        assertTrue(originalValue.equals(value));
    }

    @Test(expected = IllegalStateException.class)
    public void testPutExistingPreventOverwrite() {
        final Map<Integer, Object> autoKeyedMap = new HashCodeMap<>(new HashMap<Integer, Object>(), true);

        final String value = TEST_VALUE;

        autoKeyedMap.put(null, value);
        autoKeyedMap.put(null, value);
    }
}
