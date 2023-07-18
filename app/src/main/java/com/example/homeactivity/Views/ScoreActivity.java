package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_score);
//        tvScore = findViewById(R.id.tv_score);
//        tvCorrect = findViewById(R.id.tv_correct_ques);
//        tvWrong = findViewById(R.id.tv_wrong_score);
//        tvUncheck = findViewById(R.id.tv_uncheck_score);
//        tvNumberOfQues = findViewById(R.id.tv_numberOfQuestion);
//        btnReAttempt = findViewById(R.id.btn_reattemp);
//        btnHome = findViewById(R.id.btn_back_to_home);
//        termController = new TermController();
//
//        btnReAttempt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ScoreActivity.this, StartTestActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        Intent intent = getIntent();
//        //String studySetId = intent.getStringExtra("studySetId");
//        termController.listAllTerms("rdKlzh42W3RR3r8T59hp", studySet -> {
//            tvNumberOfQues.setText(String.valueOf(studySet.size()));
//        });
//        loadData();
//    }
//
//    private void loadData() {
//        int correctAns = 0;
//        int wrongAns = 0;
//        int uncheckAns = 0;
//        for (int i = 0; i < listOfQuestion.size(); i++) {
//            if (listOfQuestion.get(i).getSelectedAns() == -1) {
//                uncheckAns++;
//            } else {
//                if (listOfQuestion.get(i).getSelectedAns() == listOfQuestion.get(i).getCorrectAns()) {
//                    correctAns++;
//                } else wrongAns++;
//            }
//        }
//        tvCorrect.setText(String.valueOf(correctAns));
//        tvWrong.setText(String.valueOf(wrongAns));
//        tvUncheck.setText(String.valueOf(uncheckAns));
//
//        int finalScore = (correctAns*100)/listOfQuestion.size();
//
//        tvScore.setText(String.valueOf(finalScore));
//    }


}