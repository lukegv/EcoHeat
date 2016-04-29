package de.lukaskoerfer.hackathonviessmann;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.ArrayList;

import de.lukaskoerfer.hackathonviessmann.model.PredictionDataPoint;
import de.lukaskoerfer.hackathonviessmann.model.WeatherForecast;
import de.lukaskoerfer.hackathonviessmann.ui.PredictionChart;
import de.lukaskoerfer.hackathonviessmann.db.Database;
import de.lukaskoerfer.hackathonviessmann.model.GlobalLocation;
import de.lukaskoerfer.hackathonviessmann.model.WeatherForecast;
import de.lukaskoerfer.hackathonviessmann.ui.DialogBuilder;
import de.lukaskoerfer.hackathonviessmann.weather.WeatherLoadTask;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        List<PredictionDataPoint> predictionData = new ArrayList<PredictionDataPoint>();
        predictionData.add(new PredictionDataPoint(0,18));
        predictionData.add(new PredictionDataPoint(1,23));
        predictionData.add(new PredictionDataPoint(2,23));
        predictionData.add(new PredictionDataPoint(3,23));
        predictionData.add(new PredictionDataPoint(4,18));

        predictionData.get(0).setTargetTemperature(20);
        predictionData.get(0).setEnergyConsumption(100);

        predictionData.get(1).setTargetTemperature(20);
        predictionData.get(1).setEnergyConsumption(100);

        predictionData.get(2).setTargetTemperature(20);
        predictionData.get(2).setEnergyConsumption(100);

        predictionData.get(3).setTargetTemperature(20);
        predictionData.get(3).setEnergyConsumption(100);

        predictionData.get(4).setTargetTemperature(20);
        predictionData.get(4).setEnergyConsumption(100);


        PredictionChart myChart = (PredictionChart) findViewById(R.id.myLineChart);
        myChart.setPredictionData(predictionData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Settings settings = Settings.Instance(this);
        if (!settings.allSettingsAvailable()) {
            DialogBuilder.buildFirstTimeDialog(this).show();
        } else {
            GlobalLocation weatherLocation = new GlobalLocation(settings.getLocationString());
            (new WeatherLoad()).execute(weatherLocation);
        }
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_settings:
                this.openSettings();
                return true;
            default:
                return false;
        }
    }

    public void openSettings() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        this.startActivity(settingsIntent);
    }

    private void loadForecast() {
        List<WeatherForecast> weatherForecast = Database.Instance(this).getCurrentWeatherForecast();
        Log.d("Weather forecast", Integer.toString(weatherForecast.size()));
        for (WeatherForecast element : weatherForecast) {
            Log.d("Weather forecast", element.toString());
        }
    }

    private class WeatherLoad extends WeatherLoadTask {

        private ProgressDialog progressDialog;

        public WeatherLoad() {
            super(MainActivity.this);
        }

        @Override
        protected void onPreExecute() {
            this.progressDialog = ProgressDialog.show(this.context, "Loading weather forecast", "Please wait ...", true, false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void v) {
            this.progressDialog.dismiss();
            MainActivity.this.loadForecast();
            super.onPostExecute(v);
        }
    }

}
