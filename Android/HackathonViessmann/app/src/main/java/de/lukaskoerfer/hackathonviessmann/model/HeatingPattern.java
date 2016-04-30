package de.lukaskoerfer.hackathonviessmann.model;

import java.util.List;

/**
 * Created by Eugen on 30.04.2016.
 */
public class HeatingPattern {

    public void setNormalMode(List<PredictionDataPoint> predictionData){
        int n = predictionData.size();
        float time = 0;
        float temp;
        for (int i=0;i<n;i++) {
            temp = predictionData.get(i).getTime()+time %(60*60*24);
            if ( temp <=  && temp >= ) {
                predictionData.get(i).setTargetTemperature();
            }
            else{
                predictionData.get(i).setTargetTemperature();
            }
        }
    }
}
