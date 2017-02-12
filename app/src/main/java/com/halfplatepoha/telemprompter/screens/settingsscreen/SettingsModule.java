package com.halfplatepoha.telemprompter.screens.settingsscreen;

import dagger.Module;
import dagger.Provides;

/**
 * Created by surajkumarsau on 10/02/17.
 */

@Module
public class SettingsModule {

    private SettingsView view;

    public SettingsModule(SettingsView view) {
        this.view = view;
    }

    @Provides
    public SettingsView provideView() {
        return this.view;
    }

    @Provides
    public SettingsPresenter providePresenter(SettingsView view) {
        return new SettingsPresenterImpl(view);
    }
}
