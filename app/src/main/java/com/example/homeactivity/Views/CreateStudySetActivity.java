package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.CreateTermAdapter;

import java.util.ArrayList;
import java.util.List;

public class CreateStudySetActivity extends AppCompatActivity {

    private StudySetController studySetController;
    private RecyclerView rcvCreateStudySet;
    private Button btnAddCard;
    private Button btnCreate;
    private CreateTermAdapter createTermAdapter;
    private List<Term> mListTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_set);

        rcvCreateStudySet = findViewById(R.id.rcv_create_term);
        createTermAdapter = new CreateTermAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvCreateStudySet.setLayoutManager(linearLayoutManager);

        List<Term> itemList = getListCreateStudySet();
        createTermAdapter.SetData(itemList);
        rcvCreateStudySet.setAdapter(createTermAdapter);

        btnAddCard = findViewById(R.id.btnAddCard);
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.add(new Term(1, "",""));
                rcvCreateStudySet.scrollToPosition(itemList.size()-1);
            }
        });

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Term> termList = createTermAdapter.GetData();
//                studySetController.CreateStudySet(termList);
            }
        });


    }

    private List<Term> getListCreateStudySet() {
        List<Term> list = new ArrayList<>();
        list.add(new Term(1, "1",""));
        return list;
    }

}