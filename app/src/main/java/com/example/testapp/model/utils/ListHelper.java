package com.example.testapp.model.utils;

import java.util.List;

public class ListHelper {

    public static boolean isEmpty(List<?> list) {
        return (list == null) || (list.size() == 0);
    }
}
