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
    private TextView tvAuthor;

    private static final String id = "4uM1FetD6aRPQJbffDnf";;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_set);
        tvTermsNumber = findViewById(R.id.tv_terms_number);
        tvTitle = findViewById(R.id.txtTitle);
        tvAuthor = findViewById(R.id.tv_author);
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
            tvAuthor.setText("Mai Viet Hung");
        });

        termController.listAllTerms(studySetId, termList ->{
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
                intent.putExtra("studySetID1",studySetId);
                startActivity(intent);
            }
        });
        ((Button) findViewById(R.id.btnEdit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudySetActivity.this, CreateStudySetActivity.class);
                intent.putExtra("updateStudySet", studySetId);
                startActivity(intent);
            }
        });
    }
}