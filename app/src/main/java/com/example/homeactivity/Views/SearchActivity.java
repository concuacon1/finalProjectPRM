package com.example.homeactivity.Views;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.SearchResultAdapter;
import com.example.homeactivity.Utils.SearchSuggestionProvider;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    SearchView searchView;
    SearchResultAdapter adapter;
    private final Handler searchHandler = new Handler();
    private final long SEARCH_DELAY = 3000; // 5 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.rv_search_result);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearch(query);
                // Save the recent query
                handleSearch(query);

                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Cancel the previous search and schedule a new one after a delay
                searchHandler.removeCallbacksAndMessages(null);
                searchHandler.postDelayed(() -> doSearch(newText), SEARCH_DELAY);
                return false;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                // Retrieve the suggestion and update the RecyclerView
                CursorAdapter suggestionAdapter = searchView.getSuggestionsAdapter();
                Cursor cursor = suggestionAdapter.getCursor();
                if (cursor.moveToPosition(position)) {
                    @SuppressLint("Range") String suggestion = cursor
                            .getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
                    StudySetController controller = new StudySetController();
                    controller.searchStudySets(suggestion, studySets -> {
                        adapter = new SearchResultAdapter(studySets);
                        recyclerView.setAdapter(adapter);
                    });
                }

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void doSearch(String query) {
        StudySetController controller = new StudySetController();
        controller.searchStudySets(query, studySets -> {
            adapter = new SearchResultAdapter(studySets);
            recyclerView.setAdapter(adapter);
        });
    }


    public void handleSearch(String query) {
        SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this,
                SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);

        searchRecentSuggestions.saveRecentQuery(query, null);
    }

    @Override
    protected void onDestroy() {
        // Remove any pending search requests when the activity is destroyed
        searchHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}