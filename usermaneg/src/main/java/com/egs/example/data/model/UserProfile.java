package com.egs.example.data.model;

public enum UserProfile {

    ADMIN(1, "Admin"),
    USER(2, "User");


    UserProfile(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static UserProfile ofValue(int value) {
        for (UserProfile item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return null;
    }

    public static UserProfile ofName(String name) {
        for (UserProfile item : values()) {
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