package ru.vtb.dao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ActionType {
    CREATE(Constants.CREATE),
    RETRIEVE(Constants.RETRIEVE),
    UPDATE(Constants.UPDATE),
    DELETE(Constants.DELETE);

    private final String name;

    public static class Constants {
        public static final String CREATE = "CREATE";
        public static final String RETRIEVE = "RETRIEVE";
        public static final String UPDATE = "UPDATE";
        public static final String DELETE = "DELETE";
    }
}
