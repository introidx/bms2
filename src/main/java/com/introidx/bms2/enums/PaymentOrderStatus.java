package com.introidx.bms2.enums;

public enum PaymentOrderStatus {
    INITIATED("INITIATED"),
    PENDING_PG("PENDING_PG"),
    PENDING_BANK("PENDING_BANK"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED"),;

    private final String value;

    PaymentOrderStatus(String value) {
        this.value = value;
    }

}
