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
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.LoadingDialog;
import com.example.homeactivity.Utils.SessionManager;
import com.example.homeactivity.Utils.TestHistoryAdapter;

public class TestHistoryActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView rvHistoryList;
    TestController testController;
    TestHistoryAdapter adapter;
    TextView tvResult;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history);
        btnBack = findViewById(R.id.btn_back_create_studyset2);
        rvHistoryList = findViewById(R.id.rc_test_history);
        tvResult = findViewById(R.id.tv_history_result);
        loadingDialog = new LoadingDialog(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHistoryList.setLayoutManager(layoutManager);
        testController = new TestController();

        loadingDialog.startLoadingDialog();
        SessionManager sessionManager = new SessionManager(this);

        testController.getTestResultsForAccount(sessionManager.getId(), testResults -> {
            if (testResults.size() > 0) {
                adapter = new TestHistoryAdapter(testResults);
                rvHistoryList.setAdapter(adapter);
                tvResult.setVisibility(View.INVISIBLE);
                loadingDialog.dismissDialog();
            } else {
                tvResult.setVisibility(View.VISIBLE);
                loadingDialog.dismissDialog();
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