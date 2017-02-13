package com.halfplatepoha.telemprompter.screens.settingsscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.halfplatepoha.telemprompter.R;
import com.halfplatepoha.telemprompter.base.BaseActivity;
import com.halfplatepoha.telemprompter.screens.helpscreen.HelpActivity;
import com.halfplatepoha.telemprompter.utils.IConstants;

public class SettingsActivity extends BaseActivity implements SettingsView,
        View.OnClickListener{

    private TextView tvScrollSpeed;

    private TextView tvTextSize;

    private Button btnSpeedPlus;

    private Button btnSpeedMinus;

    private Button btnTextPlus;

    private Button btnTextMinus;

    private TextView tvHelp;

    private SettingsPresenter presenter;

    private SharedPreferences preferences;

    @Override
    public boolean isGoogleApiClientNeeded() {
        return true;
    }

    @Override
    public boolean enableHomeAsUp() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tvScrollSpeed = (TextView) findViewById(R.id.tvScrollSpeed);
        tvTextSize = (TextView) findViewById(R.id.tvTextSize);
        btnSpeedMinus = (Button) findViewById(R.id.btnSpeedMinus);
        btnSpeedPlus = (Button) findViewById(R.id.btnSpeedPlus);
        btnTextMinus = (Button) findViewById(R.id.btnTextMinus);
        btnTextPlus = (Button) findViewById(R.id.btnTextPlus);
        tvHelp = (TextView) findViewById(R.id.tvHelp);

        btnSpeedMinus.setOnClickListener(this);
        btnSpeedPlus.setOnClickListener(this);
        btnTextPlus.setOnClickListener(this);
        btnTextMinus.setOnClickListener(this);
        tvHelp.setOnClickListener(this);

        presenter = new SettingsPresenterImpl(this);
        preferences = getApp().getSharedPreference();

        setupToolbar();

        presenter.onCreate();
    }

    void onBtnSpeedPlusClick() {
        presenter.onSpeedPlusClicked(preferences.getInt(IConstants.PREF_SPEED, 0));
    }

    void onBtnSpeedMinusClick() {
        presenter.onSpeedMinusClicked(preferences.getInt(IConstants.PREF_SPEED, 0));
    }

    void onBtnTextPlusClick() {
        presenter.onTextPlusClicked(preferences.getInt(IConstants.PREF_TEXT, 0));
    }

    void onBtnTextMinusClick() {
        presenter.onTextMinusClicked(preferences.getInt(IConstants.PREF_TEXT, 0));
    }

    void onHelpClicked() {
        presenter.onHelpClicked();
    }

    public void onDriveClicked() {
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle(getString(R.string.app_name))
                .build();

        Drive.DriveApi.getAppFolder(getApiClient())
                .createFolder(getApiClient(), changeSet)
                .setResultCallback(new ResultCallback<DriveFolder.DriveFolderResult>() {
                    @Override
                    public void onResult(@NonNull DriveFolder.DriveFolderResult driveFolderResult) {
                        if(!driveFolderResult.getStatus().isSuccess()) {
                            showToast(getString(R.string.error_in_creating_folder));
                        }
                        showToast(getString(R.string.folder_created) + driveFolderResult.getDriveFolder().getDriveId());
                        DriveId driveId = driveFolderResult.getDriveFolder().getDriveId();
                        storeDriveIdInPreferences(driveId.encodeToString());
                    }
                });
    }

    private void storeDriveIdInPreferences(String encodeString) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(IConstants.DRIVE_ID, encodeString);
        editor.apply();
    }

    @Override
    public void displaySpeed(String speed) {
        tvScrollSpeed.setText(speed);
    }

    @Override
    public void updateSpeed(int speed) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(IConstants.PREF_SPEED, speed);
        edit.apply();
    }

    @Override
    public void openHelp() {
        startActivity(new Intent(this, HelpActivity.class));
    }

    @Override
    public void displayText(String textSize) {
        tvTextSize.setText(textSize);
    }

    @Override
    public void updateTextSize(int textSize) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(IConstants.PREF_TEXT, textSize);
        edit.apply();
    }

    @Override
    public void initSettingsValues() {
        tvTextSize.setText(Integer.toString(preferences.getInt(IConstants.PREF_TEXT, 1)));
        tvScrollSpeed.setText(Integer.toString(preferences.getInt(IConstants.PREF_SPEED, 1)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTextMinus:{
                onBtnTextMinusClick();
            }
            break;

            case R.id.btnTextPlus:{
                onBtnTextPlusClick();
            }
            break;

            case R.id.btnSpeedMinus:{
                onBtnSpeedMinusClick();
            }
            break;

            case R.id.btnSpeedPlus:{
                onBtnSpeedPlusClick();
            }
            break;

            case R.id.tvHelp:{
                onHelpClicked();
            }
        }
    }
}
