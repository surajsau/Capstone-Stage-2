package com.halfplatepoha.telemprompter.opennote.addnewnote;

import android.os.Bundle;
import android.widget.EditText;

import com.halfplatepoha.telemprompter.BaseActivity;
import com.halfplatepoha.telemprompter.R;
import com.halfplatepoha.telemprompter.opennote.DaggerNewNoteComponent;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewNoteActivity extends BaseActivity implements AddNewNoteView {

    @Bind(R.id.etTitle)
    EditText etTitle;

    @Bind(R.id.etText)
    EditText etText;

    @Inject
    AddNewNotePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        ButterKnife.bind(this);

        DaggerNewNoteComponent.builder()
                .addNewNoteModule(new AddNewNoteModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showEmptyTitleWarning() {

    }

    @Override
    public void showEmptyTextWarning() {

    }

    @OnClick(R.id.btnSaveNote)
    public void onSaveNote() {
        presenter.onSaveClicked(etTitle.getText().toString(), etText.getText().toString());
    }

}
