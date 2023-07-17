package com.example.homeactivity.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.Controllers.AccountController;
import com.example.homeactivity.Models.Account;
import com.example.homeactivity.R;
import com.google.firebase.Timestamp;
import java.util.Date;

import java.util.concurrent.atomic.AtomicReference;

public class UpdateProfileActivity extends AppCompatActivity {

    private Button update_btn;
    private AccountController accountController;
    private EditText name, nick_name, email;

    private static final String id = "4zT2o8R6a1uJm3cnWE9G";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        name = findViewById(R.id.name);
        nick_name = findViewById(R.id.nickname);
        email=findViewById(R.id.email);
        update_btn = findViewById(R.id.update_btn);

        accountController = new AccountController();
        accountController.findAccount(id,account -> {
            name.setText(account.getName());
            nick_name.setText(account.getName());
            email.setText(account.getName());
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = takeDataOnForm();
                accountController.updateAccount(account);
            }
        });
    }
    public Account takeDataOnForm() {
        Date currentDate = new Date();
        Timestamp currentTimestamp = new Timestamp(currentDate);
        String account_name = name.getText().toString().trim();
        String account_nickname = nick_name.getText().toString().trim();
        String account_email = email.getText().toString().trim();
        AtomicReference<Account> account_update = new AtomicReference<>(new Account());
        accountController = new AccountController();
        accountController.findAccount(id,account -> {
            account_update.set(new Account(account_name, account_nickname, account_email, account.getPassword(), account.getCreatedAt(), currentTimestamp));
        });
        return account_update.get();
    }

}
