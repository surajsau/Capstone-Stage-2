package com.halfplatepoha.telemprompter.screens.helpscreen;

import dagger.Module;
import dagger.Provides;

/**
 * Created by surajkumarsau on 11/02/17.
 */

@Module
public class HelpModule {

    private HelpView view;

    public HelpModule(HelpView view) {
        this.view = view;
    }

    @Provides
    public HelpView provideHelpView() {
        return this.view;
    }

    @Provides
    public HelpPresenter provideHelpPresenter(HelpView view) {
        return new HelpPresenterImpl(view);
    }
}
