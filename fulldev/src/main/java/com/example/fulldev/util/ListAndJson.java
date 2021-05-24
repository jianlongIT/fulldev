package com.example.fulldev.util;

import com.example.fulldev.exception.ServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class ListAndJson implements AttributeConverter<List<Object>, String> {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(List<Object> objects) {
        try {
            if (null == objects) {
                return null;
            }
            return mapper.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException(9999);
        }
    }

    @Override
    public List<Object> convertToEntityAttribute(String s) {

        try {
            if (null == s) {
                return null;
            }
            return mapper.readValue(s, List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException(9999);
        }
    }
}
