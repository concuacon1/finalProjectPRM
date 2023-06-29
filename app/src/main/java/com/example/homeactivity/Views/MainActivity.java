package com.example.homeactivity.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.Controllers.UserController;
import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.R;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.btn_test)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartTestActivity.class);
                startActivity(intent);
            }
        });

        StudySetController studySetController = new StudySetController();
        UserController uc = new UserController();

        studySetController.listAllStudySets("HJkbtt2WhjuOiZYyYgyz", studySets -> {
            for (StudySet s : studySets) {
                uc.findAccount(s.getUserId(), account -> {
                    Log.i("Haha", account.getName());
                });
            }
        });

    }

}