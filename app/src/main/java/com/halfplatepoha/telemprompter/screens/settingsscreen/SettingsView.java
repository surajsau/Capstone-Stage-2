package com.halfplatepoha.telemprompter.screens.settingsscreen;

import com.halfplatepoha.telemprompter.base.BaseView;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public interface SettingsView extends BaseView {
    void displaySpeed(String speed);
    void updateSpeed(int speed);

    void openHelp();
}
