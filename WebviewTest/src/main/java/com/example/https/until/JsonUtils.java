package com.example.https.until;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.Map;

/**
 * Created by yangfan on 2017/5/19.
 */

public class JsonUtils {
    private static Gson mGson = null;
    static {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        mGson = builder.create();
    }

    public JsonUtils() {
    }

    /**
     * 将Object类型转换成json串--String类型
     *
     * @param obj 需要转换的类
     * @return
     */
    public static String toJson(Object obj) {
        return mGson.toJson(obj);
    }

    public static Map fromJsonTomap(String str){
        Map map = mGson.fromJson(str, Map.class);
        return new CaseInsensitiveMap(map);

    }


}
