package com.example.homeactivity.Services;

import android.util.Log;

import com.example.homeactivity.Models.Account;
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

public class AccountService {
    private final DatabaseConnector connector;

    public AccountService() {

        connector = new DatabaseConnector("Account");
    }

    public String  createAccount(Account account) {

        account.setCreatedAt(Timestamp.now());

        WriteBatch batch = connector.getBatch();

        DocumentReference accountRef = connector.getDocumentReference();

        // Set the ID for the Account object
        String id = accountRef.getId();
        account.setId(id);

        // Add the document insertion operation to the batch
        batch.set(accountRef, account);

        // Commit the batch write operation
        batch.commit()
            .addOnFailureListener(e -> {
                // Batch write failed
                Log.e("FireStoreError", "Failed to create Account: " + e.getMessage());
                throw new RuntimeException("Failed to create Account", e);
            });

        return id;
    }


    public void findAccount(String userId, Consumer<Account> onSuccess) {
        connector.getDocumentSnapshot(userId)
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

        findAccount(updatedAccount.getId(), account -> {
            if (account != null) {
                connector.updateDocument(updatedAccount.getId(), updatedAccount)
                    .addOnFailureListener(e -> {
                        Log.e("FireStoreError", e.getMessage());
                        throw new RuntimeException("Failed to update user", e);
                    });
            }
            else {
                throw new RuntimeException("User does not exist");
            }
        });

    }

    //List all accounts exist in db, for debug purpose only
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


