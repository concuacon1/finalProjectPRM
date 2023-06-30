package com.example.homeactivity.Services;

import android.util.Log;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Utils.DatabaseConnector;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StudySetService {
    private final DatabaseConnector connector;

    public StudySetService() {
        connector = new DatabaseConnector("StudySet");
    }

    public void createStudySet(StudySet studySet) {

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

    public void findStudySet(String studySetId, Consumer<StudySet> onSuccess) {
        connector.getDocumentReference(studySetId)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        throw new RuntimeException("Failed to retrieve Study Set", task.getException());
                    }

                    DocumentSnapshot documentSnapshot = task.getResult();

                    if (!documentSnapshot.exists()) {
                        onSuccess.accept(null);
                        return;
                    }

                    StudySet studySet = documentSnapshot.toObject(StudySet.class);
                    studySet.setId(studySetId);

                    onSuccess.accept(studySet);
                });
    }

    public void deleteStudySet(String studySetId) {
        connector.deleteDocument(studySetId)
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to delete Study Set", e);
                });
    }

    public void updateStudySet(StudySet updatedStudySet) {
        updatedStudySet.setUpdatedAt(Timestamp.now());

        //Make sure Study set exist
        findStudySet(updatedStudySet.getId(), studySet -> {
            if (studySet != null) {
                connector.updateDocument(updatedStudySet.getId(), updatedStudySet)
                        .addOnFailureListener(e -> {
                            Log.e("FireStoreError", e.getMessage());
                            throw new RuntimeException("Failed to update Study Set", e);
                        });
            } else {
                throw new RuntimeException("Study Set does not exist");
            }
        });

    }

    public void listAllStudySets(Consumer<List<StudySet>> onSuccess) {
        connector.getAllDocuments()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        throw new RuntimeException("Failed to retrieve Study Set", task.getException());
                    }

                    QuerySnapshot querySnapshot = task.getResult();

                    List<StudySet> studySetList = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        StudySet studySet = documentSnapshot.toObject(StudySet.class);

                        studySet.setId(documentSnapshot.getId());
                        studySetList.add(studySet);
                    }

                    onSuccess.accept(studySetList);
                });
    }

    public void listAllStudySets(String userId, Consumer<List<StudySet>> onSuccess) {
        connector.getAllDocuments()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        throw new RuntimeException("Failed to retrieve Study Set", task.getException());
                    }

                    QuerySnapshot querySnapshot = task.getResult();

                    List<StudySet> studySetList = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        StudySet studySet = documentSnapshot.toObject(StudySet.class);
                        if (studySet.getUserId().equals(userId)) {
                            studySet.setId(documentSnapshot.getId());
                            studySetList.add(studySet);
                        }
                    }

                    onSuccess.accept(studySetList);
                });
    }


}
