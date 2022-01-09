package com.app.mapas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.app.Managers.Client;
import com.app.login.R;
import org.json.JSONArray;


public class ServeiActivity extends AppCompatActivity {


    private Double latitude;
    private Double longitude;
    private String location;
    private JSONArray serveis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servei);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getDouble("lat");
            longitude = extras.getDouble("long");
            location = latitude.toString() + ";" + longitude.toString();
            //The key argument here must match that used in the other activity
        }
        Toast.makeText(
                ServeiActivity.this,
                "Lat : " + latitude + " , "
                        + "Long : " + longitude,
                Toast.LENGTH_LONG).show();


        Button ButtonBuscaServei = (Button) findViewById(R.id.btn_busca_servei);
        Button ButtonCancel = (Button) findViewById(R.id.btn_cancel);

        EditText TextTag = (EditText) findViewById(R.id.tag);
        EditText TextDistance = (EditText) findViewById(R.id.distance);
        EditText TextQuantity = (EditText) findViewById(R.id.quantity);

        ButtonBuscaServei.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String tag = TextTag.getText().toString();
                        String dist = TextDistance.getText().toString();
                        String quant = TextQuantity.getText().toString();

                        double distance = Integer.parseInt(dist);
                        int quantity = Integer.parseInt(quant);

                        try {
                            serveis = Client.doGetRequestS(location, tag, quantity, distance);
                            System.out.println("serveisss");
                            System.out.println(serveis);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        ButtonCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ServeiActivity.this, MapsActivity.class));
            }
        });
    }
}