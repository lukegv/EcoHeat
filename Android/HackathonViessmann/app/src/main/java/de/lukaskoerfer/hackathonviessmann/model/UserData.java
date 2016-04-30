package de.lukaskoerfer.hackathonviessmann.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.w3c.dom.Element;

/**
 * Created by Koerfer on 30.04.2016.
 */
public class UserData {

    private String Username;

    private float Const_Prop;
    private float Const_Deriv;
    private String LocationString;
    private int HouseId;

    private UserData() {
    }

    public UserData(Element xml_element) {
        if (xml_element.hasAttribute("name")) {
            this.Username = xml_element.getAttribute("name");
            this.HouseId = Integer.parseInt(xml_element.getAttribute("houseid"));
            this.Const_Prop = Float.parseFloat(xml_element.getAttribute("const_prop"));
            this.Const_Deriv = Float.parseFloat(xml_element.getAttribute("const_deriv"));
            this.LocationString = xml_element.getAttribute("location");
        } else {
            this.Username = "";
        }
    }

    public void Save(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pref_username", this.Username);
        editor.putFloat("pref_const_prop", this.Const_Prop);
        editor.putFloat("pref_const_deriv", this.Const_Deriv);
        editor.putString("pref_location_string", this.LocationString);
        editor.putInt("pref_house_id", this.HouseId);
        editor.commit();
    }

    public static UserData FromSettings(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        UserData data = new UserData();
        data.Username = preferences.getString("pref_username", "");
        data.Const_Prop = preferences.getFloat("pref_const_prop", 0);
        data.Const_Deriv = preferences.getFloat("pref_const_deriv", 0);
        data.LocationString = preferences.getString("pref_location_string", "");
        data.HouseId = preferences.getInt("pref_house_id", 0);
        return data;
    }

    public static boolean IsUserLoggedIn(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.contains("pref_username");
    }

    public static boolean IsValid(UserData userData) {
        return userData != null && userData.Username != "";
    }

    public String getUsername() {
        return Username;
    }

    public float getConst_Prop() {
        return Const_Prop;
    }

    public float getConst_Deriv() {
        return Const_Deriv;
    }

    public String getLocationString() {
        return LocationString;
    }

    public int getHouseId() {
        return HouseId;
    }
}
