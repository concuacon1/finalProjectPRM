package com.example.homeactivity.Models;

import com.google.firebase.Timestamp;

public class StudySet {
    private String id;
    private String description;
    private String title;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String userId;
    private int numberOfParticipants;

    public StudySet() {
    }

    public StudySet(String id, String description, String title, Timestamp createdAt, Timestamp updatedAt,
                    String userId) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.createdAt = createdAt;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }
}
