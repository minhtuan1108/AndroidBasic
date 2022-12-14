package com.example.multinotes;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Note implements Serializable {
    private Integer id;
    private String name;
    private String content;
    private Date dateCreate;
    private Date alarmTime;

    public Note(Integer id, String name, String content, Date dateCreate, Date alarmTime) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.dateCreate = dateCreate;
        this.alarmTime = alarmTime;
    }

    public Note(String name, String content, Date dateCreate, Date alarmTime) {
        this.name = name;
        this.content = content;
        this.dateCreate = dateCreate;
        this.alarmTime = alarmTime;
    }

    public Note(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }
}
