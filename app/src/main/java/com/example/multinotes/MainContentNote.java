package com.example.multinotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainContentNote extends Activity {
    EditText contentView, nameNote;
    RecyclerView recyclerViewListImage;
    ImageView buttonSave;
    ImageAdapter adapterImage;

    DatabaseConnector databaseConnector = new DatabaseConnector(this, "mutipleNote.sqlite",null, 1);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content_note);
        nameNote = findViewById(R.id.name_detail_note);
        contentView = findViewById(R.id.content_detail_note);
        recyclerViewListImage = (RecyclerView) findViewById(R.id.list_image_in_main);
        buttonSave = findViewById(R.id.save_change);

        Note note = (Note) getIntent().getSerializableExtra("noteObject");
        UriDAO uriDAO = new UriDAO(databaseConnector);
        nameNote.setText(note.getName());


        ArrayList<UriDTO> listUriDTO = uriDAO.selectAllUriByNoteId(note.getId());

        if(listUriDTO.size() > 0){
            ArrayList<Uri> uriImageList = new ArrayList<>();
            for(int i = 0; i < listUriDTO.size(); i++){
                uriImageList.add(listUriDTO.get(i).getImage());
            }

            contentView.setText(uriImageList.get(0).toString());
            showImageInNoteDetail(uriImageList);
        }

    }

    public void showImageInNoteDetail(ArrayList<Uri> uriImageList){
        adapterImage = new ImageAdapter(uriImageList, this);
        recyclerViewListImage.setAdapter(adapterImage);
        recyclerViewListImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }
}