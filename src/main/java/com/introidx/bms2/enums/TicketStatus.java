package com.introidx.bms2.enums;

public enum TicketStatus {
    PAYMENT_PENDING("PAYMENT_PENDING"),
    CONFIRMED("CONFIRMED"),
    CANCELLED("CANCELLED"),
    FAILED("FAILED");

    private final String value;

    TicketStatus(String value) {
        this.value = value;
    }
}
