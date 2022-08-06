package com.trade.model;

public enum Status {
    PENDING_EXECUTION("PENDING_EXECUTION"), EXECUTED("EXECUTED"), NOT_EXECUTED("NOT_EXECUTED");

    private String value;

    Status (String value) {
        this.value = value;
    }

    public String value () {
        return value;
    }
}
