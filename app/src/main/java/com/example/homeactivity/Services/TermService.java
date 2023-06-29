package com.example.homeactivity.Services;

import android.util.Log;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Utils.DatabaseConnector;
import com.google.firebase.Timestamp;

public class TermService {
    private final DatabaseConnector connector;

    public TermService() {
        connector = new DatabaseConnector("Term");
    }

    public void createTerms(StudySet studySet) {

        studySet.setCreatedAt(Timestamp.now());

        connector.insertDocument(studySet)
                .addOnSuccessListener(documentReference -> {

                    String documentId = documentReference.getId();

                    studySet.setId(documentId);
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to create Study Set", e);
                });
    }
}
