package com.example.homeactivity.Controllers;

import android.util.Log;

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

public class UserController {
    private final DatabaseConnector connector;

    public UserController() {

        connector = new DatabaseConnector("User");
    }

    public void createUser(User user) {

        user.setCreatedAt(Timestamp.now());

        connector.insertDocument(user)
                .addOnSuccessListener(documentReference -> {
                    String documentId = documentReference.getId();

                    //Because user don't have userId field in db
                    user.setId(documentId);
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to create user", e);
                });
    }

    public User findUser(String userId) {
        AtomicReference<User> user = new AtomicReference<>();

        Thread thread = new Thread(() -> {
            try {
                Task<DocumentSnapshot> task = connector.getDocumentReference(userId);
                DocumentSnapshot documentSnapshot = Tasks.await(task);

                if (documentSnapshot.exists()) {
                    user.set(documentSnapshot.toObject(User.class));
                }
            } catch (ExecutionException | InterruptedException e) {
                Log.e("FireStoreError", e.getMessage());
                throw new RuntimeException("Failed to retrieve user", e);
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
        User u = user.get();
        u.setId(userId);

        return u;
    }

    public void deleteUser(String userId) {
        connector.deleteDocument(userId)
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to delete user", e);
                });
    }

    public void updateUser(User updatedUser){
        updatedUser.setUpdatedAt(Timestamp.now());

        connector.updateDocument(updatedUser.getId(), updatedUser)
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to update user", e);
                });
    }

    public List<User> listAllUsers() {
        AtomicReference<List<User>> users = new AtomicReference<>();

        Thread thread = new Thread(() -> {
            try {
                Task<QuerySnapshot> task = connector.getAllDocuments();
                QuerySnapshot querySnapshot = Tasks.await(task);

                List<User> userList = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    User user = documentSnapshot.toObject(User.class);

                    //Because user don't have userId field in db
                    user.setId(documentSnapshot.getId());
                    userList.add(user);
                }

                users.set(userList);
            } catch (Exception e) {
                Log.e("FireStoreError", e.getMessage(), e);
                throw new RuntimeException("Failed to retrieve users", e);
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("FireStoreError", e.getMessage());
            throw new RuntimeException("Thread interrupted", e);
        }

        return users.get();
    }

}


