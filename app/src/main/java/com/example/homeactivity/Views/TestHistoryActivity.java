package com.example.homeactivity.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Controllers.TestController;
import com.example.homeactivity.Models.TestResult;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.SessionManager;
import com.example.homeactivity.Utils.TestHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestHistoryActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView rvHistoryList;
    TestController testController;
    TestHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history);
        btnBack = findViewById(R.id.btn_back_create_studyset2);
        rvHistoryList = findViewById(R.id.rc_test_history);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHistoryList.setLayoutManager(layoutManager);

        SessionManager sessionManager = new SessionManager(this);

        testController.getTestResultsForAccount(sessionManager.getId(), testResults -> {
            adapter = new TestHistoryAdapter(testResults);
            rvHistoryList.setAdapter(adapter);
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}