package com.example.bookaholic.details;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.bookaholic.R;

public class Detail extends Activity {
    private ViewGroup imageListView;
    private ImageView imageSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageDetail[] images = {
                new ImageDetail(R.drawable.img1),
                new ImageDetail(R.drawable.img2),
                new ImageDetail(R.drawable.img3),
                new ImageDetail(R.drawable.img4)};

        ImageAdapter imageAdapter = new ImageAdapter(this, R.layout.image_detail, images);

        imageListView = findViewById(R.id.imageListView);
        imageSelected = findViewById(R.id.imageSelected);
//        imageListView.setAdapter(imageAdapter);
//
//        imageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                imageSelected.setImageResource(images[position].getImageID());
//            }
//        });

        for (int i = 0; i < images.length; i++){
            final View singleFrame = getLayoutInflater().inflate(R.layout.image_detail,null);
            singleFrame.setId(i);
            ImageView single_image = (ImageView) singleFrame.findViewById(R.id.single_image);
            single_image.setImageResource(images[i].getImageID());

            imageListView.addView(singleFrame);

            singleFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageSelected.setImageResource(images[singleFrame.getId()].getImageID());
                }
            });
        }

        }
}