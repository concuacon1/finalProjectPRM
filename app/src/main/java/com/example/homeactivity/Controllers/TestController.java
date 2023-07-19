package com.example.homeactivity.Controllers;

import android.util.Log;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Models.TestResult;
import com.example.homeactivity.Utils.DatabaseConnector;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class TestController {
    private final DatabaseConnector connector;

    public TestController() {
        connector = new DatabaseConnector("Testing");
    }


    public void recordTestHistory(String userId, StudySet studySet, float score) {
        Date timeTest = new Date();

        TestResult testResult = new TestResult(userId, studySet.getId(), studySet.getTitle(), timeTest, score);

        // Save the test result to the Firestore collection
        connector.insertDocument(testResult)
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to insert history", e);
                });
    }

    public void getTestResultsForAccount(String accountId, Consumer<List<TestResult>> testResultConsumer) {
        // Get a reference to the "Testing" collection
        CollectionReference testingCollectionRef = connector.getCollectionReference();

        // Perform the query to get test results with the provided accountId
        Query query = testingCollectionRef
                .whereEqualTo("userId", accountId);

        // Execute the query
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                List<TestResult> testResultsList = querySnapshot.toObjects(TestResult.class);
                testResultConsumer.accept(testResultsList);

            } else {
                Log.e("FireStoreError", "Failed to retrieve test results for the account", task.getException());
            }
        });

    }
}
