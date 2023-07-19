package com.example.homeactivity.Utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

public class DatabaseConnector {
    private final FirebaseFirestore db;

    private CollectionReference collectionReference;

    public DatabaseConnector() {
        db = FirebaseFirestore.getInstance();
    }

    public DatabaseConnector(String collectionName) {
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection(collectionName);
    }

    public Task<DocumentReference> insertDocument(String collectionName, Object documentData) {
        CollectionReference collectionRef = db.collection(collectionName);
        return collectionRef.add(documentData);
    }

    public Task<Void> updateDocument(String collectionName, String documentId, Object updatedData) {
        DocumentReference documentRef = db.collection(collectionName).document(documentId);
        return documentRef.set(updatedData);
    }

    public Task<Void> deleteDocument(String collectionName, String documentId) {
        DocumentReference documentRef = db.collection(collectionName).document(documentId);
        return documentRef.delete();
    }

    public Task<QuerySnapshot> getAllDocuments(String collectionName) {
        CollectionReference collectionRef = db.collection(collectionName);
        return collectionRef.get();
    }

    public Task<DocumentSnapshot> getDocumentSnapshot(String collectionName, String documentId) {
        return db.collection(collectionName).document(documentId).get();
    }

    public Task<DocumentReference> insertDocument(Object documentData) {
        if (collectionReference == null) {
            throw new IllegalStateException("No collection was initialize");
        }
        return collectionReference.add(documentData);
    }

    public Task<Void> updateDocument(String documentId, Object updatedData) {
        return collectionReference.document(documentId).set(updatedData);
    }

    public Task<Void> deleteDocument(String documentId) {
        //Need to specify which collection through constructor
        if (collectionReference == null) {
            throw new IllegalStateException("No collection was initialize");
        }
        return collectionReference.document(documentId).delete();
    }

    public Task<QuerySnapshot> getAllDocuments() {
        //Need to specify which collection through constructor
        if (collectionReference == null) {
            throw new IllegalStateException("No collection was initialize");
        }
        return collectionReference.get();
    }

    public Task<DocumentSnapshot> getDocumentSnapshot(String documentId) {
        //Need to specify which collection through constructor
        if (collectionReference == null) {
            throw new IllegalStateException("No collection was initialize");
        }
        return collectionReference.document(documentId).get();
    }

    public DocumentReference getDocumentReference() {
        //Need to specify which collection through constructor
        if (collectionReference == null) {
            throw new IllegalStateException("No collection was initialize");
        }
        return collectionReference.document();
    }

    public DocumentReference getDocumentReference(String documentId) {
        //Need to specify which collection through constructor
        if (collectionReference == null) {
            throw new IllegalStateException("No collection was initialize");
        }
        return collectionReference.document(documentId);
    }

    public CollectionReference getCollectionReference() {
        //Need to specify which collection through constructor
        if (collectionReference == null) {
            throw new IllegalStateException("No collection was initialize");
        }
        return collectionReference;
    }

    public CollectionReference getCollectionReference(String collectionName) {
        return db.collection(collectionName);
    }

    public WriteBatch getBatch() {
        return db.batch();
    }
}
