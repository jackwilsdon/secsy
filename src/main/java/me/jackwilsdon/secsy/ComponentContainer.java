package me.jackwilsdon.secsy;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A container for {@link Component} instances.
 */
public final class ComponentContainer {
    private static final String NO_SUCH_COMPONENT_ERROR = "no such component %s";

    private final ClassInstanceMap<Component> components;

    /**
     * Constructs a new component container that wraps the provided map.
     *
     * @param map the map to wrap
     * @param preventOverwrite prevent overwriting of existing keys
     *
     * @see {@link AutoKeyedMap#AutoKeyedMap(Map, boolean)}
     */
    public ComponentContainer(final Map<Class<?>, Component> map, final boolean preventOverwrite) {
        this.components = new ClassInstanceMap<>(map, preventOverwrite);
    }

    /**
     * Constructs a new component container that wraps the provided map.
     *
     * @param map the map to wrap
     *
     * @see {@link AutoKeyedMap#AutoKeyedMap(Map)}
     */
    public ComponentContainer(final Map<Class<?>, Component> map) {
        this(map, false);
    }

    /**
     * Returns whether or not the container contains a component of the provided class.
     *
     * @param componentClass the class of the component to check
     *
     * @return whether or not the container contains a component of the provided class
     *
     * @see Map#containsKey(Object)
     */
    public boolean contains(final Class<?> componentClass) {
        return components.containsKey(componentClass);
    }

    /**
     * Returns the component of the provided class.
     *
     * <p>
     * This method throws a {@link NoSuchElementException} if there is no component of the provided class inside the
     * container.
     * </p>
     *
     * @param componentClass the class of the component to retrieve
     *
     * @return the component of the provided class.
     *
     * @see Map#get(Object)
     */
    public <T> T get(final Class<T> componentClass) {
        if (!contains(componentClass)) {
            final String message = String.format(NO_SUCH_COMPONENT_ERROR, componentClass.getName());

            throw new NoSuchElementException(message);
        }

        return components.getCast(componentClass);
    }

    /**
     * Adds a component to the container, overwriting any other components of the same class.
     *
     * @param component the component to add to the container
     */
    public void add(final Component component) {
        components.put(null, component);
    }

    /**
     * Removes the component of the provided class from the container.
     *
     * <p>
     * This method throws a {@link NoSuchElementException} if there is no component of the provided class inside the
     * container.
     * </p>
     *
     * @param componentClass the class of the component to remove
     */
    public void remove(final Class<?> componentClass) {
        if (!contains(componentClass)) {
            final String message = String.format(NO_SUCH_COMPONENT_ERROR, componentClass.getName());

            throw new NoSuchElementException(message);
        }

        components.remove(componentClass);
    }
}
