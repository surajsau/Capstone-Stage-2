package com.halfplatepoha.telemprompter.settings;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public class SettingsPresenterImpl implements SettingsPresenter {

    private SettingsView view;
    private SettingsModel model;

    public SettingsPresenterImpl(SettingsView view, SettingsModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onSpeedPlusClicked() {
        int speed = model.getSpeed();
        if(speed < 15)
            model.updateSpeed(speed++);
    }

    @Override
    public void onSpeedMinusClicked() {
        int speed = model.getSpeed();
        if(speed > 0)
            model.updateSpeed(speed--);
    }
}
