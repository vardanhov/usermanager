package com.egs.example.service.user;

import com.egs.example.data.model.UserProfile;
import com.egs.example.data.model.UserStatus;

public class CreateUserRequest {

    private String email;

    private String password;

    private UserProfile profile;

    private UserStatus status;

    private String name;

    private String surname;
    
    public CreateUserRequest() {}

    public CreateUserRequest(String email,
                             String password,
                             UserProfile profile,
                             UserStatus status,
                             String name,
                             String surname ) {
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.status = status;
        this.name = name;
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}