package com.halfplatepoha.telemprompter.screens.addnewnote;

import com.halfplatepoha.telemprompter.base.BaseView;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public interface AddNewNoteView extends BaseView {
    void showEmptyTitleWarning();
    void showEmptyTextWarning();

    void setResultAndFinish(String title, String text);

    void onStartMic();
}
