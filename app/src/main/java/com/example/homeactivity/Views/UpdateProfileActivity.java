package com.example.homeactivity.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.R;

public class UpdateProfileActivity extends AppCompatActivity {

    Button update_btn;
    EditText name, nick_name, email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        name = findViewById(R.id.name);
        nick_name = findViewById(R.id.nickname);
        email=findViewById(R.id.email);
        update_btn = findViewById(R.id.update_btn);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(name + "" + nick_name + "" + email);
                }
        });

    }
}
