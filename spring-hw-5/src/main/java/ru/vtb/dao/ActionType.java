package ru.vtb.dao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ActionType {

    CREATE(ActionType.CREATE_VALUE),
    RETRIEVE(ActionType.RETRIEVE_VALUE),
    UPDATE(ActionType.UPDATE_VALUE),
    DELETE(ActionType.DELETE_VALUE);

    public static final String CREATE_VALUE = "CREATE";
    public static final String RETRIEVE_VALUE = "RETRIEVE";
    public static final String UPDATE_VALUE = "UPDATE";
    public static final String DELETE_VALUE = "DELETE";

    private final String name;
}
