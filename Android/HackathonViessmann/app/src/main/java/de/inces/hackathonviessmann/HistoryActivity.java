package de.inces.hackathonviessmann;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import de.inces.hackathonviessmann.data.HistoryLoadTask;
import de.inces.hackathonviessmann.model.UserData;

public class HistoryActivity extends AppCompatActivity {

    private RadarChart radarConsumption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_history);
        this.radarConsumption = (RadarChart) this.findViewById(R.id.radarConsumption);
        this.radarConsumption.setDescription("");
        this.radarConsumption.setRotationEnabled(false);
        this.radarConsumption.setTouchEnabled(false);
        this.radarConsumption.getLegend().setEnabled(false);
        this.radarConsumption.getXAxis().setDrawLabels(true);
        this.radarConsumption.getYAxis().setDrawLabels(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        (new HistoryLoad()).execute(UserData.FromSettings(this).getHouseId());
    }

    private void buildChart(RadarData data) {
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.format("%.2f", value) + " kWh";
            }
        });

        this.radarConsumption.setData(data);
        this.radarConsumption.invalidate();
    }

    private class HistoryLoad extends HistoryLoadTask {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            this.progressDialog = ProgressDialog.show(HistoryActivity.this, "Loading consumption history", "Please wait ...", true, false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(RadarData data) {
            this.progressDialog.dismiss();
            HistoryActivity.this.buildChart(data);
            super.onPostExecute(data);
        }
    }

}
