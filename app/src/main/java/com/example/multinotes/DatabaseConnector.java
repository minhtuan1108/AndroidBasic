package com.example.multinotes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseConnector extends SQLiteOpenHelper {
    public DatabaseConnector(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void queryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.execute();
    }

    public long insertData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);
        return stmt.executeInsert();
    }

    public int updateData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);
        return stmt.executeUpdateDelete();
    }

    public Cursor getData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

}