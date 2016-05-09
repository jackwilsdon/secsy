package me.jackwilsdon.secsy;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A {@link Map} that is automatically keyed.
 *
 * @param <K> the key type for the map
 * @param <V> the value type for the map
 */
public abstract class AutoKeyedMap<K, V> implements Map<K, V> {
    private final Map<K, V> map;
    private boolean preventOverwrite;

    /**
     * Constructs a new automatically keyed map that wraps the provided map.
     *
     * @param map the map to wrap
     * @param preventOverwrite prevent overwriting of existing keys
     */
    public AutoKeyedMap(final Map<K, V> map, final boolean preventOverwrite) {
        this.map = map;
        this.preventOverwrite = preventOverwrite;
    }

    /**
     * Constructs a new automatically keyed map that wraps the provided map.
     *
     * @param map the map to wrap
     */
    public AutoKeyedMap(final Map<K, V> map) {
        this(map, false);
    }

    /**
     * Returns the key for the provided value.
     *
     * <p>
     * This method looks up or calculates a key for the provided value.
     *
     * It has no implementation by default and therefore it's workings are implementation dependant.
     * </p>
     *
     * @param value the value to get the key for
     *
     * @return the key for the provided value
     */
    public abstract K getKey(final V value);

    /**
     * Returns whether or not the map prevents overwriting of keys.
     *
     * @return whether or not the map prevents overwriting of keys
     */
    public final boolean isOverwritable() {
        return !preventOverwrite;
    }

    /**
     * Clears the map.
     *
     * @see Map#clear()
     */
    public final void clear() {
        map.clear();
    }

    /**
     * Returns whether or not the wrapped map contains the provided key.
     *
     * @param key the key to check
     *
     * @return whether or not the wrapped map contains the provided key
     *
     * @see Map#containsKey(Object)
     */
    public final boolean containsKey(final Object key) {
        return map.containsKey(key);
    }

    /**
     * Returns whether or not the wrapped map contains the provided value.
     *
     * @param value the value to check
     *
     * @return whether or not the wrapped map contains the provided value
     *
     * @see Map#containsValue(Object)
     */
    public final boolean containsValue(final Object value) {
        return map.containsValue(value);
    }

    /**
     * Returns the entry set for the wrapped map.
     *
     * @return the entry set for the wrapped map
     *
     * @see Map#entrySet()
     */
    public final Set<Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    /**
     * Returns whether or not the wrapped map is equal to the provided object.
     *
     * @param o the object to check
     *
     * @return whether or not the wrapped map is equal to the provided object
     *
     * @see Map#equals(Object)
     */
    public final boolean equals(final Object o) {
        return map.equals(o);
    }

    /**
     * Returns the value for the provided key.
     *
     * @param key the key to get the value for
     *
     * @return the value for the provided key
     *
     * @see Map#get(Object)
     */
    public final V get(final Object key) {
        return map.get(key);
    }

    /**
     * Returns the hash code of the wrapped map.
     *
     * @return the hash code of the wrapped map
     *
     * @see Map#hashCode()
     */
    public final int hashCode() {
        return map.hashCode();
    }

    /**
     * Returns whether or not the wrapped map is empty.
     *
     * @return whether or not the wrapped map is empty.
     *
     * @see Map#isEmpty()
     */
    public final boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Returns the key set of the wrapped map.
     *
     * @return the key set of the wrapped map
     *
     * @see Map#keySet()
     */
    public final Set<K> keySet() {
        return map.keySet();
    }

    /**
     * Puts a key/value pair in the wrapped map.
     *
     * <p>
     * If the key is not <code>null</code> then this method will throw an {@link UnsupportedOperationException}.
     *
     * This is due to the fact that the key is automatically derived by {@link #getKey(Object)} and should not be
     * provided manually.
     * </p>
     *
     * <p>
     * If the key derived by {@link #getKey(Object)} already exists in the map and <code>preventOverwrite</code> is
     * <code>true</code> (i.e. {@link #isOverwritable()} returns <code>false</code>) then this method will throw
     * an {@link IllegalStateException}.
     * </p>
     *
     * @param key the key for the key/value pair
     * @param value the value for the key/value pair
     *
     * @return the previous value associated with the provided key
     *
     * @see Map#put(Object, Object)
     */
    public final V put(final K key, final V value) {
        if (key != null) {
            throw new UnsupportedOperationException("cannot put value with non-null key");
        }

        final K generatedKey = getKey(value);

        if (preventOverwrite && map.containsKey(generatedKey)) {
            throw new IllegalStateException("derived key already exists");
        }

        return map.put(generatedKey, value);
    }

    /**
     * Puts all of the key/values pairs from the provided map into the wrapped map.
     *
     * @param m the map to retrieve the key/value pairs from
     *
     * @see #put(Object, Object)
     * @see Map#putAll(Map)
     */
    public final void putAll(final Map<? extends K, ? extends V> m) {
        for (final Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    /**
     * Removes the value with the provided key.
     *
     * @param key the key of the value to remove
     *
     * @return the previous value for the provided key
     *
     * @see Map#remove(Object)
     */
    public final V remove(final Object key) {
        return map.remove(key);
    }

    /**
     * Returns the size of the wrapped map.
     *
     * @return the size of the wrapped map
     *
     * @see Map#size()
     */
    public final int size() {
        return map.size();
    }

    /**
     * Returns the values of the wrapped map.
     *
     * @return the values of the wrapped map
     *
     * @see Map#values()
     */
    public final Collection<V> values() {
        return map.values();
    }
}
