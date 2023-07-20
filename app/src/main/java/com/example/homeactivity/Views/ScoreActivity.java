package com.example.homeactivity.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.Controllers.TermController;
import com.example.homeactivity.R;

public class ScoreActivity extends AppCompatActivity {
    TermController termController;

    private TextView tvScore, tvCorrect, tvWrong, tvUncheck, tvNumberOfQues;
    private Button btnReAttempt;
    private ImageButton btnBack, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tvScore = findViewById(R.id.tv_score);
        tvCorrect = findViewById(R.id.tv_correct_ques);
        tvWrong = findViewById(R.id.tv_wrong_score);
        tvUncheck = findViewById(R.id.tv_uncheck_score);
        tvNumberOfQues = findViewById(R.id.tv_numberOfQuestion);
        btnReAttempt = findViewById(R.id.btn_re_attempt);
        btnBack = findViewById(R.id.btn_back);
        btnHome = findViewById(R.id.btn_back_to_home);
        termController = new TermController();

        Intent intent = getIntent();
        int correctAns = intent.getIntExtra("correctAns", 0);
        int wrongAns = intent.getIntExtra("wrongAns", 0);
        int uncheckAns = intent.getIntExtra("uncheckAns", 0);
        float finalScore = intent.getFloatExtra("finalScore", 0);

        String studySetId = intent.getStringExtra("studySetId");
        termController.listAllTerms(String.valueOf(studySetId), studySet -> {
            tvNumberOfQues.setText(String.valueOf(studySet.size()));
        });

        btnReAttempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, StartTestActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("studySetID1", studySetId);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, StudySetActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MainStartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        setData(correctAns, wrongAns, uncheckAns, finalScore);
    }

    private void setData(int correctAns, int wrongAns, int uncheckAns, float finalScore) {
        tvCorrect.setText(String.valueOf(correctAns));
        tvWrong.setText(String.valueOf(wrongAns));
        tvUncheck.setText(String.valueOf(uncheckAns));
        tvScore.setText(String.format("%.1f", finalScore) + "%");
    }


}