package com.example.rzeposlaw.localizeme.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.adapter.RecyclerViewAdapter;
import com.example.rzeposlaw.localizeme.data.ApiClient;
import com.example.rzeposlaw.localizeme.data.Location;
import com.example.rzeposlaw.localizeme.data.LocationAPI;
import com.example.rzeposlaw.localizeme.utils.FetchUrl;
import com.example.rzeposlaw.localizeme.view.RozhaOneTextView;
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

import static com.example.rzeposlaw.localizeme.activity.LoginRegisterActivity.MY_PERMISSIONS_REQUEST_LOCATION;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private LocationAPI apiService =
            ApiClient.getClient().create(LocationAPI.class);

    private static GoogleMap mMap;
    private long loggedUserId;
    private long friendsId;
    private LatLng loggedUserLocation;
    private LatLng friendsLocation;
    private View toastViewLayout;
    private List<Marker> markers = new ArrayList<>();
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        loggedUserId = intent.getLongExtra(RecyclerViewAdapter.LOGGED_USER_ID, 999L);
        friendsId = intent.getLongExtra(RecyclerViewAdapter.FRIENDS_ID, 999L);

        LayoutInflater inflater = getLayoutInflater();
        toastViewLayout = inflater.inflate(R.layout.rounded_toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(checkLocationPermission()) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 360, 1, new LocationListener() {

                @Override
                public void onLocationChanged(android.location.Location location) {
                    loggedUserLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    Call<Location> callForFriend = apiService.getUserLastLocation(Long.toString(friendsId));
                    callForFriend.enqueue(new Callback<Location>() {
                        @Override
                        public void onResponse(Call<com.example.rzeposlaw.localizeme.data.Location> call,
                                               Response<Location> response) {
                            if (response.code() == 200) {
                                Location location = response.body();
                                friendsLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                if (loggedUserLocation != null) {
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
                                } else {
                                    showToast(getString(R.string.location_unknown));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<com.example.rzeposlaw.localizeme.data.Location> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MapActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void showToast(String textToast) {
        RozhaOneTextView text = (RozhaOneTextView) toastViewLayout.findViewById(R.id.toast_text);
        text.setText(textToast);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastViewLayout);
        toast.show();
    }

    public static void addPolyline(PolylineOptions polylineOptions) {
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

    private void moveToBounds() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        mMap.animateCamera(cu);
    }
}