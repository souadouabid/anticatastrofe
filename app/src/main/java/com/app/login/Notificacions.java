package com.app.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Notificacions extends AppCompatActivity {
    ListView ListViewNoti;
    List<Notificacion> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacions);

        ListViewNoti=findViewById(R.id.ListViewNotificacion);

        CustomAdapter adapter = new CustomAdapter(this,GetData());
        ListViewNoti.setAdapter(adapter);

        ListViewNoti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Notificacion n = lst.get(i);
                Toast.makeText(getBaseContext(), n.nombre, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private List<Notificacion> GetData() {
        /*EXEMPLE DE NOTIFICACIONES.
        *
        * FALTA OBTENER LOS DATOS DEL CLIENT (no implemntado aun) I MOSTRARLOS (un bucle para leer los datos)
        * */
        lst = new ArrayList<>();
        lst.add(new Notificacion(1,R.drawable.notificacion, "Alerta Riesgo", "Esta en zona de riesgo"));
        lst.add(new Notificacion(2,R.drawable.notificacion, "Alerta Zona NO cobertura", "Esta en zona de no cobertura"));
        lst.add(new Notificacion(3,R.drawable.notificacion, "Alerta Zona NO cobertura", "Esta en zona de no cobertura"));
        lst.add(new Notificacion(4,R.drawable.notificacion, "Alerta Riesgo", "Esta en zona de riesgo"));
        lst.add(new Notificacion(5,R.drawable.notificacion, "Alerta Zona NO cobertura", "Esta en zona de no cobertura"));
        lst.add(new Notificacion(6,R.drawable.notificacion, "Alerta Riesgo", "Esta en zona de riesgo"));
        lst.add(new Notificacion(7,R.drawable.notificacion, "Alerta Riesgo", "Esta en zona de riesgo"));
        lst.add(new Notificacion(8,R.drawable.notificacion, "Alerta Zona NO cobertura", "Esta en zona de no cobertura"));
        lst.add(new Notificacion(9,R.drawable.notificacion, "Alerta Riesgo", "Esta en zona de riesgo"));
        lst.add(new Notificacion(10,R.drawable.notificacion, "Alerta Riesgo", "Esta en zona de riesgo"));
        lst.add(new Notificacion(11,R.drawable.notificacion, "Alerta Zona NO cobertura", "Esta en zona de no cobertura"));
        lst.add(new Notificacion(12,R.drawable.notificacion, "Alerta Zona NO cobertura", "Esta en zona de no cobertura"));

        return lst;

    }
}