package me.jackwilsdon.secsy;

import java.util.Map;

/**
 * A {@link Map} of instances and their classes.
 *
 * @param <V> the value type for the map
 */
public final class ClassInstanceMap<V> extends AutoKeyedMap<Class<?>, V> {

    /**
     * Constructs a new class instance map that wraps the provided map.
     *
     * @param map the map to wrap
     * @param preventOverwrite prevent overwriting of existing keys
     */
    public ClassInstanceMap(final Map<Class<?>, V> map, final boolean preventOverwrite) {
        super(map, preventOverwrite);
    }

    /**
     * Constructs a new class instance map that wraps the provided map.
     *
     * @param map the map to wrap
     */
    public ClassInstanceMap(final Map<Class<?>, V> map) {
        super(map);
    }

    /**
     * Returns the key for the provided value.
     *
     * <p>
     * This method uses {@link Object#getClass()} to find the index for the provided value.
     * </p>
     *
     * @param value the value to get the key for
     *
     * @return the key for the provided value
     */
    @Override
    public Class<?> getKey(final V value) {
        return value.getClass();
    }

    /**
     * Returns the value for the provided key.
     *
     * <p>
     * This method casts the result from {@link #get(Object)} to the provided class before returning it.
     * </p>
     *
     * @param key the key to get the value for
     * @param <T> the type of object to return
     *
     * @return the value for the provided key
     */
    public <T> T getCast(final Class<T> key) {
        final V value = get(key);

        if (value == null) {
            return null;
        }

        return key.cast(value);
    }
}
