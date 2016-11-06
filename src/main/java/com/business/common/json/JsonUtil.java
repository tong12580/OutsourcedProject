package com.business.common.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * json 简单操作的工具类
 *
 * @author lee.li
 */
public class JsonUtil {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private JsonUtil() {
    }

    /**
     * 将对象转换成json格式
     *
     * @param ts
     * @return
     */
    public static String objectToJson(Object ts) {
        String jsonStr = null;
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * 将对象转换成json格式(并自定义日期格式)
     *
     * @param ts
     * @return
     */
    public static String objectToJsonDateSerializer(Object ts, final String dateformat) {
        String jsonStr = null;
        gson = new GsonBuilder().registerTypeHierarchyAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context) -> {
            SimpleDateFormat format = new SimpleDateFormat(dateformat);
            return new JsonPrimitive(format.format(src));
        }).setDateFormat(dateformat).create();
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * 将json格式转换成list对象
     *
     * @param jsonStr
     * @return
     */
    public static List<?> jsonToList(String jsonStr) {
        List<?> objList = null;
        if (gson != null) {
            Type type = new TypeToken<List<?>>() {
            }.getType();
            objList = gson.fromJson(jsonStr, type);
        }
        return objList;
    }

    /**
     * @param jsonStr
     * @param type
     * @param
     * @return List<T>
     * @throws
     * @Title: jsonToList
     * @Description: 将Json转为对应的List
     */
    public static <T> List<T> jsonToList(String jsonStr, Class<T> type) {
        Type listType = new TypeToken<ArrayList<T>>() {
        }.getType();
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonStr, listType);
        }
        return list;
    }

    /**
     * 将json格式转换成map对象
     *
     * @param jsonStr
     * @return
     */
    public static <K, V> Map<K, V> jsonToMap(String jsonStr) {
        Map<K, V> objMap = null;
        if (gson != null) {
            Type type = new TypeToken<Map<K, V>>() {
            }.getType();
            objMap = gson.fromJson(jsonStr, type);
        }
        return objMap;
    }

    /**
     * 将json转换成bean对象
     *
     * @param jsonStr
     * @return
     */
    public static <T> T jsonToBean(String jsonStr, Class<T> cl) {
        T bean = null;
        if (gson != null) {
            bean = gson.fromJson(jsonStr, cl);
        }
        return bean;
    }

    /**
     * 将json转换成bean对象
     *
     * @param jsonStr
     * @param cl
     * @return
     */
    public static <T> T jsonToBeanDateSerializer(String jsonStr, Class<T> cl, final String pattern) {
        T bean = null;
        gson = new GsonBuilder().registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String dateStr = json.getAsString();
            try {
                return format.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).setDateFormat(pattern).create();
        if (gson != null) {
            bean = gson.fromJson(jsonStr, cl);
        }
        return bean;
    }

    /***
     * 获取json键的值
     * @param jsonStr
     * @param key
     * @return
     */
    public static <K, V> Object getJsonValue(String jsonStr, String key) {
        V bean = null;
        Map<K, V> resultMap = jsonToMap(jsonStr);
        if (resultMap != null && resultMap.size() > 0) {
            bean = resultMap.get(key);
        }
        return bean;
    }

}