package com.example.multinotes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class NoteDAO {

    private DatabaseConnector dbConnector;

    public NoteDAO(DatabaseConnector dbConnector){
        this.dbConnector = dbConnector;
    }

    public void insertNote(Note note){
        String sql = "INSERT INTO Note VALUES (null, '" + note.getName() + "', '"
                + note.getContent() + "', '"
                + note.getDateCreate().toString() + "', '"
                + note.getAlarmTime().toString() + "')";
        dbConnector.insertData(sql);
    }

    public void editNote(Note note, Integer id){
        String sql = "UPDATE Note SET Ten='" + note.getName() + "', NoiDung='"
                + note.getContent() + "', BaoThuc='"
                + note.getAlarmTime().toString() + "' WHERE id = '" + id + "')";
        dbConnector.updateData(sql);
    }

    public void deleteNote(Integer id){
        String sql = "DELETE FROM Note WHERE id = '" + id + "'";
        dbConnector.updateData(sql);
    }

    public Note selectNote(Integer id){
        Cursor data = dbConnector.getData("SELECT * FROM Note WHERE id = '" + id + "'");
        Note note = new Note();
        while (data.moveToNext()){
            note.setId(data.getInt(0));
            note.setName(data.getString(1));
            note.setContent(data.getString(2));
            note.setDateCreate(new Date(data.getString(3)));
            note.setAlarmTime(new Date(data.getString(4)));
        }

        return note;
    }

    public ArrayList<Note> selectAllNote(){
        Cursor data = dbConnector.getData("SELECT * FROM Note");
        ArrayList<Note> listNote = new ArrayList<>();
        Note note;

        while (data.moveToNext()){
            note = new Note(data.getInt(0), data.getString(1), data.getString(2), new Date(data.getString(3)), new Date(data.getString(4)));
            listNote.add(note);
        }
        return listNote;
    }

    public Note getLastNote(){
        ArrayList<Note> listNote = selectAllNote();
        return listNote.get(listNote.size() - 1);
    }
}
