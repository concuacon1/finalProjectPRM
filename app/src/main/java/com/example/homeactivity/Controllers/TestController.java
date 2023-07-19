package com.example.homeactivity.Controllers;

import android.util.Log;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Models.TestResult;
import com.example.homeactivity.Utils.DatabaseConnector;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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

    public void getTestResultsForAccount(String accountId, Consumer<List<TestResult>> testResultConsumer) {
        // Get a reference to the "Testing" collection
        CollectionReference testingCollectionRef = connector.getCollectionReference();

        // Perform the query to get test results with the provided accountId
        Query query = testingCollectionRef
                .whereArrayContains("userId", accountId);

        // Execute the query
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                List<TestResult> testResultsList = querySnapshot.toObjects(TestResult.class);

                // Get a list of unique studySetIds from the TestResult documents
                List<String> studySetIds = new ArrayList<>();
                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                    String studySetIdResult = document.getString("studySetId");
                    if (!studySetIds.contains(studySetIdResult)) {
                        studySetIds.add(studySetIdResult);
                    }
                }

                // Fetch all the associated StudySet documents in one query
                connector.getCollectionReference("studySets")
                        .whereIn(FieldPath.documentId(), studySetIds)
                        .get()
                        .addOnCompleteListener(studySetTask -> {
                            if (studySetTask.isSuccessful()) {
                                QuerySnapshot studySetSnapshot = studySetTask.getResult();
                                List<StudySet> studySetsList = studySetSnapshot.toObjects(StudySet.class);

                                // Map the StudySet documents to a map using the document ID as the key
                                Map<String, StudySet> studySetMap = new HashMap<>();
                                for (StudySet studySet : studySetsList) {
                                    studySetMap.put(studySet.getId(), studySet);
                                }

                                // Combine the TestResult objects with the associated StudySet objects
                                for (TestResult testResult : testResultsList) {
                                    String studySetIdResult = testResult.getStudySetId();
                                    StudySet associatedStudySet = studySetMap.get(studySetIdResult);
                                    testResult.setStudySet(associatedStudySet);
                                }

                                // Pass the combined TestResult list to the consumer
                                testResultConsumer.accept(testResultsList);
                            } else {
                                Log.e("FireStoreError", "Failed to retrieve StudySet", studySetTask.getException());
                            }
                        });

            } else {
                Log.e("FireStoreError", "Failed to retrieve test results for the account", task.getException());
            }
        });

    }
}
