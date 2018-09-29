package com.sky.hrpro.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: CarryJey
 * @Date: 2018/9/28 下午4:04
 */
public abstract class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static String toJsonString(Object object) {
        if (object == null) {
            return null;
        } else {
            try {
                return objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public static byte[] toJsonBytes(Object object) {
        if (object == null) {
            return null;
        } else {
            try {
                return objectMapper.writeValueAsBytes(object);
            } catch (JsonProcessingException var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public static <T> List<T> parseList(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        } else {
            try {
                return (List)objectMapper.readValue(json, CollectionType.construct(List.class, SimpleType.construct(clazz)));
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static <T> T parse(String json, Class<T> clazz) {
        if (json != null && !json.isEmpty()) {
            try {
                return objectMapper.readValue(json, clazz);
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        } else {
            return null;
        }
    }

    public static <K, V> Map<K, V> parseMap(String json) {
        return (Map) parse(json, new TypeReference<Map<K, V>>() {});
    }

    public static <T> T parse(String json, TypeReference<T> reference) {
        if (json == null) {
            return null;
        } else {
            try {
                return objectMapper.readValue(json, reference);
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static <K, V> Map<K, V> beanToMap(Object object) {
        return object == null ? null : (Map)objectMapper.convertValue(object, new TypeReference<Map<K, V>>() {
        });
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    static {
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
