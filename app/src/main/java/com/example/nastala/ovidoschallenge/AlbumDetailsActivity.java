package com.example.nastala.ovidoschallenge;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nastala.ovidoschallenge.Adapters.AlbumDetailsGVAdapter;
import com.example.nastala.ovidoschallenge.Classes.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlbumDetailsActivity extends AppCompatActivity {
    private final String TAG = "ALBUMDETAILSACTI";

    private ArrayList<Photo> photos;
    private GridView gvAlbumDetails;
    private ProgressBar pbAlbumDetails;
    private int albumId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_album_details);

        Log.d(TAG, "Orientation: " + String.valueOf(AlbumDetailsActivity.this.getResources().getConfiguration().orientation));

        Intent intent = getIntent();
        if(intent.getExtras() != null)
            albumId = intent.getExtras().getInt("albumId");

        photos = new ArrayList<>();

        gvAlbumDetails = findViewById(R.id.gvAlbumDetails);
        pbAlbumDetails = findViewById(R.id.pbAlbumDetails);

        bringPhotos();
        Log.d(TAG, String.valueOf(albumId));
    }

    private void bringPhotos() {
        gvAlbumDetails.setVisibility(View.GONE);
        pbAlbumDetails.setVisibility(View.VISIBLE);
        photos.clear();

        final String REQUEST_URL = "http://jsonplaceholder.typicode.com/photos?albumId=" + albumId;
        StringRequest request = new StringRequest(Request.Method.GET, REQUEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response success: " + response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Photo photo = new Photo();
                        photo.setAlbumId(jsonObject.getInt("albumId"));
                        photo.setId(jsonObject.getInt("id"));
                        photo.setTitle(jsonObject.getString("title"));
                        photo.setUrl(jsonObject.getString("url"));
                        photo.setThumbnailUrl(jsonObject.getString("thumbnailUrl"));

                        photos.add(photo);
                    }

                    fillGvAlbumDetails();
                } catch (JSONException e) {
                    Log.d(TAG, "Photo failure: " + e.getMessage());
                    pbAlbumDetails.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Response failure: " + error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void fillGvAlbumDetails() {
        if(AlbumDetailsActivity.this.getResources().getConfiguration().orientation == 2)
            gvAlbumDetails.setNumColumns(3);
        else
            gvAlbumDetails.setNumColumns(2);

        AlbumDetailsGVAdapter albumDetailsGVAdapter = new AlbumDetailsGVAdapter(this, photos);
        gvAlbumDetails.setAdapter(albumDetailsGVAdapter);
        pbAlbumDetails.setVisibility(View.GONE);
        gvAlbumDetails.setVisibility(View.VISIBLE);
    }
}
