package com.example.bookaholic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileAcitivity extends AppCompatActivity {
    private ImageView profilePictureImageView;
    private TextView userNameTextView;
    private TextView userEmailTextView;
    private TextView userBioTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acitivity);

        profilePictureImageView = findViewById(R.id.profile_picture);
        userNameTextView = findViewById(R.id.user_name);
        userEmailTextView = findViewById(R.id.user_email);
    }
}