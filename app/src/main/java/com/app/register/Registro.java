package com.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.Managers.Client;
import org.json.JSONException;
import java.io.IOException;

import com.app.Managers.Validators;
import com.app.login.Menuprincipal;
import com.app.login.R;
import com.app.login.popupCampsBuits;
import com.app.login.popupPassword;
import com.google.android.material.snackbar.Snackbar;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        Button ButtonRegistrarse = (Button) findViewById(R.id.buttonRegistrarse);

        EditText TextName = (EditText) findViewById(R.id.editTextNombre);
        EditText TextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText TextPhone = (EditText) findViewById(R.id.editTextTelefono);
        EditText TextPassword = (EditText) findViewById(R.id.editTextTextPassword1);
        EditText TextPassRepetida = (EditText) findViewById(R.id.editTextTextPassword2);


        ButtonRegistrarse.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (TextName.getText().toString().isEmpty() || TextEmail.getText().toString().isEmpty() ||
                                TextPhone.getText().toString().isEmpty() || TextPassword.getText().toString().isEmpty()
                                || TextPassRepetida.getText().toString().isEmpty()) {

                            startActivity(new Intent(Registro.this, popupCampsBuits.class));
                        }
                        else if (!TextPassword.getText().toString().equals(TextPassRepetida.getText().toString())) {
                            startActivity(new Intent(Registro.this, popupPassword.class));
                        }
                        else if ((Validators.validateEmail(TextEmail.getText().toString()))) {
                            Snackbar mySnackbar = Snackbar.make(view, "Wrong email format", 1600);
                            mySnackbar.show();
                        }

                        else if (Validators.validatePhone(TextPhone.getText().toString())) {
                            Snackbar mySnackbar = Snackbar.make(view, "Wrong phone format", 1600);
                            mySnackbar.show();
                        }
                        /*else if (Validators.userExists(TextEmail.getText().toString())) {
                            Snackbar mySnackbar = Snackbar.make(view, "Email already in use", 1600);
                            mySnackbar.show();
                        }*/
                        else {
                            try {
                                String name = TextName.getText().toString();
                                Integer phone = Integer.parseInt(TextPhone.getText().toString());
                                String email = TextEmail.getText().toString();
                                String password= TextPassword.getText().toString();
                                int aux = Client.CreateUser(name, phone, email, password);
                                if (aux == 208) {
                                    throw new Exception("user_already_registered");
                                }
                                Bundle informa = new Bundle();
                                informa.putString("email", TextEmail.getText().toString());
                                informa.putString("pass", TextPassword.getText().toString());

                                Intent i = new Intent(Registro.this, Menuprincipal.class);
                                i.putExtras(informa);
                                startActivity(i);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                Snackbar mySnackbar = Snackbar.make(view, "Email already in use", 1600);
                                mySnackbar.show();
                            }
                        }
                    }
                }
        );
    }
}