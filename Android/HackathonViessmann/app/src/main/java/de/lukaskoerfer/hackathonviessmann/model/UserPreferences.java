package de.lukaskoerfer.hackathonviessmann.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Koerfer on 30.04.2016.
 */
public class UserPreferences {

    private int NightTemperature;
    private int DayTemperature;

    private int WeekDayStart;
    private int WeekDayEnd;
    private int WeekendDayStart;
    private int WeekendDayEnd;

    private UserPreferences() {

    }

    public void Save(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("pref_night_temp", this.NightTemperature);
        editor.putInt("pref_day_temp", this.DayTemperature);
        editor.putInt("pref_week_day_start", this.WeekDayStart);
        editor.putInt("pref_week_day_end", this.WeekDayEnd);
        editor.putInt("pref_weekend_day_start", this.WeekendDayStart);
        editor.putInt("pref_weekend_day_end", this.WeekendDayEnd);
        editor.commit();
    }

    public static UserPreferences FromSettings(Context context) {
        UserPreferences userPreferences = new UserPreferences();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        userPreferences.NightTemperature = preferences.getInt("pref_night_temp", 18);
        userPreferences.DayTemperature = preferences.getInt("pref_day_temp", 22);
        userPreferences.WeekDayStart = preferences.getInt("pref_week_day_start", 420);
        userPreferences.WeekDayEnd = preferences.getInt("pref_week_day_end", 1260);
        userPreferences.WeekendDayStart = preferences.getInt("pref_weekend_day_start", 540);
        userPreferences.WeekendDayEnd = preferences.getInt("pref_weekend_day_end", 1380);
        return userPreferences;
    }


    public int getNightTemperature() {
        return NightTemperature;
    }

    public void setNightTemperature(int nightTemperature) {
        NightTemperature = nightTemperature;
    }

    public int getDayTemperature() {
        return DayTemperature;
    }

    public void setDayTemperature(int dayTemperature) {
        DayTemperature = dayTemperature;
    }

    public int getWeekDayStart() {
        return WeekDayStart;
    }

    public void setWeekDayStart(int weekDayStart) {
        WeekDayStart = weekDayStart;
    }

    public int getWeekDayEnd() {
        return WeekDayEnd;
    }

    public void setWeekDayEnd(int weekDayEnd) {
        WeekDayEnd = weekDayEnd;
    }

    public int getWeekendDayStart() {
        return WeekendDayStart;
    }

    public void setWeekendDayStart(int weekendDayStart) {
        WeekendDayStart = weekendDayStart;
    }

    public int getWeekendDayEnd() {
        return WeekendDayEnd;
    }

    public void setWeekendDayEnd(int weekendDayEnd) {
        WeekendDayEnd = weekendDayEnd;
    }
}
