package de.lukaskoerfer.hackathonviessmann;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Koerfer on 29.04.2016.
 */
public class Settings {

    private SharedPreferences Preferences;

    public static Settings Instance(Context context) {
        return new Settings(context);
    }

    public Settings(Context context) {
        this.Preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean allSettingsAvailable() {
        // TODO: other options
        return this.Preferences.contains("pref_username");
    }

    public String getUsername() {
        return this.Preferences.getString("pref_username", "");
    }

    public String getLocationString() {
        return this.Preferences.getString("pref_location", "");
    }

}
