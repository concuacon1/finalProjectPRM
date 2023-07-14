package com.example.homeactivity.Views;

import android.content.DialogInterface;
import android.os.Bundle;
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

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ListView listView;
    TextView tvDelAll, tvSearchHistory;
    ImageButton btnDelAll;
    ArrayAdapter<String> adapter;
    ArrayList<String> historySearch;
    SearchHistoryDbHelper dbHelper;
    Toolbar toolbar;
    StudySetController studySetController;

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

        studySetController = new StudySetController();

        dbHelper = new SearchHistoryDbHelper(this);
        historySearch = new ArrayList<>(dbHelper.getSearchHistory());

        // Set adapter to ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbHelper.getSearchHistory());

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
        searchView.setSubmitButtonEnabled(true); // Disable submit button

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.clear();
                adapter.addAll(historySearch);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    dbHelper.insertSearchQuery(query);
                    historySearch.add(query);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    tvDelAll.setVisibility(View.VISIBLE);
                    tvSearchHistory.setVisibility(View.VISIBLE);
                    btnDelAll.setVisibility(View.VISIBLE);

                    adapter.clear();
                    adapter.addAll(historySearch);
                    return false;
                }

                tvDelAll.setVisibility(View.INVISIBLE);
                tvSearchHistory.setVisibility(View.INVISIBLE);
                btnDelAll.setVisibility(View.INVISIBLE);

                studySetController.searchStudySets(newText, searchResults -> {
                    adapter.clear();
                    adapter.addAll(searchResults);
                });

                return true;
            }
        });

        return true;
    }

    public void onDeleteAll(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete all search history?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteAllSearchHistory();
                        historySearch.clear();
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}