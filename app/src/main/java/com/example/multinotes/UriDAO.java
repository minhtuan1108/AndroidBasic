package com.example.multinotes;

import android.database.Cursor;
import android.net.Uri;


import java.util.ArrayList;

public class UriDAO{
    private DatabaseConnector dbConnector;

    public UriDAO(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public void insertUri(UriDTO uriDTO){
        String sql = "INSERT INTO Uri VALUES (null, '" + uriDTO.getImage().toString() + "', '"
                + uriDTO.getIdNote() + "')";
        dbConnector.insertData(sql);
    }

    public void editUri(UriDTO uriDTO, Integer id){
        String sql = "UPDATE Uri SET Image='" + uriDTO.getImage().toString() + "', idNote='"
                + uriDTO.getIdNote() + "' WHERE id = '" + id + "')";
        dbConnector.updateData(sql);
    }

    public void deleteUri(Integer id){
        String sql = "DELETE FROM Uri WHERE id = '" + id + "'";
        dbConnector.updateData(sql);
    }

    public UriDTO selectUri(Integer id){
        Cursor data = dbConnector.getData("SELECT * FROM Uri WHERE id = '" + id + "'");
        UriDTO uriDTO = new UriDTO();
        while (data.moveToNext()){
            uriDTO.setId(data.getInt(0));
            uriDTO.setImage(Uri.parse(data.getString(1)));
            uriDTO.setIdNote(data.getInt(2));
        }

        return uriDTO;
    }

    public ArrayList<UriDTO> selectAllUri(){
        Cursor data = dbConnector.getData("SELECT * FROM Uri");
        ArrayList<UriDTO> listUri = new ArrayList<>();
        UriDTO uriDTO;

        while (data.moveToNext()){
            uriDTO = new UriDTO(data.getInt(0), Uri.parse(data.getString(1)), data.getInt(2));
            listUri.add(uriDTO);
        }
        return listUri;
    }

    public ArrayList<UriDTO> selectAllUriByNoteId(Integer noteId){
        Cursor data = dbConnector.getData("SELECT * FROM Uri WHERE idNote='" + noteId + "'");
        ArrayList<UriDTO> listUri = new ArrayList<>();
        UriDTO uriDTO;

        while (data.moveToNext()){
            uriDTO = new UriDTO(data.getInt(0), Uri.parse(data.getString(1)), data.getInt(2));
            listUri.add(uriDTO);
        }
        return listUri;
    }
}
