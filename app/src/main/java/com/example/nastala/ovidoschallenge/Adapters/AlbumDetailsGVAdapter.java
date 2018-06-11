package com.example.nastala.ovidoschallenge.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nastala.ovidoschallenge.Classes.Photo;
import com.example.nastala.ovidoschallenge.R;

import java.util.ArrayList;

public class AlbumDetailsGVAdapter extends BaseAdapter {
    private ArrayList<Photo> photos;
    private LayoutInflater mLayoutInflater;

    public AlbumDetailsGVAdapter(Activity activity, ArrayList<Photo> photos){
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return photos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.adapter_album_details_gv, parent, false);

        ImageView ivPhoto = convertView.findViewById(R.id.ivPhoto);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);

        Photo photo = photos.get(position);

        Glide.with(ivPhoto.getContext())
                .load(photo.getUrl())
                .into(ivPhoto);

        tvTitle.setText(photo.getTitle());

        return convertView;
    }
}
