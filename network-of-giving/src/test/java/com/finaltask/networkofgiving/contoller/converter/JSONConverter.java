package com.finaltask.networkofgiving.contoller.converter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConverter {

    public static String asJsonString(final Object obj) {
        try {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.setVisibility(mapper.getVisibilityChecker()
//                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
//            return mapper.writeValueAsString(obj);
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
