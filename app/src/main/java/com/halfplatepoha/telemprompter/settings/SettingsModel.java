package com.halfplatepoha.telemprompter.settings;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import javax.inject.Inject;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public class SettingsModel {

    private static final String PREF_SPEED = "pref_speed";

    public SharedPreferences preferences;

    @Inject
    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void updateSpeed(int speed) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_SPEED, speed);
        editor.apply();
    }

    public int getSpeed() {
        return preferences.getInt(PREF_SPEED, 0);
    }
}
