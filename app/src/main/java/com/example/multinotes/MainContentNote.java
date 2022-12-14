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
    private EditText contentView, nameNote;
    private RecyclerView recyclerViewListImage;
    private ImageView buttonSave;
    private ImageAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content_note);
        nameNote = findViewById(R.id.name_detail_note);
        contentView = findViewById(R.id.content_detail_note);
        recyclerViewListImage = findViewById((R.id.list_image_in_main));
        buttonSave = findViewById(R.id.save_change);

        Note note = (Note) getIntent().getSerializableExtra("noteObject");
        UriDAO uriDAO = new UriDAO(new DatabaseConnector(this, "mutipleNote.sqlite",null, 1));

        nameNote.setText(note.getName());
        String test = "";

        ArrayList<UriDTO> listUriDTO = uriDAO.selectAllUriByNoteId(note.getId());

        if(listUriDTO.size() > 0){
            ArrayList<Uri> uriImageList = new ArrayList<>();
            for(int i = 0; i < listUriDTO.size(); i++){
                uriImageList.add(listUriDTO.get(i).getImage());
                test += listUriDTO.get(i).getImage().toString() + "\n";
            }

            showImageInNoteDetail(adapter, recyclerViewListImage, uriImageList);
        }

//        contentView.setText(test);
    }

    public void showImageInNoteDetail(ImageAdapter adapter, RecyclerView recyclerView, ArrayList<Uri> uriImageList){
        adapter = new ImageAdapter(uriImageList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }
}