package com.app.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.Managers.Client;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.Managers.Validators;
import com.google.android.material.snackbar.Snackbar;

public class EditProfile extends AppCompatActivity {

    private Spinner spinnerSang, spinnerCA;
    private String ComAut, Sang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);

        Bundle informacion = this.getIntent().getExtras();
        String email = informacion.getString("email");

        spinnerSang= (Spinner) findViewById(R.id.SpinnerSang);
        spinnerCA= (Spinner) findViewById(R.id.SpinnerCA);

        EditText TextCarrer = (EditText) findViewById(R.id.editTextCarrer);
        EditText TextCiutat = (EditText) findViewById(R.id.editTextCiutat);
        EditText TextCP = (EditText) findViewById(R.id.editTextCP);
        EditText TextPais = (EditText) findViewById(R.id.editTextPais);
        EditText TextDataN = (EditText) findViewById(R.id.editTextTextDataN);

        ArrayList<String> tipusSang = new ArrayList<>();
        tipusSang.add(getString(R.string.blood_type));
        tipusSang.add("A+");
        tipusSang.add("A-");
        tipusSang.add("B+");
        tipusSang.add("B-");
        tipusSang.add("O+");
        tipusSang.add("O-");
        tipusSang.add("AB+");
        tipusSang.add("AB-");

        ArrayList<String> CA = new ArrayList<>();
        CA.add(getString(R.string.comunitat_autonoma));
        CA.add(getString(R.string.Andalusia));
        CA.add(getString(R.string.arago));
        CA.add(getString(R.string.asturies));
        CA.add(getString(R.string.cantabria));
        CA.add(getString(R.string.castella_lleo));
        CA.add(getString(R.string.castella_manxa));
        CA.add(getString(R.string.cat));
        CA.add(getString(R.string.ceuta));
        CA.add(getString(R.string.madird));
        CA.add(getString(R.string.extremadura));
        CA.add(getString(R.string.galicia));
        CA.add(getString(R.string.balears));
        CA.add(getString(R.string.canaries));
        CA.add(getString(R.string.melilla));
        CA.add(getString(R.string.murcia));
        CA.add(getString(R.string.navarra));
        CA.add(getString(R.string.pais_basc));
        CA.add(getString(R.string.valencia));
        CA.add(getString(R.string.rioja));
        System.out.println(CA);

        ArrayAdapter<String> adp = new ArrayAdapter<>(EditProfile.this, android.R.layout.simple_spinner_dropdown_item, tipusSang);
        spinnerSang.setAdapter(adp);
        spinnerSang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String element= (String) spinnerSang.getAdapter().getItem(position);
                Sang = element;
                Toast.makeText(EditProfile.this, getString(R.string.u_have_selected)+element,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adp2 = new ArrayAdapter<>(EditProfile.this, android.R.layout.simple_spinner_dropdown_item, CA);
        spinnerCA.setAdapter(adp2);
        spinnerCA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String element= (String) spinnerCA.getAdapter().getItem(position);
                ComAut = element;
                Toast.makeText(EditProfile.this, getString(R.string.u_have_selected)+element,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button ButtonUpdate = (Button) findViewById(R.id.buttonUpdate);




        ButtonUpdate.setOnClickListener(
                view -> {
                    if (TextCarrer.getText().toString().isEmpty() || TextCP.getText().toString().isEmpty() ||
                            TextCiutat.getText().toString().isEmpty() || TextPais.getText().toString().isEmpty()
                            || TextDataN.getText().toString().isEmpty() || Sang.equals(getString(R.string.blood_type)) || ComAut.equals(getString(R.string.comunitat_autonoma))) {

                        startActivity(new Intent(EditProfile.this, popupCampsBuits.class));
                    }

                    else {
                        try {
                            String carrer = TextCarrer.getText().toString();
                            String ciutat = TextCiutat.getText().toString();
                            String CP = TextCP.getText().toString();
                            String pais= TextPais.getText().toString();
                            String dataN = TextDataN.getText().toString();
                            Client.createAdditionalInfo(carrer, ciutat, ComAut, CP, pais, Sang, dataN, email);

                            Intent i = new Intent(EditProfile.this, PerfilFragment.class);
                            startActivity(i);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
}