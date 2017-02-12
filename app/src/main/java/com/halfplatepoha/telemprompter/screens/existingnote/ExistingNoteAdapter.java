package com.halfplatepoha.telemprompter.screens.existingnote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.telemprompter.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.string.no;

/**
 * Created by surajkumarsau on 12/02/17.
 */

public class ExistingNoteAdapter extends RecyclerView.Adapter<ExistingNoteAdapter.ExistingNoteViewHolder> {

    private Context context;
    private ArrayList<ExistingNote> notes;

    public ExistingNoteAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ExistingNoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.row_files, parent, false);
        return new ExistingNoteViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ExistingNoteViewHolder holder, int position) {
        if(notes.get(position) != null) {
            holder.tvText.setText(notes.get(position).getFilePath());
            holder.tvTitle.setText(notes.get(position).getFileName());
        }
    }

    public void setNotes(ArrayList<ExistingNote> notes) {
        if(this.notes == null)
            this.notes = new ArrayList<>();
        this.notes.clear();
        this.notes.addAll(notes);
        notifyDataSetChanged();
    }

    public void setNote(ExistingNote note) {
        if(notes == null)
            notes = new ArrayList<>();
        notes.add(note);
        notifyItemInserted(notes.size() - 1);
    }

    @Override
    public int getItemCount() {
        if(notes != null)
            return notes.size();
        return 0;
    }

    public class ExistingNoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.tvTitle)
        TextView tvTitle;

        @Bind(R.id.tvText)
        TextView tvText;

        public ExistingNoteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
