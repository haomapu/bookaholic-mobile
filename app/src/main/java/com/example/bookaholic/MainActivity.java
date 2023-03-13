package com.example.bookaholic;

import static android.content.ContentValues.TAG;

import com.example.bookaholic.details.Detail;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

interface UserDataChangedListener {
    void updateUserRelatedViews();
}

interface CatsDataChangedListener {
    void updateCatsRelatedViews();
}

public class MainActivity extends AppCompatActivity {

    private ImageButton buttonHome;
    private ImageButton buttonMap;
    private ImageButton buttonFavorite;
    private ImageButton buttonProfile;
    private ImageButton buttonMic;
    private Fragment fragmentMap, fragmentProfile;
    private ProductListFragment fragmentHome;
    private UserDataChangedListener userDataChangedListener;
    private CatsDataChangedListener catsDataChangedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentHome = new ProductListFragment();
        buttonHome = findViewById(R.id.bottomNavBarButtonHome);
        buttonMap = findViewById(R.id.bottomNavBarButtonMap);
        ImageButton buttonCamera = findViewById(R.id.bottomNavBarButtonCamera);
        buttonFavorite = findViewById(R.id.bottomNavBarButtonFavorite);
        buttonProfile = findViewById(R.id.bottomNavBarButtonProfile);

        buttonHome.setOnClickListener(onBottomNavBarButtonClicked);
        buttonMap.setOnClickListener(onBottomNavBarButtonClicked);
        buttonFavorite.setOnClickListener(onBottomNavBarButtonClicked);
        buttonProfile.setOnClickListener(onBottomNavBarButtonClicked);

        setUpDefaultFragment();
    }

    private void setUpDefaultFragment() {
        resetSelectedBottomNavbarButton();
        buttonHome.setImageResource(R.drawable.home_selected);
        userDataChangedListener = fragmentHome;
        catsDataChangedListener = fragmentHome;
        switchFragment(R.id.fragmentcontainerMainActivity, fragmentHome);
    }

    private final View.OnClickListener onBottomNavBarButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            resetSelectedBottomNavbarButton();
            int viewId = view.getId();
            if (viewId == R.id.bottomNavBarButtonHome) {
                Log.d(TAG, "Home button clicked!");
                buttonHome.setImageResource(R.drawable.home_selected);
                userDataChangedListener = fragmentHome;
                catsDataChangedListener = fragmentHome;
                switchFragment(R.id.fragmentcontainerMainActivity, fragmentHome);
            } else {
                Log.d(TAG, "Don't know which button clicked!");
            }
        }
    };

    private void resetSelectedBottomNavbarButton() {
        buttonHome.setImageResource(R.drawable.home_unselected);
    }

    private void switchFragment(int fragmentContainerResourceId, Fragment fragmentObject) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainerResourceId, fragmentObject)
                .commit();
    }

}