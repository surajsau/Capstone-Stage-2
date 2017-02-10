package com.halfplatepoha.telemprompter.opennote.addnewnote;

import com.halfplatepoha.telemprompter.BasePresenter;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public interface AddNewNotePresenter extends BasePresenter {
    void onSaveClicked(String title, String text);
}
