package com.example.homeactivity.Controllers;

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
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class AccountController {
    private final DatabaseConnector connector;

    public AccountController() {

        connector = new DatabaseConnector("Account");
    }


    public void createAccount(Account account, Consumer<String> onError) {
        String email = account.getEmail();
        String nickname = account.getNickname();

        // Create a query to check if either email or nickname already exists
        connector.getCollectionReference()
                .whereIn("email", Arrays.asList(email, nickname))
                .limit(1)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        for (DocumentSnapshot documentSnapshot : querySnapshot) {
                            // Check which field (email or nickname) matched the query
                            String field = documentSnapshot.getString("email");
                            if (field != null && field.equals(email)) {
                                onError.accept("An account with this email already exists.");
                            } else {
                                onError.accept("An account with this nickname already exists.");
                            }
                            return;
                        }
                    }

                    // No existing account found, proceed with creating the new account
                    account.setCreatedAt(Timestamp.now());
                    WriteBatch batch = connector.getBatch();
                    DocumentReference accountRef = connector.getDocumentReference();
                    String id = accountRef.getId();
                    account.setId(id);
                    batch.set(accountRef, account);
                    batch.commit()
                            .addOnFailureListener(e -> {
                                // Batch write failed
                                Log.e("FireStoreError", "Failed to create Account: " + e.getMessage());
                                onError.accept("Failed to create Account");
                            });
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Failed to check email/nickname availability: " + e.getMessage());
                });
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

    public void login(String emailOrNickname, String password, Consumer<Account> onSuccess) {
        connector.getCollectionReference()
                .whereEqualTo("email", emailOrNickname)
                .limit(1)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (querySnapshot.isEmpty()) {
                        // No user found with the provided email, try searching with nickname
                        connector.getCollectionReference()
                                .whereEqualTo("nickname", emailOrNickname)
                                .limit(1)
                                .get()
                                .addOnSuccessListener(querySnapshot1 -> {
                                    if (querySnapshot1.isEmpty()) {
                                        // No user found with the provided email/nickname
                                        onSuccess.accept(null);
                                    } else {
                                        DocumentSnapshot documentSnapshot = querySnapshot1.getDocuments().get(0);
                                        Account account = documentSnapshot.toObject(Account.class);
                                        // Check if the provided password matches the stored password
                                        if (account != null && account.getPassword().equals(password)) {
                                            onSuccess.accept(account);
                                        } else {
                                            onSuccess.accept(null); // Password doesn't match
                                        }
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    throw new RuntimeException("Login failed: " + e.getMessage());
                                });
                    } else {
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        Account account = documentSnapshot.toObject(Account.class);
                        // Check if the provided password matches the stored password
                        if (account != null && account.getPassword().equals(password)) {
                            onSuccess.accept(account);
                        } else {
                            onSuccess.accept(null); // Password doesn't match
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    throw new RuntimeException("Login failed: " + e.getMessage());
                });
    }

}


