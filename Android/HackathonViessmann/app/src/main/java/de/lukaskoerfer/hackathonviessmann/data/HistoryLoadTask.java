package de.lukaskoerfer.hackathonviessmann.data;

import android.graphics.Color;
import android.os.AsyncTask;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.lukaskoerfer.hackathonviessmann.model.HistoryElement;

/**
 * Created by Koerfer on 30.04.2016.
 */
public class HistoryLoadTask extends AsyncTask<Integer, Void, RadarData> {

    private static String[] Timespans = new String[] { "Night", "Morning", "Noon", "Evening" };

    @Override
    protected RadarData doInBackground(Integer... params) {
        List<HistoryElement> history = CloudConnection.GetHistory(params[0]);

        float[] totalConsumption = new float[this.Timespans.length];
        for (HistoryElement element : history) {
            totalConsumption[element.getTimespan()] = totalConsumption[element.getTimespan()] + element.getEnergyConsumption() * 20;
        }

        List<String> xVals = new ArrayList<>(Arrays.asList(this.Timespans));

        List<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < totalConsumption.length; i++) {
            yVals.add(new Entry(totalConsumption[i], i));
        }

        RadarDataSet dataSet = new RadarDataSet(yVals, "Energy consumption");
        dataSet.setColor(Color.argb(255, 245, 35, 27));
        dataSet.setLineWidth(6f);
        ArrayList<IRadarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        RadarData data = new RadarData(xVals, dataSets);
        data.setValueTextSize(12f);

        return data;
    }
}
