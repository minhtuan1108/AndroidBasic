package com.example.multinotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainContentNote extends Activity {
    EditText contentView, nameNote;
    RecyclerView recyclerViewListImage;
    ImageView buttonSave, buttonSetAlarm;
    ImageAdapter adapterImage;
    boolean isChanged = false;

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
        buttonSetAlarm = findViewById(R.id.alarm_button);

        Note note = (Note) getIntent().getSerializableExtra("noteObject");
        NoteDAO noteDAO = new NoteDAO(databaseConnector);
        UriDAO uriDAO = new UriDAO(databaseConnector);
        nameNote.setText(note.getName());
        contentView.setText(note.getContent());

        //Sử lý sự kiện cho nút lưu thay đổi
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isChanged){
                    Toast.makeText(MainContentNote.this, "Nothing changed to save", Toast.LENGTH_SHORT).show();
                }else{
                    if(nameNote.getText().toString().equals("")){
                        Toast.makeText(MainContentNote.this, "Note's name cannot empty!", Toast.LENGTH_SHORT).show();
                    }else{
                        note.setName(nameNote.getText().toString());
                        note.setContent(contentView.getText().toString());
                        noteDAO.editNote(note, note.getId());
                        Toast.makeText(MainContentNote.this, "Saved successful", Toast.LENGTH_SHORT).show();

                        isChanged = false;
                    }
                }
            }
        });

        nameNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                isChanged = true;
            }
        });

        contentView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                isChanged = true;
            }
        });

//        ArrayList<UriDTO> listUriDTO = uriDAO.selectAllUriByNoteId(note.getId());
//
//        if(listUriDTO.size() > 0){
//            ArrayList<Uri> uriImageList = new ArrayList<>();
//            for(int i = 0; i < listUriDTO.size(); i++){
//                uriImageList.add(listUriDTO.get(i).getImage());
//            }
//
//            showImageInNoteDetail(uriImageList);
//        }

        buttonSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TimeAlarmPicker.class);
                intent.putExtra("idNote", note.getId());
                intent.putExtra("noteObject", note);
                startActivity(intent);
            }
        });
    }

    public void showImageInNoteDetail(ArrayList<Uri> uriImageList){
        try{
            Log.i("> APP: ", "step 1");
            adapterImage = new ImageAdapter(uriImageList, this);
            Log.i("> APP: ", "step 2");
            recyclerViewListImage.setAdapter(adapterImage);
            Log.i("> APP: ", "step 3");
            recyclerViewListImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            Log.i("> APP: ", "step 4");
        }catch (Exception e){
            Log.i("> APP: ", "error: " + e);
        }

    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}