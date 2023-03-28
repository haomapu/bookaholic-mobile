package com.example.bookaholic.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookaholic.Comment;
import com.example.bookaholic.R;

import java.util.ArrayList;

public class ReviewAdapter extends ArrayAdapter<Comment> {
    private Context mContext;
    private int mResource;

    public ReviewAdapter(Context context, int resource, ArrayList<Comment> reviews) {
        super(context, resource, reviews);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the review for this position
        Comment review = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        // Get references to the views in the list item layout
        ImageView avatar = convertView.findViewById(R.id.avatarItem);
        TextView name = convertView.findViewById(R.id.nameItem);
        TextView content;
        content = convertView.findViewById(R.id.contentItem);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBar);

        // Set the values for the views using the review data
        avatar.setImageResource(review.getAvatar());
        name.setText(review.getName());
        content.setText(review.getContent());
        ratingBar.setRating(review.getRating());

        // Return the completed view to render on screen
        return convertView;
    }
}
