package com.example.bookaholic;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookaholic.details.Book;
import com.example.bookaholic.details.ReviewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageBook extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Book> books;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_view);
        initManageBook();
    }

    public void initManageBook(){
        ManageBookAdapter adapter = new ManageBookAdapter(ManageBook.this, Book.allBooks);
        recyclerView = findViewById(R.id.manageLayout);
        recyclerView.setAdapter(adapter);
    }
}
