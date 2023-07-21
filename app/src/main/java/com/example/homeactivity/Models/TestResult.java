package com.example.homeactivity.Models;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;

public class TestResult {
    private String userId;
    private String studySetId;
    private String studyTitle;
    private Date timeTest;
    private float score;

    public TestResult() {
    }

    public TestResult(String userId, String studySetId, String studyTitle, Date timeTest, float score) {
        this.userId = userId;
        this.studySetId = studySetId;
        this.studyTitle = studyTitle;
        this.timeTest = timeTest;
        this.score = score;
    }

    public String getStudyTitle() {
        return studyTitle;
    }

    public void setStudyTitle(String studyTitle) {
        this.studyTitle = studyTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStudySetId() {
        return studySetId;
    }

    public void setStudySetId(String studySetId) {
        this.studySetId = studySetId;
    }

    public Date getTimeTest() {
        return timeTest;
    }

    public void setTimeTest(Date timeTest) {
        this.timeTest = timeTest;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
