package com.egs.example.data.model;

public enum UserStatus {


    ACTIVE(1, "Active"),
    EMAIL_NOT_CONFIRMED(2, "Email not confirmed"),
    DEACTIVATED(3, "Deactivated");


    UserStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static UserStatus ofValue(int value) {
        for (UserStatus item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return null;
    }

    public static UserStatus ofName(String name) {
        for (UserStatus item : values()) {
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
