package com.example.nastala.ovidoschallenge.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.nastala.ovidoschallenge.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlbumDetailsFragment extends Fragment {
    //Bonus Challenge'ı yapabilmek için bu Fragment'ı Activity'e taşımam gerekti. Şu anda burası kullanılmıyor.

    private static final String ARG_ALBUMID = "albumid";
    private final String TAG = "ALBUMDETAILSFRAG";

    private ArrayList<Photo> photos;
    private GridView gvAlbumDetails;
    private ProgressBar pbAlbumDetails;
    private int albumId;

    public AlbumDetailsFragment() {

    }

    public static AlbumDetailsFragment newInstance(int albumId) {
        AlbumDetailsFragment fragment = new AlbumDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ALBUMID, albumId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            albumId = getArguments().getInt(ARG_ALBUMID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_details, container, false);

        photos = new ArrayList<>();

        gvAlbumDetails = view.findViewById(R.id.gvAlbumDetails);
        pbAlbumDetails = view.findViewById(R.id.pbAlbumDetails);

        bringPhotos();
        Log.d(TAG, String.valueOf(albumId));

        return view;
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

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    private void fillGvAlbumDetails() {
        if(getActivity() == null)
            return;

        AlbumDetailsGVAdapter albumDetailsGVAdapter = new AlbumDetailsGVAdapter(getActivity(), photos);
        gvAlbumDetails.setAdapter(albumDetailsGVAdapter);
        pbAlbumDetails.setVisibility(View.GONE);
        gvAlbumDetails.setVisibility(View.VISIBLE);
    }

}
