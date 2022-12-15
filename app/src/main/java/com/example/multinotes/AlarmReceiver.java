package com.example.multinotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String statusValue = intent.getExtras().getString("status");
        Integer idNote = intent.getExtras().getInt("idNote");

        Log.i("> APP: ", "Status Value in AlarmReceiver: " + statusValue);
        Log.i("> APP: ", "Note Object Name in AlarmReceiver: " + idNote);


        Intent myIntent = new Intent(context, PlayAlarmMusic.class);
        myIntent.putExtra("status", "on");
        myIntent.putExtra("idNote", idNote);

        context.startService(myIntent);

    }
}
