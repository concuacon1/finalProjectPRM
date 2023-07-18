package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.R;

public class StartTestActivity extends AppCompatActivity {
    private TextView tvNumberOfQuestionStt;
    private ImageButton backBtn;
    TermController termController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        termController = new TermController();


        tvNumberOfQuestionStt = findViewById(R.id.tv_numberOfQuestionStt);
        backBtn=findViewById(R.id.btn_back_start_test);
        Intent intent = getIntent();
        String studySetId = intent.getStringExtra("studySetID1");
        termController.listAllTerms(studySetId, studySet -> {
            tvNumberOfQuestionStt.setText(String.valueOf(studySet.size()));
        });

        ((Button)findViewById(R.id.btn_start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartTestActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartTestActivity.this,StudySetActivity.class);
                startActivity(intent);
            }
        });
//        Intent intent = getIntent();
//        String studySetId = intent.getStringExtra("studySetId");
        termController.listAllTerms(studySetId, studySet -> {
            tvNumberOfQuestionStt.setText(String.valueOf(studySet.size()));
        });

    }
}