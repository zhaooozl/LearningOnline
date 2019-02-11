package com.example.learn.storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.learn.config.ConfigType;
import com.example.learn.config.Configurator;

public class ExPreferences {

    private static final Context mContext = Configurator.getInstance().get(ConfigType.APP_CONTENT);

    private static final SharedPreferences sp = mContext.getSharedPreferences("Profile", Context.MODE_PRIVATE);

    public static void putString(String k, String v) {
        if (sp != null) {
            sp.edit().putString(k, v).apply();
        }
    }

    public static String getString(String k) {
        if (sp != null) {
            return sp.getString(k, null);
        } else {
            return null;
        }
    }

    public static void putBoolean(String k, boolean v) {
        if (sp != null) {
            sp.edit().putBoolean(k, v).apply();
        }
    }

    public static boolean getBoolean(String k) {
        if (sp != null) {
            return sp.getBoolean(k, false);
        } else {
            return false;
        }
    }

    public static void putInt(String k, int v) {
        if (sp != null) {
            sp.edit().putInt(k, v).apply();
        }
    }

    public static int getInt(String k) {
        if (sp != null) {
            return sp.getInt(k, -1);
        } else {
            return -1;
        }
    }

    public static void clear() {
        if (sp != null) {
            sp.edit().clear().apply();
        }
    }

}
