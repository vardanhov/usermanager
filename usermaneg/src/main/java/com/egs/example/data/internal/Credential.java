package com.egs.example.data.internal;

public class Credential {

    private String email;
    private String password;

    public Credential(String email,
                      String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
