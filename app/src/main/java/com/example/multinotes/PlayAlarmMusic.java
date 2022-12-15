package com.example.multinotes;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Date;

public class PlayAlarmMusic extends Service {

    MediaPlayer mediaPlayer;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onCreate() {
        super.onCreate();
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My Awesome App")
                .setContentText("Doing some work...")
                .setContentIntent(pendingIntent).build();

        startForeground(1337, notification);
    }

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
            mediaPlayer.start();
            id = 0;
        }else{
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        }
        return START_NOT_STICKY;
    }
}
