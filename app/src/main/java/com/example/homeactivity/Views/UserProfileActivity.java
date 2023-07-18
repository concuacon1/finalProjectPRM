package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.homeactivity.Controllers.AccountController;
import com.example.homeactivity.Models.Account;
import com.example.homeactivity.R;

public class UserProfileActivity extends AppCompatActivity {
    private Button update_btn;
    private AccountController accountController;
    private TextView name, nick_name, email,nameFirstWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = findViewById(R.id.name_user);
        nick_name = findViewById(R.id.nickname_user);
        email=findViewById(R.id.email_user);
        nameFirstWord = findViewById(R.id.nameFirst);
        String id = getIntent().getStringExtra("ID");
        accountController = new AccountController();
        accountController.findAccount(id,account -> {
            name.setText(account.getName());
            nick_name.setText(account.getNickname());
            email.setText(account.getEmail());
            String account_name = name.getText().toString().trim().substring(0,1);
            nameFirstWord.setText(account_name);
        });

    }
}