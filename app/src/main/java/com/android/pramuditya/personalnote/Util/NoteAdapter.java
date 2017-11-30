package com.android.pramuditya.personalnote.Util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.pramuditya.personalnote.R;
import com.android.pramuditya.personalnote.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 25/11/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> notes = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = this.inflater.inflate(R.layout.item_note, parent, false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = this.notes.get(position);
        holder.tvTag.setText(note.getTag().getName());
        holder.tvTitle.setText(note.getTitle());
        holder.tvNote.setText(note.getNote());
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTag, tvTitle, tvShare, tvDelete, tvNote;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.tvTag = (TextView) itemView.findViewById(R.id.tv_tag);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvShare = (TextView) itemView.findViewById(R.id.tv_share);
            this.tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            this.tvNote = (TextView) itemView.findViewById(R.id.tv_note);
        }
    }
}
