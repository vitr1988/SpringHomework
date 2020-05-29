package ru.vtb.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SplitHelper {

    /**
     * Column separator in CSV-files
     */
    private static final String SEPARATOR = ";";

    /**
     * Get information about columns in one row of CSV-file
     *
     * @param line      row of CSV-file
     * @return list of column values
     */
    public static List<String> getColumnValues(final String line) {
        if (Objects.isNull(line) || line.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(line.split(SEPARATOR));
    }
}
