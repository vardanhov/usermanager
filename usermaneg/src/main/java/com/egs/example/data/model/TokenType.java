package com.egs.example.data.model;

public enum TokenType {

    FORGOT_PASSWORD  (1, "Forgot password"),
    EMAIL_CHANGE    (2, "Email Change"),
    CONFIRM_EMAIL   (3, "Confirm Email");



    TokenType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static TokenType ofValue(int value) {
        for (TokenType item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return null;
    }

    public static TokenType ofName(String name) {
        for (TokenType item : values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    private final int value;

    private final String name;
}
