package de.lukaskoerfer.hackathonviessmann;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import de.lukaskoerfer.hackathonviessmann.model.PredictionDataPoint;
import de.lukaskoerfer.hackathonviessmann.model.WeatherForecast;
import de.lukaskoerfer.hackathonviessmann.ui.PredictionChart;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_settings:
                this.startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return false;
        }
    }

}
