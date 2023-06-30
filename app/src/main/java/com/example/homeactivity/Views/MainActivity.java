package com.example.homeactivity.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.Services.StudySetService;
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



        StudySetService sc = new StudySetService();

        sc.findStudySet( "2j353cXh7fg7jctwjvgF", studySet -> {
            if (studySet != null ){
                studySet.setId("Hi world");
                sc.updateStudySet(studySet);
            }
        });



    }

}