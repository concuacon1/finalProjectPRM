package com.example.homeactivity.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.MainStartAdapter;
import com.example.homeactivity.Utils.SessionManager;
import com.google.android.material.navigation.NavigationView;

public class MainStartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    StudySetController studySetController;
    MainStartAdapter mainStartAdapter;
    RecyclerView popularView;
    TextView first_word, name_user;
    androidx.appcompat.widget.Toolbar toolbar;

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
        ((Button) findViewById(R.id.btn_create_studyset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_course = new Intent(MainStartActivity.this, CreateStudySetActivity.class);
                startActivity(intent_course);
            }
        });
        ((Button) findViewById(R.id.btn_view_studyset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(MainStartActivity.this, UserProfileActivity.class);
                intentProfile.putExtra("ID", sessionManager.getId());
                startActivity(intentProfile);
            }
        });
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        popularView.addItemDecoration(itemDecoration);
        studySetController = new StudySetController();
        LinearLayoutManager linearLayoutManager_popular = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        popularView.setLayoutManager(linearLayoutManager_popular);
        studySetController.listAllStudySets(termList -> {
            mainStartAdapter = new MainStartAdapter(termList, this);
            popularView.setAdapter(mainStartAdapter);
        });
        name_user.setText(username);
        String account_name = name_user.getText().toString().trim().substring(0, 1);
        first_word.setText(account_name);

        /*---------------------Hooks------------------------*/

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
            Intent intentProfile = new Intent(MainStartActivity.this, UserProfileActivity.class);
            intentProfile.putExtra("ID", sessionManager.getId());
            startActivity(intentProfile);
        }

        if (item.getItemId() == R.id.nav_logout) {
            Intent intent_logout = new Intent(MainStartActivity.this, LoginActivity.class);
            startActivity(intent_logout);
        }

        if (item.getItemId() == R.id.nav_history) {
            Intent intent_history = new Intent(MainStartActivity.this, TestHistoryActivity.class);
            sessionManager.clearSession();
            startActivity(intent_history);
        }

        return true;
    }

    public void updateNavHeader() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
            Intent intent = new Intent(MainStartActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
