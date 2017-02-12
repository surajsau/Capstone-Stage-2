package com.halfplatepoha.telemprompter.screens.addnewnote;

import com.halfplatepoha.telemprompter.base.BasePresenter;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public interface AddNewNotePresenter extends BasePresenter {
    void onSaveClicked(String title, String text);

    void onStartMic();
}
