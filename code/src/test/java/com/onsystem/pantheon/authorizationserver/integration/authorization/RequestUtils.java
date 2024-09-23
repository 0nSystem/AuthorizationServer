package com.onsystem.pantheon.authorizationserver.integration.authorization;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Map;

public class RequestUtils {


    public static Map<String, Object> readString(String s) throws IOException {
        return new ObjectMapper().readValue(s, new TypeReference<Map<String, Object>>() {
        });
    }



}
