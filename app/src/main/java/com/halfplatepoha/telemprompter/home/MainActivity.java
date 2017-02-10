package com.halfplatepoha.telemprompter.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.halfplatepoha.telemprompter.R;
import com.halfplatepoha.telemprompter.opennote.newnote.NewNoteActivity;
import com.halfplatepoha.telemprompter.settings.SettingsActivity;

import javax.inject.Inject;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final int REQUEST_ADD_NEW = 1;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.btnFab)
    public void onFabClicked() {
        presenter.onAddNewClicked();
    }

    @Override
    public void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void openAddNew() {
        startActivityForResult(new Intent(this, NewNoteActivity.class), REQUEST_ADD_NEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ADD_NEW:{

            }
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
