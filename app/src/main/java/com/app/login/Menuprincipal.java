package com.app.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.login.databinding.ActivityMenuprincipalBinding;
import com.google.android.material.navigation.NavigationView;

public class Menuprincipal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuprincipalBinding binding;
    private String email, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle informacion = this.getIntent().getExtras();
        email = informacion.getString("email");
        pass = informacion.getString("pass");

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
                //startActivity(new Intent(Menuprincipal.this, Notificacions.class));
                /*
               Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Log.i("TAG", "email = "+ email + " pass = " + pass);
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
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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