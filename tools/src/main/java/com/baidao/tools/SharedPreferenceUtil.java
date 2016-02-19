package com.baidao.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by BurizaDo on 3/4/15.
 */
public class SharedPreferenceUtil {
    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void saveLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void removeKey(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(key);
        editor.commit();
    }

}
