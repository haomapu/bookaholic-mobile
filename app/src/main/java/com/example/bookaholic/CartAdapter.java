package com.example.bookaholic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookaholic.details.Book;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Book> mBookList;

    public CartAdapter(List<Book> bookList) {
        mBookList = bookList;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public ImageView bookImageView;
        public TextView bookNameTextView;
        public TextView bookQuantityTextView;
        public TextView bookPriceTextView;

        public CartViewHolder(View itemView) {
            super(itemView);
            bookImageView = itemView.findViewById(R.id.bookImageView);
            bookNameTextView = itemView.findViewById(R.id.bookNameTextView);
            bookQuantityTextView = itemView.findViewById(R.id.bookQuantityTextView);
            bookPriceTextView = itemView.findViewById(R.id.bookPriceTextView);
        }
    }



    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        Book book = mBookList.get(position);

//        holder.bookImageView.setImageResource(book.getImageResourceId());
//        holder.bookNameTextView.setText(book.getName());
//        holder.bookQuantityTextView.setText("Quantity: " + book.getQuantity());
//        holder.bookPriceTextView.setText("$" + book.getPrice());

        holder.bookNameTextView.setText("Default");
        holder.bookQuantityTextView.setText("5");
        holder.bookPriceTextView.setText("20");
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}
