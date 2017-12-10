package com.example.rzeposlaw.localizeme.data;

public class User {

    private String username;
    private String passwordHash;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }
}
