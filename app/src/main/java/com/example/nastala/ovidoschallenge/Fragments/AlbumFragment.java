package com.example.nastala.ovidoschallenge.Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nastala.ovidoschallenge.Adapters.AlbumLVAdapter;
import com.example.nastala.ovidoschallenge.AlbumDetailsActivity;
import com.example.nastala.ovidoschallenge.Classes.Album;
import com.example.nastala.ovidoschallenge.Classes.User;
import com.example.nastala.ovidoschallenge.Classes.UserAddress;
import com.example.nastala.ovidoschallenge.Classes.UserCompany;
import com.example.nastala.ovidoschallenge.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class AlbumFragment extends Fragment {
    private final String TAG = "ALBUMFRAGMENT";

    private ProgressBar pbAlbum;
    private Spinner spnUsers;
    private ListView lvAlbums;
    private LinearLayout llAlbum;
    private ArrayList<User> users;
    private ArrayList<String> userNames;
    private ArrayList<Album> userAlbums;

    //OnPause
    private boolean onPauseFlag = false;
    private ArrayList<User> onPauseUsers;
    private ArrayList<String> onPauseUserNames;
    private ArrayList<Album> onPauseUserAlbums;
    private int onPauseSpnSelectedUserPos;

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        users = new ArrayList<>();
        userNames = new ArrayList<>();
        userAlbums = new ArrayList<>();

        pbAlbum = view.findViewById(R.id.pbAlbum);
        spnUsers = view.findViewById(R.id.spnUsers);
        lvAlbums = view.findViewById(R.id.lvAlbums);
        llAlbum = view.findViewById(R.id.llAlbum);

        spnUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(onPauseFlag && onPauseSpnSelectedUserPos == position)
                    return;
                else
                    bringAlbums(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lvAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "lvAlbums item clicked position: " + position + " id: " + id);
                /*FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.flMainActivity, AlbumDetailsFragment.newInstance((int)id), "ALBUMFRAGMENT");
                ft.addToBackStack("ALBUMFRAGMENT").commit();*/

                Intent intent = new Intent(getActivity(), AlbumDetailsActivity.class);
                intent.putExtra("albumId", (int)id);
                startActivity(intent);
            }
        });

        if(!onPauseFlag)
            bringUsers();

        return view;
    }

    private void setAlbumPassive(){
        pbAlbum.setVisibility(View.VISIBLE);
        spnUsers.setEnabled(false);
        lvAlbums.setEnabled(false);
    }

    private void setAlbumActive(){
        pbAlbum.setVisibility(View.GONE);
        spnUsers.setEnabled(true);
        lvAlbums.setEnabled(true);
    }

    private void bringAlbums(int position) {
        setAlbumPassive();
        userAlbums.clear();

        int userId = position + 1;
        final String request_url = "http://jsonplaceholder.typicode.com/albums?userId=" + userId;

        StringRequest albumRequest = new StringRequest(Request.Method.GET, request_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Success " + response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    String title;
                    int userId, id;
                    Album album;
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        userId = jsonObject.getInt("userId");
                        id = jsonObject.getInt("id");
                        title = jsonObject.getString("title");
                        album = new Album(userId, id, title);
                        userAlbums.add(album);
                    }

                    fillLvAlbums();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setAlbumActive();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Fail " + error.getMessage());
                setAlbumActive();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(albumRequest);
    }

    private void bringUsers() {
        pbAlbum.setVisibility(View.VISIBLE);
        users.clear();
        userNames.clear();

        final String request_url = "http://jsonplaceholder.typicode.com/users";
        StringRequest request = new StringRequest(Request.Method.GET, request_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response success: " + response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    int id;
                    String name, username, email, phone, website, street, suite, city, zipCode, lat, lng, companyName, catchPhrase, bs;
                    UserAddress address;
                    UserCompany company;

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d(TAG, jsonObject.toString());
                        //Gettning user's general infos from jsonObject
                        id = jsonObject.getInt("id");
                        name = jsonObject.getString("name");
                        username = jsonObject.getString("username");
                        email = jsonObject.getString("email");
                        phone = jsonObject.getString("phone");
                        website = jsonObject.getString("website");

                        Log.d(TAG, jsonObject.getJSONObject("address").toString());
                        JSONObject jsonAddress = jsonObject.getJSONObject("address");
                        //Gettning user's address infos from jsonAddress
                        street = jsonAddress.getString("street");
                        suite = jsonAddress.getString("suite");
                        city = jsonAddress.getString("city");
                        zipCode = jsonAddress.getString("zipcode");
                        JSONObject jsonLocation = jsonAddress.getJSONObject("geo");
                        lat = jsonLocation.getString("lat");
                        lng = jsonLocation.getString("lng");
                        address = new UserAddress(street, suite, city, zipCode, lat, lng);
                        Log.d(TAG, address.getStreet() + " " + address.getGeo());

                        JSONObject jsonCompany = jsonObject.getJSONObject("company");
                        //Gettning user's company infos from jsonCompany
                        companyName = jsonCompany.getString("name");
                        catchPhrase = jsonCompany.getString("catchPhrase");
                        bs = jsonCompany.getString("bs");
                        company = new UserCompany(companyName, catchPhrase, bs);

                        User user = new User(id, name, username, email, address, phone, website, company);
                        userNames.add(name);
                        users.add(user);
                    }

                    fillSpnUsers();
                } catch (JSONException e) {
                    pbAlbum.setVisibility(View.GONE);
                    llAlbum.setVisibility(View.GONE);
                    Log.d(TAG, "Json try/catch failure: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbAlbum.setVisibility(View.GONE);
                Log.d(TAG, "Response fail: " + error.getMessage());
            }
        });

        if(getActivity() == null)
            return;

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    private void fillSpnUsers() {
        if(getActivity() == null)
            return;

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, userNames);
        spnUsers.setAdapter(spnAdapter);

        if(onPauseFlag)
            spnUsers.setSelection(onPauseSpnSelectedUserPos);

        pbAlbum.setVisibility(View.GONE);
        llAlbum.setVisibility(View.VISIBLE);
    }

    private void fillLvAlbums() {
        if(getActivity() == null)
            return;

        AlbumLVAdapter albumLVAdapter = new AlbumLVAdapter(getActivity(), userAlbums);
        lvAlbums.setAdapter(albumLVAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(onPauseFlag){
            userAlbums = onPauseUserAlbums;
            userNames = onPauseUserNames;
            users = onPauseUsers;

            fillSpnUsers();
            fillLvAlbums();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        onPauseFlag = true;
        onPauseSpnSelectedUserPos = spnUsers.getSelectedItemPosition();
        onPauseUsers = users;
        onPauseUserAlbums = userAlbums;
        onPauseUserNames = userNames;
    }
}
