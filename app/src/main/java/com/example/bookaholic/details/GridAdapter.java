package com.example.bookaholic.details;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookaholic.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Book> mItems;
    TextView textView;
    ImageView imageView;
    public GridAdapter(Context context, ArrayList<Book> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View view = inflater.inflate(R.layout.grid_item, null);

        Book item = mItems.get(position);
        imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(item.getImageResId());

        textView = view.findViewById(R.id.textView);
        Log.d("TAG", item.getTitle());
        textView.setText(item.getTitle());

        return view;
    }
}
