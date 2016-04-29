package de.lukaskoerfer.hackathonviessmann;

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

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LineChart myChart = (LineChart) findViewById(R.id.myLineChart);
        myChart.setDescription("");
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("8:00");
        xVals.add("12:00");
        xVals.add("16:00");
        xVals.add("20:00");
        xVals.add("24:00");

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(8,0));
        yVals.add(new Entry(11,1));
        yVals.add(new Entry(16,2));
        yVals.add(new Entry(15,3));
        yVals.add(new Entry(12,4));

        LineDataSet lineData1 = new LineDataSet(yVals, "Temperatur in °C");
        lineData1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineData1.setDrawValues(false);
        lineData1.setColor(Color.RED);
        lineData1.setDrawCircles(false);


        ArrayList<Entry> target = new ArrayList<Entry>();
        target.add(new Entry(18,0));
        target.add(new Entry(23,1));
        target.add(new Entry(23,2));
        target.add(new Entry(23,3));
        target.add(new Entry(18,4));

        LineDataSet lineData2 = new LineDataSet(target, "Target in °C");
        lineData2.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineData2.setDrawValues(false);
        lineData2.setDrawCircles(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineData1);
        dataSets.add(lineData2);
        LineData data = new LineData(xVals, dataSets);
        myChart.setData(data);
        myChart.invalidate();

    }


}
