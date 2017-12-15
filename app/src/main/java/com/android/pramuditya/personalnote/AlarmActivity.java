package com.android.pramuditya.personalnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pramuditya.personalnote.model.Note;

/**
 * Created by User on 07/12/2017.
 */

public class AlarmActivity extends AppCompatActivity {

    private TextView tvNote;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.tvNote = (TextView) findViewById(R.id.tv_note);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        Intent intent = this.getIntent();
        Note note = (Note)intent.getSerializableExtra("note");
//        Toast.makeText(this, note.getTitle(), Toast.LENGTH_SHORT).show();

    }

}
