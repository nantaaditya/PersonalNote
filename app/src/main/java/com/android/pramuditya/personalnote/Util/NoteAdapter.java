package com.android.pramuditya.personalnote.Util;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pramuditya.personalnote.R;
import com.android.pramuditya.personalnote.db.DBHelper;
import com.android.pramuditya.personalnote.fragment.FragmentCreateNote;
import com.android.pramuditya.personalnote.fragment.FragmentListNote;
import com.android.pramuditya.personalnote.fragment.IEdit;
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
    private FragmentManager fragmentManager;
    //interface
    FragmentListNote iEdit;

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
//        holder.tvTag.setText(note.getTag().getName());
        holder.tvTitle.setText(note.getTitle());
        holder.tvNote.setText(note.getNote());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView tvTag, tvTitle, tvShare, tvDelete, tvNote, tvEdit;
        public int position;

        private DBHelper dbHelper;



        private ArrayList<Note> notesall = new ArrayList<>();
        private FragmentManager fragmentManager;
        private FragmentCreateNote fragmentCreateNote;


        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.tvTag = (TextView) itemView.findViewById(R.id.tv_tag);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvShare = (TextView) itemView.findViewById(R.id.tv_share);
            this.tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            this.tvNote = (TextView) itemView.findViewById(R.id.tv_note);
            this.tvEdit = (TextView) itemView.findViewById(R.id.tv_edit);



            this.tvDelete.setOnClickListener(this);
            this.tvShare.setOnClickListener(this);
            this.tvEdit.setOnClickListener(this);


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

                    }
                    catch (Exception ex) {

                    }
                    break;
                    case  R.id.tv_edit:
                        try
                        {
                            this.dbHelper = new DBHelper(context);
                            try {
                                this.notesall.clear();
                                this.notesall.addAll(this.dbHelper.getAll(Note.class));
                            }catch (SQLException e){
                                Log.e("DB", "initializeData: ", e.getCause());
                            }

                            Note noteEdit = this.notesall.get(position);
                            String judul = noteEdit.getTitle();
                            String id = noteEdit.getId().toString();
                            String waktu = noteEdit.getReminder().toString();
                            String tag = noteEdit.getTag().toString();
                            String note = noteEdit.getNote();

//                            FragmentCreateNote FragmentCreate = new FragmentCreateNote();
//                            this.fragmentManager.beginTransaction()
//                                    .replace(R.id.fragment_wrapper,
//                                            FragmentCreate)
//                                    .commit();

//                            kirimKeCreateNote(id, judul, waktu, tag, note);
                            Toast toast = Toast.makeText(context, judul, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    catch (Exception ex){
                        Toast toast = Toast.makeText(context, "belum berhasil", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    break;
                //

            }

        }
    }
}
