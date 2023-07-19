package com.example.homeactivity.Models;

import com.google.firebase.Timestamp;

public class Account {

    private String id;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Account() {
    }

    public Account(String name, String nickname, String email,
                   String password, Timestamp createdAt, Timestamp updatedAt) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Account(String id, String name, String nickname, String email,
                   Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
