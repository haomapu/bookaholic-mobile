package com.example.bookaholic;

import static android.content.ContentValues.TAG;
import static com.example.bookaholic.FirebaseHelper.initBooksDatabaseReference;
import static com.example.bookaholic.FirebaseHelper.initCurrentUserDatabaseReference;

import com.example.bookaholic.details.Book;
import com.example.bookaholic.details.Detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bookaholic.details.Detail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

interface UserDataChangedListener {
    void updateUserRelatedViews();
}

interface BooksDataChangedListener {
    void updateBooksRelatedViews();
}

public class MainActivity extends AppCompatActivity {

    public static FirebaseAuth firebaseAuth;
    public static FirebaseUser firebaseUser;
    public static User currentSyncedUser;

    private ImageButton buttonHome;
    private ImageButton buttonMap;
    private ImageButton buttonFavorite;
    private ImageButton buttonProfile;
    private Fragment fragmentMap, fragmentProfile;
    private ProductListFragment fragmentHome;

    private UserDataChangedListener userDataChangedListener;
    private BooksDataChangedListener booksDataChangedListener;
    public static UserDataChangedListener listenerForBookDetailsActivity, listenerForBookClassifyActivity;


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
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Detail.class);
                intent.putExtra("myKey", "Test");
                startActivity(intent);
            }
        });

        setUpDefaultFragment();
    }

    private void setUpDefaultFragment() {
        resetSelectedBottomNavbarButton();
        buttonHome.setImageResource(R.drawable.home_selected);
        userDataChangedListener = fragmentHome;
        booksDataChangedListener = fragmentHome;
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
                booksDataChangedListener = fragmentHome;
                switchFragment(R.id.fragmentcontainerMainActivity, fragmentHome);
            } else {
                Log.d(TAG, "Don't know which button clicked!");
            }
        }
    };

    private void resetSelectedBottomNavbarButton() {
        buttonHome.setImageResource(R.drawable.home_unselected);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!Tools.isOnline(this))
            startActivity(new Intent(this, NoInternetActivity.class));
        else {
//            firebaseUser = firebaseAuth.getCurrentUser();
//            if (firebaseUser == null) {
//                Intent signInSignUpIntent = new Intent(MainActivity.this, SignInSignUpActivity.class);
//                startActivity(signInSignUpIntent);
//            } else {
//                initCurrentUserDatabaseReference(currentUserDatabaseListener);
                initBooksDatabaseReference(booksDatabaseListener);
//            }
        }
    }

    private final ValueEventListener currentUserDatabaseListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.d(TAG, "Detected a data change of current user on the database.");
            currentSyncedUser = snapshot.getValue(User.class);
            userDataChangedListener.updateUserRelatedViews();
            if (listenerForBookDetailsActivity != null)
                listenerForBookDetailsActivity.updateUserRelatedViews();
            if (listenerForBookClassifyActivity != null)
                listenerForBookClassifyActivity.updateUserRelatedViews();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.d(TAG, error.toString());
        }
    };

    private final ValueEventListener booksDatabaseListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.d(TAG, "All cats database changed.");
            Book.allBooks = new ArrayList<>();
//            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                Book.allBooks.add(dataSnapshot.getValue(Book.class));
//            }
            booksDataChangedListener.updateBooksRelatedViews();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.d(TAG, error.toString());
        }
    };

    private void switchFragment(int fragmentContainerResourceId, Fragment fragmentObject) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainerResourceId, fragmentObject)
                .commit();
    }

}