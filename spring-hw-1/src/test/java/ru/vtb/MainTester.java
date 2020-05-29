package ru.vtb;

import org.junit.Assert;
import org.junit.Test;
import ru.vtb.service.impl.PollReader;
import ru.vtb.util.SplitHelper;

import java.io.IOException;

public class MainTester {

    @Test
    public void testPollReader() throws IOException {
        PollReader reader = new PollReader("/testQuestions.csv");
        Assert.assertEquals(1, reader.getQuestions().size());
    }

    @Test
    public void testSplitHelper() {
        Assert.assertTrue(SplitHelper.getColumnValues("").isEmpty());
        Assert.assertArrayEquals(new String[]{"Test", "Test2"}, SplitHelper.getColumnValues("Test;Test2").toArray());
    }

}
