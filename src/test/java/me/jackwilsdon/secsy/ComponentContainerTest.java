package me.jackwilsdon.secsy;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class ComponentContainerTest {

    public ComponentContainerTest() { }

    @Test
    public void testContains() {
        final Map<Class<?>, Component> internalMap = new HashMap<>();
        final ComponentContainer container = new ComponentContainer(internalMap);

        final Component component = new FirstComponent();
        final Class<?> componentClass = component.getClass();

        internalMap.put(componentClass, component);

        assertTrue(container.contains(componentClass));
    }

    @Test
    public void testGet() {
        final Map<Class<?>, Component> internalMap = new HashMap<>();
        final ComponentContainer container = new ComponentContainer(internalMap);

        final Component component = new FirstComponent();
        final Class<?> componentClass = component.getClass();

        internalMap.put(componentClass, component);

        assertEquals(container.get(componentClass), component);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNotPresent() {
        final Map<Class<?>, Component> internalMap = new HashMap<>();
        final ComponentContainer container = new ComponentContainer(internalMap);

        container.get(FirstComponent.class);
    }

    @Test
    public void testAdd() {
        final Map<Class<?>, Component> internalMap = new HashMap<>();
        final ComponentContainer container = new ComponentContainer(internalMap);

        final Component component = new FirstComponent();
        final Class<?> componentClass = component.getClass();

        container.add(component);

        assertTrue(internalMap.containsKey(componentClass));
        assertEquals(internalMap.get(componentClass), component);
    }

    @Test
    public void testRemove() {
        final Map<Class<?>, Component> internalMap = new HashMap<>();
        final ComponentContainer container = new ComponentContainer(internalMap);

        final Component component = new FirstComponent();
        final Class<?> componentClass = component.getClass();

        internalMap.put(componentClass, component);

        container.remove(componentClass);

        assertFalse(internalMap.containsKey(componentClass));
    }

    private final class FirstComponent implements Component {
        FirstComponent() { }
    }
}
