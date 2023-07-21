package com.example.homeactivity.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.Controllers.TermController;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.LoadingDialog;

public class StartTestActivity extends AppCompatActivity {
    TermController termController;
    StudySetController studySetController;
    private TextView tvNumberOfQuestionStt, tvParticipants;
    private ImageButton backBtn;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoadingDialog();

        termController = new TermController();
        studySetController = new StudySetController();

        tvNumberOfQuestionStt = findViewById(R.id.tv_numberOfQuestionStt);
        tvParticipants = findViewById(R.id.tv_participants);

        backBtn = findViewById(R.id.btn_back_start_test);
        Intent intent = getIntent();
        String studySetId = intent.getStringExtra("studySetID1");

        termController.listAllTerms(studySetId, studySet -> {
            tvNumberOfQuestionStt.setText(String.valueOf(studySet.size()));
            studySetController.findStudySet(studySetId, studySet1 -> {
                tvParticipants.setText(String.valueOf(studySet1.getNumberOfParticipants()));
                loadingDialog.dismissDialog();
            });
        });

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartTestActivity.this, TestActivity.class);
                intent.putExtra("studySetID2", studySetId);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}