package com.example.multinotes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimeAlarmPicker extends AppCompatActivity {

    TimePicker alarmTime;
    TextView textTime;
    Button btnHenGio, btnHuy, btnDungLai;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Integer idNote;
    Note note;
    NoteDAO noteDAO = new NoteDAO(new DatabaseConnector(this, "mutipleNote.sqlite",null, 1));
//    static Ringtone r;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("noteObject", note);
        startActivity(intent);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_alarm_picker);

        alarmTime = (TimePicker) findViewById(R.id.time_picker);
        textTime = (TextView) findViewById(R.id.text_time);
        btnHenGio = (Button) findViewById(R.id.set_alarm);
        btnDungLai = (Button) findViewById(R.id.stop_alarm);
        btnHuy = (Button) findViewById(R.id.cancel_alarm);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        final Intent intent = new Intent(TimeAlarmPicker.this, AlarmReceiver.class);
        note = (Note) getIntent().getSerializableExtra("noteObject");
        idNote = getIntent().getExtras().getInt("idNote");

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.S)
            @SuppressLint("UnspecifiedImmutableFlag")
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY, alarmTime.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTime.getCurrentMinute());

                Integer alarmHours = alarmTime.getCurrentHour();
                Integer alarmMinutes = alarmTime.getCurrentMinute();

                String stringAlarmTime = "";
                String stringAlarmMinutes = alarmMinutes.toString();


                if(alarmMinutes < 10){
                    stringAlarmMinutes = "0" + alarmMinutes;
                }else stringAlarmMinutes = "" + alarmMinutes;

                if(alarmHours > 12) {
                    alarmHours = alarmHours - 12;
                    stringAlarmTime = stringAlarmTime.concat(alarmHours.toString()).concat(":").concat(stringAlarmMinutes).concat(" PM");
                }else stringAlarmTime = stringAlarmTime.concat(alarmHours.toString()).concat(":").concat(stringAlarmMinutes).concat(" AM");

                Log.i("> APP: ", "Note Object Name in TimeAlarmPicker: " + idNote);
                intent.putExtra("status", "on");
                intent.putExtra("idNote", idNote);

                pendingIntent = PendingIntent.getBroadcast(TimeAlarmPicker.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                textTime.setText(noteDAO.selectNote(idNote).getName() + " will alarm at " + stringAlarmTime);
            }
        });

        btnDungLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textTime.setText("Dừng lại");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("status", "off");
                sendBroadcast(intent);
            }
        });

        

    }
}