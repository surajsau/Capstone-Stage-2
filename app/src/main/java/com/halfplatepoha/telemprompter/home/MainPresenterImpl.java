package com.halfplatepoha.telemprompter.home;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView view;

    public MainPresenterImpl(MainView view) {
        this.view = view;
    }

    @Override
    public void onSettingsClicked() {
        view.openSettings();
    }

    @Override
    public void onAddNewClicked() {
        view.openAddNew();
    }
}
