package com.example.homeactivity.Controllers;

import android.util.Log;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Models.User;
import com.example.homeactivity.Utils.DatabaseConnector;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class StudySetController {
    private final DatabaseConnector connector;

    public StudySetController() {
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

    public StudySet findStudySet(String studySetId) {
        AtomicReference<StudySet> studySet = new AtomicReference<>();

        Thread thread = new Thread(() -> {
            try {
                Task<DocumentSnapshot> task = connector
                        .getDocumentReference(studySetId);
                DocumentSnapshot documentSnapshot = Tasks.await(task);

                if (documentSnapshot.exists()) {
                    studySet.set(documentSnapshot.toObject(StudySet.class));
                }
            } catch (ExecutionException | InterruptedException e) {
                Log.e("FireStoreError", e.getMessage());
                throw new RuntimeException("Failed to retrieve Study Set", e);
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("FireStoreError", e.getMessage());
            throw new RuntimeException("Thread interrupted", e);
        }

        //Because user don't have userId field in db
        StudySet ss = studySet.get();
        ss.setId(studySetId);

        return ss;
    }

    public void deleteStudySet(String studySetId) {
        connector.deleteDocument(studySetId)
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to delete Study Set", e);
                });
    }

    public void updateStudySet(StudySet studySet) {
        studySet.setUpdatedAt(Timestamp.now());

        connector.updateDocument(studySet.getId(), studySet)
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to update Study Set", e);
                });
    }

    public List<StudySet> listAllStudySets() {
        AtomicReference<List<StudySet>> studySets = new AtomicReference<>();

        Thread thread = new Thread(() -> {
            try {
                Task<QuerySnapshot> task = connector.getAllDocuments();
                QuerySnapshot querySnapshot = Tasks.await(task);

                List<StudySet> studySetList = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    StudySet studySet = documentSnapshot.toObject(StudySet.class);

                    studySet.setId(documentSnapshot.getId());
                    studySetList.add(studySet);
                }

                studySets.set(studySetList);
            } catch (Exception e) {
                Log.e("FireStoreError", e.getMessage(), e);
                throw new RuntimeException("Failed to retrieve Study Sets", e);
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("FireStoreError", e.getMessage());
            throw new RuntimeException("Thread interrupted", e);
        }

        return studySets.get();
    }

    public List<StudySet> listAllStudySets(String userId) {
        AtomicReference<List<StudySet>> studySets = new AtomicReference<>();

        Thread thread = new Thread(() -> {
            try {
                QuerySnapshot querySnapshot = Tasks.await(connector.getAllDocuments());

                List<StudySet> studySetList = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    StudySet studySet = documentSnapshot.toObject(StudySet.class);
                    if (studySet.getUserId().equals(userId)) {
                        studySet.setId(documentSnapshot.getId());
                        studySetList.add(studySet);
                    }
                }

                studySets.set(studySetList);
            } catch (Exception e) {
                Log.e("FireStoreError", e.getMessage(), e);
                throw new RuntimeException("Failed to retrieve Study Sets", e);
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("FireStoreError", e.getMessage());
            throw new RuntimeException("Thread interrupted", e);
        }

        return studySets.get();
    }
}
