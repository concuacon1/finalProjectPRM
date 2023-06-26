package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.CreateStudySetAdapter;

import java.util.ArrayList;
import java.util.List;

public class CreateStudySetActivity extends AppCompatActivity {

    private RecyclerView rcvCreateStudySet;
    Button btnAddCard;
    private CreateStudySetAdapter createStudySetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_set);

        rcvCreateStudySet = findViewById(R.id.rcv_create_term);
        createStudySetAdapter = new CreateStudySetAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvCreateStudySet.setLayoutManager(linearLayoutManager);

        List<Term> itemList = getListCreateStudySet();
        createStudySetAdapter.setData(itemList);
        rcvCreateStudySet.setAdapter(createStudySetAdapter);

        btnAddCard = findViewById(R.id.btnAddCard);
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.add(new Term(1, "",""));
                createStudySetAdapter.notifyDataSetChanged();
            }
        });
    }

    private List<Term> getListCreateStudySet() {
        List<Term> list = new ArrayList<>();
        list.add(new Term(1, "1",""));
        return list;
    }

}