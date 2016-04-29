package de.lukaskoerfer.hackathonviessmann.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.GregorianCalendar;
import java.util.List;

import de.lukaskoerfer.hackathonviessmann.model.WeatherForecast;

/**
 * Created by Koerfer on 29.04.2016.
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Database.db";

    public static Database Instance(Context context) {
        return new Database(context);
    }

    public Database(Context context) {
        super(context, Database.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbContracts.WeatherForecastTable.SqlCreate());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new SQLiteException("No upgrade method implemented");
    }

    public void updateWeatherForecast(List<WeatherForecast> updatedForecast) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DbContracts.WeatherForecastTable.TABLE_FORECAST + ";");
        for (WeatherForecast forecast : updatedForecast) {
            db.execSQL("INSERT INTO " + DbContracts.WeatherForecastTable.TABLE_FORECAST + " VALUES ( " );
        }
    }

    public boolean isWeatherForecastUpToDate(GregorianCalendar datetime) {
        return false;
    }

}
