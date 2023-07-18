package com.example.homeactivity.Models;

import java.util.Date;

public class TestResult {
    private String userId;
    private String studySetId;
    private Date timeTest;
    private boolean isFinished;
    private float score;

    public TestResult() {
    }

    public TestResult(String userId, String studySetId, Date timeTest, boolean isFinished, float score) {
        this.userId = userId;
        this.studySetId = studySetId;
        this.timeTest = timeTest;
        this.isFinished = isFinished;
        this.score = score;
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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
