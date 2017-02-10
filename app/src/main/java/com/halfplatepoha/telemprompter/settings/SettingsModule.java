package com.halfplatepoha.telemprompter.settings;

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
    public SettingsModel provideModel() {
        return new SettingsModel();
    }

    @Provides
    public SettingsPresenter providePresenter(SettingsView view, SettingsModel model) {
        return new SettingsPresenterImpl(view, model);
    }
}
