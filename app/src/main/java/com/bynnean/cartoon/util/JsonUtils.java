package com.bynnean.cartoon.util;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * ${desc}
 *
 * @author bin2.he@gmail.com
 * @date 16-10-9-上午11:26
 */
public class JsonUtils {

    private static GsonBuilder instance;

    private static synchronized GsonBuilder build() {
        if (instance == null) {
            instance = new GsonBuilder().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation();
        }
        return instance;
    }

    public static GsonBuilder getInstance(){
        return build();
    }

    public static String toJson(Object obj){
        return getInstance().create().toJson(obj);
    }

    public static String toJson(Object obj ,Type type){
        return getInstance().create().toJson(obj ,type);
    }

    public static <T> T fromJson(String json, Class<T> clazz){
        return getInstance().create().fromJson(json, clazz);
    }

    public static <T> List<T> fromJsonToArray(String json, Class<T> clazz){
        return getInstance().create().fromJson(json, new TypeToken<List<T>>(){}.getType());
    }

    public static void registerTypeAdapter(Type type ,Object typeAdapter){
        getInstance().registerTypeAdapter(type, typeAdapter);
    }
}
