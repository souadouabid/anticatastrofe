package com.app.mapas;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.Managers.Client;
import org.json.JSONException;
import java.io.IOException;
import com.app.inicio.Inicio;
import com.app.login.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MarkerActivity extends AppCompatActivity {


    private Float latitude;
    private Float longitude;
    private Integer id_m;
    private String email;
    private Spinner spinner;
    private static final String[] paths = {"Red", "Yellow", "Green", "Cian", "Blue", "Pink"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getFloat("lat");
            longitude = extras.getFloat("long");
            //The key argument here must match that used in the other activity
        }
        Toast.makeText(
                MarkerActivity.this,
                "Lat : " + latitude + " , "
                        + "Long : " + longitude,
                Toast.LENGTH_LONG).show();

        id_m = (int)(Math.random()*100000);
        id_m *= 10;
        Button ButtonCreaEtiq = (Button) findViewById(R.id.btn_crea_etiq);
        Button ButtonCancel = (Button) findViewById(R.id.btn_cancel);

        EditText TextTitol = (EditText) findViewById(R.id.titol);
        EditText TextDescripcio = (EditText) findViewById(R.id.desc);


        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MarkerActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(MarkerActivity.this, item.toString(),
                            Toast.LENGTH_SHORT).show();
                            id_m += position;
                }
                Toast.makeText(MarkerActivity.this, "Selected",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        ButtonCreaEtiq.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    String titol = TextTitol.getText().toString();
                    String desc = TextDescripcio.getText().toString();

                    try {
                        SharedPreferences sp=getSharedPreferences("key", Context.MODE_PRIVATE);
                        email = sp.getString("email", "android@gmail.com");

                        Client.createLandmarkPep(id_m, latitude, longitude, titol, desc, email);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(MarkerActivity.this, MapsActivity.class));
                    }
                }
        );


        ButtonCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MarkerActivity.this, MapsActivity.class));
            }
        });
    }

/*
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                break;
            case 1:
                id_m += 1;
                break;
            case 2:
                id_m += 2;
                break;
            case 3:
                id_m += 3;
                break;
            case 4:
                id_m += 4;

                break;
            case 5:
                id_m += 5;
                break;
            case 6:
                id_m += 6;
                break;
            case 7:
                id_m += 7;
                break;
            case 8:
                id_m += 8;
                break;

        }
    }  */




}


