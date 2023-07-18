package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.Controllers.TermController;
import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.TermAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class StudySetActivity extends AppCompatActivity {

    private RecyclerView rcvTerm;
    private TermAdapter termAdapter;
    private TextView tvTermsNumber;
    private StudySetController studySetController;
    private TermController termController;
    private TextView tvTitle;

    private static final String id = "4zT2o8R6a1uJm3cnWE9G";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_set);
        tvTermsNumber = findViewById(R.id.tv_terms_number);
        tvTitle = findViewById(R.id.txtTitle);
        rcvTerm = findViewById(R.id.rcv_list_term);
        termAdapter = new TermAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTerm.setLayoutManager(linearLayoutManager);

        //load data
        studySetController = new StudySetController();
        termController = new TermController();

        Intent intent = getIntent();
        String studySetId = intent.getStringExtra("studySetId");
        studySetController.findStudySet(id, studySet -> {
            tvTitle.setText(studySet.getTitle());
        });

        termController.listAllTerms(id, termList ->{
            termAdapter.SetData(termList);
            tvTermsNumber.setText("Terms in this set ("+termList.size()+")");

            ((Button) findViewById(R.id.btnFlashcard)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StudySetActivity.this, FlashcardActivity.class);
                    intent.putExtra("terms", (Serializable) termList);
                    startActivity(intent);
                }
            });

        });

        rcvTerm.setAdapter(termAdapter);

        ((Button) findViewById(R.id.btnTest)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudySetActivity.this, StartTestActivity.class);
                startActivity(intent);
            }
        });
        ((Button) findViewById(R.id.btnEdit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudySetActivity.this, CreateStudySetActivity.class);
                intent.putExtra("updateStudySet", id);
                startActivity(intent);
            }
        });
        ((ImageButton) findViewById(R.id.close_button_2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}