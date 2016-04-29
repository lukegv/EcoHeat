package de.lukaskoerfer.hackathonviessmann.model;

/**
 * Created by Koerfer on 29.04.2016.
 */
public class GlobalCoordinate {

    private double Latitude;
    private double Longitude;

    public GlobalCoordinate(double lat, double lon) {
        this.Latitude = lat;
        this.Longitude = lon;
    }

    public double getLatitude() {
        return this.Latitude;
    }

    public double getLongitude() {
        return this.Longitude;
    }
}
