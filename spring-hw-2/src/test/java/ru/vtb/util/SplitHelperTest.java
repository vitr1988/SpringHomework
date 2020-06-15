package ru.vtb.util;

import org.junit.Assert;
import org.junit.Test;

public class SplitHelperTest {

    @Test
    public void testSplitHelper() {
        Assert.assertTrue(SplitHelper.getColumnValues("").isEmpty());
        Assert.assertArrayEquals(new String[]{"Test", "Test2"}, SplitHelper.getColumnValues("Test;Test2").toArray());
    }
}
