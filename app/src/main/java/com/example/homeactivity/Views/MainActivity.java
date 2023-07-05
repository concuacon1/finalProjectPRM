package com.example.homeactivity.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.Controllers.AccountController;
import com.example.homeactivity.Models.Account;
import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Models.Term;
import com.example.homeactivity.Services.AccountService;
import com.example.homeactivity.Services.StudySetService;
import com.example.homeactivity.R;
import com.example.homeactivity.Services.TermService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.btn_test)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartTestActivity.class);
                startActivity(intent);
            }
        });

        List<Term> terms = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Term t = new Term();
            terms.add(t);
        }

        TermService service = new TermService();
        service.createTerms(terms);

        Term term = new Term();
        term.setTerm("Hello");
        term.setDefinition("Xin Chao");

        service.createTerm(term);


    }

}