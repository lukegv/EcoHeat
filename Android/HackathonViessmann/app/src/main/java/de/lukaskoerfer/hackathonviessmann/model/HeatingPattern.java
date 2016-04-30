package de.lukaskoerfer.hackathonviessmann.model;

import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Eugen on 30.04.2016.
 */
public class HeatingPattern {

    public static void setEcoMode(List<PredictionDataPoint> predictionData, Context context){
        UserPreferences prefs = UserPreferences.FromSettings(context);
        int n = predictionData.size();
        float time = System.currentTimeMillis();
        float temp;
        for (int i=0;i<n;i++) {
            temp = (predictionData.get(i).getTime()+time/1000) %(60*60*24);
            Log.d("HeatPAt",String.valueOf(prefs.getWeekDayStart()*60) + " end:" + String.valueOf(prefs.getWeekDayEnd()*60));
            if ( temp <= prefs.getWeekDayStart()*60 || temp >= prefs.getWeekDayEnd()*60) {
                predictionData.get(i).setTargetTemperature(prefs.getNightTemperature());
                Log.d("HeatPAt","night");
            }
            else{
                predictionData.get(i).setTargetTemperature(prefs.getDayTemperature());
                Log.d("HeatPAt","day");
            }
        }
    }

    public static void setEcoPlusMode(List<PredictionDataPoint> predictionData, Context context){
        UserPreferences prefs = UserPreferences.FromSettings(context);
        int n = predictionData.size();
        float time = System.currentTimeMillis();
        float temp;
        for (int i=0;i<n;i++) {
            temp = (predictionData.get(i).getTime()+time/1000) %(60*60*24);
            Log.d("HeatPAt",String.valueOf(prefs.getWeekDayStart()*60) + " end:" + String.valueOf(prefs.getWeekDayEnd()*60));
            if ( temp <= prefs.getWeekDayStart()*60 || temp >= prefs.getWeekDayEnd()*60) {
                predictionData.get(i).setTargetTemperature(prefs.getNightTemperature()-1f);
                Log.d("HeatPAt","night");
            }
            else{
                predictionData.get(i).setTargetTemperature(prefs.getDayTemperature()-1f);
                Log.d("HeatPAt","day");
            }
        }
    }

    public static void setNormalMode(List<PredictionDataPoint> predictionData, Context context){
        UserPreferences prefs = UserPreferences.FromSettings(context);
        int n = predictionData.size();
        for (int i=0;i<n;i++) { predictionData.get(i).setTargetTemperature(prefs.getDayTemperature());}

    }
}
