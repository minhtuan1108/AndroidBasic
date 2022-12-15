package com.example.multinotes;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class PlayAlarmMusic extends Service {

    MediaPlayer mediaPlayer;
    Integer id;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String statusValue = intent.getExtras().getString("status");
        Integer idNote = intent.getExtras().getInt("idNote");
        Log.i("> APP: ", "Status Value in Play Alarm Music: " + statusValue);
        Log.i("> APP: ", "Note Object Name in Play Alarm Music: " + idNote);

        if(statusValue.equals("on")){
            id = 1;
        }else{
            if(statusValue.equals("off"))
                id = 0;
        }

        if(id == 1){
            mediaPlayer = MediaPlayer.create(this, R.raw.ringtonealarm);

//            Intent i = new Intent(this, MainActivity.class);
//            i.putExtra("idNote", idNote);
//            startActivity(i);

            mediaPlayer.start();
            id = 0;
        }else{
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        return START_NOT_STICKY;
    }
}
