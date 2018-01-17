package com.example.rzeposlaw.localizeme.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.adapter.RecyclerViewAdapter;
import com.example.rzeposlaw.localizeme.data.ApiClient;
import com.example.rzeposlaw.localizeme.data.Location;
import com.example.rzeposlaw.localizeme.data.LocationAPI;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private LocationAPI apiService =
            ApiClient.getClient().create(LocationAPI.class);

    private GoogleMap mMap;
    private long loggedUserId;
    private long friendsId;
    private LatLng loggedUserLocation;
    private LatLng friendsLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        loggedUserId = intent.getLongExtra(RecyclerViewAdapter.LOGGED_USER_ID, 999L);
        friendsId = intent.getLongExtra(RecyclerViewAdapter.FRIENDS_ID, 999L);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Call<Location> callUserLocation = apiService.getUserLastLocation(Long.toString(loggedUserId));
        callUserLocation.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<com.example.rzeposlaw.localizeme.data.Location> call,
                                   Response<Location> response) {
                if(response.code() == 200){
                    Location location = response.body();
                    loggedUserLocation = new LatLng(location.getLatitude(), location.getLongitude());
                }
            }

            @Override
            public void onFailure(Call<com.example.rzeposlaw.localizeme.data.Location> call, Throwable t) {

            }
        });

        Call<Location> callForFriend = apiService.getUserLastLocation(Long.toString(friendsId));
        callForFriend.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<com.example.rzeposlaw.localizeme.data.Location> call,
                                   Response<Location> response) {
                if(response.code() == 200){
                    Location location = response.body();
                    friendsLocation = new LatLng(location.getLatitude(), location.getLongitude());
                }
            }

            @Override
            public void onFailure(Call<com.example.rzeposlaw.localizeme.data.Location> call, Throwable t) {

            }
        });
    }
}