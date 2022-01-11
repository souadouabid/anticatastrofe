package com.app.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.login.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Servei> arrayList;

    public CustomAdapter(Context context, ArrayList<Servei> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public  View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        }
        TextView id;
        TextView location;
        TextView name;
        TextView icon;
        TextView author;
        //TextView distance;
        id = (TextView) convertView.findViewById(R.id.id);
        location = (TextView) convertView.findViewById(R.id.location);
        name = (TextView) convertView.findViewById(R.id.name);
        icon = (TextView) convertView.findViewById(R.id.icon);
        author = (TextView) convertView.findViewById(R.id.author);
        //distance = (TextView) convertView.findViewById(R.id.distance);


        id.setText(arrayList.get(position).getId().toString());
        location.setText(arrayList.get(position).getLocation());
        name.setText(arrayList.get(position).getName());
        icon.setText(arrayList.get(position).getIcon());
        author.setText(arrayList.get(position).getAuthor());
        //Integer d = (Integer) arrayList.get(position).getDistance();
        //distance.setText(arrayList.get(position).getDistance().toString());

        return convertView;
    }
}
