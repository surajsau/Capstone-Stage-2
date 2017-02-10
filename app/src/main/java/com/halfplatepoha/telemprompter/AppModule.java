package com.halfplatepoha.telemprompter;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by surajkumarsau on 10/02/17.
 */

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    public SharedPreferences provideSharedPreference() {
        return this.app.getSharedPreferences("telepromp_prefs", Context.MODE_PRIVATE);
    }
}
