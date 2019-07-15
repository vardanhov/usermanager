package com.egs.example.data.internal;

import com.egs.example.data.model.UserProfile;
import com.egs.example.data.model.UserStatus;

public class UpdateUserRequest {

    private final String id;

    private final UserProfile profile;

    private final UserStatus status;

    private final String name;

    private final String surname;

    public UpdateUserRequest(String id, UserProfile profile, UserStatus status, String name, String surname) {
        this.id = id;
        this.profile = profile;
        this.status = status;
        this.name = name;
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }


}