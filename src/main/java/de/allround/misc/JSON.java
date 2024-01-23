package de.allround.misc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JSON {
    private static final Gson GSON = new Gson();

    public static String toJson(Object o){
        return GSON.toJson(o);
    }

    public static <T> T toType(String s, Class<T> tClass){
        return GSON.fromJson(s, tClass);
    }
}
