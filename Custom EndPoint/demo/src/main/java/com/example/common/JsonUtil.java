package com.example.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.sql.Timestamp;
import java.util.Date;

public class JsonUtil {

    public static String toJSON(Object jsonText) {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        serializeConfig.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
        return JSON.toJSONString(jsonText, serializeConfig, new SerializerFeature[0]);
    }
}