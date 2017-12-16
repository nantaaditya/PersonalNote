package com.android.pramuditya.personalnote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.pramuditya.personalnote.model.Note;

/**
 * Created by dnadh on 12/12/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){

        Note note = null;
        if(intent.getSerializableExtra("note") != null) {
            note = (Note)intent.getSerializableExtra("note");

        }


        intent = new Intent(context, AlarmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("note", note);
        context.startActivity(intent);

    }
}
