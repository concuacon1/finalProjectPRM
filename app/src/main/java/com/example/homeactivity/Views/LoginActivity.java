package com.example.homeactivity.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.Controllers.AccountController;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.SessionManager;


public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private Button buttonLogin;
    private AccountController accountController;
    private SessionManager sessionManager;
    private Button buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        AccountController accountController= new AccountController();
        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {

            redirectToHome();
        }
        buttonLogin.setOnClickListener(v -> loginUser());
    }
    public void onRegisterClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    private void redirectToHome() {

    }
    private void loginUser() {
        String emailOrUsername = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();

        if (emailOrUsername.isEmpty()) {
            Toast.makeText(this, "Please enter your email address or username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Phương thức xử lý khi người dùng nhấp vào "Forgot Password?"
//    public void onForgotPasswordClick(View view) {
//        showForgotPasswordDialog();
//    }

        // Hiển thị hộp thoại yêu cầu email để đặt lại mật khẩu
//    private void showForgotPasswordDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Forgot Password");
//        builder.setMessage("Please enter your email to reset your password:");
//
//        final EditText input = new EditText(this);
//        builder.setView(input);
//
//        builder.setPositiveButton("Reset Password", (dialog, which) -> {
//            String email = input.getText().toString();
//            resetPassword(email);
//        });
//
//        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
//
//        builder.show();
//    }

        // Xử lý đặt lại mật khẩu, kiểm tra email và gửi thông báo cho người dùng
//    private void resetPassword(String email) {
//        // Kiểm tra email trong danh sách tài khoản
//        boolean emailExists = false;
//        for (String username : userAccounts.keySet()) {
//            if (userAccounts.get(username).equals(email)) {
//                emailExists = true;
//                break;
//            }
//        }

//        if (emailExists) {
//            // Gửi email chứa mật khẩu tới người dùng (trong trường hợp thực tế, bạn sẽ gửi thông báo đặt lại mật khẩu)
//            Toast.makeText(this, "Reset password email sent", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show();
//        }
//    }
    }
}