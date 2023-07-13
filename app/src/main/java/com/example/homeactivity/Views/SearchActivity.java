package com.example.homeactivity.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.homeactivity.R;
import com.example.homeactivity.Utils.SearchHistoryDbHelper;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ListView listView;
    TextView tvDelAll, tvSearchHistory;
    ImageButton btnDelAll;

    ArrayAdapter<String> adapter;

    ArrayList<String> historySearch;
    SearchHistoryDbHelper dbHelper;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        listView = findViewById(R.id.lst_hint);
        tvDelAll = findViewById(R.id.tv_delete_history);
        btnDelAll = findViewById(R.id.btn_delete_history);
        tvSearchHistory = findViewById(R.id.tv_search_history);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        dbHelper = new SearchHistoryDbHelper(this);
        historySearch = new ArrayList<>(dbHelper.getSearchHistory());

        // Set adapter to ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historySearch);

        listView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);

        // Get the search item from the menu
        MenuItem searchItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Customize the search view
        searchView.setIconifiedByDefault(true); // Set to true for iconified view by default
        searchView.setSubmitButtonEnabled(false); // Disable submit button
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    dbHelper.insertSearchQuery(query);
                    historySearch.add(query);
                    adapter.notifyDataSetChanged();
                    searchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                int visibility = TextUtils.isEmpty(newText) ? View.VISIBLE : View.INVISIBLE;
                tvDelAll.setVisibility(visibility);
                tvSearchHistory.setVisibility(visibility);
                btnDelAll.setVisibility(visibility);

                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    public void onDeleteAll(View view) {
        dbHelper.deleteAllSearchHistory();
        historySearch.clear();
        historySearch.addAll(dbHelper.getSearchHistory());
        adapter.notifyDataSetChanged();
    }
}