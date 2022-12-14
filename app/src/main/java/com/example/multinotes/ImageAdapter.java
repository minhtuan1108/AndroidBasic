package com.example.multinotes;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    public ArrayList<Uri> listImage;
    public Context myContext;

    public ImageAdapter(ArrayList<Uri> listImage, Context myContext) {
        this.listImage = listImage;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Nạp view (Image View)
        View view = inflater.inflate(R.layout.image_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = listImage.get(position);
        holder.setParentCtx(myContext);
        holder.img.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private Context parentCtx;
        public ImageView img;
        public View itemview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemview = itemView;
            this.img = (ImageView) itemView.findViewById(R.id.item_image_view);
        }

        public Context getParentCtx() {
            return parentCtx;
        }

        public void setParentCtx(Context parentCtx) {
            this.parentCtx = parentCtx;
        }

        public ImageView getImg() {
            return img;
        }

        public void setImg(ImageView img) {
            this.img = img;
        }
    }
}
