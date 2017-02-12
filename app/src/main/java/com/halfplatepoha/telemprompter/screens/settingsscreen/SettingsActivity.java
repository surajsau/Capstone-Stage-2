package com.halfplatepoha.telemprompter.screens.settingsscreen;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.halfplatepoha.telemprompter.base.BaseActivity;
import com.halfplatepoha.telemprompter.utils.IConstants;
import com.halfplatepoha.telemprompter.R;
import com.halfplatepoha.telemprompter.screens.helpscreen.HelpActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static junit.runner.Version.id;

public class SettingsActivity extends BaseActivity implements SettingsView {

    @Bind(R.id.tvScrollSpeed)
    TextView tvScrollSpeed;

    @Inject
    SettingsPresenter presenter;

    @Inject
    SharedPreferences preferences;

    @Override
    public boolean isGoogleApiClientNeeded() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        DaggerSettingsComponent.builder()
                .appComponent(getApp().getComponent())
                .settingsModule(new SettingsModule(this))
                .build()
                .inject(this);

        setupToolbar();

        presenter.onCreate();
    }

    @OnClick(R.id.btnSpeedPlus)
    void onBtnSpeedPlusClick() {
        presenter.onSpeedPlusClicked(preferences.getInt(IConstants.PREF_SPEED, 0));
    }

    @OnClick(R.id.btnSpeedMinus)
    void onBtnSpeedMinusClick() {
        presenter.onSpeedMinusClicked(preferences.getInt(IConstants.PREF_SPEED, 0));
    }

    @OnClick(R.id.tvHelp)
    void onHelpClicked() {
        presenter.onHelpClicked();
    }

    @OnClick(R.id.btnDriveLogin)
    void onDriveClicked() {
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle("Teleprompter")
                .build();

        Drive.DriveApi.getAppFolder(getApiClient())
                .createFolder(getApiClient(), changeSet)
                .setResultCallback(new ResultCallback<DriveFolder.DriveFolderResult>() {
                    @Override
                    public void onResult(@NonNull DriveFolder.DriveFolderResult driveFolderResult) {
                        if(!driveFolderResult.getStatus().isSuccess()) {
                            showToast("Error in creating folder");
                        }
                        showToast("Folder created : " + driveFolderResult.getDriveFolder().getDriveId());
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

}
