package com.app.mapas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.app.Managers.Client;
import com.app.login.R;
import com.app.models.CustomAdapter;
import com.app.models.Servei;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ServeiActivity extends AppCompatActivity {


    private Double latitude;
    private Double longitude;
    private String location_user;
    private JSONArray serveis;
    private int num_serveis;

    ArrayList<Servei> arrayList;
    ListView listView;

    Integer id;
    String location;
    String name;
    String icon;
    String author;
    String distance;
    String[] photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servei);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getDouble("lat");
            longitude = extras.getDouble("long");
            location_user = latitude.toString() + ";" + longitude.toString();
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

                        double search_distance = Integer.parseInt(dist);
                        int search_quantity = Integer.parseInt(quant);

                        try {
                            serveis = Client.doGetRequestS(location_user, tag, search_quantity, search_distance);
                            System.out.println("serveisss");
                            System.out.println(serveis);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        JSONObject servei = null;
                        num_serveis = serveis.length();
                        arrayList = new ArrayList<>();
                        listView= (ListView) findViewById(R.id.listView);
                        for (int i = 0; i < num_serveis; ++i) {
                            try {
                                servei = serveis.getJSONObject(i);
                                System.out.println(servei);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                id = servei.getInt("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                location = servei.getString("location");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                name = servei.getString("name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                icon = servei.getString("icon");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                author = servei.getString("author");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            /*try {
                                distance = servei.getString("distance");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                             */
                            float distance = 0;
                            try {
                                distance = Float.parseFloat(servei.getString("distance"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println(id);
                            System.out.println(location);
                            System.out.println(name);
                            System.out.println(icon);
                            System.out.println(author);

                            //System.out.println(distance);
                            //distance = (int)distance;
                            //System.out.println(distance);
                            Servei s = new Servei();
                            s.setId(id);
                            s.setLocation(location);
                            s.setName(name);
                            s.setIcon(icon);
                            s.setAuthor(author);
                            //s.setDistance(((int) distance));
                            arrayList.add(s);
                            System.out.println("ola1");

                        }
                        System.out.println("ola2");

                        set_adapter();
                        System.out.println("ola3");

                    }
                }
        );

        ButtonCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ServeiActivity.this, MapsActivity.class));
            }
        });
    }

    private void set_adapter() {
        System.out.println("ola21");

        CustomAdapter adapter = new CustomAdapter(this, arrayList);
        System.out.println("ola22");
        System.out.println(arrayList);

        listView.setAdapter(adapter);
        System.out.println("ola23");

    }

}