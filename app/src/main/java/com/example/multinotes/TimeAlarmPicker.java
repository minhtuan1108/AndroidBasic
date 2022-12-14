package com.example.multinotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextClock;
import android.widget.TimePicker;

import java.util.Timer;
import java.util.TimerTask;

public class TimeAlarmPicker extends AppCompatActivity {

    TimePicker alarmTime;
    TextClock currentTime;
//    static Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_alarm_picker);

        alarmTime = (TimePicker) findViewById(R.id.time_picker);
        currentTime = (TextClock) findViewById(R.id.text_time);

        try {
            RingtoneManager.setActualDefaultRingtoneUri(
                    getApplicationContext(), RingtoneManager.TYPE_RINGTONE,
                    Uri.parse("android.resource://" + getPackageName() + "/raw/ringtone_alarm.mp3"));

        }catch (Exception e){
            e.printStackTrace();
        }

        Ringtone mediaPlayer = RingtoneManager.getRingtone(this, Uri.parse("android.resource://" + getPackageName() + "/raw/ringtone_alarm.mp3"));

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(currentTime.getText().toString().equals(AlarmTime())){
                    Log.i("> APP: ", "Current Time: to play ringtone " + currentTime.getText().toString());
                    mediaPlayer.play();

                }else{
                    Log.i("> APP: ", "stop ring tone");
                    mediaPlayer.stop();
                }
            }
        }, 0, 1000);
    }

    public String AlarmTime(){
        Integer alarmHours = alarmTime.getCurrentHour();
        Integer alarmMinutes = alarmTime.getCurrentMinute();

        String stringAlarmTime;
        String stringAlarmMinutes = alarmMinutes.toString();

        if(alarmMinutes < 10){
            stringAlarmMinutes = "0" + alarmMinutes;
        }else stringAlarmMinutes = "" + alarmMinutes;

        stringAlarmTime = "0".concat(alarmHours.toString()).concat(":").concat(stringAlarmMinutes);

        Log.i("> APP: ", "AlarmTime: " + stringAlarmTime);
        Log.i("> APP: ", "Current time = Alarm Time: " + currentTime.getText().toString().equalsIgnoreCase(stringAlarmTime));

        return stringAlarmTime;

    }
}