package com.example.android.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

public class MiscUtils {

    @NonNull
    public static String getLocationFromSharePreferences(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(
                context.getString(R.string.location_preference_key),
                context.getString(R.string.settings_select_location_default)
        );
    }
}
