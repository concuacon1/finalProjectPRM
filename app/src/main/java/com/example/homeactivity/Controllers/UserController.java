package com.example.homeactivity.Controllers;

import android.util.Log;

import com.example.homeactivity.Models.Account;
import com.example.homeactivity.Utils.DatabaseConnector;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UserController {
    private final DatabaseConnector connector;

    public UserController() {

        connector = new DatabaseConnector("Account");
    }

    public void createAccount(Account account) {

        account.setCreatedAt(Timestamp.now());

        connector.insertDocument(account)
                .addOnSuccessListener(documentReference -> {
                    String documentId = documentReference.getId();

                    //Because account don't have userId field in db
                    account.setId(documentId);
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to create account", e);
                });
    }


    public void findAccount(String userId, Consumer<Account> onSuccess) {
        connector.getDocumentReference(userId)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        throw new RuntimeException("Failed to find account", task.getException());
                    }

                    DocumentSnapshot documentSnapshot = task.getResult();

                    if (!documentSnapshot.exists()) {
                        onSuccess.accept(null);
                        return;
                    }

                    Account account = documentSnapshot.toObject(Account.class);
                    account.setId(userId);

                    onSuccess.accept(account);
                });
    }

    public void deleteAccount(String userId) {
        connector.deleteDocument(userId)
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to delete user", e);
                });
    }

    public void updateAccount(Account updatedAccount) {
        updatedAccount.setUpdatedAt(Timestamp.now());

        connector.updateDocument(updatedAccount.getId(), updatedAccount)
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", e.getMessage());
                    throw new RuntimeException("Failed to update user", e);
                });
    }

    public void listAllAccounts(Consumer<List<Account>> onSuccess) {
        connector.getAllDocuments()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        throw new RuntimeException("Failed to find all users", task.getException());
                    }

                    QuerySnapshot querySnapshot = task.getResult();

                    List<Account> accountList = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        Account account = documentSnapshot.toObject(Account.class);

                        //Because account don't have userId field in db
                        account.setId(documentSnapshot.getId());
                        accountList.add(account);
                    }

                    onSuccess.accept(accountList);

                });

    }

}


