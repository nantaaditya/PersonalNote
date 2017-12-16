package com.android.pramuditya.personalnote;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pramuditya.personalnote.model.Note;
import com.mikepenz.iconics.view.IconicsButton;

/**
 * Created by User on 07/12/2017.
 */

public class AlarmActivity extends AppCompatActivity {

    private TextView tvNote;
    private TextView tvTitle;
    private Button btStop;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        this.tvNote = (TextView) findViewById(R.id.tv_note);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        this.btStop = (IconicsButton) findViewById(R.id.b_stop);



        Intent intent = this.getIntent();
        Note note = (Note)intent.getSerializableExtra("note");
        this.tvTitle.setText(note.getTitle());
        this.tvNote.setText(note.getNote());


        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


        this.btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
            }
        });
    }

}
