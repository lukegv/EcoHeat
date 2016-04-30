package de.lukaskoerfer.hackathonviessmann;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.text.NumberFormat;
import java.util.List;

import de.lukaskoerfer.hackathonviessmann.data.UserdataLoadTask;
import de.lukaskoerfer.hackathonviessmann.model.HeatingPattern;
import de.lukaskoerfer.hackathonviessmann.model.LinearForecastInterpolation;
import de.lukaskoerfer.hackathonviessmann.model.PredictionCalculator;
import de.lukaskoerfer.hackathonviessmann.model.PredictionDataPoint;
import de.lukaskoerfer.hackathonviessmann.model.UserPreferences;
import de.lukaskoerfer.hackathonviessmann.model.WeatherForecast;
import de.lukaskoerfer.hackathonviessmann.ui.PredictionChart;
import de.lukaskoerfer.hackathonviessmann.db.Database;
import de.lukaskoerfer.hackathonviessmann.model.UserData;
import de.lukaskoerfer.hackathonviessmann.data.WeatherLoadTask;

public class MainActivity extends AppCompatActivity {

    PredictionChart myChart;
    List<PredictionDataPoint> predictionData;
    MultiStateToggleButton stateToggle;
    TextView textEnergy;
    TextView textCosts;
    TextView textSavings;
    Float standardEnergyConsumption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        stateToggle = (MultiStateToggleButton) findViewById(R.id.stateToggle);
        stateToggle.setValue(0);
        stateToggle.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                updateChart();
            }
        });

        myChart = (PredictionChart) findViewById(R.id.myLineChart);
        myChart.setScaleEnabled(false);
        myChart.setDrawMarkerViews(false);

        textEnergy = (TextView) findViewById(R.id.textEnergy);
        textCosts = (TextView) findViewById(R.id.textCosts);
        textSavings = (TextView) findViewById(R.id.textSavings);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!UserData.IsUserLoggedIn(this)) {
            this.switchAccount(false);
        } else {
            this.handleUser(false);
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
            case R.id.item_switch_user:
                this.switchAccount(true);
                return true;
            case R.id.item_preferences:
                Intent preferencesIntent = new Intent(this, PreferenceActivity.class);
                this.startActivity(preferencesIntent);
                return true;
            case R.id.item_history:
                Intent historyIntent = new Intent(this, HistoryActivity.class);
                this.startActivity(historyIntent);
                return true;
            case R.id.item_rank:
                Intent rankIntent = new Intent(this, RankActivity.class);
                this.startActivity(rankIntent);
                return true;
            default:
                return false;
        }
    }

    public void switchAccount(final boolean cancelable) {
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
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (cancelable) {
                    dialog.dismiss();
                } else {
                    MainActivity.this.finish();
                }
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private void handleUser(boolean changed) {
        UserData userData = UserData.FromSettings(this);
        if (changed || !Database.Instance(this).isWeatherForecastUpToDate()) {
            (new WeatherLoad()).execute(userData.getLocation());
        } else {
            this.handleWeather();
        }
    }


    private void handleWeather() {
        List<WeatherForecast> weatherForecast = Database.Instance(this).getCurrentWeatherForecast();
        predictionData = LinearForecastInterpolation.getPredictionPoints(weatherForecast);
        stateToggle.setValue(0);
    }

    private void updateChart(){
        switch (stateToggle.getValue()){
            case 0:HeatingPattern.setNormalMode(predictionData,this);
                    break;
            case 1:HeatingPattern.setEcoMode(predictionData,this);
                    break;
            case 2:HeatingPattern.setEcoPlusMode(predictionData,this);
                    break;
        }
        Log.d("WERT",String.valueOf(stateToggle.getValue()));
        UserData userData= UserData.FromSettings(this);

        float energyConsumption;
        energyConsumption = PredictionCalculator.predictEnergyConsumption(predictionData,userData.getConst_Prop(),userData.getConst_Deriv(),5);

        myChart.setPredictionData(predictionData);
        if (stateToggle.getValue()==0) standardEnergyConsumption = energyConsumption;
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        float energyPrice = UserPreferences.FromSettings(this).getEnergyPrice();

        textEnergy.setText("Energy Consumption: " + formatter.format(energyConsumption) + " kWh");
        textCosts.setText("Costs: " + formatter.format(energyConsumption * energyPrice) + " €");
        String savingsString = (standardEnergyConsumption == energyConsumption) ? "-,--" : formatter.format((standardEnergyConsumption - energyConsumption) * energyPrice);
        textSavings.setText("Savings: " + savingsString + " €");
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
                MainActivity.this.handleUser(true);
            } else {
                Toast.makeText(MainActivity.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                MainActivity.this.switchAccount(UserData.IsUserLoggedIn(this.context));
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
            this.progressDialog = ProgressDialog.show(this.context, "Loading weather data", "Please wait ...", true, false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void v) {
            this.progressDialog.dismiss();
            MainActivity.this.handleWeather();
            super.onPostExecute(v);
        }

    }

}
