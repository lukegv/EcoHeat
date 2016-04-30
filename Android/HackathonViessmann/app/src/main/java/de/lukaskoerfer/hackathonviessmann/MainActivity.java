package de.lukaskoerfer.hackathonviessmann;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

import de.lukaskoerfer.hackathonviessmann.data.UserdataLoadTask;
import de.lukaskoerfer.hackathonviessmann.model.LinearForecastInterpolation;
import de.lukaskoerfer.hackathonviessmann.model.PredictionCalculator;
import de.lukaskoerfer.hackathonviessmann.model.PredictionDataPoint;
import de.lukaskoerfer.hackathonviessmann.model.WeatherForecast;
import de.lukaskoerfer.hackathonviessmann.ui.PredictionChart;
import de.lukaskoerfer.hackathonviessmann.db.Database;
import de.lukaskoerfer.hackathonviessmann.model.UserData;
import de.lukaskoerfer.hackathonviessmann.model.WeatherForecast;
import de.lukaskoerfer.hackathonviessmann.data.WeatherLoadTask;

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
        if (!UserData.IsUserLoggedIn(this)) {
            this.switchAccount();
        } else {
            this.updateUser();
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
            case R.id.item_switch_account:
                this.switchAccount();
                return true;
            case R.id.item_preferences:
                Intent preferencesIntent = new Intent(this, PreferenceActivity.class);
                this.startActivity(preferencesIntent);
            default:
                return false;
        }
    }

    public void switchAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("EcoHeat login");
        View view = this.getLayoutInflater().inflate(R.layout.dialog_login, null);
        final EditText etUsername = (EditText) view.findViewById(R.id.etUsername);
        builder.setView(view);
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredUsername = etUsername.getText().toString();
                (new UserdataLoad()).execute(enteredUsername);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private void updateUser() {
        UserData userData = UserData.FromSettings(this);
        if (!Database.Instance(this).isWeatherForecastUpToDate(null, userData)) {

        }
    }

    private void updateWeather() {
        List<WeatherForecast> weatherForecast = Database.Instance(this).getCurrentWeatherForecast();
		Log.d("Weather forecast", Integer.toString(weatherForecast.size()));
        for (WeatherForecast element : weatherForecast) {
            Log.d("Weather forecast", element.toString());
            System.out.println(LinearForecastInterpolation.stringToSecond(element.getEndTime()));
        }
        List<PredictionDataPoint> predictionData = LinearForecastInterpolation.getPredictionPoints(weatherForecast);
        for(int i=0;i<predictionData.size();i++) {
            predictionData.get(i).setTargetTemperature(23);
            predictionData.get(i).setEnergyConsumption(15);
		}
		PredictionChart myChart = (PredictionChart) findViewById(R.id.myLineChart);
        myChart.setPredictionData(predictionData);
    }

    private class UserdataLoad extends UserdataLoadTask {

        private ProgressDialog progressDialog;

        public UserdataLoad() {
            super(MainActivity.this);
        }

        @Override
        protected void onPreExecute() {
            this.progressDialog = ProgressDialog.show(this.context, "Loading user data", "Please wait ...", true, false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(UserData userData) {
            this.progressDialog.dismiss();
            if (UserData.IsValid(userData)) {
                userData.Save(this.context);
                MainActivity.this.updateUser();
            } else {
                Toast.makeText(MainActivity.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                MainActivity.this.switchAccount();
            }
            super.onPostExecute(userData);
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
            MainActivity.this.updateWeather();
            super.onPostExecute(v);
        }
    }

}
