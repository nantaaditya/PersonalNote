package com.android.pramuditya.personalnote.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.pramuditya.personalnote.R;
import com.android.pramuditya.personalnote.Util.NoteAdapter;
import com.android.pramuditya.personalnote.db.DBHelper;
import com.android.pramuditya.personalnote.model.Note;
import com.android.pramuditya.personalnote.model.Tag;
import com.mikepenz.iconics.view.IconicsTextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 25/11/2017.
 */

public class FragmentListNote extends Fragment{
    private View view;
    private ArrayList<Note> notes = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private RecyclerView rvListNote;
    //DB
    private DBHelper dbHelper;
    private IconicsTextView tvDelete;
    private View view2;

    //private TextView tvDelete;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = (View) inflater.inflate(R.layout.fragment_list_note, container, false);

        this.initializeElement();
        return this.view;
    }

    private void initializeElement() {
        this.noteAdapter = new NoteAdapter(getActivity(), this.notes);
        this.rvListNote = (RecyclerView) this.view.findViewById(R.id.rv_list_note);
        this.rvListNote.setAdapter(this.noteAdapter);
        this.rvListNote.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.dbHelper = new DBHelper(getActivity());
        this.initializeData();

    }



    private void initializeData() {
        try {
            this.notes.clear();
            this.notes.addAll(this.dbHelper.getAll(Note.class));

            if (notes.isEmpty()) {
                //delete after development
                Tag tag = new Tag("other");
                this.dbHelper.createOrUpdate(tag);
                Note note = new Note("Note 1", new Date(), tag, "Ini note ku okeh");
                this.dbHelper.createOrUpdate(note);
                Note note2 = new Note("Note 2", new Date(), tag, "Ini note ku okeh");
                this.dbHelper.createOrUpdate(note2);
                this.notes.addAll(this.dbHelper.getAll(Note.class));
            }
        } catch (SQLException e) {
            Log.e("DB", "initializeData: ", e.getCause());
        }
    }

}
