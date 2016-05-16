package de.inces.hackathonviessmann.model;

import android.content.Context;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Eugen on 30.04.2016.
 */
public class HeatingPattern {

    public static void setEcoMode(List<PredictionDataPoint> predictionData, Context context){
        UserPreferences prefs = UserPreferences.FromSettings(context);
        int n = predictionData.size();
        float time = System.currentTimeMillis();
        float help = time/(1000*60*60*24);
        float help2 = help%7;
        float temp;
        for (int i=0;i<n;i++) {
            temp = (predictionData.get(i).getTime()+time/1000) %(60*60*24);
            Calendar calender=Calendar.getInstance();
            calender.add(Calendar.SECOND,(int) predictionData.get(i).getTime());
            if (calender.getTime().getDay() == 6 || calender.getTime().getDay() == 7)
            {
                if ( temp <= prefs.getWeekendDayStart()*60 || temp >= prefs.getWeekendDayEnd()*60) {
                    predictionData.get(i).setTargetTemperature(prefs.getNightTemperature());
                }
                else{
                    predictionData.get(i).setTargetTemperature(prefs.getDayTemperature());
                }
            }
            else{
                if ( temp <= prefs.getWeekDayStart()*60 || temp >= prefs.getWeekDayEnd()*60) {
                    predictionData.get(i).setTargetTemperature(prefs.getNightTemperature());
                }
                else{
                    predictionData.get(i).setTargetTemperature(prefs.getDayTemperature());
                }
            }

        }
    }

    public static void setEcoPlusMode(List<PredictionDataPoint> predictionData, Context context){
        UserPreferences prefs = UserPreferences.FromSettings(context);
        int n = predictionData.size();
        float time = System.currentTimeMillis();
        float help = time/(1000*60*60*24);
        float help2 = help%7;
        float temp;
        for (int i=0;i<n;i++) {
            temp = (predictionData.get(i).getTime()+time/1000) %(60*60*24);
            Calendar calender=Calendar.getInstance();
            calender.add(Calendar.SECOND,(int) predictionData.get(i).getTime());
            if (calender.getTime().getDay() == 6 || calender.getTime().getDay() == 7)
            {
                if ( temp <= prefs.getWeekendDayStart()*60 || temp >= prefs.getWeekendDayEnd()*60) {
                    predictionData.get(i).setTargetTemperature(prefs.getNightTemperature()-1);
                }
                else{
                    predictionData.get(i).setTargetTemperature(prefs.getDayTemperature()-1);
                }
            }
            else{
                if ( temp <= prefs.getWeekDayStart()*60 || temp >= prefs.getWeekDayEnd()*60) {
                    predictionData.get(i).setTargetTemperature(prefs.getNightTemperature()-1);
                }
                else{
                    predictionData.get(i).setTargetTemperature(prefs.getDayTemperature()-1);
                }
            }

        }
    }

    public static void setNormalMode(List<PredictionDataPoint> predictionData, Context context){
        UserPreferences prefs = UserPreferences.FromSettings(context);
        int n = predictionData.size();
        for (int i=0;i<n;i++) { predictionData.get(i).setTargetTemperature(prefs.getDayTemperature());}

    }
}
