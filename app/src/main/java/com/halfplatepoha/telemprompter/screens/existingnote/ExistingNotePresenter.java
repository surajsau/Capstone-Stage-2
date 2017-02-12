package com.halfplatepoha.telemprompter.screens.existingnote;

import android.database.Cursor;

import com.halfplatepoha.telemprompter.base.BasePresenter;

/**
 * Created by surajkumarsau on 11/02/17.
 */

public interface ExistingNotePresenter extends BasePresenter {
    void onItemClick(String text);
}
