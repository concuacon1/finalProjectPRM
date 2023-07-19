package com.example.homeactivity.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Controllers.TestController;
import com.example.homeactivity.Models.TestResult;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.SessionManager;
import com.example.homeactivity.Utils.TestHistoryAdapter;

public class TestHistoryActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView rvHistoryList;
    TestController testController;
    TestHistoryAdapter adapter;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history);
        btnBack = findViewById(R.id.btn_back_create_studyset2);
        rvHistoryList = findViewById(R.id.rc_test_history);
        tvResult = findViewById(R.id.tv_history_result);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHistoryList.setLayoutManager(layoutManager);
        testController = new TestController();

        SessionManager sessionManager = new SessionManager(this);

        testController.getTestResultsForAccount(sessionManager.getId(), testResults -> {
            if (testResults.size() > 0) {
                tvResult.setVisibility(View.INVISIBLE);
                adapter = new TestHistoryAdapter(testResults);
                rvHistoryList.setAdapter(adapter);
            } else {
                tvResult.setVisibility(View.VISIBLE);
            }

        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}