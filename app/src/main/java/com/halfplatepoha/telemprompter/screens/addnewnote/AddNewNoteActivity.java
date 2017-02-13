package com.halfplatepoha.telemprompter.screens.addnewnote;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.MetadataChangeSet;
import com.halfplatepoha.telemprompter.R;
import com.halfplatepoha.telemprompter.base.ApiClientAsyncTask;
import com.halfplatepoha.telemprompter.base.BaseActivity;
import com.halfplatepoha.telemprompter.db.DataProvider;
import com.halfplatepoha.telemprompter.utils.IConstants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Locale;

public class AddNewNoteActivity extends BaseActivity implements AddNewNoteView,
        View.OnClickListener{

    private EditText etTitle;

    private EditText etText;

    private Button btnStartMic;

    private Button btnAddNewNote;

    private AddNewNotePresenter presenter;

    private static final int REQUEST_SPEECH = 1;

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
        setContentView(R.layout.activity_add_new_note);

        etText = (EditText) findViewById(R.id.etText);
        etTitle = (EditText) findViewById(R.id.etTitle);
        btnAddNewNote = (Button) findViewById(R.id.btnSaveNote);
        btnStartMic = (Button) findViewById(R.id.btnStartMic);

        btnAddNewNote.setOnClickListener(this);
        btnStartMic.setOnClickListener(this);

        presenter = new AddNewNotePresenterImpl(this);

        setupToolbar();
        presenter.onCreate();
    }

    @Override
    public void showEmptyTitleWarning() {
        showToast(getString(R.string.title_empty_warning));
    }

    @Override
    public void showEmptyTextWarning() {
        showToast(getString(R.string.text_empty_warning));
    }

    @Override
    public void setResultAndFinish(final String title, final String text) {
        File file = new File(getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()), title + ".txt");
        new SaveFileTask(file).execute(text);

        Drive.DriveApi.newDriveContents(getApiClient())
                .setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
                    @Override
                    public void onResult(@NonNull DriveApi.DriveContentsResult result) {
                        if (!result.getStatus().isSuccess()) {
                            showToast(getString(R.string.error_creating_file_content));
                            return;
                        }

                        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                .setTitle(title)
                                .setMimeType("text/plain")
                                .setStarred(true).build();

                        Drive.DriveApi.getAppFolder(getApiClient())
                                .createFile(getApiClient(), changeSet, result.getDriveContents())
                                .setResultCallback(new ResultCallback<DriveFolder.DriveFileResult>() {
                                    @Override
                                    public void onResult(@NonNull DriveFolder.DriveFileResult result) {
                                        if (!result.getStatus().isSuccess()) {
                                            showToast(getString(R.string.error_creating_file));
                                            return;
                                        }
                                        showToast(getString(R.string.created_file) + result.getDriveFile().getDriveId());
                                        Log.e("create file", result.getDriveFile().getDriveId().encodeToString() + " " + result.getDriveFile().getDriveId().getResourceId());

                                        DriveFile file = result.getDriveFile().getDriveId().asDriveFile();
                                        file.open(getApiClient(), DriveFile.MODE_WRITE_ONLY, null)
                                                .setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
                                                    @Override
                                                    public void onResult(@NonNull DriveApi.DriveContentsResult result) {
                                                        if(!result.getStatus().isSuccess()) {
                                                            showToast(getString(R.string.error_opening_file));
                                                        }

                                                        DriveContents driveContents = result.getDriveContents();
                                                        new EditContentsAsyncTask(AddNewNoteActivity.this, driveContents)
                                                                .execute(text);
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    @Override
    public void onStartMic() {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Start");

        try {
            startActivityForResult(speechIntent, REQUEST_SPEECH);
        } catch (Exception e) {
            e.printStackTrace();
            showToast(getString(R.string.speed_not_supported));
        }
    }

    public void onSaveNote() {
        presenter.onSaveClicked(etTitle.getText().toString(), etText.getText().toString());
    }

    public void onStartMicClicked() {
        presenter.onStartMic();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveNote:
                onSaveNote();
                break;

            case R.id.btnStartMic:
                onStartMicClicked();
                break;
        }
    }

    private class SaveFileTask extends AsyncTask<String, Void, String> {

        File file;

        public SaveFileTask(File file) {
            this.file = file;
        }

        @Override
        protected String doInBackground(String... params) {
            BufferedWriter b = null;
            try {
                b = new BufferedWriter(new FileWriter(file));
                b.write(params[0]);
                b.close();

                ContentValues fileContent = new ContentValues();
                fileContent.put(DataProvider.Contract.FileInfoColumns.FILE_NAME, file.getName());
                fileContent.put(DataProvider.Contract.FileInfoColumns.FILE_PATH, file.getCanonicalPath());
                getContentResolver().insert(DataProvider.Contract.FileInfoColumns.CONTENT_URI, fileContent);
//                Cursor c = getContentResolver().query(DataProvider.Contract.FileInfoColumns.CONTENT_URI, null, null, null, null);
//                Log.e("Cursor count", c.getCount() + "");
                Log.e("Content", getContentResolver().getType(DataProvider.Contract.FileInfoColumns.CONTENT_URI));
                return params[0];
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(IConstants.RESULT_TEXT, s);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SPEECH:{
                if(data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    StringBuilder sb = new StringBuilder(etText.getText().toString());
                    for(String s : result) {
                        sb.append(s);
                    }

                    etText.setText(sb.toString());
                }
            }
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class EditContentsAsyncTask extends ApiClientAsyncTask<String, Void, DriveContents> {

        private DriveContents content;

        public EditContentsAsyncTask(Context context, DriveContents content) {
            super(context);
            this.content = content;
        }

        @Override
        protected DriveContents doInBackgroundConnected(String... args) {
            try {
                ParcelFileDescriptor parcelFileDescriptor = content.getParcelFileDescriptor();
                FileOutputStream fo = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
                Writer writer = new OutputStreamWriter(fo);
                writer.write(args[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onPostExecute(DriveContents content) {
            content.commit(getApiClient(), null)
                    .setResultCallback(new ResultCallback<com.google.android.gms.common.api.Status>() {
                        @Override
                        public void onResult(@NonNull com.google.android.gms.common.api.Status status) {
                            if(status.isSuccess()) {
                                showToast(getString(R.string.file_written));
                            } else {
                                showToast(getString(R.string.file_writing_failed));
                            }
                        }
                    });
        }
    }
}
