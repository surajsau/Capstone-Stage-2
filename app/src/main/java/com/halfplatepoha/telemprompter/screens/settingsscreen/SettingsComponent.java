package com.halfplatepoha.telemprompter.screens.settingsscreen;

import com.halfplatepoha.telemprompter.app.AppComponent;
import com.halfplatepoha.telemprompter.screens.helpscreen.HelpActivity;
import com.halfplatepoha.telemprompter.screens.helpscreen.HelpModule;
import com.halfplatepoha.telemprompter.screens.settingsscreen.SettingsActivity;
import com.halfplatepoha.telemprompter.screens.settingsscreen.SettingsModule;

import dagger.Component;

/**
 * Created by surajkumarsau on 10/02/17.
 */

@Component(dependencies = AppComponent.class, modules = {SettingsModule.class})
public interface SettingsComponent {
    void inject(SettingsActivity activity);
}
