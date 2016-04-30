package de.lukaskoerfer.hackathonviessmann.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import de.lukaskoerfer.hackathonviessmann.model.UserData;
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
        for (WeatherForecast forecastElement : updatedForecast) {
            // TODO
            db.execSQL("INSERT INTO " + DbContracts.WeatherForecastTable.TABLE_FORECAST + " VALUES ( '" +
                    forecastElement.getStartTime() + "' , '" +
                    forecastElement.getEndTime() + "' , '" +
                    Double.toString(forecastElement.getMinTemperature()) + "' , '" +
                    Double.toString(forecastElement.getMaxTemperature()) + "' );" );
        }
    }

    public List<WeatherForecast> getCurrentWeatherForecast() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.query(false, DbContracts.WeatherForecastTable.TABLE_FORECAST, null, null, null, null, null, null, null);
        List<WeatherForecast> forecast = new ArrayList<>();
        boolean resultLeft = result.moveToFirst();
        while (resultLeft) {
            String startTime = result.getString(result.getColumnIndex(DbContracts.WeatherForecastTable.COLUMN_START));
            String endTime = result.getString(result.getColumnIndex(DbContracts.WeatherForecastTable.COLUMN_END));
            double minTemp = result.getDouble(result.getColumnIndex(DbContracts.WeatherForecastTable.COLUMN_MIN_TEMP));
            double maxTemp = result.getDouble(result.getColumnIndex(DbContracts.WeatherForecastTable.COLUMN_MAX_TEMP));
            forecast.add(new WeatherForecast(startTime, endTime, minTemp, maxTemp));
            resultLeft = result.moveToNext();
        }
        return forecast;
    }

    public boolean isWeatherForecastUpToDate(GregorianCalendar datetime, UserData userData) {
        SQLiteDatabase db = this.getReadableDatabase();
        return false;
    }

}
