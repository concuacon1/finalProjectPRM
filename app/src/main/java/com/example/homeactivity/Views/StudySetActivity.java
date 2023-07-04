package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.TermAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudySetActivity extends AppCompatActivity {

    private RecyclerView rcvTerm;
    private TermAdapter termAdapter;
    private TextView tvTermsNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_set);
        tvTermsNumber = findViewById(R.id.tv_terms_number);

        rcvTerm = findViewById(R.id.rcv_list_term);
        termAdapter = new TermAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTerm.setLayoutManager(linearLayoutManager);

        termAdapter.SetData(getListTerm());
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
                startActivity(intent);
            }
        });

    }

    private List<Term> getListTerm() {
        List<Term> list = new ArrayList<>();
        list.add(new Term(1,"A","QN=362 Trong di chúc Hồ Chí Minh viết rằng\n" +
                "a. Đảng phải có kế hoạch tốt để phát triển kinh tế, văn hóa\n" +
                "b. Đảng phải có kế hoạch tốt để phát triển kinh tế, chính trị\n" +
                "c. Đảng phải có kế hoạch tốt để phát triển kinh tế, xã hội"));
        list.add(new Term(1,"A","QN=362 Trong di chúc Hồ Chí Minh viết rằng\n" +
                "a. Đảng phải có kế hoạch tốt để phát triển kinh tế, văn hóa\n" +
                "b. Đảng phải có kế hoạch tốt để phát triển kinh tế, chính trị\n" +
                "c. Đảng phải có kế hoạch tốt để phát triển kinh tế, xã hội"));
        list.add(new Term(1,"A","QN=362 Trong di chúc Hồ Chí Minh viết rằng\n" +
                "a. Đảng phải có kế hoạch tốt để phát triển kinh tế, văn hóa\n" +
                "b. Đảng phải có kế hoạch tốt để phát triển kinh tế, chính trị\n" +
                "c. Đảng phải có kế hoạch tốt để phát triển kinh tế, xã hội"));
        list.add(new Term(1,"A","QN=362 Trong di chúc Hồ Chí Minh viết rằng\n" +
                "a. Đảng phải có kế hoạch tốt để phát triển kinh tế, văn hóa\n" +
                "b. Đảng phải có kế hoạch tốt để phát triển kinh tế, chính trị\n" +
                "c. Đảng phải có kế hoạch tốt để phát triển kinh tế, xã hội"));
        list.add(new Term(1,"A","QN=362 Trong di chúc Hồ Chí Minh viết rằng\n" +
                "a. Đảng phải có kế hoạch tốt để phát triển kinh tế, văn hóa\n" +
                "b. Đảng phải có kế hoạch tốt để phát triển kinh tế, chính trị\n" +
                "c. Đảng phải có kế hoạch tốt để phát triển kinh tế, xã hội"));
        tvTermsNumber.setText("Terms in this set ("+list.size()+")");
        return list;
    }
}