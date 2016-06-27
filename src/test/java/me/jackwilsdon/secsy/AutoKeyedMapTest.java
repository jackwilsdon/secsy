package me.jackwilsdon.secsy;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link AutoKeyedMap}.
 *
 * @see AutoKeyedMap
 */
public final class AutoKeyedMapTest {
    private static final String TEST_VALUE = "Value";

    /**
     * Tests for {@link AutoKeyedMap}.
     *
     * @see AutoKeyedMap
     */
    public AutoKeyedMapTest() { }

    /**
     * Tests putting a value into the map.
     *
     * <p>
     *     This test puts a value in the map with a {@code null} key. It then checks to ensure that the key
     *     returned by {@link AutoKeyedMap#getKey(Object)} is correct for the provided value.
     * </p>
     *
     * @see AutoKeyedMap#put(Object, Object)
     * @see AutoKeyedMap#getKey(Object)
     */
    @Test
    public void testPut() {
        final AutoKeyedMap<Integer, Object> autoKeyedMap = new HashCodeMap<>(new HashMap<Integer, Object>());

        // Add the test value to the map with a null key.
        autoKeyedMap.put(null, TEST_VALUE);

        // Get the generated key from the map.
        final Integer key = autoKeyedMap.getKey(TEST_VALUE);

        // Ensure that the key is correct.
        assertEquals(autoKeyedMap.get(key), TEST_VALUE);
    }

    /**
     * Tests putting a value into the map with {@code preventOverwrite} set to {@code true}.
     *
     * <p>
     *     This test first creates a map with {@code preventOverwrite} set to {@code true}. It then puts a value in the
     *     map with a {@code null} key and checks to ensure that the key returned by
     *     {@link AutoKeyedMap#getKey(Object)} is correct for the provided value.
     * </p>
     *
     * @see AutoKeyedMap#put(Object, Object)
     * @see AutoKeyedMap#getKey(Object)
     */
    @Test
    public void testPutPreventOverwrite() {
        final AutoKeyedMap<Integer, Object> autoKeyedMap = new HashCodeMap<>(new HashMap<Integer, Object>(), true);

        // Add the test value to the map with a null key.
        autoKeyedMap.put(null, TEST_VALUE);

        // Get the generated key from the map.
        final Integer key = autoKeyedMap.getKey(TEST_VALUE);

        // Ensure that the key is correct.
        assertEquals(autoKeyedMap.get(key), TEST_VALUE);
    }

    /**
     * Tests putting an existing value into the map.
     *
     * <p>
     *     This test puts a value in the map with a {@code null} key twice. It stores the return value from
     *     {@link AutoKeyedMap#put(Object, Object)} on the second call and compares the value to the first value
     *     added to the map. This is done to ensure that {@link AutoKeyedMap#put(Object, Object)} returns the
     *     previous value for the generated key.
     * </p>
     *
     * @see AutoKeyedMap#put(Object, Object)
     * @see AutoKeyedMap#getKey(Object)
     */
    @Test
    public void testPutExisting() {
        final AutoKeyedMap<Integer, Object> autoKeyedMap = new HashCodeMap<>(new HashMap<Integer, Object>());

        final Object firstObject = new FakeHashCodeObject(0);
        final Object secondObject = new FakeHashCodeObject(0);

        autoKeyedMap.put(null, firstObject);

        // Put the second object in with the same automatically generated key (i.e. same hashCode value).
        final Object originalValue = autoKeyedMap.put(null, secondObject);

        // Ensure that put has returned the first object (i.e. the original value for the automatically generated key).
        assertTrue(originalValue.equals(firstObject));

        // Get the generated key for the second object.
        final Integer key = autoKeyedMap.getKey(secondObject);

        // Ensure that the key is correct for the second object.
        assertEquals(autoKeyedMap.get(key), secondObject);
    }

    /**
     * Tests putting an existing value into the map with {@code preventOverwrite} set to {@code true}.
     *
     * <p>
     *     This test first creates a map with {@code preventOverwrite} set to {@code true}. It then puts a value in the
     *     map with a {@code null} key twice. This should throw an {@link IllegalStateException}, as
     *     {@code preventOverwrite} ensures that a key cannot be overwritten.
     * </p>
     *
     * @see AutoKeyedMap#put(Object, Object)
     */
    @Test(expected = IllegalStateException.class)
    public void testPutExistingPreventOverwrite() {
        final Map<Integer, Object> autoKeyedMap = new HashCodeMap<>(new HashMap<Integer, Object>(), true);

        autoKeyedMap.put(null, TEST_VALUE);
        autoKeyedMap.put(null, TEST_VALUE);
    }

    /**
     * Tests putting a value into the map with a non-null key.
     *
     * <p>
     *     This test puts a value into the map with a non-null key. It should throw an
     *     {@link UnsupportedOperationException}, as the key is automatically generated in an
     *     {@link AutoKeyedMap}.
     * </p>
     *
     * @see AutoKeyedMap#put(Object, Object)
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testPutNonNullKey() {
        final Map<Integer, Object> autoKeyedMap = new HashCodeMap<>(new HashMap<Integer, Object>());

        autoKeyedMap.put(0, TEST_VALUE);
    }

    /**
     * An automatically keyed map that uses the hash code of the value as it's key.
     *
     * @param <V> the value type for the map
     */
    private static final class HashCodeMap<V> extends AutoKeyedMap<Integer, V> {
        HashCodeMap(final Map<Integer, V> map, final boolean preventOverwrite) {
            super(map, preventOverwrite);
        }

        HashCodeMap(final Map<Integer, V> map) {
            super(map);
        }

        @Override
        public Integer getKey(final V value) {
            return value.hashCode();
        }
    }

    /**
     * A class that has a fake hash code (i.e. one that can be set at construction time).
     *
     * <p>
     *     Note that this class breaks the {@code equals}/{@code hashCode} contract and should only be used for testing.
     * </p>
     */
    private static final class FakeHashCodeObject {
        private final int hashCode;

        FakeHashCodeObject(final int hashCode) {
            this.hashCode = hashCode;
        }

        public int hashCode() {
            return hashCode;
        }
    }
}
