package com.example.homeactivity.Services;

import android.util.Log;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Utils.DatabaseConnector;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StudySetService {
    private final DatabaseConnector connector;

    public StudySetService() {
        connector = new DatabaseConnector("StudySet");
    }

    public String createStudySet(StudySet studySet) {
        studySet.setCreatedAt(Timestamp.now());

        WriteBatch batch = connector.getBatch();

        DocumentReference studySetRef = connector.getDocumentReference();

        // Set the ID for the study set object
        String id = studySetRef.getId();
        studySet.setId(id);

        // Add the document insertion operation to the batch
        batch.set(studySetRef, studySet);

        // Commit the batch write operation
        batch.commit()
            .addOnFailureListener(e -> {
                // Batch write failed
                Log.e("FireStoreError", "Failed to create Study Set: " + e.getMessage());
                throw new RuntimeException("Failed to create Study Set", e);
            });

        return id;
    }

    public void findStudySet(String studySetId, Consumer<StudySet> onSuccess) {
        connector.getDocumentSnapshot(studySetId)
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

        findStudySet(updatedStudySet.getId(), studySet -> {

            //Make sure Study set exist
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

    //List all study sets exist in db, for debug purpose only
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
                    studySetList.add(studySet);
                }

                onSuccess.accept(studySetList);
            });
    }

    //List all study set by userId
    public void listAllStudySets(String userId, Consumer<List<StudySet>> onSuccess) {
        connector.getCollectionReference()
                //Use query in FireStore
            .whereEqualTo("userId", userId)
            .get()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    throw new RuntimeException("Failed to retrieve Study Sets", task.getException());
                }

                QuerySnapshot querySnapshot = task.getResult();
                List<StudySet> studySetList = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    StudySet studySet = documentSnapshot.toObject(StudySet.class);
                    studySetList.add(studySet);
                }

                onSuccess.accept(studySetList);
            });
    }

}
