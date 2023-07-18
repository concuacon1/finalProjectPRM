package com.example.homeactivity.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.Controllers.AccountController;
import com.example.homeactivity.R;

public class ForgotPasswordActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

    }

    public void onResetPasswordClick(View view) {
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email to reset password", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Password reset request has been sent to your email", Toast.LENGTH_SHORT).show();
        }
    }
}