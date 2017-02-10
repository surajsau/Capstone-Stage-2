package com.halfplatepoha.telemprompter;

import android.content.SharedPreferences;

import dagger.Component;

/**
 * Created by surajkumarsau on 10/02/17.
 */

@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(App app);

    SharedPreferences preferences();
}
