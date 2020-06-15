package ru.vtb.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IntHolder {
    private int value;

    public int increment(){
        return ++value;
    }
}
