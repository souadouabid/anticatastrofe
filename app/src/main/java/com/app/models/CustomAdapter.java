package com.app.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        TextView name;
        ImageView icon;
        TextView author;
        TextView distance;
        TextView location;
        ImageView photo;

        id = (TextView) convertView.findViewById(R.id.id);
        name = (TextView) convertView.findViewById(R.id.name);
        icon = (ImageView) convertView.findViewById(R.id.icon);
        author = (TextView) convertView.findViewById(R.id.author);
        distance = (TextView) convertView.findViewById(R.id.distance);
        location = (TextView) convertView.findViewById(R.id.location);
        photo = (ImageView) convertView.findViewById(R.id.photo);


        id.setText(arrayList.get(position).getId().toString());
        name.setText(arrayList.get(position).getName());
        author.setText(arrayList.get(position).getAuthor());
        distance.setText(arrayList.get(position).getDistance());
        location.setText(arrayList.get(position).getLocation());

        String imageString = arrayList.get(position).getIcon();
        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        icon.setImageBitmap(decodedImage);

        imageString = arrayList.get(position).getPhoto();
        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        photo.setImageBitmap(decodedImage);

        return convertView;
    }
}
