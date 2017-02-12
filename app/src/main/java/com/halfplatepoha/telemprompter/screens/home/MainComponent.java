package com.halfplatepoha.telemprompter.screens.home;

import com.halfplatepoha.telemprompter.app.AppComponent;

import dagger.Component;

/**
 * Created by surajkumarsau on 10/02/17.
 */

@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
