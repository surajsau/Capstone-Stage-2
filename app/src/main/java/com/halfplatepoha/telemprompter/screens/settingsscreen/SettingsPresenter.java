package com.halfplatepoha.telemprompter.screens.settingsscreen;

import com.halfplatepoha.telemprompter.base.BasePresenter;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public interface SettingsPresenter extends BasePresenter {
    void onSpeedPlusClicked(int speed);
    void onSpeedMinusClicked(int speed);
    void onHelpClicked();

    void onTextPlusClicked(int textSize);
    void onTextMinusClicked(int textSize);
}
