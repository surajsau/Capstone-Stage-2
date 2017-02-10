package com.halfplatepoha.telemprompter.opennote.addnewnote;

import android.text.TextUtils;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public class AddNewNotePresenterImpl implements AddNewNotePresenter {

    private AddNewNoteView view;
    private AddNewNodeModel model;

    public AddNewNotePresenterImpl(AddNewNoteView view, AddNewNodeModel model) {
        this.view = view;
        this.model = model;
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
            model.addNote(title, text);
        }

    }

    @Override
    public void onCreate() {
        view.setToolbarTitle("Add New Note");
    }
}
