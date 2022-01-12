package com.app.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import android.widget.TextView;
import com.app.Managers.Client;
import com.google.android.material.navigation.NavigationView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.login.databinding.ActivityMenuprincipalBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Menuprincipal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuprincipalBinding binding;
    private String email, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        /*
        setContentView(R.layout.nav_header_menuprincipal);
        TextView tv = (TextView)findViewById(R.id.nomUsuari);
        tv.setText("Welcome to Tutlane");
*/
        Bundle informacion = this.getIntent().getExtras();
        email = informacion.getString("email");
        pass = informacion.getString("pass");




        /*setContentView(R.layout.nav_header_menuprincipal);
        TextView nomUsuari = (TextView) findViewById(R.id.nomUsuari);
        nomUsuari.setText("hola");*/

        binding = ActivityMenuprincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("pass", pass);
        editor.commit();

        setSupportActionBar(binding.appBarMenuprincipal.toolbar);
        binding.appBarMenuprincipal.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle informa = new Bundle();
                informa.putString("email", email);
                Intent in = new Intent(Menuprincipal.this, Notificacions.class);
                in.putExtras(informa);
                startActivity(in);
                Log.i("TAG", "email = " + email + " pass = " + pass);
            }
        });
        binding.appBarMenuprincipal.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle informa = new Bundle();
                informa.putString("email", email);
                Intent in = new Intent(Menuprincipal.this, chatApp.class);
                in.putExtras(informa);
                startActivity(in);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nomUsuari);
        TextView navUserEmail = (TextView) headerView.findViewById(R.id.emailUsuari);
        TextView navUserStreet = (TextView) headerView.findViewById(R.id.streetUsuari);
        TextView navUserCity = (TextView) headerView.findViewById(R.id.CityUsuari);
        TextView navUserState = (TextView) headerView.findViewById(R.id.stateUsuari);
        TextView navUserCP = (TextView) headerView.findViewById(R.id.CPUsuari);
        TextView navUserCountry = (TextView) headerView.findViewById(R.id.countryUsuari);
        TextView navUserBlood = (TextView) headerView.findViewById(R.id.BloodUsuari);


        JSONObject usuari = null;
        String nom = null;
        String street = "";
        String city = "";
        String CP = "";
        String country = "";
        String blood = "";
        String state = "";
        try {
            usuari = Client.getPerson(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            nom = usuari.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject usuariAI = null;

        try {
            if (Client.hasAdditionalInfo(email)) {
                usuariAI = Client.getAdditionalInfo(email);
                street = usuariAI.getString("street");
                city = usuariAI.getString("city");
                state = usuariAI.getString("state");
                CP = usuariAI.getString("postal_code");
                country = usuariAI.getString("country");
                blood = usuariAI.getString("blood_type");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        navUserEmail.setText(email);
        navUsername.setText(nom);
        navUserStreet.setText(getString(R.string.street)+street);
        navUserCity.setText(getString(R.string.city)+city);
        navUserState.setText(getString(R.string.cmoaut)+state);
        navUserCP.setText(getString(R.string.codipostal)+CP);
        navUserCountry.setText(getString(R.string.conutry)+country);
        navUserBlood.setText(getString(R.string.bloodType)+blood);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        View nav = navigationView.getHeaderView(0);
        TextView txtEmail = (TextView) nav.findViewById(R.id.tvEmail);
        txtEmail.setText(email);
        TextView nom = nav.findViewById(R.id.tvNom);
        nom.setText("");
        ImageView im = (ImageView) nav.findViewById(R.id.imageViewPersona);
        im.setImageResource(R.drawable.ic_perfil);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                //cambio del param2 nav_gallery a nav_perfil
                R.id.perfilFragment, R.id.principalFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menuprincipal);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuprincipal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menuprincipal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}