package me.jackwilsdon.secsy;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link Map} that is numerically indexed.
 *
 * @param <V> the value type for the map
 */
public final class IndexedMap<V> extends AutoKeyedMap<Integer, V> {
    private int nextIndex = 1;
    private final Map<V, Integer> indexes = new HashMap<>();

    /**
     * Constructs a new indexed map that wraps the provided map.
     *
     * @param map the map to wrap
     */
    public IndexedMap(final Map<Integer, V> map) {
        super(map, true);
    }

    /**
     * Returns the key for the provided value.
     *
     * <p>
     * This method looks up the index for the provided value.
     *
     * If the value is not present in the map, a {@link IllegalStateException} is thrown.
     * </p>
     *
     * @param value the value to get the key for
     *
     * @return the key for the provided value
     */
    @Override
    public Integer getKey(final V value) {
        if (!indexes.containsKey(value)) {
            throw new IllegalStateException("no index for value");
        }

        return indexes.get(value);
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
     * @param key the key for the key/value pair
     * @param value the value for the key/value pair
     *
     * @return the previous value associated with the provided key
     *
     * @see Map#put(Object, Object)
     */
    @Override
    public V put(final Integer key, final V value) {
        final boolean alreadyPresent = containsValue(value);

        if (!alreadyPresent) {
            indexes.put(value, nextIndex++);
        }

        try {
            return super.put(key, value);
        } catch (UnsupportedOperationException exception) {
            if (!alreadyPresent) {
                indexes.remove(value);
            }

            throw exception;
        }
    }
}
