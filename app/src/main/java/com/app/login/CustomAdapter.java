package com.app.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Notificacion> lst;

    public CustomAdapter(Context context, List<Notificacion> lst) {
        this.context = context;
        this.lst = lst;
    }

    public void setLst(List<Notificacion> lst) {
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView ImageVievNoti;
        TextView TextViewNombre;
        TextView TextViewDescripcion;

        Notificacion n = lst.get(i);

        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.listview_notificacion, null);

       ImageVievNoti = view.findViewById(R.id.imageViewNotificacion);
       TextViewNombre = view.findViewById(R.id.MensajeOwner);
       TextViewDescripcion = view.findViewById(R.id.mensajeSend);

       ImageVievNoti.setImageResource(n.imagen);
       TextViewNombre.setText(n.nombre);
       TextViewDescripcion.setText(n.des);

        return view;
    }
}
