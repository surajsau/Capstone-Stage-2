package com.halfplatepoha.telemprompter.screens.addnewnote;

import dagger.Module;
import dagger.Provides;

/**
 * Created by surajkumarsau on 10/02/17.
 */

@Module
public class AddNewNoteModule {

    private AddNewNoteView view;

    public AddNewNoteModule(AddNewNoteView view) {
        this.view = view;
    }

    @Provides
    public AddNewNoteView providesAddNewNoteView() {
        return this.view;
    }

    @Provides
    public AddNewNotePresenter providesAddNewNotePresenter(AddNewNoteView view) {
        return new AddNewNotePresenterImpl(view);
    }
}
