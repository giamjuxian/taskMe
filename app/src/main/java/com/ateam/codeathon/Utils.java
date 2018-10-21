package com.ateam.codeathon;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Utils {
    public static void setNumberOfItemPosted(Context context, int value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Number_of_item_posted", value);
        editor.apply();
    }

    public static int getNumberOfItemPosted(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("Number_of_item_posted", 5);
    }

    public static void setListing(String sharedPrefereceKey, Context context, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(sharedPrefereceKey, value);
        editor.apply();
    }

    public static String getListing(String sharedPrefereceKey, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(sharedPrefereceKey, "");
    }
}
