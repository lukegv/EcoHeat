package de.inces.hackathonviessmann;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import de.inces.hackathonviessmann.model.UserPreferences;
import io.apptik.widget.MultiSlider;

public class PreferenceActivity extends AppCompatActivity {

    private TextView tvDayTemperature;
    private TextView tvNightTemperature;
    private TextView tvWeekTimes;
    private TextView tvWeekendTimes;
    private TextView tvEnergyPrice;

    private MultiSlider sliderDayTemperature;
    private MultiSlider sliderNightTemperature;
    private MultiSlider sliderWeekTimes;
    private MultiSlider sliderWeekendTimes;
    private MultiSlider sliderEnergyPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_preference);
        this.tvDayTemperature = (TextView) findViewById(R.id.tvDayTemperature);
        this.tvNightTemperature = (TextView) findViewById(R.id.tvNightTemperature);
        this.tvWeekTimes = (TextView) findViewById(R.id.tvWeekTimes);
        this.tvWeekendTimes = (TextView) findViewById(R.id.tvWeekendTimes);
        this.tvEnergyPrice = (TextView) findViewById(R.id.tvEnergyPrice);

        this.sliderDayTemperature = (MultiSlider) findViewById(R.id.sliderDayTemperature);
        this.sliderDayTemperature.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                String presentation = "Daytime temperature: " + Integer.toString(value) + "°C";
                tvDayTemperature.setText(presentation);
            }
        });
        this.sliderNightTemperature = (MultiSlider) findViewById(R.id.sliderNightTemperature);
        this.sliderNightTemperature.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                String presentation = "Nighttime temperature: " + Integer.toString(value) + "°C";
                tvNightTemperature.setText(presentation);
            }
        });
        this.sliderWeekTimes = (MultiSlider) findViewById(R.id.sliderWeekTimes);
        this.sliderWeekTimes.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                String startTimePres = TimePresentation(multiSlider.getThumb(0).getValue());
                String endTimePres = TimePresentation(multiSlider.getThumb(1).getValue());
                String presentation = "During the week daytime: \n" + startTimePres + " to " + endTimePres;
                tvWeekTimes.setText(presentation);
            }
        });
        this.sliderWeekendTimes = (MultiSlider) findViewById(R.id.sliderWeekendTimes);
        this.sliderWeekendTimes.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                String startTimePres = TimePresentation(multiSlider.getThumb(0).getValue());
                String endTimePres = TimePresentation(multiSlider.getThumb(1).getValue());
                String presentation = "On weekend daytime: \n" + startTimePres + " to " + endTimePres;
                tvWeekendTimes.setText(presentation);
            }
        });
        this.sliderEnergyPrice = (MultiSlider) findViewById(R.id.sliderEnergyPrice);
        this.sliderEnergyPrice.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                String presentation = "Energy Price: " + String.format("%.3f", ((float)value / 1000f)) + " € / kWh";
                tvEnergyPrice.setText(presentation);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserPreferences userPreferences = UserPreferences.FromSettings(this);
        this.sliderDayTemperature.getThumb(0).setValue(userPreferences.getDayTemperature());
        this.sliderNightTemperature.getThumb(0).setValue(userPreferences.getNightTemperature());
        this.sliderWeekTimes.getThumb(0).setValue(userPreferences.getWeekDayStart());
        this.sliderWeekTimes.getThumb(1).setValue(userPreferences.getWeekDayEnd());
        this.sliderWeekendTimes.getThumb(0).setValue(userPreferences.getWeekendDayStart());
        this.sliderWeekendTimes.getThumb(1).setValue(userPreferences.getWeekendDayEnd());
        this.sliderEnergyPrice.getThumb(0).setValue((int) (userPreferences.getEnergyPrice() * 1000));
    }

    @Override
    protected void onPause() {
        super.onPause();
        UserPreferences userPreferences = UserPreferences.FromSettings(this);
        userPreferences.setDayTemperature(this.sliderDayTemperature.getThumb(0).getValue());
        userPreferences.setNightTemperature(this.sliderNightTemperature.getThumb(0).getValue());
        userPreferences.setWeekDayStart(this.sliderWeekTimes.getThumb(0).getValue());
        userPreferences.setWeekDayEnd(this.sliderWeekTimes.getThumb(1).getValue());
        userPreferences.setWeekendDayStart(this.sliderWeekendTimes.getThumb(0).getValue());
        userPreferences.setWeekendDayEnd(this.sliderWeekendTimes.getThumb(1).getValue());
        userPreferences.setEnergyPrice((float)this.sliderEnergyPrice.getThumb(0).getValue() / 1000);
        userPreferences.Save(this);
    }

    private static String TimePresentation(int minutes) {
        int hours = (int) Math.floor(((double)minutes) / 60);
        int restMinutes = minutes % 60;
        String appendix = (hours >= 12) ? " p.m." : " a.m.";
        hours = (hours > 12) ? (hours - 12) : hours;
        hours = (hours == 0) ? (hours + 12) : hours;
        return Integer.toString(hours) + ":" + String.format("%02d", restMinutes) + appendix;
    }
}
