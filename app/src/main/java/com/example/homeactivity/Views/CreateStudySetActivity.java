package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.CreateTermAdapter;

import java.util.ArrayList;
import java.util.List;

public class CreateStudySetActivity extends AppCompatActivity {

    private StudySetController studySetController = new StudySetController();
    private RecyclerView rcvCreateStudySet;
    private Button btnAddCard;
    private Button btnCreate;
    private EditText etTitle;
    private EditText etDefinition;
    private CreateTermAdapter createTermAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_set);

        rcvCreateStudySet = findViewById(R.id.rcv_create_term);
        btnAddCard = findViewById(R.id.btnAddCard);
        btnCreate = findViewById(R.id.btnCreate);
        etTitle = findViewById(R.id.etTitle);
        etDefinition = findViewById(R.id.etDescription);


        createTermAdapter = new CreateTermAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvCreateStudySet.setLayoutManager(linearLayoutManager);

        List<Term> itemList = getListCreateStudySet();
        createTermAdapter.SetData(itemList);
        rcvCreateStudySet.setAdapter(createTermAdapter);

        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.add(new Term());
                rcvCreateStudySet.scrollToPosition(itemList.size()-1);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createStudySet();
            }
        });


    }

    private void createStudySet() {
        StudySet studySet = new StudySet();
        studySet.setTitle(etTitle.getText().toString());
        if (studySet.getTitle().matches("")) {
            Toast.makeText(CreateStudySetActivity.this, "Empty title", Toast.LENGTH_SHORT).show();
            return;
        }
        studySet.setDescription(etDefinition.getText().toString());
        if (studySet.getDescription().matches("")) {
            Toast.makeText(CreateStudySetActivity.this, "Empty description", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Term> termList = createTermAdapter.GetData();
        for (Term t: termList
        ) {
            if (t.getTerm() == null || t.getTerm().isEmpty()) {
                Toast.makeText(CreateStudySetActivity.this, "Empty term", Toast.LENGTH_SHORT).show();
                return;
            }
            if (t.getDefinition() == null || t.getDefinition().isEmpty()) {
                Toast.makeText(CreateStudySetActivity.this, "Empty definition", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        studySetController.createStudySet(studySet, termList);
        Intent intent = new Intent(CreateStudySetActivity.this, StudySetActivity.class);
        intent.putExtra("studySetId",studySet.getId());
        startActivity(intent);
        finish();
    }

    private List<Term> getListCreateStudySet() {
        List<Term> list = new ArrayList<>();
        list.add(new Term());
        return list;
    }

}