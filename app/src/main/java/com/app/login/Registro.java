package com.app.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.Managers.Client;
import org.json.JSONException;
import java.io.IOException;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        Button ButtonRegistrarse = (Button) findViewById(R.id.buttonRegistrarse);

        EditText TextName = (EditText) findViewById(R.id.editTextNombre);
        EditText TextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText TextPhone = (EditText) findViewById(R.id.editTextTelefono);
        EditText TextPassword = (EditText) findViewById(R.id.editTextPassword);



        ButtonRegistrarse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    /*String name = TextName.getText().toString();
                    String email = TextEmail.getText().toString();
                    Integer phone = Integer.parseInt(TextPhone.getText().toString());
                    String password = TextPassword.getText().toString();

                    Client.CreateUser(name, phone, email, password);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();*/
                    startActivity(new Intent(Registro.this, MainActivity.class));
                }
            }
        });

    }
}