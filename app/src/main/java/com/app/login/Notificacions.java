package com.app.login;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.Managers.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Notificacions extends AppCompatActivity {
    ListView ListViewNoti;
    private List<Notificacion> lst;
    private CustomAdapter adapter;
    private String email;
    JSONArray jsonNotis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacions);

        ListViewNoti=findViewById(R.id.ListViewNotificacion);

        adapter = new CustomAdapter(this,GetData());
        ListViewNoti.setAdapter(adapter);

        ListViewNoti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Notificacion n = lst.get(i);
                Bundle info = getIntent().getExtras();
                email = info.getString("email");

                /*Comprobacion ver si pasan los datos correctamente entre activities*/
                Log.i("TAG", "email = "+ email);
                /**/
                AlertDialog.Builder builder = new AlertDialog.Builder(Notificacions.this);
                builder.setCancelable(true);
                builder.setTitle("Confirmació");
                builder.setMessage("¡¿Segur que vols eliminar la notificació?!");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Client.deleteUserNotification(email, n.id);
                                    lst.remove(i);
                                    adapter.setLst(lst);
                                    adapter.notifyDataSetChanged();

                                    Toast.makeText(getBaseContext(), "notication deleted",
                                            Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }


    private List<Notificacion> GetData(){
        /*EXEMPLE DE NOTIFICACIONES. para comprobar si se borran!!
        */
        lst = new ArrayList<>();
        lst.add(new Notificacion(1,R.drawable.notificacion, "Alerta Riesgo", "NOTIFICACION a eliminar"));
        lst.add(new Notificacion(2,R.drawable.notificacion, "Alerta Zona NO cobertura", "Esta en zona de no cobertura"));
        lst.add(new Notificacion(3,R.drawable.notificacion, "Alerta Zona NO cobertura", "Esta en zona de no cobertura"));
        /* */

        /*Lectura de notificacion obtenidas desde el Client*/

        try {
            jsonNotis = Client.getUserNotifications(email);
            for (int i = 0; i < jsonNotis.length(); i++) {
                try {
                    JSONObject jsonObject = jsonNotis.getJSONObject(i);
                    int id = (int) jsonObject.get("id");
                    String title = (String) jsonObject.get("tag");
                    String desc = (String) jsonObject.get("description");

                    //add notificacion
                    lst.add(new Notificacion(id,R.drawable.notificacion, title, desc));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }
}