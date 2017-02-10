package com.halfplatepoha.telemprompter.settings;

import dagger.Component;

/**
 * Created by surajkumarsau on 10/02/17.
 */

@Component(modules = SettingsModule.class)
public interface SettingsComponent {
    void inject(SettingsActivity activity);
}
