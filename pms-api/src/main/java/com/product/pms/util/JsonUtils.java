package com.product.pms.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.product.pms.core.ApplicationError;
import com.product.pms.core.ApplicationException;

public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).setSerializationInclusion(Include.NON_NULL);

    public static String formatJson(Object json) {

        try {
            return OBJECT_MAPPER.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(ApplicationError.SERVER_ERROR);
        }

    }

    private JsonUtils() {

    }

}
