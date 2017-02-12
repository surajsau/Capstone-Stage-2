package com.halfplatepoha.telemprompter.base;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.halfplatepoha.telemprompter.app.App;
import com.halfplatepoha.telemprompter.R;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient apiClient;

    private boolean isGoogleApiClientNeeded;

    private static final int REQUEST_DRIVE_RESOLUTION = 0;
    private static final int REQUEST_RESOLVE_CONNECTION = 1;

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public GoogleApiClient getApiClient() {
        return apiClient;
    }

    public abstract boolean isGoogleApiClientNeeded();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiClient = new GoogleApiClient.Builder(this)
                .addScope(Drive.SCOPE_FILE)
                .addScope(Drive.SCOPE_APPFOLDER)
                .addApi(Drive.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isGoogleApiClientNeeded()) {
            apiClient.connect();
        }
    }

    @Override
    public void setToolbarTitle(String title) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public App getApp() {
        return (App)getApplication();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("client", "connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("client", "suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("client", "failed");
        if(connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_CONNECTION);
            } catch (IntentSender.SendIntentException e) {
                showToast("Unable to resolve");
            }
        } else {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, REQUEST_DRIVE_RESOLUTION).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_RESOLVE_CONNECTION:{
                if(resultCode == RESULT_OK)
                    apiClient.connect();
            }
            break;
        }
    }
}
