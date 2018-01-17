package com.example.rzeposlaw.localizeme.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.adapter.RecyclerViewAdapter;
import com.example.rzeposlaw.localizeme.data.ApiClient;
import com.example.rzeposlaw.localizeme.data.Location;
import com.example.rzeposlaw.localizeme.data.LocationAPI;
import com.example.rzeposlaw.localizeme.utils.FetchUrl;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private LocationAPI apiService =
            ApiClient.getClient().create(LocationAPI.class);

    private static GoogleMap mMap;
    private long loggedUserId;
    private long friendsId;
    private LatLng loggedUserLocation;
    private LatLng friendsLocation;
    private List<Marker> markers = new ArrayList<>();

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

        if(loggedUserLocation != null && friendsLocation != null) {
            MarkerOptions markerOptionsLogged = new MarkerOptions();
            markerOptionsLogged.position(loggedUserLocation);
            markerOptionsLogged.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            markers.add(mMap.addMarker(markerOptionsLogged));

            MarkerOptions markerOptionsFriend = new MarkerOptions();
            markerOptionsFriend.position(friendsLocation);
            markerOptionsFriend.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            markers.add(mMap.addMarker(markerOptionsFriend));

            moveToBounds();

            String url = getUrl(loggedUserLocation, friendsLocation);
            FetchUrl FetchUrl = new FetchUrl();
            FetchUrl.execute(url);
        }
    }

    public static void addPolyline(PolylineOptions polylineOptions){
        mMap.addPolyline(polylineOptions);
    }

    private String getUrl(LatLng origin, LatLng dest) {

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        String sensor = "sensor=false";

        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        String url = "https://maps.googleapis.com/maps/api/directions/json?" + parameters;

        return url;
    }

    private void moveToBounds()
    {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        mMap.animateCamera(cu);
    }
}