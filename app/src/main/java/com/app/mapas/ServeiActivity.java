package com.app.mapas;

import static java.lang.Double.parseDouble;
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
    Double distance;
    String photo;

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

                        double search_distance = 0;
                        boolean distance_exist = false;
                        if (!dist.equals("")){
                            search_distance = Integer.parseInt(dist);
                            distance_exist = true;
                        }

                        int search_quantity = 0;
                        boolean quantity_exist = false;
                        if (!quant.equals("")){
                            search_quantity = Integer.parseInt(dist);
                            quantity_exist = true;
                        }

                        JSONObject json_parameters = new JSONObject();

                        if (!tag.equals("")) {
                            try {
                                json_parameters.put("tags", tag);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (distance_exist) {
                            try {
                                json_parameters.put("distance",search_distance);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (quantity_exist) {
                            try {
                                json_parameters.put("quantity",search_quantity);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            json_parameters.put("location",location_user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            serveis = Client.doGetRequestS(json_parameters);
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

                            JSONArray photos = null;
                            try {
                                photos = servei.getJSONArray("photos");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (photos.length()>0) {
                                try {
                                    photo = (String) photos.get(0);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                            //location est?? amb el format "41,946591;2,2422739"
                            //hem de separar-ho per ";" i canviar les ',' per '.'
                            // i finalment calculem la dist??ncia de les coordenades del servei amb les nostres

                            location = location.replace(',', '.');
                            String[] tokens=location.split(";");
                            double lat = parseDouble(tokens[0]);
                            double lon = parseDouble(tokens[1]);
                            distance = MapsActivity.distance(lat, lon, latitude, longitude);
                            distance /= 1000;
                            distance = Math.round(distance * 100d)/100d;

                            location = location.replace(';', ',');

                            Servei s = new Servei();
                            s.setId(id);
                            s.setName(name);
                            s.setAuthor(author);
                            s.setIcon(icon);
                            s.setDistance(distance.toString());
                            s.setPhoto(photo);
                            s.setLocation(location);
                            arrayList.add(s);
                        }
                        set_adapter();
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
        CustomAdapter adapter = new CustomAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }

}