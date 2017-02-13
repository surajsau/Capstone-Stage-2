package com.halfplatepoha.telemprompter.screens.existingnote;

/**
 * Created by surajkumarsau on 11/02/17.
 */

public class ExistingNotePresenterImpl implements ExistingNotePresenter {

    private ExistingNoteView view;

    public ExistingNotePresenterImpl(ExistingNoteView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        view.setToolbarTitle("Choose Existing");
    }

    @Override
    public void onItemClick(String text) {
        view.onItemClicked(text);
    }

}
