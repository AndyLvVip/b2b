package uca.platform.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uca.platform.exception.InternalServerException;

import java.io.IOException;

/**
 * Created by andy.lv
 * on: 2019/1/25 17:06
 */
public class StdObjectMapper {

    private final ObjectMapper objectMapper;

    public StdObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJson(Object object) {
        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> type) {
        try {
            return this.objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new InternalServerException(e);
        }
    }

}
