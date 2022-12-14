package com.example.multinotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Note> listNote = new ArrayList<>();
    ArrayList<Uri> listUriImage = new ArrayList<>();

    static NoteAdapter noteAdapter;
    ImageAdapter imageAdapter;
    static RecyclerView recyclerViewNote;
    RecyclerView recyclerViewImage;
    ImageView addButton;
    ImageView helpButton;
    PopupWindow popupWindow;
    EditText newNoteName, newNoteContent;
    ImageView pickImage;
    TextView saveButton;
    View popupView;

    public DatabaseConnector databaseConnector = new DatabaseConnector(this, "mutipleNote.sqlite",null, 1);
    NoteDAO noteDAO = new NoteDAO(databaseConnector);
    UriDAO uriDAO = new UriDAO(databaseConnector);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tạo các bảng database
        databaseConnector.queryData("CREATE TABLE IF NOT EXISTS Note(id INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR(200), NoiDung VARCHAR(2000), NgayTao VARCHAR(20), BaoThuc VARCHAR(20))");
        databaseConnector.queryData("CREATE TABLE IF NOT EXISTS Uri(id INTEGER PRIMARY KEY AUTOINCREMENT, Image VARCHAR(500), idNote INTEGER)");

        //Tạo giao diện chính
        recyclerViewNote = findViewById(R.id.note_list);
        addButton = findViewById(R.id.add_file);
        helpButton = findViewById(R.id.info_icon);

        //Tạo popUp window thêm note
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_add_note, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        //Các thành phần trong của sổ popup
        pickImage = (ImageView) popupView.findViewById(R.id.pick_image);
        saveButton = (TextView) popupView.findViewById(R.id.button_add);
        recyclerViewImage = (RecyclerView) popupView.findViewById(R.id.list_image);
        newNoteName = (EditText) popupView.findViewById(R.id.new_note_name);
        newNoteContent = (EditText) popupView.findViewById(R.id.new_note_content);

        //Set sự kiện cho nút thêm
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set tất cả về trạng thái ban đầu
                popupWindow.showAtLocation(recyclerViewNote, Gravity.CENTER, 0, 0);

                android.view.ViewGroup.LayoutParams layoutParams = recyclerViewImage.getLayoutParams();
                layoutParams.height = 0;
                recyclerViewImage.setLayoutParams(layoutParams);
                newNoteName.setText("");
                newNoteContent.setText("");
                listUriImage.clear();

            }
        });

        //Set sự kiện cho popup menu thêm note mới
        popupView.findViewById(R.id.container_popup_window).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(view == popupView) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

        //Set sự kiện cho nút chọn ảnh
        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        //Set sự kiện cho nút save (tạo) note mới
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) popupView.findViewById(R.id.new_note_name)).getText().toString();
                String content = ((EditText) popupView.findViewById(R.id.new_note_content)).getText().toString();

                if(!name.equals("")){
                    addNewNote(name, content, listUriImage);
                    listUriImage.clear();
                    popupWindow.dismiss();
                }else{
                    Toast.makeText(MainActivity.this, "Name cannot empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Show note trong database
        showListNoteDemo(sortByTime(noteDAO.selectAllNote(), false));
    }

    public void addNewNote(String name, String contents, ArrayList<Uri> uriArrayList){
        noteDAO.insertNote(new Note(name, contents, new Date(), new Date()));
        ArrayList<Note> listNote = noteDAO.selectAllNote();
        Note note = noteDAO.getLastNote();

        //Lưu uri các hình ảnh vào cơ sở dữ liệu
        for(int i = 0; i < uriArrayList.size(); i++){
            uriDAO.insertUri(new UriDTO(uriArrayList.get(i), note.getId()));
        }

        showListNoteDemo(sortByTime(listNote, false));
    }

    public static void showListNoteDemo(ArrayList<Note> lsNote){
        noteAdapter = new NoteAdapter(lsNote, recyclerViewNote.getContext());
        recyclerViewNote.setAdapter(noteAdapter);
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(recyclerViewNote.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    public static ArrayList<Note> sortByTime(ArrayList<Note> list, boolean increase){

//        Note temp;
        if(!increase){
            //Bubble sort
            for(int i = 1; i < list.size(); i++){
                for(int j = 0; j < list.size() - i; j++){
                    if(list.get(j).getDateCreate().before(list.get(j + 1).getDateCreate())){
                        wrapNote(list.get(j), list.get(j+1));
                    }
                }
            }

            return list;
        }else{
            for(int i = 1; i < list.size(); i++){
                for(int j = 0; j < list.size() - i; j++){
                    if(list.get(j).getDateCreate().after(list.get(j + 1).getDateCreate())){
                        wrapNote(list.get(j), list.get(j+1));
                    }
                }
            }

            return list;
        }
    }

    public static void wrapNote(Note a, Note b){
        Note tmp = new Note();

        tmp.setId(a.getId());
        tmp.setName(a.getName());
        tmp.setContent(a.getContent());
        tmp.setDateCreate(a.getDateCreate());
        tmp.setAlarmTime(a.getAlarmTime());

        a.setId(b.getId());
        a.setName(b.getName());
        a.setContent(b.getContent());
        a.setDateCreate(b.getDateCreate());
        a.setAlarmTime(b.getAlarmTime());

        b.setId(tmp.getId());
        b.setName(tmp.getName());
        b.setContent(tmp.getContent());
        b.setDateCreate(tmp.getDateCreate());
        b.setAlarmTime(tmp.getAlarmTime());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            ClipData clipData = data.getClipData();

            if(clipData != null){
                for (int i = 0; i < clipData.getItemCount(); i++)
                    listUriImage.add(clipData.getItemAt(i).getUri());
            }else{
                listUriImage.add(data.getData());
            }

            showListPicturePicked(imageAdapter, recyclerViewImage, listUriImage);
        }
    }

    public void showListPicturePicked(ImageAdapter adapterImage, RecyclerView recyclerView, ArrayList<Uri> listUriImage){
        if(listUriImage.size() != 0){
            adapterImage = new ImageAdapter(listUriImage, this);
            android.view.ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
            layoutParams.height = 300;
            recyclerView.setLayoutParams(layoutParams);
            recyclerView.setAdapter(adapterImage);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }else {
            android.view.ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
            layoutParams.height = 0;
            recyclerView.setLayoutParams(layoutParams);
        }
    }

    int countPressBackTimes = 0;
    @Override
    public void onBackPressed() {
        countPressBackTimes++;
        if(countPressBackTimes == 1){
            Toast.makeText(this, "Press back button again to exit app", Toast.LENGTH_SHORT).show();
        }else{
            if(countPressBackTimes == 2){
                super.onBackPressed();
                countPressBackTimes = 0;
            }
        }
    }
}