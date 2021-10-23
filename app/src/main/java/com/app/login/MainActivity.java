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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ButtonRegistrar = (Button) findViewById(R.id.buttonRegistrar);
        Button ButtonEntrar = (Button) findViewById(R.id.buttonEntrar);

        EditText TextLogin = (EditText) findViewById(R.id.editTextUser);
        EditText Textpassword = (EditText) findViewById(R.id.editTextPassword);

        ButtonEntrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (Textpassword.getText().toString().equals(Client.getUserPassword(TextLogin.getText().toString()))){
                                    startActivity(new Intent(MainActivity.this, Menuprincipal.class));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );


        ButtonRegistrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Registro.class));
            }
        });
    }
}