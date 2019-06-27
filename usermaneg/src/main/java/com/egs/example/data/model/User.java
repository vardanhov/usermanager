package com.egs.example.data.model;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;

    private UserProfile profile;

    private UserStatus status;

    private String email;

    private String password;

    private String name;

    private String surname;

    public Map<TokenType, UserToken> tokens;

    public User() {}


    public User(String id, UserProfile profile, UserStatus status, String email, String password, String name, String surname) {
        this.id = id;
        this.profile = profile;
        this.status = status;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Map<TokenType, UserToken> getTokens() {
        return tokens;
    }

    public void addToken(UserToken token) {
        if (tokens == null) {
            tokens = new HashMap<>();
        }
        tokens.put(token.getType(), token);
    }

    public UserToken getToken(TokenType type) {
        UserToken token = null;
        if (tokens != null) {
            token = tokens.get(type);
        }
        return token;
    }

    public void setTokens(Map<TokenType, UserToken> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", profile=" + profile +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}