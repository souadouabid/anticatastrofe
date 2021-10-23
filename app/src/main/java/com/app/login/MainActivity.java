package com.app.login;

import androidx.appcompat.app.AppCompatActivity;
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

import com.app.Managers.Client;
import com.app.inicio.Inicio;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ButtonRegistrar = (Button) findViewById(R.id.buttonRegistrar);
        Button ButtonEntrar = (Button) findViewById(R.id.buttonEntrar);

        EditText TextLogin = (EditText) findViewById(R.id.editTextUser);
        EditText Textpassword = (EditText) findViewById(R.id.editTextPassword);

        //maps



        ButtonEntrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextLogin.getText().toString().equals("user") && Client.getUserPassword(Textpassword.getText().toString()).equals("firstuser")) {
                                startActivity(new Intent(MainActivity.this, Menuprincipal.class));
                        }

                    }
                }
        );


        ButtonRegistrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Registro.class));
            }
        });
    }


}