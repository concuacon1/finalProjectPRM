package com.example.homeactivity.Controllers;

import android.util.Log;

import com.example.homeactivity.Models.TestResult;
import com.example.homeactivity.Utils.DatabaseConnector;

import java.util.Date;

public class TestController {
    private final DatabaseConnector connector;

    public TestController() {
        connector = new DatabaseConnector("Testing");
    }


    public void recordTestHistory(String userId, String studySetId, float score) {
        // Create a new TestResult object with the provided data
        Date timeTest = new Date(); // Use the current time as the test time
        boolean isFinished = true; // Assuming the test is finished when recording the history

        TestResult testResult = new TestResult(userId, studySetId, timeTest, isFinished, score);

        // Save the test result to the Firestore collection
        connector.insertDocument(testResult)
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to insert history", e);
                });
    }


}
