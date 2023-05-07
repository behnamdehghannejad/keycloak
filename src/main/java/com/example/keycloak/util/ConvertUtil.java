package com.example.keycloak.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConvertUtil {

    public static Map<String, String> convertJsonToMap(String body) {
        try {
            return new ObjectMapper().readValue(body, new TypeReference<>() {});
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
