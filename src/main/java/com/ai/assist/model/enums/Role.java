package com.ai.assist.model.enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN(0),
    AGEN(1),
    CUSTOMER(2);

    private final int value;

    Role(int value) {
        this.value = value;
    }

}
