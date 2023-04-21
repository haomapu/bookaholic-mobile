package com.example.bookaholic;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookaholic.details.Book;
import com.example.bookaholic.details.Detail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecentlyAddAdapter extends RecyclerView.Adapter<RecentlyAddAdapter.RecentlyAddViewHolder> {

    private List<Book> listBooks;
    private final Context context;

    public RecentlyAddAdapter(Context context, List<Book> listBooks) {
        this.context = context;
        this.setBooks((ArrayList<Book>) listBooks);
    }

    public void setBooks(ArrayList<Book> books) {
        this.listBooks = new ArrayList<>();
        for (Book book : books ) {
            this.listBooks.add(book.deepCopy());
        }
    }
    @NonNull
    @Override
    public RecentlyAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recently_add_layout, parent, false);
        return new RecentlyAddViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyAddViewHolder holder, int position) {
        Book book = listBooks.get(position);
        Glide.with(context)
                .load(book.getImages().get(0))
                .into(holder.mImageView);
        holder.layout.setOnClickListener(v -> startBookDetailActivity(book));
        holder.nameView.setText(book.getTitle());
        holder.buyerView.setText(book.getBuyer().toString());
        holder.price.setText(book.displayablePrice());
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    public class RecentlyAddViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageView;
        private final ConstraintLayout layout;
        private final TextView nameView;
        private final TextView buyerView;
        private final TextView price;


        public RecentlyAddViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_recently_add_itemview);
            mImageView = itemView.findViewById(R.id.recentlyadd_image);
            nameView = itemView.findViewById(R.id.recently_add_name);
            buyerView = itemView.findViewById(R.id.textview_bookbuyer);
            price = itemView.findViewById(R.id.recentlyadd_price);
        }
    }

    private void startBookDetailActivity(Book book) {
        try {
            Intent intent = new Intent(context, Detail.class);
            Bundle bundle = new Bundle();
            bundle.putString("bookName", book.getTitle());
            intent.putExtras(bundle);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterByDate() {
        this.listBooks.clear();
        ArrayList<Book> books = Book.allBooks;
        Collections.sort(books, Comparator.comparing(Book::getDate).reversed());
        for (int i = 0; i < Math.min(Book.allBooks.size(), 5); i++) {
            this.listBooks.add(books.get(i));
        }
        notifyDataSetChanged();

    }
}