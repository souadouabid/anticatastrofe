package com.app.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class chatApp extends AppCompatActivity {
    private RecyclerView rvMensajes;
    private EditText etMensaje;
    private ImageButton btnSend;

    private List<MensajeO> lstMensajes;
    private AdapterMensaje mAdapter;
    private void setComponents() {
        rvMensajes = findViewById(R.id.viewMensajes);
        etMensaje = findViewById(R.id.TextMensaje);
        btnSend = findViewById(R.id.btnSend);

        lstMensajes = new ArrayList<>();
        mAdapter = new AdapterMensaje(lstMensajes);
        rvMensajes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMensajes.setAdapter(mAdapter);
        rvMensajes.setHasFixedSize(true);

        /*
        * CONECTAR FIREBASE
        */
        FirebaseFirestore.getInstance().collection("Chat")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange mDocumentChange : queryDocumentSnapshots.getDocumentChanges()){
                            if(mDocumentChange.getType() == DocumentChange.Type.ADDED){
                                lstMensajes.add(mDocumentChange.getDocument().toObject(MensajeO.class));
                                mAdapter.notifyDataSetChanged();
                                rvMensajes.smoothScrollToPosition(lstMensajes.size());
                            }
                        }
                    }
                });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etMensaje.length() == 0) return;
                MensajeO mensajeO = new MensajeO();
                mensajeO.setMensaje(etMensaje.getText().toString());
                mensajeO.setNombre("nombre");/////cambiar !!
                FirebaseFirestore.getInstance().collection("Chat").add(mensajeO);
                etMensaje.setText("");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_app);
        setComponents();

    }
}