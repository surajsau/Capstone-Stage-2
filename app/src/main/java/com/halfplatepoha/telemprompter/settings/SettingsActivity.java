package com.halfplatepoha.telemprompter.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.halfplatepoha.telemprompter.R;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity implements SettingsView {

    @Bind(R.id.tvScrollSpeed)
    TextView tvScrollSpeed;

    @Inject
    SettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @OnClick(R.id.btnSpeedPlus)
    void onBtnSpeedPlusClick() {
        presenter.onSpeedPlusClicked();
    }

    @OnClick(R.id.btnSpeedMinus)
    void onBtnSpeedMinusClick() {
        presenter.onSpeedMinusClicked();
    }

    @Override
    public void displaySpeed(String speed) {
        tvScrollSpeed.setText(speed);
    }
}
