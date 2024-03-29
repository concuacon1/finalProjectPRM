package com.example.homeactivity.Views;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.Controllers.TermController;
import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.CreateTermAdapter;
import com.example.homeactivity.Utils.LoadingDialog;
import com.example.homeactivity.Utils.SessionManager;

import java.util.List;

public class CreateStudySetActivity extends AppCompatActivity {

    LoadingDialog loadingDialog;
    SessionManager sessionManager;
    private StudySetController studySetController;
    private TermController termController;
    private RecyclerView rcvCreateStudySet;
    private Button btnAddCard;
    private Button btnCreate;
    private EditText etTitle;
    private EditText etDescription;
    private CreateTermAdapter createTermAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_set);

        rcvCreateStudySet = findViewById(R.id.rcv_create_term);
        btnAddCard = findViewById(R.id.btnAddCard);
        btnCreate = findViewById(R.id.btnCreate);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        studySetController = new StudySetController();
        termController = new TermController();
        sessionManager = new SessionManager(this);
        loadingDialog = new LoadingDialog(CreateStudySetActivity.this);
        findViewById(R.id.btn_back_create_studyset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        createTermAdapter = new CreateTermAdapter(this);
        String studySetId = intent.getStringExtra("updateStudySet");

        if (studySetId != null) {
            studySetController.findStudySet(studySetId, studySet -> {
                etTitle.setText(studySet.getTitle());
                etDescription.setText(studySet.getDescription());
            });
            termController.listAllTerms(studySetId, termList -> {
                createTermAdapter.SetData(termList);
            });
            btnCreate.setText("Update");
            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateStudySet(studySetId);
                }
            });
        } else {
            createTermAdapter.NewList();
            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createStudySet();
                }
            });
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvCreateStudySet.setLayoutManager(linearLayoutManager);


        rcvCreateStudySet.setAdapter(createTermAdapter);

        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTermAdapter.AddItem();
                rcvCreateStudySet.scrollToPosition(createTermAdapter.getItemCount() - 1);
            }
        });

        findViewById(R.id.close_button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelDiaglog();
            }
        });
    }

    private void createStudySet() {
        loadingDialog.startLoadingDialog();
        StudySet studySet = new StudySet();
        studySet.setTitle(etTitle.getText().toString());
        studySet.setDescription(etDescription.getText().toString());
        studySet.setUserId(sessionManager.getId());
        if (studySet.getTitle() == null || studySet.getTitle().isEmpty()) {
            Toast.makeText(CreateStudySetActivity.this, "Empty title", Toast.LENGTH_SHORT).show();
            loadingDialog.dismissDialog();
            return;
        }
        if (studySet.getDescription() == null || studySet.getDescription().isEmpty()) {
            Toast.makeText(CreateStudySetActivity.this, "Empty description", Toast.LENGTH_SHORT).show();
            loadingDialog.dismissDialog();
            return;
        }
        List<Term> termList = createTermAdapter.GetData();
        for (Term t : termList
        ) {
            if (t.getTerm() == null || t.getTerm().isEmpty()) {
                Toast.makeText(CreateStudySetActivity.this, "Empty term", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
                return;
            }
            if (t.getDefinition() == null || t.getDefinition().isEmpty()) {
                Toast.makeText(CreateStudySetActivity.this, "Empty definition", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
                return;
            }
        }
        try {
            String studySetId = studySetController.createStudySet(studySet);
            for (Term t : termList
            ) {
                t.setStudySetId(studySetId);
            }
            termController.createTerms(termList);
        } catch (Exception e) {
            Toast.makeText(CreateStudySetActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            loadingDialog.dismissDialog();
            return;
        }

        loadingDialog.dismissDialog();

        Intent intent = new Intent(CreateStudySetActivity.this, StudySetActivity.class);
        intent.putExtra("studySetId", studySet.getId());
        startActivity(intent);

        finish();
    }

    private void updateStudySet(String studySetId) {
        StudySet studySet = new StudySet();
        studySet.setId(studySetId);
        studySet.setTitle(etTitle.getText().toString());
        studySet.setDescription(etDescription.getText().toString());
        studySet.setUserId(sessionManager.getId());
        if (studySet.getTitle().matches("")) {
            Toast.makeText(CreateStudySetActivity.this, "Empty title", Toast.LENGTH_SHORT).show();
            return;
        }
        studySet.setDescription(etDescription.getText().toString());
        if (studySet.getDescription().matches("")) {
            Toast.makeText(CreateStudySetActivity.this, "Empty description", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            studySetController.updateStudySet(studySet);
        } catch (Exception e) {
            Toast.makeText(CreateStudySetActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
        List<Term> termList = createTermAdapter.GetData();

        for (Term t : termList
        ) {
            if (t.getTerm() == null || t.getTerm().isEmpty()) {
                Toast.makeText(CreateStudySetActivity.this, "Empty term", Toast.LENGTH_SHORT).show();
                return;
            }
            if (t.getDefinition() == null || t.getDefinition().isEmpty()) {
                Toast.makeText(CreateStudySetActivity.this, "Empty definition", Toast.LENGTH_SHORT).show();
                return;
            }
            if (t.getId() == null) {
                t.setStudySetId(studySetId);
                try {
                    termController.createTerm(t);
                } catch (Exception e) {
                    Toast.makeText(CreateStudySetActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
            if (t.getId() != null) {
                try {
                    termController.updateTerm(t);
                } catch (Exception e) {
                    Toast.makeText(CreateStudySetActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

        }
        loadingDialog.startLoadingDialog();
        termController.listAllTerms(studySetId, termList1 -> {
            for (Term t : termList1
            ) {
                if (!termList.contains(t)) {
                    try {
                        termController.deleteTerm(t.getId());
                    } catch (Exception e) {
                        Toast.makeText(CreateStudySetActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
                loadingDialog.dismissDialog();
                Intent intent = new Intent(CreateStudySetActivity.this, StudySetActivity.class);
                intent.putExtra("studySetId", studySet.getId());
                startActivity(intent);
                finish();
            }

        });
    }

    @Override
    public void onBackPressed() {
        showCancelDiaglog();
    }

    private void showCancelDiaglog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_exit_studyset);
        builder.setMessage(R.string.messeage_exit_studyset).
                setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}