package me.jackwilsdon.secsy;

import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class ClassInstanceMapTest {

    @Test
    public void testGetKey() {
        final ClassInstanceMap<Object> instanceMap = new ClassInstanceMap<>(new HashMap<Class<?>, Object>());

        assertEquals(Object.class, instanceMap.getKey(new Object()));
    }

    @Test
    public void testGetCast() {
        final ClassInstanceMap<Object> instanceMap = new ClassInstanceMap<>(new HashMap<Class<?>, Object>());

        final Object value = new Object();

        instanceMap.put(null, value);

        final Object retrievedValue = instanceMap.getCast(Object.class);

        assertEquals(value, retrievedValue);
    }
}
