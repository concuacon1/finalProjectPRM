package com.example.homeactivity.Views;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.SearchRecentSuggestionsLimited;
import com.example.homeactivity.Utils.SearchResultAdapter;
import com.example.homeactivity.Utils.SearchSuggestionProvider;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvResult;
    Toolbar toolbar;
    SearchView searchView;
    SearchResultAdapter adapter;
    private final Handler searchHandler = new Handler();
    private final long SEARCH_DELAY = 3000; // 3 seconds delay
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.rv_search_result);
        tvResult = findViewById(R.id.tv_search_reult);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationIcon(R.drawable.ic_left);  //your icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainStartActivity.class);
                context.startActivity(intent);
            }
        });
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
                if (TextUtils.isEmpty(query)){
                    tvResult.setText("Search something...");
                    tvResult.setVisibility(View.VISIBLE);
                    return false;
                }

                doSearch(query);
                // Save the recent query
                handleSearch(query);
                tvResult.setVisibility(View.INVISIBLE);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    tvResult.setText("Search something...");
                    tvResult.setVisibility(View.VISIBLE);
                }
                tvResult.setVisibility(View.INVISIBLE);
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
                    doSearch(suggestion);
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
            if (studySets.size() == 0) {
                tvResult.setText("Not found");
                tvResult.setVisibility(View.VISIBLE);
                return;
            }
            tvResult.setVisibility(View.INVISIBLE);
        });
        searchView.clearFocus();
    }


    public void handleSearch(String query) {
        SearchRecentSuggestionsLimited searchRecentSuggestions = new SearchRecentSuggestionsLimited(this,
                SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE, 5);
        searchRecentSuggestions.saveRecentQuery(query, null);
    }

    @Override
    protected void onDestroy() {
        // Remove any pending search requests when the activity is destroyed
        searchHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}