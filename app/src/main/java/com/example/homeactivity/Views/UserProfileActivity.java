package com.example.homeactivity.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Controllers.AccountController;
import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.LoadingDialog;
import com.example.homeactivity.Utils.MainPageAdapter;

public class UserProfileActivity extends AppCompatActivity {
    MainPageAdapter mainStartAdapter;
    private AccountController accountController;
    private StudySetController studySetController;
    private TextView name, nick_name, email, nameFirstWord, headerStudySet;
    private RecyclerView rvMyStudySet;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = findViewById(R.id.name_user);
        nick_name = findViewById(R.id.nickname_user);
        email = findViewById(R.id.email_user);
        nameFirstWord = findViewById(R.id.nameFirst);
        headerStudySet = findViewById(R.id.tv_header_studyset);
        rvMyStudySet = findViewById(R.id.rv_mystudyset);
        loadingDialog = new LoadingDialog(this);

        findViewById(R.id.btn_back_userprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadingDialog.startLoadingDialog();

        String id = getIntent().getStringExtra("ID");
        accountController = new AccountController();

        accountController.findAccount(id, account -> {
            name.setText(account.getName());
            nick_name.setText(account.getNickname());
            email.setText(account.getEmail());
            nameFirstWord.setText(String.valueOf(account.getNickname().charAt(0)));
            headerStudySet.setText(account.getNickname() + "'s Study Set");
        });

        LinearLayoutManager linearLayoutManager_popular = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvMyStudySet.setLayoutManager(linearLayoutManager_popular);
        studySetController = new StudySetController();
        studySetController.listAllStudySets(id, termList -> {
            mainStartAdapter = new MainPageAdapter(termList);
            rvMyStudySet.setAdapter(mainStartAdapter);
            loadingDialog.dismissDialog();
        });


    }
}