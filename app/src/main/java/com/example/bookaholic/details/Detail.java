package com.example.bookaholic.details;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bookaholic.R;

import java.util.ArrayList;
import java.util.List;

public class Detail extends Activity {
    private ViewGroup imageListView;
    private ImageView imageSelected;
    private TextView descriptionTxt;
    GridView gridView;
    ImageDetail[] images = {
            new ImageDetail(R.drawable.img1),
            new ImageDetail(R.drawable.img2),
            new ImageDetail(R.drawable.img3),new ImageDetail(R.drawable.img3),new ImageDetail(R.drawable.img3),new ImageDetail(R.drawable.img3),
            new ImageDetail(R.drawable.img4)};
    ArrayList<Book> itemList = new ArrayList<>();
//    Book book1 = new Book("Hao","Hao","Hao","Hao",R.drawable.img1,"Hao",100);
//    Book book2 = new Book("Haha","Hao","Hao","Hao",R.drawable.img2,"Hao",100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        initRecommend();
        initDescription();
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
        imageSelected.setImageResource(images[0].getImageID());
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

    public void initDescription(){
        descriptionTxt = findViewById(R.id.descriptionTxt);
        descriptionTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTextView(descriptionTxt);
            }
        });
    }

//    public void initRecommend(){
//        itemList.add(book1);
//        itemList.add(book2);
//        gridView = findViewById(R.id.gridview);
//        GridAdapter adapter = new GridAdapter(this, itemList);
//        System.out.println(itemList.get(1).getTitle());
//        gridView.setAdapter(adapter);
//    }

    private void toggleTextView(TextView textView) {
        if (textView.getMaxLines() == 4) {
            textView.setMaxLines(Integer.MAX_VALUE);
            textView.setEllipsize(null);
        } else {
            textView.setMaxLines(4);
            textView.setEllipsize(TextUtils.TruncateAt.END);
        }
    }
}
