package com.ai.assist.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {

    ADMIN("Admin"),
    AGENT("Agent"),
    CUSTOMER("Customer");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public static Role fromValue(String value){
        return Arrays.stream(Role.values())
                .filter(r -> r.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role value: " + value));
    }

}
