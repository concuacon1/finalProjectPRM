package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.R;

public class StartTestActivity extends AppCompatActivity {
    private TextView tvNumberOfQuestionStt;
    StudySetController studySetController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
        ((Button)findViewById(R.id.btn_start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartTestActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });
        tvNumberOfQuestionStt = findViewById(R.id.tv_numberOfQuestionStt);

        Intent intent = getIntent();
        String studySetId = intent.getStringExtra("studySetId");
        studySetController.listAllStudySets(studySetId, studySet -> {
            tvNumberOfQuestionStt.setText(studySet.size());
        });

    }
}