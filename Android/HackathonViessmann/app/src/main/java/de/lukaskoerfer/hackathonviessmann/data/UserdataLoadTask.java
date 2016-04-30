package de.lukaskoerfer.hackathonviessmann.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import de.lukaskoerfer.hackathonviessmann.model.UserData;

/**
 * Created by Koerfer on 30.04.2016.
 */
public class UserdataLoadTask extends AsyncTask<String, Void, UserData> {

    protected Context context;

    public UserdataLoadTask(Context c) {
        this.context = c;
    }

    @Override
    protected UserData doInBackground(String... username) {
        UserData userData = CloudConnection.GetUserData(username[0]);
        return userData;
    }
}
