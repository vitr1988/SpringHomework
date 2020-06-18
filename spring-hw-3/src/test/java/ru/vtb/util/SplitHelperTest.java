package ru.vtb.util;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class SplitHelperTest {

    @Test
    public void testSplitHelper() {
        assertTrue(SplitHelper.getColumnValues("").isEmpty());
        assertArrayEquals(new String[]{"Test", "Test2"}, SplitHelper.getColumnValues("Test;Test2").toArray());
    }
}
