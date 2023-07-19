package com.example.homeactivity.Models;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class TestResult {
    private String userId;
    private String studySetId;
    private Date timeTest;
    private boolean isFinished;
    private float score;

    private StudySet studySet;

    public TestResult() {
    }

    public TestResult(String userId, String studySetId, Date timeTest, boolean isFinished, float score) {
        this.userId = userId;
        this.studySetId = studySetId;
        this.timeTest = timeTest;
        this.isFinished = isFinished;
        this.score = score;
    }

    @Exclude
    public StudySet getStudySet() {
        return studySet;
    }

    public void setStudySet(StudySet studySet) {
        this.studySet = studySet;
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
