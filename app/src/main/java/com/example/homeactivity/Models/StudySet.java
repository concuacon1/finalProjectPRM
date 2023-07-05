package com.example.homeactivity.Models;

import com.google.firebase.Timestamp;

import java.util.Map;

public class StudySet {
    private String id;
    private String description;
    private String title;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isAvailable;

    private Map<String, Boolean> categories;

    private String userId;


    public StudySet() {
    }

    public StudySet(String id, String description, String title, Timestamp createdAt, Timestamp updatedAt,
                    boolean isAvailable, Map<String, Boolean> categories, String userId) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isAvailable = isAvailable;
        this.categories = categories;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Map<String, Boolean> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, Boolean> categories) {
        this.categories = categories;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
