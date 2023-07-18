package com.example.homeactivity.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.homeactivity.Controllers.AccountController;
import com.example.homeactivity.Models.Account;
import com.example.homeactivity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;
    private AccountController accountController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = ((EditText) findViewById(R.id.editTextFullName)).getText().toString();
                String username = editTextUsername.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()||fullName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "\n" +
                                    "Please enter full information",
                            Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(username, email, password);
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void registerUser(String username, String email, String password) {
        accountController = new AccountController();
        Account account = new Account();
        account.setNickname(username);
        account.setEmail(email);
        account.setPassword(password);
        accountController.createAccount(account,s -> {
            Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
        }, e->{
            Toast.makeText(this,e, Toast.LENGTH_SHORT).show();
        });
        Toast.makeText(this, "\n" +
                "Sign Up Success!", Toast.LENGTH_SHORT).show();
        finish();
    }

}
