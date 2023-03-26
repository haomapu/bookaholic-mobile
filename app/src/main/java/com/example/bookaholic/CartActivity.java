package com.example.bookaholic;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaholic.details.Book;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView mCartRecyclerView;
    private CartAdapter mCartAdapter;
    private List<Book> mBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize the book list with some sample books
        mBookList = new ArrayList<>();
//        mBookList.add(new Book("Book 1", R.drawable.book1, 1, 10));
//        mBookList.add(new Book("Book 2", R.drawable.book2, 2, 20));
//        mBookList.add(new Book("Book 3", R.drawable.book3, 3, 30));

        // Get a reference to the RecyclerView
        mCartRecyclerView = findViewById(R.id.cartRecyclerView);

        // Create an instance of the CartAdapter class and pass the book list to its constructor
        mCartAdapter = new CartAdapter(mBookList);

        // Set the CartAdapter instance to the RecyclerView
        mCartRecyclerView.setAdapter(mCartAdapter);
    }
}
