package ru.vtb.util;

import org.junit.Assert;
import org.junit.Test;

public class IntHolderTest {

    @Test
    public void testIncrement() {
        final IntHolder holder = new IntHolder(7);
        Assert.assertEquals(8, holder.increment());
        Assert.assertEquals(9, holder.increment());
    }

    @Test
    public void testGetValue() {
        final int value = -7;
        final IntHolder holder = new IntHolder(value);
        Assert.assertEquals(value, holder.getValue());
    }
}
