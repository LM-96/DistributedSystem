package it.distributedsystems.util;

import java.util.ArrayList;
import java.util.List;

public class GeneralUtils {

    public static <T> List<T> withNullAsEmptyList(List<T> list) {
        if(list == null)
            return new ArrayList<T>();
        else
            return list;
    }

}
