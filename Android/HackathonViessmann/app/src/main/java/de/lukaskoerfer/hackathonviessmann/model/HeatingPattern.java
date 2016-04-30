package de.lukaskoerfer.hackathonviessmann.model;

import android.content.Context;

import java.util.List;

/**
 * Created by Eugen on 30.04.2016.
 */
public class HeatingPattern {

    public void setEcoMode(List<PredictionDataPoint> predictionData, Context context){
        UserPreferences prefs = UserPreferences.FromSettings(context);
        int n = predictionData.size();
        float time = 0;
        float temp;
        for (int i=0;i<n;i++) {
            temp = predictionData.get(i).getTime()+time %(60*60*24);
            if ( temp <= prefs.getWeekDayStart() && temp >= prefs.getWeekDayEnd()) {
                predictionData.get(i).setTargetTemperature(prefs.getNightTemperature());
            }
            else{
                predictionData.get(i).setTargetTemperature(prefs.getDayTemperature());
            }
        }
    }

    public void setNormalMode(List<PredictionDataPoint> predictionData, Context context){
        UserPreferences prefs = UserPreferences.FromSettings(context);
        int n = predictionData.size();
        for (int i=0;i<n;i++) { predictionData.get(i).setTargetTemperature(prefs.getDayTemperature());}

    }
}
