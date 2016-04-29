package de.lukaskoerfer.hackathonviessmann.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Date;

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


    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public double getMinTemperature() {
        return MinTemperature;
    }

    public double getMaxTemperature() {
        return MaxTemperature;
    }
}
