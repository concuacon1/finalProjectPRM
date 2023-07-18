package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.homeactivity.Controllers.TermController;
import com.example.homeactivity.R;

public class StartTestActivity extends AppCompatActivity {
    private TextView tvNumberOfQuestionStt;
    TermController termController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        termController = new TermController();

        ((Button)findViewById(R.id.btn_start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartTestActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });
        tvNumberOfQuestionStt = findViewById(R.id.tv_numberOfQuestionStt);

        Intent intent = getIntent();
        //String studySetId = intent.getStringExtra("studySetId");
        termController.listAllTerms("rdKlzh42W3RR3r8T59hp", studySet -> {
            tvNumberOfQuestionStt.setText(String.valueOf(studySet.size()));
        });

//        ((ImageButton)findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(StartTestActivity.this,StudySetActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}