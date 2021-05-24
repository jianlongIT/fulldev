package com.example.fulldev.util;

import com.example.fulldev.exception.ServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

@Converter
public class MapAndJson implements AttributeConverter<Map<String, Object>, String> {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try {
            if (null == stringObjectMap) {
                return null;
            }
            return mapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException(9999);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {

        try {
            if (null == s) {
                return null;
            }
            return mapper.readValue(s, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException(9999);
        }
    }
}
