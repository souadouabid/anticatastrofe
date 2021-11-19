package com.app.mapas;
import androidx.appcompat.app.AppCompatActivity;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        Button ButtonCreaEtiq = (Button) findViewById(R.id.btn_crea_etiq);
        Button ButtonCancel = (Button) findViewById(R.id.btn_cancel);

        EditText TextTitol = (EditText) findViewById(R.id.titol);
        EditText TextDescripcio = (EditText) findViewById(R.id.desc);




        ButtonCreaEtiq.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    int id = (int)(Math.random()*100000);
                    id *= 10;

                    String titol = TextTitol.getText().toString();
                    String desc = TextDescripcio.getText().toString();

                    try {
                        Client.createLandmarkPep(id, latitude, longitude, titol, desc);
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


}