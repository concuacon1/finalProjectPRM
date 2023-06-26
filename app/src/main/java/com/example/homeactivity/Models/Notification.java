package com.example.homeactivity.Models;

import java.util.Date;

public class Notification {
    private int notificationId;
    private String message;
    private Date scheduledTime;

    public Notification(int notificationId, String message, Date scheduledTime) {
        this.notificationId = notificationId;
        this.message = message;
        this.scheduledTime = scheduledTime;
    }

    // Getters and Setters
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
