package com.example.homeactivity.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeactivity.Controllers.AccountController;
import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.Controllers.TermController;
import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.SessionManager;
import com.example.homeactivity.Utils.TermAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class StudySetActivity extends AppCompatActivity {

    private RecyclerView rcvTerm;
    private TermAdapter termAdapter;
    private TextView tvTermsNumber;
    private StudySetController studySetController;
    private TermController termController;
    private TextView tvTitle;
    private TextView tvAuthor;

    private Button btnEdit;
    private Button btnDelete;
    Intent intent;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_set);
        tvTermsNumber = findViewById(R.id.tv_terms_number);
        tvTitle = findViewById(R.id.txtTitle);
        tvAuthor = findViewById(R.id.tv_author);
        rcvTerm = findViewById(R.id.rcv_list_term);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        ((ImageView)findViewById(R.id.btn_back_studyset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sessionManager = new SessionManager(this);

        termAdapter = new TermAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTerm.setLayoutManager(linearLayoutManager);

        //load data
        studySetController = new StudySetController();
        termController = new TermController();

        intent = getIntent();
        String studySetId = intent.getStringExtra("studySetId");
        studySetController.findStudySet(studySetId, studySet -> {
            tvTitle.setText(studySet.getTitle());
            AccountController controller = new AccountController();
            controller.findAccount(studySet.getUserId(), account -> {
                tvAuthor.setText(account.getNickname());
            });
            if (!Objects.equals(sessionManager.getId(), studySet.getUserId())) {
                btnEdit.setVisibility(View.INVISIBLE);
                btnDelete.setVisibility(View.INVISIBLE);
            } else {
                btnEdit.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
            }
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
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudySetActivity.this, CreateStudySetActivity.class);
                intent.putExtra("updateStudySet", studySetId);
                startActivity(intent);
            }
        });
        ((ImageButton) findViewById(R.id.close_button_2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudySet(studySetId);
            }
        });
    }

    private void deleteStudySet(String studySetId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to delete this study set?");
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    studySetController.deleteStudySet(studySetId);
                    Toast.makeText(StudySetActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    Toast.makeText(StudySetActivity.this, "Delete error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

