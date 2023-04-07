package com.example.bookaholic;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
    private ImageView returnBtn;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_view);
        returnBtn = findViewById(R.id.returnManage);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onBackPressed();
            }
        });
        initManageBook();
    }

    public void initManageBook(){
        ManageBookAdapter adapter = new ManageBookAdapter(ManageBook.this, Book.allBooks);
        recyclerView = findViewById(R.id.manageLayout);
        recyclerView.setAdapter(adapter);
    }
}
