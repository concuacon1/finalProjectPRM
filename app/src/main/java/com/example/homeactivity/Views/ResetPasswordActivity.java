package com.example.homeactivity.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editTextEmail = findViewById(R.id.editTextEmail);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onResetPasswordClick(View view) {
        String email = editTextEmail.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ email", Toast.LENGTH_SHORT).show();
        } else {
            resetPassword(email);
        }
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this,
                                    "\n" +
                                            "Password reset link has been sent to your email address. Please check your email and follow the instructions to reset your password.",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(ResetPasswordActivity.this,
                                    "\n" +
                                            "Password reset link could not be sent. Please check your email address again or try again laterdB.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
