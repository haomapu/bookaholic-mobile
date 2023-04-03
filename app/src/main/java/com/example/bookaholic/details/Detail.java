package com.example.bookaholic.details;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookaholic.Comment;
import com.example.bookaholic.R;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {
    private ViewGroup imageListView;
    private ImageView imageSelected;
    private TextView descriptionTxt;
    private ImageView returnBtn;
    private TextView titleTxt;

    private TextView priceTxt;

    private RatingBar ratingBar;
    private Button addToCartButton;
    private NotificationBadge shopping_badge;
    private Book currentBook;
    FragmentTransaction fragmentTransaction;

    RecyclerView gridView;
    RecyclerView commentListView;
    int countCart = 0;
    ArrayList<Book> itemList = new ArrayList<>();
//    Book book1 = new Book("Hao","Hao","Hao","Hao",R.drawable.img1,"Hao",100);
//    Book book2 = new Book("Haha","Hao","Hao","Hao",R.drawable.img2,"Hao",100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        loadData();
        initAddToCartButton();
        initRecommend();
        initBasicInfo();
        initShowButton();
        initComment();
        initFavorite();
        initBookDetail();
        returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imageListView = findViewById(R.id.imageListView);
        imageSelected = findViewById(R.id.imageSelected);

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
    public void initFavorite(){
        ImageView imageViewHeart = findViewById(R.id.image_view_heart);
        imageViewHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (true){
                    imageViewHeart.setColorFilter(ContextCompat.getColor(Detail.this, R.color.black), PorterDuff.Mode.SRC_IN);
                }
                else {
                    imageViewHeart.setColorFilter(ContextCompat.getColor(Detail.this, R.color.red), PorterDuff.Mode.SRC_IN);
                }
            }
        });
    }
    public void initBasicInfo(){
        titleTxt = findViewById(R.id.titleTxt);
        priceTxt = findViewById(R.id.priceTxt);
        ratingBar = findViewById(R.id.ratingBar);
        descriptionTxt = findViewById(R.id.descriptionTxt);

        titleTxt.setText(currentBook.getTitle());
        priceTxt.setText(currentBook.getDisplayablePrice());
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
        for (int i = 0; i < currentBook.getComments().size() && i < 2; i++){
            comments.add(currentBook.getComments().get(i));
        }
        ReviewAdapter adapter = new ReviewAdapter(comments);

        commentListView = findViewById(R.id.commentListView);
        commentListView.setAdapter(adapter);
    }

    public void initRecommend(){
        gridView = findViewById(R.id.gridview);
        GridAdapter adapter = new GridAdapter(this, Book.allBooks);
        gridView.setAdapter(adapter);
    }

    public void initAddToCartButton(){
        addToCartButton = findViewById(R.id.addToCartButton);
        shopping_badge = findViewById(R.id.shopping_badge);

        addToCartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                shopping_badge.setNumber(++countCart);
            }
        });
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
    private void initBookDetail(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DetailFragment detailFragment = DetailFragment.newInstance("J.K Rowling", "Novel", "12/12/2003", "Hardcover", "17x25 cm", "549 pages");

        fragmentTransaction.replace(R.id.fragmentBookDetail, detailFragment);
        fragmentTransaction.commit();
    }

    private void initShowButton(){
        Button showBottomSheetButton = findViewById(R.id.show_bottom_sheet_button);
        Button showBookDetail = findViewById(R.id.showBookDetail);
        showBottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance(currentBook.getComments(), 1);
                bottomSheetFragment.show(getSupportFragmentManager(), BottomSheetFragment.TAG);
            }

        });
        showBookDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance("J.K Rowling", "Novel", "12/12/2003", "Hardcover", "17x25 cm", "549 pages", 2);
                bottomSheetFragment.show(getSupportFragmentManager(), BottomSheetFragment.TAG);
            }

        });

    }
}
