package com.ai.assist.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TicketStatus {

    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    ON_HOLD("On Hold"),
    RESOLVED("Resolved"),
    CLOSED("Closed"),
    REOPENED("Reopened"),
    CANCELED("Canceled");

    private final String value;

    TicketStatus(String value) {
        this.value = value;
    }

    public static TicketStatus fromValue(String value){
        return Arrays.stream(TicketStatus.values())
                .filter(ticketStatus -> ticketStatus.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket value: " + value));
    }

}
