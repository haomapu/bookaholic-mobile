package com.example.bookaholic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaholic.details.Book;

import java.util.ArrayList;
import java.util.List;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.BestSellerViewHolder> {
    private List<Book> listBooks;
    private Context context;
    public BestSellerAdapter(Context context, List<Book> listBooks) {
        this.context = context;
        this.setBooks((ArrayList<Book>) listBooks);
    }

    public void setBooks(ArrayList<Book> books) {
        this.listBooks = new ArrayList<>();
        for (Book book : books) {
            this.listBooks.add(book.deepCopy());
        }
    }

    @NonNull
    @Override
    public BestSellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.best_seller_layout , parent , false);
        return new BestSellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellerViewHolder holder, int position) {
        holder.mImageView.setImageResource(listBooks.get(position).getImageResId());
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    public class BestSellerViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        public BestSellerViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.clothing_image);
        }
    }
}
