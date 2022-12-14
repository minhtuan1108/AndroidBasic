package com.example.multinotes;

import android.net.Uri;

import java.io.Serializable;

public class UriDTO implements Serializable {
    private Integer id;
    private Uri image;
    private Integer idNote;

    public UriDTO(Integer id, Uri image, Integer idNote) {
        this.id = id;
        this.image = image;
        this.idNote = idNote;
    }

    public UriDTO(Uri image, Integer idNote) {
        this.image = image;
        this.idNote = idNote;
    }

    public UriDTO(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public Integer getIdNote() {
        return idNote;
    }

    public void setIdNote(Integer idNote) {
        this.idNote = idNote;
    }
}
