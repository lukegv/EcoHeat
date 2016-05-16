package de.inces.hackathonviessmann.model;

import org.w3c.dom.Element;

/**
 * Created by Koerfer on 29.04.2016.
 */
public class WeatherForecast {

    private String StartTime;
    private String EndTime;

    private double MinTemperature;
    private double MaxTemperature;

    public WeatherForecast(Element forecastElement) {
        this.StartTime = forecastElement.getAttribute("from");
        this.EndTime = forecastElement.getAttribute("to");
        Element temperatureElement = (Element) forecastElement.getElementsByTagName("temperature").item(0);
        this.MinTemperature = Double.parseDouble(temperatureElement.getAttribute("min"));
        this.MaxTemperature = Double.parseDouble(temperatureElement.getAttribute("max"));
    }

    public WeatherForecast(String startTime, String endTime, double minTemperature, double maxTemperature) {
        this.StartTime = startTime;
        this.EndTime = endTime;
        this.MinTemperature = minTemperature;
        this.MaxTemperature = maxTemperature;
    }

    public String getStartTime() {
        return this.StartTime;
    }

    public String getEndTime() {
        return this.EndTime;
    }

    public double getMinTemperature() {
        return this.MinTemperature;
    }

    public double getMaxTemperature() {
        return this.MaxTemperature;
    }

    public double getAvgTemperature() {
        return (this.MaxTemperature + this.MinTemperature) / 2;
    }

    @Override
    public String toString() {
        return this.StartTime + " - " + this.EndTime + " : " + Double.toString(this.getAvgTemperature());
    }
}
