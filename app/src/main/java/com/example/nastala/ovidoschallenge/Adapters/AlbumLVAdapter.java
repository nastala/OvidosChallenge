package com.example.nastala.ovidoschallenge.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nastala.ovidoschallenge.Classes.Album;
import com.example.nastala.ovidoschallenge.R;

import java.util.ArrayList;

public class AlbumLVAdapter extends BaseAdapter{
    private ArrayList<Album> userAlbums;
    private LayoutInflater mLayoutInflater;

    public AlbumLVAdapter(Activity activity, ArrayList<Album> userAlbums){
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.userAlbums = userAlbums;
    }

    @Override
    public int getCount() {
        return userAlbums.size();
    }

    @Override
    public Object getItem(int position) {
        return userAlbums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userAlbums.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.adapter_album_lv, parent, false);

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        Album album = userAlbums.get(position);

        tvTitle.setText(album.getTitle());

        return convertView;
    }
}
