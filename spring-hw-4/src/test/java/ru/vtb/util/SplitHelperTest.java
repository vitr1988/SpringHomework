package ru.vtb.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Разделитель строк должен ")
public class SplitHelperTest {

    @Test
    @DisplayName("корректно разделять значения")
    public void shouldCorrectSplitValues() {
        assertTrue(SplitHelper.getColumnValues("").isEmpty());
        assertArrayEquals(new String[]{"Test", "Test2"}, SplitHelper.getColumnValues("Test;Test2").toArray());
    }
}
