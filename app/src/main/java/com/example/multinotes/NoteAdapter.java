package com.example.multinotes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    public ArrayList<Note> notes;
    public Context mCtx;

    public NoteAdapter(ArrayList<Note> notes, Context mCtx) {
        this.notes = notes;
        this.mCtx = mCtx;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Nạp layout của View Note
        View noteView = inflater.inflate(R.layout.note_demo, parent, false);
        return new ViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = (Note) notes.get(position);
        holder.setNote(note);
        holder.setParentContext(mCtx);
        holder.name.setText(note.getName());
        holder.dateCreate.setText(note.getDateCreate().toLocaleString());
        holder.content.setText(note.getContent());
    }

    public void updateList(ArrayList<Note> newList){
        notes = newList;
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Context parentContext;
        public View itemView;
        public TextView name;
        public TextView dateCreate;
        public TextView content;
        private Note note;
        private Integer idNote;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.name = this.itemView.findViewById(R.id.note_name);
            this.dateCreate = this.itemView.findViewById(R.id.note_date_create);
            this.content = this.itemView.findViewById(R.id.note_content);
            this.itemView.findViewById(R.id.note_panel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iGetContactInfo = new Intent(parentContext, MainContentNote.class);

                    iGetContactInfo.putExtra("noteObject", note);
                    parentContext.startActivity(iGetContactInfo);
                }
            });
        }

        public Note getNote() {
            return note;
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Context getParentContext() {
            return parentContext;
        }

        public void setParentContext(Context parentContext) {
            this.parentContext = parentContext;
        }

        public View getItemView() {
            return itemView;
        }

        public void setItemView(View itemView) {
            this.itemView = itemView;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getDateCreate() {
            return dateCreate;
        }

        public void setDateCreate(TextView dateCreate) {
            this.dateCreate = dateCreate;
        }

        public TextView getContent() {
            return content;
        }

        public void setContent(TextView content) {
            this.content = content;
        }

        public Integer getIdNote() {
            return idNote;
        }

        public void setIdNote(Integer idNote) {
            this.idNote = idNote;
        }
    }

}
