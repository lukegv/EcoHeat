package de.lukaskoerfer.hackathonviessmann.ui;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import de.lukaskoerfer.hackathonviessmann.model.PredictionDataPoint;
import de.lukaskoerfer.hackathonviessmann.model.WeatherForecast;

/**
 * Created by Philipp on 29.04.2016.
 */
public class PredictionChart extends LineChart{

    private ArrayList<Entry> outsideTemperatures;
    private ArrayList<Entry> targetTemperatures;
    private ArrayList<Entry> energyComsumptions;
    private ArrayList<String> timeStamps;

    private LineDataSet lineOutsideTemperature;
    private LineDataSet lineTargetTemperature;
    private LineDataSet lineEnergyConsumption;

    private  ArrayList<ILineDataSet> dataSets;

    private  LineData data;

    public PredictionChart(android.content.Context context){
        super(context);
        initializeData();
    }

    public PredictionChart(android.content.Context context, android.util.AttributeSet attrs){
        super(context, attrs);
        initializeData();
    }

    public PredictionChart(android.content.Context context, android.util.AttributeSet attrs, int defStyle){
        super(context, attrs,defStyle);
        initializeData();
    }

    public void initializeData(){
        outsideTemperatures = new ArrayList();
        targetTemperatures = new ArrayList();
        energyComsumptions = new ArrayList();
        timeStamps = new ArrayList();


        dataSets = new ArrayList<ILineDataSet>();
    }

    private void setColors(){
        lineOutsideTemperature.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineOutsideTemperature.setDrawValues(false);
        lineOutsideTemperature.setColor(Color.RED);
        lineOutsideTemperature.setDrawCircles(false);

        lineTargetTemperature.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineTargetTemperature.setDrawValues(false);
        lineTargetTemperature.setDrawCircles(false);
        return;
    }

    public void setPredictionData(List<PredictionDataPoint> predictionData){

        outsideTemperatures.clear();
        targetTemperatures.clear();
        energyComsumptions.clear();
        timeStamps.clear();

        Integer index = 0;

        for(PredictionDataPoint point: predictionData) {
            timeStamps.add(String.valueOf( point.getTime()));
            targetTemperatures.add(new Entry(point.getTargetTemperature(),index));
            energyComsumptions.add(new Entry(point.getEnergyConsumption(),index));
            outsideTemperatures.add(new Entry(point.getOutsideTemperature(),index));
            index++;
        }

        lineOutsideTemperature = new LineDataSet(outsideTemperatures, "Temperatur in °C");

        lineTargetTemperature = new LineDataSet(targetTemperatures, "Target in °C");

        setColors();

        dataSets.clear();
        dataSets.add(lineOutsideTemperature);
        dataSets.add(lineTargetTemperature);

        LineData data = new LineData(timeStamps, dataSets);
        this.setData(data);
        this.invalidate();

        return;
    }
}
