package ru.vtb.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Хранитель целых чисел должен ")
public class IntHolderTest {

    @Test
    @DisplayName("корректно инкрементировать значения")
    public void shouldIncrement() {
        final IntHolder holder = new IntHolder(7);
        assertEquals(8, holder.increment());
        assertEquals(9, holder.increment());
    }

    @Test
    @DisplayName("корректно возвращать текущие значения")
    public void shouldGetValue() {
        final int value = -7;
        final IntHolder holder = new IntHolder(value);
        assertEquals(value, holder.getValue());
    }
}
