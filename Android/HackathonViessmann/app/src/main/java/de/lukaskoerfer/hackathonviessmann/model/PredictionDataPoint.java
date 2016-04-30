package de.lukaskoerfer.hackathonviessmann.model;

import java.util.Calendar;

/**
 * Created by Philipp on 29.04.2016.
 */
public class PredictionDataPoint {
    private float time;
    private float targetTemperature;
    private float energyConsumption;
    private float outsideTemperature;

    public PredictionDataPoint(float time, float outsideTemperature){
        this.outsideTemperature = outsideTemperature;
        this.time = time;
        this.energyConsumption = 0;
        this.targetTemperature = 15;
    }

    public void setTargetTemperature(float targetTemperature){
        this.targetTemperature = targetTemperature;
    }

    public void setEnergyConsumption(float energyConsumption){
        this.energyConsumption = energyConsumption;
    }


    public float getTime(){return time;}
    public float getTargetTemperature(){return targetTemperature;}
    public float getEnergyConsumption(){return energyConsumption;}
    public float getOutsideTemperature(){return outsideTemperature;}
}
