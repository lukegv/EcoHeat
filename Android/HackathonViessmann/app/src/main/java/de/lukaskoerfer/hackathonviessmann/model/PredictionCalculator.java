package de.lukaskoerfer.hackathonviessmann.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugen on 30.04.2016.
 */

/**This class host the function for calculation the predicted energy consumption
 * The computation is based on a physical model but the parameters are approximated by the constants a and b
 * */
public class PredictionCalculator {

    public void predictEnergyConsumption (List<PredictionDataPoint> predictionData, float a, float b ){
        int n =predictionData.size();
        float[] TemperatureDifference = new float[n];
        for (int i=0; i<n; i=i+1) { TemperatureDifference[i]=(predictionData.get(i).getTargetTemperature() - predictionData.get(i).getOutsideTemperature());}

        float[] deriv_TemperatureDifference = new float[n];
        deriv_TemperatureDifference[0] = 0;
        deriv_TemperatureDifference[n] = 0;
        for (int i=1; i<n-1; i=i+1) { deriv_TemperatureDifference[i]= (TemperatureDifference[i+1] - TemperatureDifference[i-1])/2;}

        for (int i=0; i<n; i=i+1) {
            predictionData.get(i).setEnergyConsumption(a*TemperatureDifference[i] + b*deriv_TemperatureDifference[i]);
        }
    }
}

