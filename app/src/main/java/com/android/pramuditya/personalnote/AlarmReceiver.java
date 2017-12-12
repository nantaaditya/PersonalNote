package com.android.pramuditya.personalnote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by dnadh on 12/12/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){
        Toast.makeText(context, "sukses",Toast.LENGTH_SHORT);
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
