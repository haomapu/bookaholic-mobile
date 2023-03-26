package com.example.bookaholic;

import static android.content.ContentValues.TAG;
import static com.example.bookaholic.MainActivity.firebaseUser;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseHelper {
    private static FirebaseDatabase database;
    private static FirebaseStorage storage;
    private static DatabaseReference currentUserRef, booksRef;

    public static void initCurrentUserDatabaseReference(ValueEventListener listener) {
        try {
            if (database == null) database = FirebaseDatabase.getInstance();
            currentUserRef = database.getReference("Users").child(firebaseUser.getUid());
            currentUserRef.addValueEventListener(listener);
        } catch (Exception exception) {
            Log.d(TAG, exception.toString());
        }
    }

    public static void initBooksDatabaseReference(ValueEventListener listener) {
        try {
            if (database == null) database = FirebaseDatabase.getInstance();
            booksRef = database.getReference("Cats");
            booksRef.addValueEventListener(listener);
        } catch (Exception exception) {
            Log.d(TAG, exception.toString());
        }
    }
}
