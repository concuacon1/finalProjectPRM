package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.Controllers.TermController;
import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.TermAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudySetActivity extends AppCompatActivity {

    private RecyclerView rcvTerm;
    private TermAdapter termAdapter;
    private TextView tvTermsNumber;
    private StudySetController studySetController;
    private TermController termList;
    private TextView tvTitle;

    private static final String id = "4zT2o8R6a1uJm3cnWE9G";;
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
        termList = new TermController();

        Intent intent = getIntent();
        String studySetId = intent.getStringExtra("studySetId");
        studySetController.findStudySet(id, studySet -> {
            tvTitle.setText(studySet.getTitle());
        });

        termList.listAllTerms(id, termList ->{
            termAdapter.SetData(termList);
            tvTermsNumber.setText("Terms in this set ("+termList.size()+")");
        });

        rcvTerm.setAdapter(termAdapter);
        ((Button) findViewById(R.id.btnFlashcard)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudySetActivity.this, FlashcardActivity.class);
                startActivity(intent);
            }
        });
        ((Button) findViewById(R.id.btnTest)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudySetActivity.this, StartTestActivity.class);
                intent.putExtra("studySetID", studySetId);
                startActivity(intent);
                startActivity(intent);
            }
        });

    }
}