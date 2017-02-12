package com.halfplatepoha.telemprompter.screens.home;

import com.halfplatepoha.telemprompter.base.BasePresenter;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public interface MainPresenter extends BasePresenter {
    void onSettingsClicked();
    void onAddNewClicked();
    void onChooseExistingClicked();
    void onResultReceived(String text, int size);
    void onStartStopClicked();
}
