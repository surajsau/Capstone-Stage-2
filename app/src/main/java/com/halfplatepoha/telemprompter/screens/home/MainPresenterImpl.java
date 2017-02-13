package com.halfplatepoha.telemprompter.screens.home;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView view;

    private StartStopState currentState;

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

    @Override
    public void onChooseExistingClicked() {
        view.openChooseExisting();
    }

    @Override
    public void onResultReceived(String text, int size) {
        currentState = StartStopState.START;
        view.updateText(text, size);
        view.dismissFabSheet();
        view.startScroll();
    }

    @Override
    public void onStartStopClicked() {
        view.updateStartStopButtonText(currentState.getText());
        if(StartStopState.START.getText().equalsIgnoreCase(currentState.getText())) {
            view.stopScroll();
            currentState = StartStopState.STOP;
        } else {
            view.startScroll();
            currentState = StartStopState.START;
        }
    }

    @Override
    public void onCreate() {
        currentState = StartStopState.STOP;
    }

    private enum StartStopState {
        START("Start"), STOP("Stop");

        private String text;

        StartStopState(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
