package com.app.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.Managers.Client;
import com.app.mapas.MapsActivity;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button buttonLogOut, buttonBaixa;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        buttonBaixa = view.findViewById(R.id.buttonBaixa);
        buttonLogOut = view.findViewById(R.id.buttonLogOut);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });
        SharedPreferences prefs = getActivity().getSharedPreferences("Preferences", 0);
        String correo = prefs.getString("email", "");
        String pass = prefs.getString("pass", "");
        Log.i("TAG", "email = "+ correo + " pass = " + pass);
        buttonBaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    builder.setTitle("Confirmació");
                    builder.setMessage("¡¿Segur que vols donar-te de baixa?!");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Client.deleteUser(correo, pass);
                                        Intent intent2 = new Intent();
                                        intent2.setClass(getActivity(), MainActivity.class);
                                        getActivity().startActivity(intent2);
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
        return view;
    }
}