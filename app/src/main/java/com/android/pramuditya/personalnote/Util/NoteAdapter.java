package com.android.pramuditya.personalnote.Util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.pramuditya.personalnote.R;
import com.android.pramuditya.personalnote.db.DBHelper;
import com.android.pramuditya.personalnote.model.Note;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 25/11/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    public String title, pesan;
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
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTag, tvTitle, tvShare, tvDelete, tvNote;
        public int position;

        private DBHelper dbHelper;

        private ArrayList<Note> notesall = new ArrayList<>();

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.tvTag = (TextView) itemView.findViewById(R.id.tv_tag);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvShare = (TextView) itemView.findViewById(R.id.tv_share);
            this.tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            this.tvNote = (TextView) itemView.findViewById(R.id.tv_note);

            this.tvDelete.setOnClickListener(this);
            this.tvShare.setOnClickListener(this);

            //title = this.tvTitle.getText().toString();
            //pesan = this.tvNote.getText().toString();

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_delete:
                    this.dbHelper = new DBHelper(context);
                    try {
                        this.notesall.clear();
                        this.notesall.addAll(this.dbHelper.getAll(Note.class));
                    }catch (SQLException e){
                        Log.e("DB", "initializeData: ", e.getCause());
                    }

                    Note notedel = this.notesall.get(position);

                    try {
                        this.dbHelper.deleteById(Note.class, notedel.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    notes.remove(position);
                    notifyDataSetChanged();
                    break;
                //
                case R.id.tv_share:
                    try {

                        this.dbHelper = new DBHelper(context);
                        try {
                            this.notesall.clear();
                            this.notesall.addAll(this.dbHelper.getAll(Note.class));
                        }catch (SQLException e){
                            Log.e("DB", "initializeData: ", e.getCause());
                        }

                        Note noteshare = this.notesall.get(position);

                        String pe = noteshare.getTitle().toString();
                        String no = noteshare.getNote().toString();

                        Intent waIntent = new Intent(Intent.ACTION_SEND);
                        waIntent.setType("text/plain");

                        //waIntent.setPackage("com.whatsapp");

                        waIntent.putExtra(Intent.EXTRA_TEXT, "Title : " + pe + " Note : " + no );

                        context.startActivity(waIntent);

                    } catch (Exception ex) {
                        
                    }
                    break;
                //

            }

        }
    }
}
