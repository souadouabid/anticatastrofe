package com.app.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMensaje extends RecyclerView.Adapter<AdapterMensaje.MensajeHolder> {
    private List<MensajeO> lstMensajes;

    public AdapterMensaje(List<MensajeO> lstMensajes) {
        this.lstMensajes = lstMensajes;
    }

    @NonNull
    @Override
    public MensajeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje, parent, false);
        return new MensajeHolder(mview);

    }

    @Override
    public void onBindViewHolder(@NonNull MensajeHolder holder, int i) {
        holder.nombre.setText(lstMensajes.get(i).getNombre());
        holder.mensaje.setText(lstMensajes.get(i).getMensaje());

    }

    @Override
    public int getItemCount() {
        return lstMensajes.size();
    }

    class MensajeHolder extends  RecyclerView.ViewHolder {
        private TextView nombre;
        private TextView mensaje;

        public MensajeHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.MensajeOwner);
            mensaje = itemView.findViewById(R.id.mensajeSend);

        }
    }
}
