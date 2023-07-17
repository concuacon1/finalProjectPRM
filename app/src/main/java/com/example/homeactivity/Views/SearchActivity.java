package com.example.homeactivity.Views;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.SearchHistoryDbHelper;
import com.example.homeactivity.Utils.SearchSuggestionProvider;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity{

    ListView listView;
    TextView tvDelAll, tvSearchHistory;
    ImageButton btnDelAll;
    ArrayAdapter<String> adapter;
    ArrayList<String> historySearch;
    SearchHistoryDbHelper dbHelper;
    Toolbar toolbar;
    StudySetController studySetController;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        handleSearch(getIntent());

        listView = findViewById(R.id.lst_hint);
        tvDelAll = findViewById(R.id.tv_delete_history);
        btnDelAll = findViewById(R.id.btn_delete_history);
        tvSearchHistory = findViewById(R.id.tv_search_history);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleSearch(intent);
    }

    public void handleSearch( Intent intent)
    {
        if (Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions searchRecentSuggestions=new SearchRecentSuggestions(this,
                    SearchSuggestionProvider.AUTHORITY,SearchSuggestionProvider.MODE);

            searchRecentSuggestions.saveRecentQuery(query,null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);

        // Get the search item from the menu
        MenuItem searchItem = menu.findItem(R.id.search_bar);
        searchView = (SearchView) searchItem.getActionView();

        // Customize the search view
        searchView.setIconifiedByDefault(true); // Set to true for iconified view by default
        searchView.setSubmitButtonEnabled(true); // Disable submit button

        SearchManager searchManager = (SearchManager) getSystemService(SearchActivity.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        return super.onCreateOptionsMenu(menu);
    }
}