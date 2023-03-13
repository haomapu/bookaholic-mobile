package com.example.bookaholic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookaholic.details.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private ArrayList<Book> books;
    private Context context;

    public BookAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.setBooks(books);
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = new ArrayList<>();
        for (Book book : books) {
            this.books.add(book.deepCopy());
        }
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameView, priceView, typesView;
        private ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_book_home_itemview);
            nameView = itemView.findViewById(R.id.textview_bookname_home_itemview);
            priceView = itemView.findViewById(R.id.textview_bookprice_home_itemview);
            typesView = itemView.findViewById(R.id.textview_booktype_home_itemview);
            layout = itemView.findViewById(R.id.layout_book_home_itemview);
        }
    }
}
