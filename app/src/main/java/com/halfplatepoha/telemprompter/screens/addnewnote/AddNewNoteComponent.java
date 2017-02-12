package com.halfplatepoha.telemprompter.screens.addnewnote;

import com.halfplatepoha.telemprompter.screens.addnewnote.AddNewNoteActivity;
import com.halfplatepoha.telemprompter.screens.addnewnote.AddNewNoteModule;

import dagger.Component;

/**
 * Created by surajkumarsau on 10/02/17.
 */

@Component(modules = {AddNewNoteModule.class})
public interface AddNewNoteComponent {
    void inject(AddNewNoteActivity activity);
}
