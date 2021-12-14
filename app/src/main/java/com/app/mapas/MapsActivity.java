package com.app.mapas;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.Managers.Client;
import com.app.login.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.app.login.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    final static String TAG = "MapsActivity";
    final String APP_ID = "6ba9413074ff7a43ed1598a11ad344e1";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";


    final long MIN_TIME =  3 * 1000;//
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    //la primera vez que se muestra el dialogo
    public static boolean DIALOG_IS_SHOWING = false;

    String Location_Provider = LocationManager.GPS_PROVIDER;

    LocationManager mLocationManager;
    LocationListener mLocationListener;

    private FusedLocationProviderClient client;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button mTypeBtn, mTypeBtn2, mButoninfo, activaEtiq, showEtiq, hideEtiq, tres;
    private FloatingActionButton mButtonWeather;
    private boolean activaMarkers;
    private JSONArray landmarks;
    private final List<Marker> mMarker = new ArrayList<Marker>();
    private Integer numMarkers;
    private boolean agafartempscasa;
    String idWeathercasa;
    int idWeathercasa2;
    private double tempcasa;
    private double speedcasa;

    //every x seconds execute task
    Handler handler = new Handler();
    Runnable runnable;
    int delay =  30 * 1000;//cada x segundos se ejecutara la tarea
    LatLng lastLocation = new LatLng(0,0);//la ultima ubicacion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        client = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getWeatherForCurrentLocation();



    }

    public void generador_marcadors(GoogleMap googleMap) {
        mMap = googleMap;
        final LatLng punto1 = new LatLng(41.4144948, 2.1526945);
        mMap.addMarker(new MarkerOptions().position(punto1).title("Barcelona"));
    }

    public void onMapReady(GoogleMap map) {
        mMap = map;
        /*googleMap.addMarker(new MarkerOptions()
            .position(new LatLng(41.4144948,2.1526945))
            .title("Marker"));
        */

        agafartempscasa = false;

        try {
            landmarks = Client.getAllLandmarks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject land = null;
        numMarkers = landmarks.length();

      /*  for(int i = 0; i < numMarkers; ++i){
            try {
                land = landmarks.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            double lat = 0;
            double lon = 0;
            String title = null;
            String desc = null;
            Integer id = 0;
            try {
                lat = land.getDouble("coordinate_x");
                System.out.println(lat);
            } catch (JSONException e) {
                e.printStackTrace();
            };
            try {
                lon = land.getDouble("coordinate_y");
                System.out.println(lon);
            } catch (JSONException e) {
                e.printStackTrace();
            };
            try {
                title = land.getString("tag");
                System.out.println(title);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                desc = land.getString("description");
                System.out.println(desc);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                id = land.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Float color = (((float)id % 10) * 60) % 360;

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat,lon))
                    .title(title)
                    .snippet(desc)
                    .visible(false)
                    .icon(BitmapDescriptorFactory.defaultMarker(color))

            );

            mMarker.add(marker);
        }
*/




        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);

            client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {

                        LatLng l = new LatLng(location.getLatitude(), location.getLongitude());
                        lastLocation = l;

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(l)      // Sets the center of the map to Mountain View
                                .zoom(12)                   // Sets the zoom
                                .bearing(270)                // Sets the orientation of the camera to west
                                .tilt(10)                   // Sets the tilt of the camera to x degrees
                                .build();                   // Creates a CameraPosition from the builder
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        String Latitude = String.valueOf(location.getLatitude());
                        String Longitude = String.valueOf(location.getLongitude());
                        RequestParams params = new RequestParams();
                        params.put("lat", Latitude);
                        params.put("lon", Longitude);
                        params.put("appid", APP_ID);
                        requestWeather(params,false, false);
                    }
                }

            });
        }
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (activaMarkers) {

                    Intent i = new Intent(MapsActivity.this, MarkerActivity.class);
                    i.putExtra("lat",(float)latLng.latitude);
                    i.putExtra("long",(float)latLng.longitude);
                    startActivity(i);
                }
            }
        });

        mButtonWeather = (FloatingActionButton) findViewById(R.id.btnWeather);
        mButtonWeather.setVisibility(View.VISIBLE);
        mButtonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWeatherService(lastLocation,true, false);
            }
        });

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

        tres = (Button) findViewById(R.id.bresidencia);
        tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agafartresidencia();
            }
        });

        activaEtiq = (Button) findViewById(R.id.activaEtiq);
        activaEtiq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activaMarkers = true;
            }
        });

        showEtiq = (Button) findViewById(R.id.showEtiq);
        showEtiq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // .setVisible(true a tots els markers)
                for(int i = 0; i < numMarkers; ++i){
                    Marker marker = mMarker.get(i);
                    marker.setVisible(true);
                }

                showEtiq.setVisibility(View.INVISIBLE);
                hideEtiq.setVisibility(View.VISIBLE);
            }
        });

        hideEtiq = (Button) findViewById(R.id.hideEtiq);
        hideEtiq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // .setVisible(false a tots els markers)
                for(int i = 0; i < numMarkers; ++i){
                    Marker marker = mMarker.get(i);
                    marker.setVisible(false);
                }
                showEtiq.setVisibility(View.VISIBLE);
                hideEtiq.setVisibility(View.INVISIBLE);
            }
        });

    }
    public void agafartresidencia(){
        agafartempscasa = true;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {
                if (agafartempscasa) {
                    mMap.clear();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(arg0);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));
                    markerOptions.title("Temps Residencia:");
                    callWeatherService(arg0, false, true);
                    markerOptions.snippet( evaluarIdWeather(idWeathercasa2));

                    Marker marker = mMap.addMarker(markerOptions);
                    marker.showInfoWindow();

                    agafartempscasa = false;
                }
            }
        });
    }
    private String evaluarIdWeather(int id){
        if(id == 200 || id == 201 || id == 202 || id == 210 ||
                id == 211 || id == 212 || id == 221  ||
                id == 230 || id == 231 || id == 232)
                {
            return "Tempesta";

        }

        if(id == 502 || id == 503 || id == 504 || id == 521 ||
                id == 522 ) {
            return "Pluja Forta";

        }
        if(id == 602 || id == 611 || id == 613 || id == 621 ||
                id == 622 ) {
            return "Neu Forta";

        }
        if((id == 781 )) {
            return "Tornado";

        }
        return "No hi ha perill";
    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);



                if(lastLocation!=null && lastLocation.latitude!=0 && lastLocation.longitude!=0 && !DIALOG_IS_SHOWING){
                    callWeatherService(lastLocation,false, false);
                }
            }
        }, delay);
        super.onResume();
    }


    private void callWeatherService(LatLng location,boolean fromButton,  boolean fromResidencia){
        String Latitude = String.valueOf(location.latitude);
        String Longitude = String.valueOf(location.longitude);

        RequestParams params = new RequestParams();
        params.put("lat", Latitude);
        params.put("lon", Longitude);
        params.put("units", "metric");
        params.put("appid", APP_ID);
        requestWeather(params,fromButton, fromResidencia);
    }

    private void getWeatherForCurrentLocation() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = location -> {

            LatLng l = new LatLng(location.getLatitude(), location.getLongitude());

            lastLocation = l;

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(l)      // Sets the center of the map to Mountain View
                    .zoom(12)                   // Sets the zoom
                    .bearing(270)                // Sets the orientation of the camera to west
                    .tilt(10)                   // Sets the tilt of the camera to x degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListener);

    }



    private void updateUI(JSONObject weather,boolean fromButton, boolean fromResidencia) {
        try {
            int idWeather = weather.getJSONArray("weather").getJSONObject(0).getInt("id");
            double temp = weather.getJSONObject("main").getDouble("temp");
            double speed = weather.getJSONObject("wind").getDouble("speed");

            if(fromButton){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setPositiveButton("Aceptar", (dialog, which) -> {

                });

                String msg = "El clima Actual:";
                msg+= "\nVelocidad del viento es: " +String.valueOf(speed)+" Km/h";
                msg+= "\nTemperatura es: "+temp+" Cº";

                builder.setMessage(msg)
                        .setTitle("Clima actual");

                AlertDialog dialog = builder.create();

                dialog.show();
                return;
            }
            if(fromResidencia){
                idWeathercasa2 = idWeather;
                return;

            }
            if((idWeather == 200 || idWeather == 201 || idWeather == 202 || idWeather == 210 ||
                    idWeather == 211 || idWeather == 212 || idWeather == 221  ||
                    idWeather == 230 || idWeather == 231 || idWeather == 232)
                    && !MapsActivity.DIALOG_IS_SHOWING){
                    mostrarTempesta();

            }

            if((idWeather == 502 || idWeather == 503 || idWeather == 504 || idWeather == 521 ||
                    idWeather == 522 )  && !MapsActivity.DIALOG_IS_SHOWING){
                plujaForta();

            }
            if((idWeather == 602 || idWeather == 611 || idWeather == 613 || idWeather == 621 ||
                    idWeather == 622 )  && !MapsActivity.DIALOG_IS_SHOWING){
                mostraNeuForta();

            }
            if((idWeather == 781 )&& !MapsActivity.DIALOG_IS_SHOWING) {
                mostraTornado();

            }


            /*
            Log.d("mapsActivity", "entro al if");

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.thunder_popup, null);

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height);

            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });

             */


        } catch (JSONException e) {
            Log.e("mapsActivity", e.toString());
            e.printStackTrace();
        }
    }

    private void mostrarTempesta() {
        MapsActivity.DIALOG_IS_SHOWING = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {

            MapsActivity.DIALOG_IS_SHOWING = false;

        });

        builder.setMessage("ALERTA TEMPESTA IMNINET!!")
                .setTitle("ALERTA");

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    private void plujaForta() {
        MapsActivity.DIALOG_IS_SHOWING = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {

            MapsActivity.DIALOG_IS_SHOWING = false;

        });

        builder.setMessage("ALERTA PLUJA FORTA IMNINET!!")
                .setTitle("ALERTA");

        AlertDialog dialog = builder.create();

        dialog.show();


    }

    private void mostraNeuForta() {
        MapsActivity.DIALOG_IS_SHOWING = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {

            MapsActivity.DIALOG_IS_SHOWING = false;

        });

        builder.setMessage("ALERTA NEVADES PERILLOSES IMNINETS!!")
                .setTitle("ALERTA");

        AlertDialog dialog = builder.create();

        dialog.show();


    }

    private void mostraTornado() {
        MapsActivity.DIALOG_IS_SHOWING = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {

            MapsActivity.DIALOG_IS_SHOWING = false;

        });

        builder.setMessage("TORNADO INMINENT! BUSQUI REFUGI")
                .setTitle("ALERTA");

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    private void mostraVent() {

    }

    private void mostrTemperaturaSotaZero() {

    }

    private void requestWeather(RequestParams params,boolean fromButton,  boolean fromResidencia) {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("WEATHER", response.toString());

                updateUI(response,fromButton, fromResidencia);
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("WEATHER", responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("WEATHER", errorResponse.toString());

                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }


}