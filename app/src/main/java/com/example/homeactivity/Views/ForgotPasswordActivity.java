package com.example.homeactivity.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void onResetPasswordClick(View view) {
        // Xử lý gửi yêu cầu reset mật khẩu ở đây
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email để reset mật khẩu", Toast.LENGTH_SHORT).show();
        } else {
            // Xử lý gửi yêu cầu reset mật khẩu thành công
            Toast.makeText(this, "Yêu cầu reset mật khẩu đã được gửi đến email của bạn", Toast.LENGTH_SHORT).show();
        }
    }
}
