package ru.vtb.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Утилитарный класс для работы с коллекциями должен ")
public class CollectionsUtilsTest {

    @DisplayName("корректно конвертировать итерируемые объекты в список")
    @Test
    public void shouldCorrectlyConvertIterableToList() {
        var expectedList = Arrays.asList("1", "2", "3");
        var actualList = CollectionUtils.toList(expectedList::iterator);
        assertThat(actualList)
                .isNotNull()
                .hasSize(expectedList.size())
                .isEqualTo(expectedList);
    }
}
