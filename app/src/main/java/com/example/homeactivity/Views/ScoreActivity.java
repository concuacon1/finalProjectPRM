package com.example.homeactivity.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.Controllers.TermController;
import com.example.homeactivity.Models.Question;
import com.example.homeactivity.R;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    TermController termController;

    private TextView tvScore, tvCorrect, tvWrong, tvUncheck, tvNumberOfQues;
    private Button btnReAttempt, btnHome;
    private List<Question> listOfQuestion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tvScore = findViewById(R.id.tv_score);
        tvCorrect = findViewById(R.id.tv_correct_ques);
        tvWrong = findViewById(R.id.tv_wrong_score);
        tvUncheck = findViewById(R.id.tv_uncheck_score);
        tvNumberOfQues = findViewById(R.id.tv_numberOfQuestion);
        btnReAttempt = findViewById(R.id.btn_reattemp);
        btnHome = findViewById(R.id.btn_back_to_home);
        termController = new TermController();

        btnReAttempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, StartTestActivity.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, StudySetActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });


        Intent intent = getIntent();
        int correctAns = intent.getIntExtra("correctAns", 0);
        int wrongAns = intent.getIntExtra("wrongAns", 0);
        int uncheckAns = intent.getIntExtra("uncheckAns", 0);
        int finalScore = intent.getIntExtra("finalScore", 0);
        termController.listAllTerms("CQtc0QhQ3WkaRtC2Ntz5", studySet -> {
            tvNumberOfQues.setText(String.valueOf(studySet.size()));
        });
        setData(correctAns, wrongAns, uncheckAns, finalScore);
    }

    private void setData(int correctAns, int wrongAns, int uncheckAns, int finalScore) {

        tvCorrect.setText(String.valueOf(correctAns));
        tvWrong.setText(String.valueOf(wrongAns));
        tvUncheck.setText(String.valueOf(uncheckAns));

        if (listOfQuestion.size() > 0) {
            finalScore = (correctAns * 100) / listOfQuestion.size();
        }

        tvScore.setText(String.valueOf(finalScore));
    }


}