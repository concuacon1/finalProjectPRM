package com.example.homeactivity.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "search_history.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    private static final String TABLE_SEARCH_HISTORY = "search_history";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SEARCH_QUERY = "search_query";

    public SearchHistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the search history table
        String createTableQuery = "CREATE TABLE " + TABLE_SEARCH_HISTORY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SEARCH_QUERY + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
    }

    public void insertSearchQuery(String searchQuery) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SEARCH_QUERY, searchQuery);
        db.insert(TABLE_SEARCH_HISTORY, null, values);
        db.close();
    }

    public List<String> getSearchHistory() {
        List<String> searchHistory = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {COLUMN_SEARCH_QUERY};
        Cursor cursor = db.query(TABLE_SEARCH_HISTORY, projection, null, null, null, null, COLUMN_ID + " DESC");

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_SEARCH_QUERY);
            do {
                String searchQuery = cursor.getString(columnIndex);
                searchHistory.add(searchQuery);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return searchHistory;
    }

    public void deleteAllSearchHistory() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SEARCH_HISTORY, null, null);
        db.close();
    }
}