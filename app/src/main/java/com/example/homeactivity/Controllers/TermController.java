package com.example.homeactivity.Controllers;

import android.util.Log;

import com.example.homeactivity.Models.Term;
import com.example.homeactivity.Utils.DatabaseConnector;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TermController {
    private final DatabaseConnector connector;

    public TermController() {
        connector = new DatabaseConnector("Term");
    }

    public String createTerm(Term term) {
        DocumentReference termRef = connector.getDocumentReference();

        // Set the ID for the term object
        String id = termRef.getId();
        term.setId(id);

        // Create a new batch
        WriteBatch batch = connector.getBatch();

        // Add the document insertion operation to the batch
        batch.set(termRef, term);

        // Commit the batch write operation
        batch.commit()
            .addOnFailureListener(e -> {
                // Batch write failed
                Log.e("FireStoreError", "Failed to create term: " + e.getMessage());
                throw new RuntimeException("Failed to create term", e);
            });
        return id;
    }

    //Using Batch for multiple adding will more efficient
    public void createTerms(List<Term> terms) {
        WriteBatch batch = connector.getBatch();

        for (Term term : terms) {
            DocumentReference termRef = connector.getDocumentReference();
            DocumentSnapshot termSnapshot = termRef.get().getResult();

            if (!termSnapshot.exists()) {
                term.setId(termRef.getId());
                batch.set(termRef, term);
            }
        }

        batch.commit()
            .addOnFailureListener(e -> {
                Log.e("FireStoreError", e.getMessage());
                throw new RuntimeException("Failed to create terms", e);
            });
    }

    public void deleteTerm(String termId) {
        connector.deleteDocument(termId)
            .addOnFailureListener(e -> {
                Log.e("FireStoreError", e.getMessage());
                throw new RuntimeException("Failed to delete term", e);
            });
    }

    //Using Batch for multiple deleting will more efficient
    public void deleteTerms(List<Term> terms) {
        WriteBatch batch = connector.getBatch();

        for (Term term : terms) {
            DocumentReference termRef = connector.getDocumentReference(term.getId());
            batch.delete(termRef);
        }

        batch.commit()
            .addOnFailureListener(e -> {
                Log.e("FireStoreError", "Failed to delete terms: " + e.getMessage());
                throw new RuntimeException("Failed to delete terms", e);
            });
    }

    public void findTerm(String termId, Consumer<Term> onSuccess) {
        connector.getDocumentSnapshot(termId)
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    throw new RuntimeException("Failed to retrieve Term", task.getException());
                }

                DocumentSnapshot documentSnapshot = task.getResult();

                if (!documentSnapshot.exists()) {
                    onSuccess.accept(null);
                    return;
                }

                Term term = documentSnapshot.toObject(Term.class);

                onSuccess.accept(term);
            });
    }

    //List all term by studySetId
    public void listAllTerms(String studySetId, Consumer<List<Term>> onSuccess) {
        connector.getCollectionReference()
            //Use query in FireStore
            .whereEqualTo("studySetId", studySetId)
            .get()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    throw new RuntimeException("Failed to retrieve Terms", task.getException());
                }

                QuerySnapshot querySnapshot = task.getResult();
                List<Term> termList = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    Term term = documentSnapshot.toObject(Term.class);
                    termList.add(term);
                }

                onSuccess.accept(termList);
            });
    }

    public void updateTerm(Term updatedTerm) {
        findTerm(updatedTerm.getId(), studySet -> {

            //Make sure Study set exist
            if (studySet != null) {
                connector.updateDocument(updatedTerm.getId(), updatedTerm)
                    .addOnFailureListener(e -> {
                        Log.e("FireStoreError", e.getMessage());
                        throw new RuntimeException("Failed to update Term", e);
                    });
            } else {
                throw new RuntimeException("Term does not exist");
            }
        });

    }


}
