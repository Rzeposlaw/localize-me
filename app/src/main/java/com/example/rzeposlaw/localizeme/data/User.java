package com.example.rzeposlaw.localizeme.data;

public class User {

    private String username;
    private String passwordHash;
    private String email;
    private String sex;

    public User(String username, String password, String email, String sex) {
        this.username = username;
        this.passwordHash = password;
        this.email = email;
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
