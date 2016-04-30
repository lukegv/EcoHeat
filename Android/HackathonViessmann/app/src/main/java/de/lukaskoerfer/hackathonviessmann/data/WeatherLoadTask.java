package de.lukaskoerfer.hackathonviessmann.data;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import de.lukaskoerfer.hackathonviessmann.data.OpenWeather;
import de.lukaskoerfer.hackathonviessmann.db.Database;
import de.lukaskoerfer.hackathonviessmann.model.GlobalLocation;
import de.lukaskoerfer.hackathonviessmann.model.WeatherForecast;

/**
 * Created by Koerfer on 29.04.2016.
 */
public abstract class WeatherLoadTask extends AsyncTask<GlobalLocation, Void, Void> {

    protected Context context;

    public WeatherLoadTask(Context c) {
        this.context = c;
    }

    @Override
    protected Void doInBackground(GlobalLocation... coordinate) {
        List<WeatherForecast> forecast = OpenWeather.GetForecast(coordinate[0]);
        Database.Instance(this.context).updateWeatherForecast(forecast);
        return null;
    }
}
