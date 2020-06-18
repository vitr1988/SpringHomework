package ru.vtb.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntHolderTest {

    @Test
    public void testIncrement() {
        final IntHolder holder = new IntHolder(7);
        assertEquals(8, holder.increment());
        assertEquals(9, holder.increment());
    }

    @Test
    public void testGetValue() {
        final int value = -7;
        final IntHolder holder = new IntHolder(value);
        assertEquals(value, holder.getValue());
    }
}
