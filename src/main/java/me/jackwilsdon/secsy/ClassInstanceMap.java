package me.jackwilsdon.secsy;

import java.util.Map;

@SuppressWarnings("checkstyle:all")
public final class ClassInstanceMap<V> extends AutoKeyedMap<Class<?>, V> {
    public ClassInstanceMap(Map<Class<?>, V> map, boolean preventOverwrite) {
        super(map, preventOverwrite);
    }

    public ClassInstanceMap(Map<Class<?>, V> map) {
        super(map);
    }

    @Override
    public Class<?> getKey(V value) {
        return value.getClass();
    }

    public <T> T getCast(Class<T> key) {
        V value = get(key);

        return value == null ? null : key.cast(value);
    }
}
