package de.lukaskoerfer.hackathonviessmann.model;

/**
 * Created by Koerfer on 29.04.2016.
 */
public class GlobalLocation {

    private double Latitude;
    private double Longitude;

    public GlobalLocation(double lat, double lon) {
        this.Latitude = lat;
        this.Longitude = lon;
    }

    public GlobalLocation(String locationString) {
        String[] coordinates = locationString.split("#");
        this.Latitude = Double.parseDouble(coordinates[0]);
        this.Longitude = Double.parseDouble(coordinates[1]);
    }

    public double getLatitude() {
        return this.Latitude;
    }

    public double getLongitude() {
        return this.Longitude;
    }
}
