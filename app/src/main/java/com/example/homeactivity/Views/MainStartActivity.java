package com.example.homeactivity.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Controllers.AccountController;
import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.MainStartAdapter;
import com.example.homeactivity.Utils.SessionManager;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;

public class MainStartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    StudySetController studySetController;
    MainStartAdapter mainStartAdapter;
    RecyclerView popularView;
    TextView first_word,name_user;
    androidx.appcompat.widget.Toolbar toolbar;
    Menu menu;
    private AccountController accountController;
    SessionManager sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);
        sessionManager = new SessionManager(this);
        String username = sessionManager.getUsername();
        /*---------------------recycleview------------------------*/
        popularView = findViewById(R.id.popularView);
        first_word = findViewById(R.id.first_word);
        name_user = findViewById(R.id.name_user);
        studySetController = new StudySetController();
        LinearLayoutManager linearLayoutManager_popular = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        popularView.setLayoutManager(linearLayoutManager_popular);
        studySetController.listAllStudySets( termList ->{
            mainStartAdapter = new MainStartAdapter(termList,this);
            popularView.setAdapter(mainStartAdapter);
        });
            name_user.setText(username);
            String account_name = name_user.getText().toString().trim().substring(0,1);
            first_word.setText(account_name);

        /*---------------------Hooks------------------------*/

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.nav_course){
            Intent intent_course = new Intent(MainStartActivity.this,StudySetActivity.class);
            startActivity(intent_course);}


        if(item.getItemId() == R.id.nav_profile){
            Intent intent_profine = new Intent(MainStartActivity.this,UserProfileActivity.class);
            intent_profine.putExtra("ID",sessionManager.getId());
            startActivity(intent_profine);}

        if(item.getItemId() == R.id.nav_logout){
            Intent intent_logout = new Intent(MainStartActivity.this,LoginActivity.class);
            startActivity(intent_logout);}
        return true;
    }



}
