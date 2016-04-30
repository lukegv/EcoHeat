package de.lukaskoerfer.hackathonviessmann.ui;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import de.lukaskoerfer.hackathonviessmann.R;
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

        this.setPinchZoom(false);
        this.setDoubleTapToZoomEnabled(false);
        this.getAxisLeft().setDrawGridLines(false);
        this.getAxisRight().setDrawGridLines(false);
        this.getAxisRight().setEnabled(false);
        this.getXAxis().setDrawGridLines(false);
        this.getXAxis().setLabelsToSkip(10);
        this.getAxisLeft().setAxisMinValue(0);
        this.setTouchEnabled(false);
        this.setDescription("");
    }


    private void setColors(){
        lineOutsideTemperature.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineOutsideTemperature.setDrawValues(false);
        lineOutsideTemperature.setDrawCircles(false);
        lineOutsideTemperature.setDrawCubic(true);
        lineOutsideTemperature.setLineWidth(4);
        lineOutsideTemperature.setColor(Color.argb(255,245,35,27));


        lineTargetTemperature.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineTargetTemperature.setDrawValues(false);
        lineTargetTemperature.setDrawCircles(false);
        lineTargetTemperature.setLineWidth(4);
        lineTargetTemperature.setColor(Color.BLACK);



        lineEnergyConsumption.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineEnergyConsumption.setDrawValues(false);
        lineEnergyConsumption.setColor(Color.argb(125,245,35,27));
        lineEnergyConsumption.setFillColor(Color.argb(125,245,35,27));
        lineEnergyConsumption.setDrawCircles(false);
        lineEnergyConsumption.setDrawFilled(true);
        lineEnergyConsumption.setLineWidth(0);

        return;
    }

    public void setPredictionData(List<PredictionDataPoint> predictionData){

        outsideTemperatures.clear();
        targetTemperatures.clear();
        energyComsumptions.clear();
        timeStamps.clear();

        Log.d("PChart","setPRedData");
        Integer index = 0;

        for(PredictionDataPoint point: predictionData) {
            timeStamps.add(String.valueOf( (int)point.getTime()/60/60) + "h");
            targetTemperatures.add(new Entry(point.getTargetTemperature(),index));
            energyComsumptions.add(new Entry(point.getEnergyConsumption(),index));
            outsideTemperatures.add(new Entry(point.getOutsideTemperature(),index));
            index++;
        }

        lineOutsideTemperature = new LineDataSet(outsideTemperatures, "Temperature in °C");

        lineTargetTemperature = new LineDataSet(targetTemperatures, "Target in °C");

        lineEnergyConsumption = new LineDataSet(energyComsumptions, "Energy Consumption");

        setColors();

        dataSets.clear();
        dataSets.add(lineEnergyConsumption);
        dataSets.add(lineOutsideTemperature);
        dataSets.add(lineTargetTemperature);

        LineData data = new LineData(timeStamps, dataSets);
        this.setData(data);
        this.invalidate();

        return;
    }
}
