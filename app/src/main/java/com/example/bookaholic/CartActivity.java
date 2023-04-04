package com.example.bookaholic;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaholic.details.Book;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView mCartRecyclerView;
    private CartAdapter mCartAdapter;
    private TextView mTotalPriceTextView;
    private ArrayList<OrderBook> mBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Comment comment = new Comment("Good book", "Hao", R.drawable.img1,5);
        ArrayList<Comment> mComment = new ArrayList<>();
        mComment.add(comment);

        // Initialize the book list with some sample books
        mBookList = Order.currentOrder.getOrderBooks();

        // Get a reference to the RecyclerView
        mCartRecyclerView = findViewById(R.id.cartRecyclerView);
        mTotalPriceTextView = findViewById(R.id.totalPriceTextView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCartRecyclerView.setLayoutManager(layoutManager);

        // Create an instance of the CartAdapter class and pass the book list to its constructor
        mCartAdapter = new CartAdapter(mBookList, this);

        // Set the CartAdapter instance to the RecyclerView
        mCartRecyclerView.setAdapter(mCartAdapter);

        // Calculate the total price
        int totalPrice = 0;
        for (OrderBook orderBook : mBookList) {
            totalPrice += orderBook.book.getPrice();
        }
        mTotalPriceTextView.setText("Total: " + totalPrice + " VNĐ");

    }
}
