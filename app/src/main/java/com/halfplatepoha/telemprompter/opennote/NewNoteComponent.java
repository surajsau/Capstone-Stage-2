package com.halfplatepoha.telemprompter.opennote;

import com.halfplatepoha.telemprompter.opennote.addnewnote.AddNewNoteActivity;
import com.halfplatepoha.telemprompter.opennote.addnewnote.AddNewNoteModule;

import dagger.Component;

/**
 * Created by surajkumarsau on 10/02/17.
 */

@Component(modules = {AddNewNoteModule.class})
public interface NewNoteComponent {
    void inject(AddNewNoteActivity activity);
}
