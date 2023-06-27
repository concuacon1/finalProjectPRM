package com.example.homeactivity.Models;

import com.google.firebase.Timestamp;

public class StudySet {
    private int id;
    private int userId;

    private String description;
    private String title;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isAvailable;

    public StudySet() {
    }

    public StudySet(int id, int userId, String description, String title, Timestamp createdAt, Timestamp updatedAt, boolean isAvailable) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isAvailable = isAvailable;
    }

    public StudySet(int id, String description, String title,
                    Timestamp createdAt, Timestamp updatedAt, boolean isAvailable) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
