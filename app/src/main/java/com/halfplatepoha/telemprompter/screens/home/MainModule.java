package com.halfplatepoha.telemprompter.screens.home;

import dagger.Module;
import dagger.Provides;

/**
 * Created by surajkumarsau on 10/02/17.
 */

@Module
public class MainModule {

    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    public MainView provideMainView() {
        return this.view;
    }

    @Provides
    public MainPresenter provideMainPresenter(MainView view) {
        return new MainPresenterImpl(view);
    }
}
