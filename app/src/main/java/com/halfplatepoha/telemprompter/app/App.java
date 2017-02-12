package com.halfplatepoha.telemprompter.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public SharedPreferences getSharedPreference() {
        return getSharedPreferences("telepromp_prefs", Context.MODE_PRIVATE);
    }

}
