package com.example.bookaholic.details;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Rating;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;


import com.example.bookaholic.Comment;
import static com.example.bookaholic.MainActivity.currentSyncedUser;
import com.example.bookaholic.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Review extends Dialog {

    private final float userRate = 0;
    private Book book;

    public Review(@NonNull Context context) {
        super(context);
    }

    public Review(@NonNull Context context, Book book) {
        super(context);
        this.book = book;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.review_dialog);

        final AppCompatButton rateNowBtn = findViewById(R.id.rateNowBtn);
        final AppCompatButton laterBtn = findViewById(R.id.laterBtn);
        final RatingBar ratingBar = findViewById(R.id.ratingBar);
        final EditText contentReview = findViewById(R.id.content);
        final TextView review1 = findViewById(R.id.review1);
        final TextView review2 = findViewById(R.id.review2);
        final TextView review3 = findViewById(R.id.review3);
        final TextView review4= findViewById(R.id.review4);
        review1.setOnClickListener(v -> {
            review1.setBackgroundResource(R.drawable.ic_cat_bg_2);
            review1.setTextColor(Color.WHITE);
            review2.setTextColor(Color.BLACK);
            review3.setTextColor(Color.BLACK);
            review4.setTextColor(Color.BLACK);

            review2.setBackgroundResource(R.drawable.ic_cat_bg);
            review3.setBackgroundResource(R.drawable.ic_cat_bg);
            review4.setBackgroundResource(R.drawable.ic_cat_bg);
            contentReview.setText(review1.getText());
            ratingBar.setRating(5);
        });
        review2.setOnClickListener(v -> {
            review2.setBackgroundResource(R.drawable.ic_cat_bg_2);
            review1.setBackgroundResource(R.drawable.ic_cat_bg);
            review3.setBackgroundResource(R.drawable.ic_cat_bg);
            review4.setBackgroundResource(R.drawable.ic_cat_bg);
            review2.setTextColor(Color.WHITE);
            review1.setTextColor(Color.BLACK);
            review3.setTextColor(Color.BLACK);
            review4.setTextColor(Color.BLACK);
            contentReview.setText(review2.getText());
            ratingBar.setRating(4);
        });
        review3.setOnClickListener(v -> {
            review3.setBackgroundResource(R.drawable.ic_cat_bg_2);
            review3.setTextColor(Color.WHITE);
            review2.setTextColor(Color.BLACK);
            review1.setTextColor(Color.BLACK);
            review4.setTextColor(Color.BLACK);
            review2.setBackgroundResource(R.drawable.ic_cat_bg);
            review1.setBackgroundResource(R.drawable.ic_cat_bg);
            review4.setBackgroundResource(R.drawable.ic_cat_bg);
            contentReview.setText(review3.getText());
            ratingBar.setRating(3);
        });
        review4.setOnClickListener(v -> {
            review4.setBackgroundResource(R.drawable.ic_cat_bg_2);
            review4.setTextColor(Color.WHITE);
            review2.setTextColor(Color.BLACK);
            review3.setTextColor(Color.BLACK);
            review1.setTextColor(Color.BLACK);

            review2.setBackgroundResource(R.drawable.ic_cat_bg);
            review3.setBackgroundResource(R.drawable.ic_cat_bg);
            review1.setBackgroundResource(R.drawable.ic_cat_bg);
            contentReview.setText(review4.getText());
            ratingBar.setRating(2);
        });

        rateNowBtn.setOnClickListener(v -> {
            String content = contentReview.getText().toString();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Books")
                    .child(book.getId()).child("comments");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int sum = 0;
                    if(dataSnapshot.exists() && dataSnapshot.hasChildren()){
                        Comment newComment = new Comment(content, currentSyncedUser.getFullName(), currentSyncedUser.getAvatar(), (int) ratingBar.getRating());
                        ArrayList<Comment> comments = new ArrayList<>();
                        for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                            Comment comment = commentSnapshot.getValue(Comment.class);
                            if (comment.getName().equalsIgnoreCase(newComment.getName())){
                                sum++;
                            }
                            if (sum >= 2){
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Error");
                                builder.setMessage("You can review this book 2 times");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        onBackPressed();
                                    }
                                });
                                builder.show();
                                return;
                            }
                            comments.add(comment);
                        }
                        comments.add(0, newComment);

                        myRef.setValue(comments);
                    } else {
                        Comment newComment = new Comment(content, currentSyncedUser.getFullName(), currentSyncedUser.getAvatar(), (int) ratingBar.getRating());
                        ArrayList<Comment> comments = new ArrayList<>();
                        comments.add(0, newComment);
                        myRef.setValue(comments);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors here
                }
            });

            dismiss();
        });

        laterBtn.setOnClickListener(v -> dismiss());

    }

    private void animateImage(ImageView ratingImage) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }
}