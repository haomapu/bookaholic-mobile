package com.example.bookaholic.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bookaholic.Comment;
import com.example.bookaholic.R;

import java.util.ArrayList;

public class Detail extends Activity {
    private ViewGroup imageListView;
    private ImageView imageSelected;
    private TextView descriptionTxt;
    private TextView titleTxt;

    private TextView priceTxt;

    private RatingBar ratingBar;

    private Book currentBook;
    GridView gridView;
    ListView commentListView;
    ArrayList<Book> itemList = new ArrayList<>();
//    Book book1 = new Book("Hao","Hao","Hao","Hao",R.drawable.img1,"Hao",100);
//    Book book2 = new Book("Haha","Hao","Hao","Hao",R.drawable.img2,"Hao",100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        loadData();

        initRecommend();
        initBasicInfo();
        initComment();
//        ImageAdapter imageAdapter = new ImageAdapter(this, R.layout.image_detail, images);

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
        ArrayList<String> images = currentBook.getImages();

        imageSelected.setImageResource(Integer.parseInt(images.get(0)));
        for (int i = 0; i < images.size(); i++){
            final View singleFrame = getLayoutInflater().inflate(R.layout.image_detail,null);
            singleFrame.setId(i);
            ImageView single_image = (ImageView) singleFrame.findViewById(R.id.single_image);
            single_image.setImageResource(Integer.parseInt(images.get(i)));

            imageListView.addView(singleFrame);

            singleFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageSelected.setImageResource(Integer.parseInt(images.get(singleFrame.getId())));
                }
            });
        }
    }
    private void loadData() {
        Intent data = getIntent();
        Bundle bundle = data.getExtras();
        currentBook = Book.findBookByTitle(bundle.getString("bookName"));
        Log.e("TAG", currentBook.getAuthor());
        //Set data to screen

    }

    public void initBasicInfo(){
        titleTxt = findViewById(R.id.titleTxt);
        priceTxt = findViewById(R.id.priceTxt);
        ratingBar = findViewById(R.id.ratingBar);
        descriptionTxt = findViewById(R.id.descriptionTxt);

        titleTxt.setText(currentBook.getTitle());
        priceTxt.setText(currentBook.getPrice().toString());
        ratingBar.setRating(currentBook.getRateAvg());
        descriptionTxt.setText(currentBook.getDescription());
        descriptionTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTextView(descriptionTxt);
            }
        });
    }
    ArrayList<Comment> comments = new ArrayList<>();

    public void initComment(){
        ArrayList<Comment> reviews = new ArrayList<>();
        reviews.add(new Comment("Great book, highly recommended", "John Smith", R.drawable.avatar1, 4));
        reviews.add(new Comment( "Loved it!", "Jane Doe",R.drawable.avatar2 , 5));
        reviews.add(new Comment("Not my cup of tea", "Mike Johnson",R.drawable.avatar3 , 2));
        reviews.add(new Comment("Hihi", "Hao",R.drawable.avatar4 , 4));

// Create the adapter and set it to the ListView
        ReviewAdapter reviewAdapter = new ReviewAdapter(this, R.layout.review_item, reviews);
        commentListView = findViewById(R.id.commentListView);
        commentListView.setAdapter(reviewAdapter);
    }

    public void initRecommend(){
        comments.add(new Comment("Good book", "Hao", R.drawable.img1, 5));
//        Book book1 = new Book("test", "test", "test", "test", "test",comments , 100, R.drawable.img1);
//        itemList.add(book1);
//        itemList.add(book1);
//        itemList.add(book1);
//        itemList.add(book1);
//        itemList.add(new Book("test2", "test", "test", "test", "test",comments , 100, R.drawable.img2));
//        itemList.add(new Book("test3", "test", "test", "test", "test",comments , 100, R.drawable.img3));
//        itemList.add(new Book("test4", "test", "test", "test", "test",comments , 100, R.drawable.img4));
        gridView = findViewById(R.id.gridview);
        GridAdapter adapter = new GridAdapter(this, itemList);
        gridView.setAdapter(adapter);
    }

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
