package com.halfplatepoha.telemprompter.screens.existingnote;

import dagger.Module;
import dagger.Provides;

/**
 * Created by surajkumarsau on 11/02/17.
 */

@Module
public class ExistingNoteModule {

    private ExistingNoteView view;

    public ExistingNoteModule(ExistingNoteView view) {
        this.view = view;
    }

    @Provides
    public ExistingNoteView providesExistingNoteView() {
        return this.view;
    }

    @Provides
    public ExistingNotePresenter providesExistingNotePresenter(ExistingNoteView view) {
        return new ExistingNotePresenterImpl(view);
    }
}
