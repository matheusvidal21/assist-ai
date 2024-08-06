package com.ai.assist.utils;

import java.util.HashMap;
import java.util.Map;

public class EmailUtil {

    private EmailUtil() { }

    public static Map<String, Object> buildEmailVariables(Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Parâmetros de chave e valor devem ser fornecidos em pares.");
        }

        Map<String, Object> variables = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            if (!(keyValuePairs[i] instanceof String)) {
                throw new IllegalArgumentException("Chave deve ser uma String.");
            }
            variables.put((String) keyValuePairs[i], keyValuePairs[i + 1]);
        }
        return variables;
    }
}
