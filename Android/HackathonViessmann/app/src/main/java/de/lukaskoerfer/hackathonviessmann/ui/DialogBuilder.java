package de.lukaskoerfer.hackathonviessmann.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;

import de.lukaskoerfer.hackathonviessmann.MainActivity;

/**
 * Created by Koerfer on 29.04.2016.
 */
public class DialogBuilder {

    public static AlertDialog buildFirstTimeDialog(final MainActivity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("No settings found!");
        builder.setMessage("It seems like you are using the app for the first time. Please edit the settings to get started!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.openSettings();
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }

}
