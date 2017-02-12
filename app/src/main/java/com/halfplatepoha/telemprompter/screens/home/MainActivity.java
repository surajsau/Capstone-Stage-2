package com.halfplatepoha.telemprompter.screens.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.halfplatepoha.telemprompter.BuildConfig;
import com.halfplatepoha.telemprompter.base.BaseActivity;
import com.halfplatepoha.telemprompter.ui.Fab;
import com.halfplatepoha.telemprompter.utils.IConstants;
import com.halfplatepoha.telemprompter.R;
import com.halfplatepoha.telemprompter.screens.addnewnote.AddNewNoteActivity;
import com.halfplatepoha.telemprompter.screens.existingnote.ExistingNoteActivity;
import com.halfplatepoha.telemprompter.screens.settingsscreen.SettingsActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends BaseActivity implements MainView, View.OnClickListener {

    private static final int REQUEST_ADD_NEW = 1;
    private static final int REQUEST_CHOOSE_EXISTING = 2;

    MainPresenter presenter;

    SharedPreferences preferences;

    Fab btnFab;

    View sheetView;

    View dimOverlay;

    TextView tvText;

    ScrollView scroll;

    AdView ads;

    TextView tvAddNew;

    TextView tvChooseExisting;

    TextView tvSettings;

    Button btnStartStop;

    MaterialSheetFab<Fab> materialSheetFab;

    private Handler timeHandler;
    private ScrollRunnable scrollRunnable;

    @Override
    public boolean isGoogleApiClientNeeded() {
        return false;
    }

    @Override
    public boolean enableHomeAsUp() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFab = (Fab) findViewById(R.id.btnFab);
        ads = (AdView) findViewById(R.id.adView);
        btnStartStop = (Button) findViewById(R.id.btnStartStop);
        scroll = (ScrollView) findViewById(R.id.scroll);
        tvText = (TextView) findViewById(R.id.tvText);
        dimOverlay = findViewById(R.id.overlay);
        sheetView = findViewById(R.id.fab_sheet);

        tvAddNew = (TextView) findViewById(R.id.fabAddNew);
        tvChooseExisting = (TextView) findViewById(R.id.fabChooseExisting);
        tvSettings = (TextView) findViewById(R.id.fabSettings);

        tvAddNew.setOnClickListener(this);
        tvSettings.setOnClickListener(this);
        tvChooseExisting.setOnClickListener(this);
        btnStartStop.setOnClickListener(this);

        presenter = new MainPresenterImpl(this);
        preferences = getApp().getSharedPreference();

        materialSheetFab = new MaterialSheetFab<>(btnFab, sheetView, dimOverlay,
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
                ContextCompat.getColor(this, R.color.colorPrimaryDark));

        timeHandler = new Handler();
        scrollRunnable = new ScrollRunnable();

        if(BuildConfig.ADS) {
            MobileAds.initialize(getApplicationContext(), getString(R.string.ad_unit_id));
            AdRequest request = new AdRequest.Builder().build();
            ads.loadAd(request);
            ads.setVisibility(View.VISIBLE);
        }

        getDataFromBundle();
    }

    private void getDataFromBundle() {
        String filePath = getIntent().getStringExtra(IConstants.RESULT_FILE_PATH);
        if(!TextUtils.isEmpty(filePath)) {
            new GetContentTask().execute(filePath);
        }
    }

    public void onAddNewClicked() {
        presenter.onAddNewClicked();
    }

    public void onChooseExistingClicked() {
        presenter.onChooseExistingClicked();
    }

    public void onSettingsClicked() {
        presenter.onSettingsClicked();
    }

    public void onStartStopClicked() {
        presenter.onStartStopClicked();
    }

    @Override
    public void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void openAddNew() {
        startActivityForResult(new Intent(this, AddNewNoteActivity.class), REQUEST_ADD_NEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ADD_NEW:{
                if(data != null) {
                    String text = data.getStringExtra(IConstants.RESULT_TEXT);
                    presenter.onResultReceived(text, preferences.getInt(IConstants.PREF_SPEED, 0));
                }
            }
            break;

            case REQUEST_CHOOSE_EXISTING:{
                if(data != null) {
                    String filePath = data.getStringExtra(IConstants.RESULT_FILE_PATH);
                    new GetContentTask().execute(filePath);

//                    String text = data.getStringExtra(IConstants.RESULT_TEXT);
//                    tvText.setText(text);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void openChooseExisting() {
        startActivityForResult(new Intent(this, ExistingNoteActivity.class), REQUEST_CHOOSE_EXISTING);
    }

    @Override
    public void updateText(String text, int size) {
        tvText.setText(text);
    }

    @Override
    public void dismissFabSheet() {
        if(materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
            materialSheetFab.showFab();
        }
    }

    @Override
    public void openFabSheet() {
        materialSheetFab.showSheet();
    }

    @Override
    public void startScroll() {
        int speed = preferences.getInt(IConstants.PREF_SPEED, 0);
        int textSize = preferences.getInt(IConstants.PREF_TEXT, 0);
        tvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24*(1 + (float)(textSize/15)));

        scrollRunnable.setDelay((long)(100*(2 - (float)(speed/15))));
        timeHandler.postDelayed(scrollRunnable, 0);
    }

    @Override
    public void stopScroll() {
        timeHandler.removeCallbacks(scrollRunnable);
    }

    @Override
    public void updateStartStopButtonText(String text) {
        btnStartStop.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddNew:{
                onAddNewClicked();
                break;
            }

            case R.id.fabChooseExisting:{
                onChooseExistingClicked();
                break;
            }

            case R.id.fabSettings:{
                onSettingsClicked();
                break;
            }

            case R.id.btnStartStop:{
                onStartStopClicked();
                break;
            }
        }
    }

    private class ScrollRunnable implements Runnable {

        private long delay;

        public void setDelay(long delay) {
            this.delay = delay;
        }

        @Override
        public void run() {
            scroll.smoothScrollBy(0, 5);
            timeHandler.postDelayed(this, delay);
        }
    }

    private class GetContentTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            File file = new File(params[0]);
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String temp;
                StringBuilder s = new StringBuilder("");
                while ((temp = br.readLine()) != null) s.append("\n").append(temp);
                String result = s.toString().trim();
                if (result.charAt(0) > 122 | result.charAt(0) < 48)
                    return result.substring(1);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            presenter.onResultReceived(s, preferences.getInt(IConstants.PREF_SPEED, 0));
        }
    }

}
