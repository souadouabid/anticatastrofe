package com.app.login;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.app.Managers.Client;
import com.app.register.Registro;
import org.json.JSONException;
import java.io.IOException;
import com.app.inicio.Inicio;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
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
        ImageButton ayuda = (ImageButton) findViewById(R.id.imageButtonHelp);



        ButtonEntrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (Client.userPasswordMatch(TextLogin.getText().toString(),Textpassword.getText().toString())){
                                Bundle informa = new Bundle();
                                informa.putString("email", TextLogin.getText().toString());
                                informa.putString("pass", Textpassword.getText().toString());

                                SharedPreferences sp=getSharedPreferences("key", Context.MODE_PRIVATE);
                                SharedPreferences.Editor ed=sp.edit();
                                ed.putString("email", TextLogin.getText().toString());
                                ed.commit();

                                Intent i = new Intent(MainActivity.this, Menuprincipal.class);
                                i.putExtras(informa);
                                startActivity(i);
                               // startActivity(new Intent(MainActivity.this, Menuprincipal.class));
                            }
                            else {
                                Snackbar mySnackbar = Snackbar.make(view, "Wrong email or password", 1600);
                                mySnackbar.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {

                        }

                    }
                }
        );


        ButtonRegistrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Registro.class));
            }
        });

        ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, popupPassword.class));
            }
        });
    }


}