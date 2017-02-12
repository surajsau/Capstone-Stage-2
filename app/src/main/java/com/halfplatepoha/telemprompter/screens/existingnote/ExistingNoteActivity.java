package com.halfplatepoha.telemprompter.screens.existingnote;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.widget.DataBufferAdapter;
import com.halfplatepoha.telemprompter.R;
import com.halfplatepoha.telemprompter.base.ApiClientAsyncTask;
import com.halfplatepoha.telemprompter.base.BaseActivity;
import com.halfplatepoha.telemprompter.db.DataProvider;
import com.halfplatepoha.telemprompter.utils.IConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExistingNoteActivity extends BaseActivity implements ExistingNoteView,
        AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ExistingDriveAdapter.DriveClickListener{

    @Inject
    ExistingNotePresenter presenter;

    @Inject
    SharedPreferences preferences;

    @Bind(R.id.lvFiles)
    ListView lvFiles;

    @Bind(R.id.tvListEmpty)
    TextView tvListEmpty;

    ExistingDriveAdapter dataBufferAdapter;

    private static final int LOADER_ID = 1;

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
        setContentView(R.layout.activity_existing_note);
        ButterKnife.bind(this);

        DaggerExistingNoteComponent.builder()
                .appComponent(getApp().getComponent())
                .existingNoteModule(new ExistingNoteModule(this))
                .build()
                .inject(this);

        setupToolbar();
        presenter.onCreate();

        dataBufferAdapter = new ExistingDriveAdapter(this, this);

        setupListView();
    }

    private void setupListView() {
//        lvFiles.setAdapter(dataBufferAdapter);
        lvFiles.setAdapter(new ExistingCursorAdapter(this));
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        lvFiles.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tvText = (TextView)view.findViewById(R.id.tvText);
        presenter.onItemClick(tvText.getText().toString());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DataProvider.Contract.FileInfoColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ((ExistingCursorAdapter) lvFiles.getAdapter()).swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((ExistingCursorAdapter) lvFiles.getAdapter()).swapCursor(null);
    }

//    @OnClick(R.id.btnDrive)
    public void onDriveButtonClick() {
        Drive.DriveApi.getAppFolder(getApiClient())
            .listChildren(getApiClient()).setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
            @Override
            public void onResult(@NonNull DriveApi.MetadataBufferResult metadataBufferResult) {
                if(!metadataBufferResult.getStatus().isSuccess()) {
                    showToast("Problem retrieving data");
                    return;
                }

                dataBufferAdapter.clear();
                dataBufferAdapter.append(metadataBufferResult.getMetadataBuffer());
                showToast("Done!");
            }
        });
    }

    @Override
    public void onItemClicked(String text) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(IConstants.RESULT_FILE_PATH, text);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onDriveClick(Metadata metadata) {
        Log.e("Id", metadata.getDriveId().toString());
        Drive.DriveApi.getFile(getApiClient(), metadata.getDriveId())
                .open(getApiClient(), DriveFile.MODE_READ_ONLY, null)
                .setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
                    @Override
                    public void onResult(@NonNull DriveApi.DriveContentsResult result) {
                        if(!result.getStatus().isSuccess()) {
                            showToast("Error getting content");
                        }

                        DriveContents contents = result.getDriveContents();
                        new ReadContentTask(ExistingNoteActivity.this, contents).execute();
                    }
                });
    }

    private class ReadContentTask extends ApiClientAsyncTask<Void, Void, String> {

        private DriveContents contents;

        public ReadContentTask(Context context, DriveContents content) {
            super(context);
            this.contents = content;
        }

        @Override
        protected String doInBackgroundConnected(Void... params) {
            String result = "";
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(contents.getInputStream()));
                StringBuffer builder = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null)
                    builder.append(line);

                result = builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            contents.discard(getGoogleApiClient());
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
//            contents.commit(getApiClient(), null)
//                    .setResultCallback(new ResultCallback<com.google.android.gms.common.api.Status>() {
//                        @Override
//                        public void onResult(@NonNull com.google.android.gms.common.api.Status status) {
//                            if(status.isSuccess()) {
//                                showToast("Read success");
//                            } else {
//                                showToast("Read failure");
//                            }
//                        }
//                    });

            Intent result = new Intent();
            result.putExtra(IConstants.RESULT_TEXT, s);
            setResult(RESULT_OK, result);
            finish();
        }
    }
}
