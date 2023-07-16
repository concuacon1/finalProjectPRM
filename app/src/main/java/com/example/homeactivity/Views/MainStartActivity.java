package com.example.homeactivity.Views;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.MainStartAdapter;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;

public class MainStartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    StudySetController studySetController;
    MainStartAdapter mainStartAdapter;
    RecyclerView popularView, recentView;
    androidx.appcompat.widget.Toolbar toolbar;
    Menu menu;
    private static final String id = "4zT2o8R6a1uJm3cnWE9G";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);
        /*---------------------recycleview------------------------*/
        popularView = findViewById(R.id.popularView);
        recentView = findViewById(R.id.recentView);
        LinearLayoutManager linearLayoutManager_popular = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        popularView.setLayoutManager(linearLayoutManager_popular);
        studySetController.listAllStudySets( termList ->{
            mainStartAdapter.SetData(termList);
        });
        popularView.setAdapter(mainStartAdapter);
        LinearLayoutManager linearLayoutManager_recent = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recentView.setLayoutManager(linearLayoutManager_recent);
        studySetController.listAllStudySets(id, termList ->{
            mainStartAdapter.SetData(termList);
        });
        recentView.setAdapter(mainStartAdapter);
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

        return true;
    }

}
