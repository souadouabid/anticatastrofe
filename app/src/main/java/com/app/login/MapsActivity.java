package com.app.login;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.app.login.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements
                                                    GoogleMap.OnMyLocationButtonClickListener,
                                                    GoogleMap.OnMyLocationClickListener,
                                                    OnMapReadyCallback,
                                                    ActivityCompat.OnRequestPermissionsResultCallback
    {

    private FusedLocationProviderClient client;
    GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button mTypeBtn, mTypeBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        client = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void generador_marcadors(GoogleMap googleMap){
        mMap = googleMap;
        final LatLng punto1 = new LatLng(41.4144948,2.1526945);
        mMap.addMarker(new MarkerOptions().position(punto1).title("Barcelona"));
    }

    public void onMapReady(GoogleMap map) {
        /*googleMap.addMarker(new MarkerOptions()
            .position(new LatLng(41.4144948,2.1526945))
            .title("Marker"));
        */

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);

            client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        LatLng l = new LatLng(location.getLatitude(),location.getLongitude());
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(l)      // Sets the center of the map to Mountain View
                                .zoom(14)                   // Sets the zoom
                                .bearing(270)                // Sets the orientation of the camera to west
                                .tilt(10)                   // Sets the tilt of the camera to x degrees
                                .build();                   // Creates a CameraPosition from the builder
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }

            });
        }
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        //a otro lado
        mTypeBtn = (Button) findViewById(R.id.btnsatelit);
        mTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mTypeBtn.setVisibility(View.INVISIBLE);
                mTypeBtn2.setVisibility(View.VISIBLE);
            }
        });
        mTypeBtn2 = (Button) findViewById(R.id.bthibrid);
        mTypeBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                mTypeBtn2.setVisibility(View.INVISIBLE);
                mTypeBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
}