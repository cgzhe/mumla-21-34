package se.lublin.mumla.app;

import static se.lublin.mumla.Settings.PREF_LANGUAGE;
import static se.lublin.mumla.Settings.PREF_THEME;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class MumlaApplication extends Application implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        applyTheme(preferences);
        applyLanguage(preferences);
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, @Nullable String key) {
        if (key == null) {
            return;
        }
        switch (key) {
            case PREF_LANGUAGE:
                applyLanguage(preferences);
                break;
            case PREF_THEME:
                applyTheme(preferences);
                break;
        }
    }

    private void applyLanguage(SharedPreferences preferences) {
        String language = preferences.getString(PREF_LANGUAGE, "system");
        if (language.equals("system")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getResources().getConfiguration().setLocales(LocaleList.getDefault());
            } else {
                getResources().getConfiguration().locale = Locale.getDefault();
            }
        } else {
            Locale locale = new Locale(language);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getResources().getConfiguration().setLocales(new LocaleList(locale));
            } else {
                getResources().getConfiguration().locale = locale;
            }
        }
        getResources().updateConfiguration(getResources().getConfiguration(), getResources().getDisplayMetrics());
    }

    private static void applyTheme(SharedPreferences preferences) {
        // The "system" and "force*" values are new (see preference_notranslate.xml).
        // We let other (older) value result in system default theme, and write that
        // to the preference store.
        switch (preferences.getString(PREF_THEME, "system")) {
            case "forceLight":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "forceDark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "system":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                preferences.edit().putString(PREF_THEME, "system").apply();
                break;
        }
    }
}
