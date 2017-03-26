package com.itheima.shop.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */

public class GsonUtils {

    public static <T> String parseToJson(T t) {
        Gson gson = new Gson();
        return gson.toJson(t);
    }

    public static <T> T parseToBean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static <T> T parseToBean(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }


    public static <T> List<T> parseToList(String json, Class<?> clazz) {
        Gson gson = new Gson();
        List<T> ints2 = gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
        return ints2;
    }

    public static String parseListToJson(List lists) {
        Gson gson = new Gson();
        String json = gson.toJson(lists);
        return json;
    }

}
