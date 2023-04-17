package com.example.bookaholic.details;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
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

    private float userRate = 0;
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

        rateNowBtn.setOnClickListener(v -> {
            String content = contentReview.getText().toString();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Books")
                    .child(book.getId()).child("comments");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists() && dataSnapshot.hasChildren()){
                        Comment newComment = new Comment(content, currentSyncedUser.getFullName(), currentSyncedUser.getAvatar(), ratingBar.getNumStars());
                        ArrayList<Comment> comments = new ArrayList<>();
                        for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                            Comment comment = commentSnapshot.getValue(Comment.class);
                            comments.add(comment);
                        }
                        comments.add(0, newComment);

                        myRef.setValue(comments);
                    } else {
                        Comment newComment = new Comment(content, currentSyncedUser.getFullName(), currentSyncedUser.getAvatar(), ratingBar.getNumStars());
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

//        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
//            if (rating <= 1) {
//                ratingImage.setImageResource(R.drawable.ic_1_star);
//            } else if (rating <= 2) {
//                ratingImage.setImageResource(R.drawable.ic_2_star);
//            } else if (rating <= 3) {
//                ratingImage.setImageResource(R.drawable.ic_3_star);
//            } else if (rating <= 4) {
//                ratingImage.setImageResource(R.drawable.ic_4_star);
//            } else {
//                ratingImage.setImageResource(R.drawable.ic_5_star);
//            }
//
//            animateImage(ratingImage);
//            userRate = rating;
//        });
    }

    private void animateImage(ImageView ratingImage) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }
}