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
}