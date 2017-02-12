package com.halfplatepoha.telemprompter.screens.existingnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.widget.DataBufferAdapter;
import com.halfplatepoha.telemprompter.R;

/**
 * Created by surajkumarsau on 12/02/17.
 */

public class ExistingDriveAdapter extends DataBufferAdapter<Metadata> {

    public ExistingDriveAdapter(Context context) {
        super(context, R.layout.row_files);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null ){
            view = LayoutInflater.from(getContext()).inflate(R.layout.row_files, viewGroup, false);
        }
        Metadata metadata = getItem(i);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(metadata.getTitle());
        return view;
    }
}
