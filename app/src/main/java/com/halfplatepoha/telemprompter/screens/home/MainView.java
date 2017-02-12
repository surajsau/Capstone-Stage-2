package com.halfplatepoha.telemprompter.screens.home;

import com.halfplatepoha.telemprompter.base.BaseView;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public interface MainView extends BaseView {
    void openSettings();
    void openAddNew();
    void openChooseExisting();
    void updateText(String text, int size);
    void dismissFabSheet();
    void openFabSheet();
    void startScroll();
    void stopScroll();
    void updateStartStopButtonText(String text);
}
