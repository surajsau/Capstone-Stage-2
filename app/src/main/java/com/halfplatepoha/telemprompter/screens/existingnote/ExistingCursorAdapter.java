package com.halfplatepoha.telemprompter.screens.existingnote;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.telemprompter.R;
import com.halfplatepoha.telemprompter.db.DataProvider;

/**
 * Created by surajkumarsau on 12/02/17.
 */

public class ExistingCursorAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public ExistingCursorAdapter(Context context) {
        super(context, null);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvText= (TextView) view.findViewById(R.id.tvText);

        tvText.setText(cursor.getString(cursor.getColumnIndex(DataProvider.Contract.FileInfoColumns.FILE_PATH)));
        tvTitle.setText(cursor.getString(cursor.getColumnIndex(DataProvider.Contract.FileInfoColumns.FILE_NAME)));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.row_files, parent, false);
    }
}
