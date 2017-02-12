package com.halfplatepoha.telemprompter.screens.settingsscreen;

import android.content.Intent;

import static junit.runner.Version.id;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public class SettingsPresenterImpl implements SettingsPresenter {

    private SettingsView view;

    public SettingsPresenterImpl(SettingsView view) {
        this.view = view;
    }

    @Override
    public void onSpeedPlusClicked(int speed) {
        if(++speed < 15) {
            view.displaySpeed(Integer.toString(speed));
            view.updateSpeed(speed);
        }
    }

    @Override
    public void onSpeedMinusClicked(int speed) {
        if(--speed > 0) {
            view.displaySpeed(Integer.toString(speed));
            view.updateSpeed(speed);
        }
    }

    @Override
    public void onHelpClicked() {
        view.openHelp();
    }

    @Override
    public void onTextPlusClicked(int textSize) {
        if(++textSize < 15) {
            view.displayText(Integer.toString(textSize));
            view.updateTextSize(textSize);
        }
    }

    @Override
    public void onTextMinusClicked(int textSize) {
        if(--textSize > 0) {
            view.displayText(Integer.toString(textSize));
            view.updateTextSize(textSize);
        }
    }

    @Override
    public void onCreate() {
        view.setToolbarTitle("Settings");
        view.initSettingsValues();
    }
}
