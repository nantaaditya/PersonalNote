package com.android.pramuditya.personalnote.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.pramuditya.personalnote.R;
import com.android.pramuditya.personalnote.Util.NoteAdapter;
import com.android.pramuditya.personalnote.Util.TagAdapter;
import com.android.pramuditya.personalnote.db.DBHelper;
import com.android.pramuditya.personalnote.model.Tag;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by User on 21/11/2017.
 */

public class FragmentCreateTag extends Fragment implements View.OnClickListener {
    private View view;
    private EditText etTag;
    private Button bSave;
    private ArrayList<Tag> tags = new ArrayList<>();
    private TagAdapter tagAdapter;
    private RecyclerView rvTags;
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view = (View) inflater.inflate(R.layout.fragment_create_tag, container, false);
        initializeElement();
        return this.view;
    }

    private void initializeElement() {
        this.tagAdapter = new TagAdapter(getActivity(), this.tags);
        this.rvTags = (RecyclerView) this.view.findViewById(R.id.rv_tags);
        this.rvTags.setAdapter(this.tagAdapter);
        this.rvTags.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.etTag = (EditText) this.view.findViewById(R.id.et_tag);
        this.bSave = (Button) this.view.findViewById(R.id.b_save);

        this.bSave.setOnClickListener(this);
        this.dbHelper = new DBHelper(getActivity());
        this.getData();
    }

    private void getData() {
        try {
            this.tags.clear();
            this.tags.addAll(dbHelper.getAll(Tag.class));
        } catch (SQLException e) {
            Log.e("DB", "get tags: ", e.getCause());
        }
    }

    @Override
    public void onClick(View view) {
        if (view == bSave) {
            try {
                String tagTitle = etTag.getText().toString();
                if (!tagTitle.isEmpty()) {
                    Tag tag = new Tag(tagTitle);
                    this.dbHelper.createOrUpdate(tag);
                    this.tagAdapter.notifyDataSetChanged();
                    this.getData();
                } else {
                    Toast.makeText(getActivity(), "Tag tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            } catch (SQLException e) {
                Toast.makeText(getActivity(), "Nama tag sudah dipakai", Toast.LENGTH_LONG).show();
            }
        }
    }
}
