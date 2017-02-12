package com.halfplatepoha.telemprompter.screens.existingnote;

import com.halfplatepoha.telemprompter.app.AppComponent;

import dagger.Component;

/**
 * Created by surajkumarsau on 11/02/17.
 */

@Component(dependencies = AppComponent.class, modules = ExistingNoteModule.class)
public interface ExistingNoteComponent {
    void inject(ExistingNoteActivity activity);
}
