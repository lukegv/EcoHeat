package de.inces.hackathonviessmann.data;

import android.content.Context;
import android.os.AsyncTask;

import de.inces.hackathonviessmann.model.UserData;

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
