package com.halfplatepoha.telemprompter.screens.addnewnote;

import android.text.TextUtils;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public class AddNewNotePresenterImpl implements AddNewNotePresenter {

    private AddNewNoteView view;

    public AddNewNotePresenterImpl(AddNewNoteView view) {
        this.view = view;
    }

    @Override
    public void onSaveClicked(String title, String text) {
        boolean isTitleEmpty = TextUtils.isEmpty(title);
        boolean isTextEmpty = TextUtils.isEmpty(text);

        if(isTitleEmpty || isTextEmpty) {
            if(isTitleEmpty)
                view.showEmptyTitleWarning();
            if(isTextEmpty)
                view.showEmptyTextWarning();
        } else {
            view.setResultAndFinish(title, text);
        }

    }

    @Override
    public void onStartMic() {
        view.onStartMic();
    }

    @Override
    public void onCreate() {
    }
}
