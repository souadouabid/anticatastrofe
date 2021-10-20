package com.app.login;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.inicio.Inicio;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ButtonRegistrar = (Button) findViewById(R.id.buttonRegistrar);
        Button ButtonEntrar = (Button) findViewById(R.id.buttonEntrar);

        EditText TextLogin = (EditText) findViewById(R.id.editTextUser);
        EditText Textpassword = (EditText) findViewById(R.id.editTextPassword);

        ButtonEntrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(TextLogin.getText().toString().equals("user") && Textpassword.getText().toString().equals("firstuser")) {
                    startActivity(new Intent(MainActivity.this, Inicio.class));
                }
            }
        });


        ButtonRegistrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Registro.class));
            }
        });






        /*ButtonEntrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //muestra el resultado
                        Log.v("username", TextLogin.getText().toString());
                        Log.v("contraseña", Textpassword.getText().toString());

                    }
                }
        );*/
    }
}