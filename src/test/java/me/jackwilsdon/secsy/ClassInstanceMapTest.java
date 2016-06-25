package me.jackwilsdon.secsy;

import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link ClassInstanceMap}.
 *
 * @see ClassInstanceMap
 */
public final class ClassInstanceMapTest {

    /**
     * Tests for {@link ClassInstanceMap}.
     *
     * @see ClassInstanceMap
     */
    public ClassInstanceMapTest() { }

    /**
     * Tests getting a cast value from the map.
     *
     * <p>
     *     This tests puts a value in the map and then retrieves it by class using
     *     {@link ClassInstanceMap#getCast(Class)}. It then checks the returned value against the original one.
     * </p>
     */
    @Test
    public void testGetCast() {
        final ClassInstanceMap<Object> instanceMap = new ClassInstanceMap<>(new HashMap<Class<?>, Object>());

        final Object value = new Integer(0);

        instanceMap.put(null, value);

        // Retrieve the value as an Integer object.
        final Integer retrievedValue = instanceMap.getCast(Integer.class);

        assertEquals(value, retrievedValue);
    }
}
