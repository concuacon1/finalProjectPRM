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
import com.example.homeactivity.Controllers.TermController;
import com.example.homeactivity.Models.Question;
import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.QuestionAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestActivity extends AppCompatActivity {
    private RecyclerView questionView;
    private TextView tvNumberOfQuestion, tvstudyName;
    private Button submitB;
    private ImageButton prevQuestion, nextQuestion;
    private ImageView menu;
    private int questionNumber;
    private StudySetController studySetController;
    private TermController termController;

    Intent intent = getIntent();
    String studySetId = "4uM1FetD6aRPQJbffDnf";

    private List<Question> listOfQuestion = new ArrayList<>();
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
    private List<Question> generateQuestions(List<Term> listTerm) {
        List<Question> questions = new ArrayList<>(listTerm.size());
        Random random = new Random();

        for (Term term : listTerm) {
            Question q = new Question();
            q.setQuestion(term.getTerm());

            List<Term> options = new ArrayList<>(listTerm);
            options.remove(term);

            List<String> shuffledOptions = new ArrayList<>();
            shuffledOptions.add(term.getDefinition());

            for (int i = 0; i < 3; i++) {
                int randomIndex = random.nextInt(options.size());
                shuffledOptions.add(options.get(randomIndex).getDefinition());
                options.remove(randomIndex);
            }

            Collections.shuffle(shuffledOptions);

            q.setOptionA(shuffledOptions.get(0));
            q.setOptionB(shuffledOptions.get(1));
            q.setOptionC(shuffledOptions.get(2));
            q.setOptionD(shuffledOptions.get(3));

            int correctAnswerIndex = shuffledOptions.indexOf(term.getDefinition());
            q.setCorrectAns(correctAnswerIndex);
            q.setSelectedAns(-1);

            questions.add(q);
        }

        return questions;
    }
    List<Term> listTerm = (List<Term>) getIntent().getSerializableExtra("terms");
    List<Question> questions = generateQuestions(listTerm);

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
        termController.listAllTerms(studySetId, terms -> {
            tvNumberOfQuestion.setText("1/" + String.valueOf(terms.size()));
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
