package com.example.homeactivity.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.MainPageAdapter;
import com.example.homeactivity.Utils.SessionManager;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView tvHello;
    Button btnCreateStudySet;
    RecyclerView rvHomePage;
    SessionManager sessionManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    StudySetController studySetController;
    MainPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHello = findViewById(R.id.tv_hello);
        rvHomePage = findViewById(R.id.rv_home);
        studySetController = new StudySetController();

        findViewById(R.id.btn_create_studyset_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_course = new Intent(MainActivity.this, CreateStudySetActivity.class);
                startActivity(intent_course);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvHomePage.setLayoutManager(manager);
        studySetController.listTop10StudySetsByParticipants(studySets -> {
            adapter = new MainPageAdapter(studySets);
            rvHomePage.setAdapter(adapter);
        });

        sessionManager = new SessionManager(this);
        tvHello.setText("Hello, " + sessionManager.getUsername());

        /*---------------------Navigation------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        updateNavHeader();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_profile) {
            Intent intentProfile = new Intent(MainActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("ID", sessionManager.getId());
            startActivity(intentProfile);
        }

        if (item.getItemId() == R.id.nav_logout) {
            Intent intent_logout = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent_logout);
        }

        if (item.getItemId() == R.id.nav_history) {
            Intent intent_history = new Intent(MainActivity.this, TestHistoryActivity.class);
            sessionManager.clearSession();
            startActivity(intent_history);
        }

        return true;
    }

    public void updateNavHeader() {

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView first_word_name = headerView.findViewById(R.id.tv_firstword);
        TextView full_name = headerView.findViewById(R.id.tv_fullname);

        first_word_name.setText(String.valueOf(sessionManager.getUsername().charAt(0)));
        full_name.setText(sessionManager.getUsername());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_toolbar) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}