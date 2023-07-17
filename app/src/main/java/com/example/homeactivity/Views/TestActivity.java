package com.example.homeactivity.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.Models.Questions;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.QuestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private RecyclerView questionView;
    private TextView tvNumberOfQuestion, tvstudyName;
    private Button submitB;
    private ImageButton prevQuestion, nextQuestion;
    private ImageView menu;
    private int questionNumber;
    private StudySetController studySetController;

    Intent intent = getIntent();
    String studySetId = intent.getStringExtra("studySetId");

    private List<Questions> listOfQuestion = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
        QuestionAdapter questionAdapter = new QuestionAdapter(listOfQuestion);
        questionView.setAdapter(questionAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionView.setLayoutManager(layoutManager);
        setSnapHelper();
        setClickListeners();

    }

    private void init() {
        questionView = findViewById(R.id.tv_questions_view);
        tvNumberOfQuestion = findViewById(R.id.tv_numberOfQuestion);
        tvstudyName = findViewById(R.id.tv_study_name);
        submitB = findViewById(R.id.btn_submit);
        prevQuestion = findViewById(R.id.prev_question);
        nextQuestion = findViewById(R.id.next_question);
        menu = findViewById(R.id.test_menu);
        questionNumber = 0;
        studySetController.findStudySet(studySetId, studySet -> {
            tvstudyName.setText(studySet.getTitle());
        });
        studySetController.listAllStudySets(studySetId, studySet -> {
            tvNumberOfQuestion.setText("1/" + String.valueOf(studySet.size()));
        });
    }

    private void setSnapHelper() {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionView);

        questionView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                questionNumber = recyclerView.getLayoutManager().getPosition(view);
                studySetController.listAllStudySets(studySetId, studySet -> {
                    tvNumberOfQuestion.setText(String.valueOf(questionNumber + 1) + " / " + String.valueOf(studySet.size()));
                });
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setClickListeners() {
        prevQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNumber > 0) {
                    questionView.smoothScrollToPosition(questionNumber - 1);
                }
            }
        });

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNumber < listOfQuestion.size() - 1) {
                    questionView.smoothScrollToPosition(questionNumber + 1);
                }
            }
        });
    }

}
