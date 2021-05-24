package com.example.fulldev.util;

import com.example.fulldev.exception.ServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenericAndJson<T> {

    private static ObjectMapper mapper;

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static <T> String objectToJson(T stringObjectMap) {
        try {
            if (null == stringObjectMap) {
                return null;
            }
            return GenericAndJson.mapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException(9999);
        }
    }

    public static <T> List<T> jsonToObject(String s) {
        if (null == s) {
            return null;
        }
        try {
            List<T> list = GenericAndJson.mapper.readValue(s, new TypeReference<List<T>>() {
            });
            return list;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException(9999);
        }
    }

    public static <T> T jsonToObject(String s, TypeReference<T> tr) {
        if (null == s) {
            return null;
        }
        try {
            T o = GenericAndJson.mapper.readValue(s, tr);
            return o;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException(9999);
        }
    }
}
