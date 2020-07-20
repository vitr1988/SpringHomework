package ru.vtb.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CollectionUtils {

    public static <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
