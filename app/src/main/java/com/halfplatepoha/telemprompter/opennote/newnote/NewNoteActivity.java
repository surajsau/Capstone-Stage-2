package com.halfplatepoha.telemprompter.opennote.newnote;

import android.content.Intent;
import android.os.Bundle;

import com.halfplatepoha.telemprompter.BaseActivity;
import com.halfplatepoha.telemprompter.R;
import com.halfplatepoha.telemprompter.opennote.addnewnote.AddNewNoteActivity;

public class NewNoteActivity extends BaseActivity implements NewNoteView {

    private static final int REQUEST_ADD_NEW_NOTE = 1;
    private static final int REQUEST_CHOOSE_EXISTING_NOTE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
    }

    @Override
    public void openAddNewNote() {
        startActivityForResult(new Intent(this, AddNewNoteActivity.class), REQUEST_ADD_NEW_NOTE);
    }

    @Override
    public void openChooseExistingNote() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ADD_NEW_NOTE:{

            }
            break;

            case REQUEST_CHOOSE_EXISTING_NOTE:{

            }
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
