package com.halfplatepoha.telemprompter.screens.helpscreen;

import android.os.Bundle;

import com.halfplatepoha.telemprompter.base.BaseActivity;
import com.halfplatepoha.telemprompter.R;

import javax.inject.Inject;

public class HelpActivity extends BaseActivity implements HelpView {

    @Inject
    HelpPresenter presenter;

    @Override
    public boolean isGoogleApiClientNeeded() {
        return false;
    }

    @Override
    public boolean enableHomeAsUp() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setupToolbar();

        presenter.onCreate();
    }
}
