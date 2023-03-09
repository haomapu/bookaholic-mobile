package com.example.bookaholic.details;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookaholic.R;

public class ImageAdapter extends ArrayAdapter<ImageDetail> {
    private final Context context;
    private final ImageDetail[] users;

    public ImageAdapter(Context context, int layoutToBeInflated, ImageDetail[] users) {
        super(context, R.layout.image_detail, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.length;
    }

    @Override
    public ImageDetail getItem(int position) {
        return users[position];
    }

    /*@Override
    public long getItemId(int position) {
        return position;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.image_detail, null);

        ImageView singleImage = (ImageView) view.findViewById(R.id.single_image);

        singleImage.setImageResource(users[position].getImageID());
        return view;
    }
}
