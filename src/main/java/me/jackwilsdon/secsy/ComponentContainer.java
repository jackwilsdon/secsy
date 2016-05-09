package me.jackwilsdon.secsy;

import java.util.Map;
import java.util.NoSuchElementException;

public final class ComponentContainer {
    private static final String NO_SUCH_COMPONENT_ERROR = "no such component %s";

    private final ClassInstanceMap<Component> components;

    public ComponentContainer(final Map<Class<?>, Component> map, final boolean preventOverwrite) {
        this.components = new ClassInstanceMap<>(map, preventOverwrite);
    }

    public ComponentContainer(final Map<Class<?>, Component> map) {
        this(map, false);
    }

    public boolean contains(final Class<?> componentClass) {
        return components.containsKey(componentClass);
    }

    public <T> T get(final Class<T> componentClass) {
        if (!contains(componentClass)) {
            final String message = String.format(NO_SUCH_COMPONENT_ERROR, componentClass.getName());

            throw new NoSuchElementException(message);
        }

        return components.getCast(componentClass);
    }

    public void add(final Component component) {
        components.put(null, component);
    }

    public void remove(final Class<?> componentClass) {
        if (!contains(componentClass)) {
            final String message = String.format(NO_SUCH_COMPONENT_ERROR, componentClass.getName());

            throw new NoSuchElementException(message);
        }

        components.remove(componentClass);
    }
}
