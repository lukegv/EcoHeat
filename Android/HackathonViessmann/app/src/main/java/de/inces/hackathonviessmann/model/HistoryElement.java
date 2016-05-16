package de.inces.hackathonviessmann.model;

import org.w3c.dom.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Koerfer on 30.04.2016.
 */
public class HistoryElement {

    private String DateTime;
    private float EnergyConsumption;

    public HistoryElement(Element xmlElement) {
        this.DateTime = xmlElement.getAttribute("timestamp");
        this.EnergyConsumption = Float.parseFloat(xmlElement.getAttribute("energy"));
    }

    public String getDateTime() {
        return this.DateTime;
    }

    public int getTimespan() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(this.DateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hod = calendar.get(Calendar.HOUR_OF_DAY);
        hod = (hod + 2 >= 24) ? (hod - 22) : (hod + 2);
        return (int)Math.floor((float)hod / 6f);
    }

    public float getEnergyConsumption() {
        return this.EnergyConsumption;
    }
}
