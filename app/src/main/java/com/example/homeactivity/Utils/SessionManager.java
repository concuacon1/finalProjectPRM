package com.example.homeactivity.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    private final SharedPreferences sharedPreferences;

    public SessionManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveSession(String username, String email, String id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KeyPreference.KEY_USERNAME, username);
        editor.putString(KeyPreference.KEY_EMAIL, email);
        editor.putString(KeyPreference.KEY_ACCOUNT_ID, id);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.contains(KeyPreference.KEY_USERNAME)
                && sharedPreferences.contains(KeyPreference.KEY_EMAIL);
    }

    public String getUsername() {
        return sharedPreferences.getString(KeyPreference.KEY_USERNAME, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KeyPreference.KEY_EMAIL, "");
    }

    public String getId() {
        return sharedPreferences.getString(KeyPreference.KEY_ACCOUNT_ID, "");
    }

    public void clearSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}