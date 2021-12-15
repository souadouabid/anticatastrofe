package com.app.mapas;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.TextView;
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

import com.google.android.material.card.MaterialCardView;
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
    LatLng co;

    MaterialCardView selectCard;
    TextView tvMarkers;
    boolean [] selectedMarkers;
    ArrayList<Integer> markerList = new ArrayList<>();
    String[] markerArray = {"Antenes", "Refugis", "Personals"};

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

        selectCard = findViewById(R.id.selectCard);
        tvMarkers = findViewById(R.id.tvMarkers);
        selectedMarkers = new boolean[markerArray.length];

    }



    public void onMapReady(GoogleMap map) {
        mMap = map;

        agafartempscasa = false;
        System.out.println("ola1");

        try {
            landmarks = Client.getAllLandmarks();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject land = null;
        System.out.println("2");
        numMarkers = landmarks.length();

        for(int i = 0; i < numMarkers; ++i){
            try {        System.out.println("33");
                land = landmarks.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            double lat = 0;
            double lon = 0;
            String title = null;
            String desc = null;
            Integer id = 0;
            String tag = null;
            String email = null;
            System.out.println("44");

            try {
                System.out.println("55");
                System.out.println(land);

                assert land != null;
                System.out.println("66");

                lat = land.getDouble("coordinate_x");
                System.out.println("77");

            } catch (JSONException e) {
                e.printStackTrace();
            };
            System.out.println("88");

            try {
                lon = land.getDouble("coordinate_y");
            } catch (JSONException e) {
                e.printStackTrace();
            };

            try {
                title = land.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                desc = land.getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                id = land.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                tag = land.getString("tag");
                System.out.println(tag);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                email = land.getString("creator_email");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Float color = (((float)id % 10) * 60) % 360;
            Boolean marcat = false;
            assert tag != null;
            System.out.println("ola");

            if (tag.equals("antena")) {
                System.out.println("ola1antena");

                Marker mark = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(title)
                        .snippet(desc)
                        .visible(false)
                        .alpha(0.9999f)
                        .icon((BitmapDescriptorFactory.fromResource(R.drawable.antena)))

                );
                System.out.println("ola2");

                marcat = true;
                mMarker.add(mark);
                System.out.println("ola3");

            }


            if (!marcat & tag.equals("refugi")){
                Marker mark = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat,lon))
                        .title(title)
                        .snippet(desc)
                        .visible(false)
                        .alpha(0.9998f)
                        .icon((BitmapDescriptorFactory.fromResource(R.drawable.refugi)))
                );
                marcat = true;
                mMarker.add(mark);
            }

            if (!marcat & tag.equals(email)){
                Marker mark = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat,lon))
                        .title(title)
                        .snippet(desc)
                        .visible(false)
                        .alpha(0.9997f)
                        .icon(BitmapDescriptorFactory.defaultMarker(color))
                );
                marcat = true;
                mMarker.add(mark);
            }


        }



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


        selectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // .setVisible(true a tots els markers)
                showMarkersDialog();
            }
        });

    }

    private void showMarkersDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

        builder.setTitle("Selecciona Etiquetes");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(markerArray, selectedMarkers, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    markerList.add(which);
                }else{
                    markerList.remove(which);
                }
            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0; i < markerList.size(); ++i){
                    stringBuilder.append(markerArray[markerList.get(i)]);

                    if (i != markerList.size() -1) {
                        stringBuilder.append(", ");
                    }
                    tvMarkers.setText(stringBuilder.toString());
                }

                for(int i = 0; i < numMarkers; ++i){
                    Marker marker = mMarker.get(i);
                    if ((marker.getAlpha() == 0.9999f && selectedMarkers[0])
                            || (marker.getAlpha() == 0.9998f && selectedMarkers[1])
                            || (marker.getAlpha() == 0.9997f && selectedMarkers[2])) {
                        marker.setVisible(true);
                    } else{
                        marker.setVisible(false);
                    }
                }

            }

        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < selectedMarkers.length; ++i){
                    selectedMarkers[i]=false;
                    markerList.clear();
                    tvMarkers.setText("");
                }
            }
        });
        builder.show();


    }

    public void agafartresidencia(){
        agafartempscasa = true;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {
                co = arg0;
                if (agafartempscasa) {
                    mMap.clear();
                    callWeatherService(arg0, false, true);


                    agafartempscasa = false;
                }
            }
        });
    }
    private void crearmarkercasa(int id){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(co);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(co));
        markerOptions.title("Temps Residencia:");

        markerOptions.snippet( evaluarIdWeather(id));

        Marker marker = mMap.addMarker(markerOptions);
        marker.showInfoWindow();

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
        if( id == 501 || id == 511 || id == 520 ||
                id == 531 ) {
            return "Pluja Moderada";

        }
        if(id == 602 || id == 611 || id == 613 || id == 621 ||
                id == 622 ) {
            return "Neu Forta";

        }
        if(id == 600 || id == 601 || id == 612 || id == 615 ||
                id == 616 || id == 620 ) {
            return "Neu Moderada";

        }
        if((id == 781 )) {
            return "Tornado";

        }
        if((id == 800 )) {
            return "Cel clar, no hi ha perill";

        }
        if(id == 801 || id == 802
        ) {
            return "Pocs Nuvols";

        }
        if(id == 803
        ) {
            return "Nuvols moderats";

        }
        if(id == 804
        ) {
            return "Molts nuvols";

        }
        if(id == 300 || id == 301 || id == 302 || id == 310 ||
                id == 311 || id == 312 || id == 313  ||
                id == 314 || id == 321 || id == 500 )
        {
            return "Pluja Suau";

        }
        if(id == 701 || id == 711 || id == 721 || id == 731 || id == 741
        ) {
            return "Boira";

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
    private void updateUIbuttonresidencia(JSONObject weather,boolean fromButton, boolean fromResidencia) {

        try {
            int idWeather3 = weather.getJSONArray("weather").getJSONObject(0).getInt("id");
            double temp = weather.getJSONObject("main").getDouble("temp");
            double speed = weather.getJSONObject("wind").getDouble("speed");

            String b1;
            if(fromButton || fromResidencia ){

                idWeathercasa2 = idWeather3;
                crearmarkercasa(idWeather3);
                Log.d("weather4",String.valueOf(idWeathercasa2));

                String clima = evaluarIdWeather(idWeather3);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setPositiveButton("Aceptar", (dialog, which) -> {

                });
                if(fromButton){
                    b1 = "El clima Actual de la seva posició és: ";
                }
                else {
                    b1 = "El clima Actual de la seva residencia és: ";
                }

                String msg = clima ;
                msg+= "\nVelocidad del viento es: " +String.valueOf(speed)+" Km/h";
                msg+= "\nTemperatura es: "+temp+" Cº";

                builder.setMessage(msg)
                        .setTitle(b1);

                AlertDialog dialog = builder.create();

                dialog.show();
                return;
            }



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
                Log.d("WEATHER3", response.toString());
                if(fromButton || fromResidencia) {
                    updateUIbuttonresidencia(response, fromButton, fromResidencia);
                }
                else {
                    updateUI(response, fromButton, fromResidencia);
                }
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("WEATHER1", responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("WEATHER2", errorResponse.toString());

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